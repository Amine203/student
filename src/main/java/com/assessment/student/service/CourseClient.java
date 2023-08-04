package com.assessment.student.service;


import com.assessment.student.entity.Course;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CourseClient {

    private final WebClient webClient;

    @Value("${course.api.key}")
    private  String apiKey;

    public CourseClient(WebClient.Builder webClientBuilder, @Value("${course.api.url}") String courseApiUrl) {
        this.webClient = webClientBuilder.baseUrl(courseApiUrl).build();

    }

    public List<Course> getAllCourses() {
        return webClient.get()
                .uri("/courses")
                .header("API-Key",apiKey)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> {
                            return Mono.error(new RuntimeException("Client error: " + response.statusCode()));
                        }
                )
                .onStatus(

                        status -> status.is5xxServerError(),
                        response -> {
                            return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
                        }
                )
                .bodyToMono(new ParameterizedTypeReference<List<Course>>() {})
                .block();
    }
    public Course findCourseById(Long id) {
        return webClient.get()
                .uri("/courses/"+id)
                .header("API-Key",apiKey)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError(),
                        response -> {
                            return Mono.error(new RuntimeException("Client error: " + response.statusCode()));
                        }
                )
                .onStatus(

                        status -> status.is5xxServerError(),
                        response -> {
                            return Mono.error(new RuntimeException("Server error: " + response.statusCode()));
                        }
                )
                .bodyToMono(Course.class)
                .block();
    }
}
