package com.thecodealchemist.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HiController {
    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    public HiController(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String hi(){
        discoveryClient.getServices().forEach(System.out::println);
        discoveryClient.getInstances("consulClient").forEach(serviceInstance -> {
                System.out.println(serviceInstance.getPort());
            System.out.println(serviceInstance.getServiceId());
        });
        return "Hi";
    }
    @GetMapping("/call-student")
    public String callStudentService() {
        return restTemplate.getForObject("http://studentService/students", String.class);
    }
}
