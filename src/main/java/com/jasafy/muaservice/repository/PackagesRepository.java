package com.jasafy.muaservice.repository;

import com.jasafy.muaservice.model.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackagesRepository extends JpaRepository<Packages, Long> {
}
