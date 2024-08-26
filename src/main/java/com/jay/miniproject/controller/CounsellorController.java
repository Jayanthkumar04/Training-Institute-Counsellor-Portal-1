package com.jay.miniproject.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpServerErrorException;

import com.jay.miniproject.Dto.Dashboard;
import com.jay.miniproject.entities.Counsellor;
import com.jay.miniproject.entities.Enquiry;
import com.jay.miniproject.service.CounsellorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	
	CounsellorService counsellorService; 
	
	CounsellorController(CounsellorService counsellorService)
	{
		this.counsellorService = counsellorService;
	}
	
	@GetMapping("/")
	public String viewHomePage(Model model)
	{
		Counsellor c = new Counsellor();
		model.addAttribute("counsellor",c);
		
		return "login";
	}
	
	@PostMapping("/login")
	public String loginPage(Counsellor counsellor,HttpServletRequest request,Model model)
	{
	
		Counsellor  c= counsellorService.login(counsellor.getEmail(),counsellor.getPassword());
	
	    if(c == null) {
	    	
	    	model.addAttribute("emsg","email id or password is not matching");
	    	return "login";
	    }
	    
	    else {
	    	
	    	HttpSession session = request.getSession(true);
	    	session.setAttribute("counsellorId", c.getCounsellorId());
	    	//will go todashboard page
	    	return "redirect:/dashboard";
	    }
	    
	    
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req)
	{
	    HttpSession session = req.getSession(false);
	    
	    session.invalidate();
	    
		return "redirect:/";
	}
	
	@GetMapping("/register")
	public String registerHandling(Model model)
	{
		Counsellor c = new Counsellor();
		
		model.addAttribute("counsellor",c);
		
		return "register";
	}
    
	@PostMapping("/register")
	public String saveCounsellor(Counsellor c,Model model)
	{
	      if(counsellorService.findByEmail(c.getEmail()) == null)
	      {
		 counsellorService.register(c);
		 model.addAttribute("smsg","counsellor added successfully");
		 return "register";
	      }
	      
	      else
	      {
	    	  model.addAttribute("emsg","email already exists");
	         return "register";
	      }
	      
	}
	
	
	
	
	
	

	
	
}
