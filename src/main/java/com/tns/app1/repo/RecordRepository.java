package com.tns.app1.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tns.app1.model.RecordEntity;

public interface RecordRepository extends JpaRepository<RecordEntity, Long> {

}
