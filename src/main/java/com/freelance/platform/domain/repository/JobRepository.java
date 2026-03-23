package com.freelance.platform.domain.repository;

import com.freelance.platform.domain.enums.JobStatus;
import com.freelance.platform.domain.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByEmployerId(Long employerId);

    List<Job> findByStatus(JobStatus status);

    List<Job> findByStatusOrderByCreated(JobStatus status);
}