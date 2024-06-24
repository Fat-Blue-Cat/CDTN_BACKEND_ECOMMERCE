package org.example.ecommerceweb.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.ecommerceweb.service.AnalyticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/analytic")
public class AnalyticController {
    private final AnalyticService analyticService;

    @GetMapping("/")
    public ResponseEntity<?> getAnalytic(){
        try {
            return ResponseEntity.ok().body(analyticService.getAnalytic());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
