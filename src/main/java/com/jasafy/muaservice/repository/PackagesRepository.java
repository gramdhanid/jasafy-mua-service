package com.jasafy.muaservice.repository;

import com.jasafy.muaservice.model.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackagesRepository extends JpaRepository<Packages, Long> {

    @Query("select p from Packages p where p.id = ?1 and p.profiles.username = ?2 and p.deleted = ?3 and p.active = ?4")
    Optional<Packages> findByIdAndProfiles_UsernameAndDeletedAndActive(Long id, String username, Boolean deleted, Boolean active);

    @Query("select p from Packages p where p.profiles.username = ?1 and p.deleted = ?2 and p.active = ?3")
    List<Packages> findByProfiles_UsernameAndDeletedAndActive(String username, Boolean deleted, Boolean active);

    @Query("select p from Packages p where p.id = ?1 and p.deleted = ?2 and p.active = ?3")
    Optional<Packages> findByIdAndDeletedAndActive(Long id, Boolean deleted, Boolean active);


}
