package com.nur.services;

import java.util.List;
import com.nur.entity.AppPlanEntity;

public interface AppPlanService {
	
	List<String> getPlanCategory();
	
	String upsert(AppPlanEntity entity);
	
	List<AppPlanEntity> getAllPlans();
	
	AppPlanEntity getPlan(int pid);
	
	String delete(int pid);
	
	String softDelete(int pid);
	
}
