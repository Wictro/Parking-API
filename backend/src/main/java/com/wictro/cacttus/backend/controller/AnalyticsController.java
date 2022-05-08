package com.wictro.cacttus.backend.controller;

import com.wictro.cacttus.backend.dto.http.GenericJsonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    @GetMapping
    public GenericJsonResponse<?> getAllAnalytics(){
        return null;
    }
}
