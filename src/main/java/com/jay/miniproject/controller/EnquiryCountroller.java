package com.jay.miniproject.controller;

import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jay.miniproject.Dto.Dashboard;
import com.jay.miniproject.Dto.ViewEnquiryFilterRequest;
import com.jay.miniproject.entities.Enquiry;
import com.jay.miniproject.service.CounsellorService;
import com.jay.miniproject.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryCountroller {

	@Autowired
	EnquiryService enquiryService;
	
	@Autowired
	CounsellorService counsellorService;
	
    @GetMapping("/enquiry")	
	public String addEnquiry(Model model)
	{
		Enquiry enquiry = new Enquiry();
		
		model.addAttribute("enquiry",enquiry);
	
		return "AddEnquiry";
	}
    
    @PostMapping("/addEnquiry")     //@Model Attribute("enq") Enquriy enquiry
    public String addEnquiryHandling(Enquiry enquiry,HttpServletRequest request ,Model model) throws Exception
    {
    	 HttpSession session = request.getSession(false);
    	
    	 Integer counsellorId = (Integer) session.getAttribute("counsellorId");
    	 
    	boolean isSaved = enquiryService.addEnquiry(enquiry, counsellorId);
    	
    	if(isSaved == true)
    	{
    	model.addAttribute("smsg","enquiry added sucessfully");
    
    	}
    	else 
    	{
    	
    	model.addAttribute("emsg","adding enquiry got failed");
    	
    	}
    	
    	return "AddEnquiry";
    	
    	}
    
    @GetMapping("/dashboard")
    public String displayDashboard(HttpServletRequest req,Model model)
    {
    	HttpSession session = req.getSession(false);
    	Integer counsellorId = (Integer) session.getAttribute("counsellorId");
    	
    	Dashboard dboard = counsellorService.getDashboardResponse(counsellorId);
    	
    	model.addAttribute("dashboardinfo",dboard);
    	
    	return "dashboard";
    }
    

	@GetMapping("/viewEnquiries")
	public String viewEnquiries(HttpServletRequest req,Enquiry enquiry,Model model)
	{
		
		HttpSession session = req.getSession(false);
		
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
	   List<Enquiry> entries = enquiryService.getAllEnquiries(counsellorId);
	   
	   model.addAttribute("entryList",entries); 
	   
	   ViewEnquiryFilterRequest filterRequest = new ViewEnquiryFilterRequest();
	   
	   model.addAttribute("viewEnquiryFilterRequest",filterRequest);
	   
	   
	   return "viewEnquiries";
		
	}
	@PostMapping("/filter-enquiries")
	public String filterEnquiries(ViewEnquiryFilterRequest viewEnquiryFilterRequest,HttpServletRequest request, Model model)
	{
		
		HttpSession session = request.getSession(false);
		
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		List<Enquiry> enqlist = enquiryService.getEnquriesWithFilters(viewEnquiryFilterRequest, counsellorId);
		
		model.addAttribute("entryList",enqlist);
		
		return "viewEnquiries";
	}
	
	
	@GetMapping("/edit")
	public String editEnquiry(@RequestParam("enqId") Integer enqId,Model model)
	{
		
		Enquiry e = enquiryService.getEnquiryById(enqId);
		
		model.addAttribute("enquiry",e);
		
		return "AddEnquiry";
		
	}
	
    
	
	
}
