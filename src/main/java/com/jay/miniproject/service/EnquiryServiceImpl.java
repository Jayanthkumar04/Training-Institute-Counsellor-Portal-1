package com.jay.miniproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.jay.miniproject.Dto.ViewEnquiryFilterRequest;
import com.jay.miniproject.entities.Counsellor;
import com.jay.miniproject.entities.Enquiry;
import com.jay.miniproject.repository.CounsellorRepository;
import com.jay.miniproject.repository.EnquiryRepository;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
    EnquiryRepository enquiryRepository;
	
	@Autowired 
	CounsellorRepository counsellorRepository;
	
	@Override
	public boolean addEnquiry(Enquiry enquiry, Integer counsellorId) throws Exception {
		
		Counsellor counsellor = counsellorRepository.findById(counsellorId).orElse(null);
		
		if(counsellor == null)
		{
			throw new Exception("no counserllor found");
		}
		
		enquiry.setCounsellor(counsellor);
		
		Enquiry save = enquiryRepository.save(enquiry);
		
		if(save.getEnqId() != null) return true;
		
		return false;
		
	}
//	@Override
//	public boolean addEnquiry(Enquiry enquiry, Integer counsellorId) {
//		
//     //	enquiryRepository.save(enquiry);
//		
//		Optional<Counsellor> counsellor = counsellorRepository.findById(counsellorId);
//		
//		if(counsellor.isPresent())
//		{
//			enquiry.setCounsellor(counsellor.get());
//		    enquiryRepository.save(enquiry);
//		   return true;
//		}
//		else
//		{
//			return false;
//		}
//		
//	}

	@Override
	public List<Enquiry> getAllEnquiries(Integer counsellorId) {
		return enquiryRepository.findAllEnquiriesByCounsellorId(counsellorId);
	}

//	@Override
//	public List<Enquiry> getEnquriesWithFilters(ViewEnquiryFilterRequest filterReq, Integer counsellorId) {
//	
//		Optional<Counsellor> counsellorOptional = counsellorRepository.findById(counsellorId);
//
//		List<Counsellor> counsellorList = new ArrayList<>();
//		
//		counsellorOptional.ifPresent(counsellorList::add); 
//	    
//		List<Enquiry> list = counsellorRepository.findByRequest(filterReq.getCourseName(),filterReq.getClassMode(),filterReq.getEnqStatus());
//	
//		return list;
//	}

	@Override
	public Enquiry getEnquiryById(Integer enqId) {
	
		return enquiryRepository.findById(enqId).orElse(null);
	}

	@Override
	public List<Enquiry> getEnquriesWithFilters(ViewEnquiryFilterRequest filterReq, Integer counsellorId) {
	
		Enquiry enquiry = new Enquiry();
		
		if(StringUtils.isNotEmpty(filterReq.getEnqStatus())) {
			enquiry.setEnqStatus(filterReq.getEnqStatus());
		}
		if(StringUtils.isNotEmpty(filterReq.getClassMode())) {
			enquiry.setClassMode(filterReq.getClassMode());
		}
		if(StringUtils.isNotEmpty(filterReq.getCourseName())) {
			enquiry.setCourseName(filterReq.getCourseName());
		}
		
		Counsellor c = counsellorRepository.findById(counsellorId).orElse(null);
		enquiry.setCounsellor(c);
		Example<Enquiry> of = Example.of(enquiry);
		
		List<Enquiry> enqList = enquiryRepository.findAll(of);
 		return enqList;
	}

	
}
