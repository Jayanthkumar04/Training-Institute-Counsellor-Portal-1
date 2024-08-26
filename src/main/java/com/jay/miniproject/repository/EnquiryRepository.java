package com.jay.miniproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jay.miniproject.entities.Enquiry;

@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry,Integer> {

//	List<Enquiry> findAllByCounsellorId(Integer counsellorId);

//	List<Enquiry> findAllEnquiriesByCounsellorId(Integer counsellorId);

	@Query("SELECT e FROM Enquiry e WHERE e.counsellor.id = :counsellorId")
	List<Enquiry> findAllEnquiriesByCounsellorId(@Param("counsellorId") Integer counsellorId);

	
}
