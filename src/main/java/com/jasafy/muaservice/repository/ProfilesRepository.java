package com.jasafy.muaservice.repository;

import com.jasafy.muaservice.model.Profiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilesRepository extends JpaRepository<Profiles, Long> {

    @Query("select p from Profiles p where p.id = ?1 and p.deleted = ?2 and p.active = ?3")
    Optional<Profiles> findByIdAndDeletedAndActive(Long id, Boolean deleted, Boolean active);

    @Query("""
            select p from Profiles p
            where upper(p.businessName) like upper(concat('%', ?1, '%')) and p.active = ?2 and p.deleted = ?3""")
    Page<Profiles> findByBusinessNameContainsIgnoreCaseAndActiveAndDeleted(String businessName, Boolean active, Boolean deleted, Pageable pageable);


}
