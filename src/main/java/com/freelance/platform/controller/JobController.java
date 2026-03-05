package com.freelance.platform.controller;

import com.freelance.platform.domain.enums.JobStatus;
import com.freelance.platform.dto.request.JobRequest;
import com.freelance.platform.dto.response.JobResponse;
import com.freelance.platform.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobResponse> create(@Valid @RequestBody JobRequest request, @RequestParam Long employerId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.create(request, employerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getById(id));
    }

    @GetMapping("/open")
    public ResponseEntity<List<JobResponse>> getAllOpen() {
        return ResponseEntity.ok(jobService.getAllOpen());
    }

    @GetMapping("/employer/{employerId}")
    public ResponseEntity<List<JobResponse>> getByEmployer(@PathVariable Long employerId) {
        return ResponseEntity.ok(jobService.getByEmployer(employerId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<JobResponse> updateStatus(@PathVariable Long id,
                                                    @RequestParam String status) { // Mude de JobStatus para String
        return ResponseEntity.ok(jobService.updateStatus(id, JobStatus.valueOf(status.toUpperCase())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jobService.delete(id);
        return ResponseEntity.noContent().build();
    }

}