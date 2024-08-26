package com.jay.miniproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jay.miniproject.Dto.Dashboard;
import com.jay.miniproject.entities.Counsellor;
import com.jay.miniproject.entities.Enquiry;
import com.jay.miniproject.repository.CounsellorRepository;
import com.jay.miniproject.repository.EnquiryRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CounsellorServiceImpl implements CounsellorService{

	@Autowired
	CounsellorRepository counsellorRepository;
	
	@Autowired
    EnquiryRepository enquiryRepository;
	
	
	public Counsellor findByEmail(String email) {
	
		return counsellorRepository.findByEmail(email);
	}
	
	@Override
	public boolean register(Counsellor counsellor) {

		Counsellor savedCounsellor = counsellorRepository.save(counsellor);
		
		if(null != savedCounsellor)
		{
			return true;
		}
		
		return false;
	}

	@Override

	public Counsellor login(String email, String password) {

	Counsellor counsellor = counsellorRepository.findByEmailAndPassword(email, password);	
		
	return counsellor;	
	}
	
	@Override
	public Dashboard getDashboardResponse(Integer counsellorId) {

	Dashboard dashboard = new Dashboard();
	List<Enquiry> enquiryList = enquiryRepository.findAllEnquiriesByCounsellorId(counsellorId);
	
	int totalEnquiries = enquiryList.size();
	
	int enrolled = enquiryList.stream()
	              .filter(e->e.getEnqStatus().equals("enrolled"))
	              .collect(Collectors.toList())
	              .size();
	
	int open = enquiryList.stream()
            .filter(e->e.getEnqStatus().equals("open"))
            .collect(Collectors.toList())
            .size();

	int lost = enquiryList.stream()
            .filter(e->e.getEnqStatus().equals("lost"))
            .collect(Collectors.toList())
            .size();

	dashboard.setTotalEnquiries(totalEnquiries);
	
	dashboard.setEnrolledEnquiries(enrolled);

	dashboard.setOpenEnquiries(open);
	
	dashboard.setLostEnquiries(lost);
	
	return dashboard;
	}
//	@Override
//
//	public Counsellor login(String email, String password) {
//	
//		Counsellor counsellor = counsellorRepository.findByEmail(email);
//		
//		if(counsellor != null &&  counsellor.getPassword().equals(password)) return counsellor;
//		
//		else
//		{
//		
//			return null;
//		}
//	}

	

//	@Override
//	public boolean register(Counsellor counsellor) {
//	
//	try {	
//     counsellorRepository.save(counsellor);		
//	
//     return true;
//	}
//	catch(Exception e)
//	{
//		return false;
//	}
//    
//	}

//	@Override
//	public Dashboard getDashboardResponse(Integer counsellorId) {
//	
//		Optional<Counsellor> coun = counsellorRepository.findById(counsellorId);
//		
//		
//		List<Enquiry> enquiries = null;
//		if(coun.isPresent())
//		{
//		enquiries = enquiryRepository.findAllEnquiriesByCounsellorId(counsellorId);
//		}
//		
//		int totalEnquiries = enquiries.size();
//		
//		int openCount = 0;
//		int enrolledCount=0;
//		int lost=0;
//		for(Enquiry enquiry : enquiries)
//		{
//			if(enquiry.getEnqStatus().equals("open"))
//			{
//				openCount++;
//			}
//		   
//			else if(enquiry.getEnqStatus().equals("enrolled"))
//			{
//				enrolledCount++;
//			}
//			else {
//				lost++;
//			}
//			
//			
//		}
//		
//		Dashboard db = new Dashboard(totalEnquiries, openCount, enrolledCount, lost);
//		
//		return db;
//		
//	}


}
