package com.freelance.platform.controller;

import com.freelance.platform.domain.enums.ApplicationStatus;
import com.freelance.platform.dto.response.ApplicationResponse;
import com.freelance.platform.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> apply(@RequestParam Long jobId,
                                                     @RequestParam Long freelancerId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.apply(jobId, freelancerId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>> getByJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getByJob(jobId));
    }

    @GetMapping("/freelancer/{freelancerId}")
    public ResponseEntity<List<ApplicationResponse>> getByFreelancer(@PathVariable Long freelancerId) {
        return ResponseEntity.ok(applicationService.getByFreelancer(freelancerId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(@PathVariable Long id,
                                                            @RequestParam ApplicationStatus status) {
        return ResponseEntity.ok(applicationService.updateStatus(id, status));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}