package ru.kstu.aec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseTestController {

    @GetMapping("/course-test")
    public String courseTest() {
        return "course-test";
    }
}
