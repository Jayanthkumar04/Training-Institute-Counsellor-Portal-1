package com.jay.miniproject.service;

import com.jay.miniproject.Dto.Dashboard;
import com.jay.miniproject.entities.Counsellor;

public interface CounsellorService {

	public Counsellor login(String email,String password);
	
	public boolean register(Counsellor counsellor);
	
	public Dashboard getDashboardResponse(Integer counsellorId); 
	
	public Counsellor findByEmail(String email);
	
}
