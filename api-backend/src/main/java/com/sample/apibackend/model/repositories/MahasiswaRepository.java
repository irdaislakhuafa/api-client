package com.sample.apibackend.model.repositories;

import com.sample.apibackend.model.entities.MahasiswaEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MahasiswaRepository extends JpaRepository<MahasiswaEntity, Long> {
    MahasiswaEntity findByNpm(String npm);

    Boolean existsByNpm(String npm);
}
