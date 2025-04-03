package com.d288.backendproject.dao;

import com.d288.backendproject.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
public interface DivisionRepository extends JpaRepository<Division, Long> {
    @Query("SELECT d FROM Division d WHERE d.id BETWEEN :start AND :end")
    List<Division> findDivisionsInRange(@Param("start") Long start, @Param("end") Long end);
}
