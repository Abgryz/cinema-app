package com.suitt.tables.jobTittle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTittleRepository extends JpaRepository<JobTittle, String> {
}
