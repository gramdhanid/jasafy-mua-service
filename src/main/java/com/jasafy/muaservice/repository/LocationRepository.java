package com.jasafy.muaservice.repository;

import com.jasafy.muaservice.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {


    @Query("select l from Location l where l.profiles.id = ?1 and l.deleted = ?2")
    List<Location> findByProfiles_IdAndDeleted(Long id, Boolean deleted);
}
