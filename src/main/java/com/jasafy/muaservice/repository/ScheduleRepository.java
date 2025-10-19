package com.jasafy.muaservice.repository;

import com.jasafy.muaservice.model.ScheduleTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleTemplate, Long> {


    @Query("select s from ScheduleTemplate s where s.id = ?1 and s.deleted = ?2")
    Optional<ScheduleTemplate> findByIdAndDeleted(Long id, Boolean deleted);

    @Query("select s from ScheduleTemplate s where s.profiles.id = ?1 and s.available = ?2 and s.deleted = ?3")
    List<ScheduleTemplate> findByProfiles_IdAndAvailableAndDeleted(Long id, Boolean available, Boolean deleted);

}
