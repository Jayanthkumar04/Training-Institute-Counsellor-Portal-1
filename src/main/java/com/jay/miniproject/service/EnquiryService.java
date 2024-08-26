package com.jay.miniproject.service;

import java.util.List;

import com.jay.miniproject.Dto.ViewEnquiryFilterRequest;
import com.jay.miniproject.entities.Enquiry;

public interface EnquiryService {

	public boolean addEnquiry(Enquiry enquiry,Integer counsellorId) throws Exception;
	
	public List<Enquiry> getAllEnquiries(Integer counsellorId);
	
	public List<Enquiry> getEnquriesWithFilters(ViewEnquiryFilterRequest filterReq,Integer counsellorId);
	
	public Enquiry getEnquiryById(Integer enqId);
	
}
