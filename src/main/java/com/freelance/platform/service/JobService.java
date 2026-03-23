package com.freelance.platform.service;

import com.freelance.platform.domain.enums.JobStatus;
import com.freelance.platform.domain.model.Job;
import com.freelance.platform.domain.model.User;
import com.freelance.platform.domain.repository.JobRepository;
import com.freelance.platform.dto.request.JobRequest;
import com.freelance.platform.dto.response.JobResponse;
import com.freelance.platform.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final UserService userService;

    public JobService(JobRepository jobRepository, UserService userService) {
        this.jobRepository = jobRepository;
        this.userService = userService;
    }

    @Transactional
    public JobResponse create(JobRequest request, Long employerId) {
        User employer = userService.getEntityById(employerId);

        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .budget(request.getBudget())
                .status(JobStatus.OPEN)
                .employer(employer)
                .build();

        job = jobRepository.save(job);

        return toResponse(job);
    }

    public Job getEntityById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job com o ID: " + id + "não encontrado"));
    }

    public JobResponse findById(Long id) {
        return toResponse(getEntityById(id));
    }

    public List<JobResponse> findAllOpen() {
        return jobRepository.findByStatusOrderByCreated(JobStatus.OPEN)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<JobResponse> findByEmployer(Long employerId) {
        return jobRepository.findByEmployerId(employerId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public JobResponse updateStatus(Long id, JobStatus status) {
        Job job = getEntityById(id);
        job.setStatus(status);
        job = jobRepository.save(job);
        return toResponse(job);
    }

    private JobResponse toResponse(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .status(job.getStatus())
                .budget(job.getBudget())
                .employerId(job.getEmployer().getId())
                .employerName(job.getEmployer().getName())
                .createdAt(job.getCreatedAt())
                .applicationCount(job.getApplications() != null ? job.getApplications().size() : 0)
                .build();
    }

    @Transactional
    public void delete(Long id) {
        Job job = getEntityById(id);

        jobRepository.delete(job);
    }

}