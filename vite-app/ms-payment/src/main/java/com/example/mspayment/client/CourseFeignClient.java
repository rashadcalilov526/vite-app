package com.example.mspayment.client;//package com.example.mspayment.client;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import java.util.List;
//
//@FeignClient(value = "ms-ingress", url = "http://localhost:8081/courses")
//public interface CourseFeignClient {
//
//    @GetMapping("/name/{id}")
//    String getCourseNameById(@PathVariable Long id);
//}