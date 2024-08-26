package com.jay.miniproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jay.miniproject.entities.Counsellor;
import com.jay.miniproject.entities.Enquiry;

@Repository
public interface CounsellorRepository extends JpaRepository<Counsellor, Integer> {


//	@Query(value="select e from Enquiry e where e.courseName= :courseName and e.classMode=:classMode and e.enqStatus=:enqStatus",nativeQuery=true)
//	List<Enquiry> findByRequest(String courseName,String classMode,String enqStatus);
	
	Counsellor findByEmail(String email);
	
	public Counsellor findByEmailAndPassword(String email,String password);
	
	@Query(value="select *from enquiry where counsellorId =: counsellorId",nativeQuery=true)
	List<Enquiry> findEnquriesByCounsellorId(Integer counsellorId);
	
}
