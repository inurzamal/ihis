package com.nur.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.nur.entity.AppPlanEntity;
import com.nur.services.AppPlanService;

@RestController
public class AppPlanRestController {
	
	@Autowired
	private AppPlanService service;
	
	@GetMapping("/category")
	public ResponseEntity<List<String>> getPlanCategory(){
		List<String> planCategory = service.getPlanCategory();
		return new ResponseEntity<>(planCategory,HttpStatus.OK);
		
	}
	
	@PostMapping("/plan")
	public ResponseEntity<String> createPlan(@RequestBody AppPlanEntity plan) {
		String status = service.upsert(plan);
		return new ResponseEntity<>(status, HttpStatus.CREATED);
	}
	
	@GetMapping("/plans")
	public ResponseEntity<List<AppPlanEntity>> getAllPlans(){		
		List<AppPlanEntity> allPlans = service.getAllPlans();
		return new ResponseEntity<>(allPlans, HttpStatus.OK);
	}
	
	@GetMapping("/plan/{pid}")
	public ResponseEntity<AppPlanEntity> getPlan(@PathVariable int pid){
		AppPlanEntity plan = service.getPlan(pid);
		if(plan == null) {
			return new ResponseEntity<>(plan,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(plan,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{pid}")
	public ResponseEntity<String> delete(@PathVariable int pid){		
		String status = service.delete(pid);
		return new ResponseEntity<>(status,HttpStatus.OK);		
	}
	
	@DeleteMapping("/softDelete/{pid}")
	public ResponseEntity<String> softDelete(@PathVariable int pid){
		String softDelete = service.softDelete(pid);		
		return new ResponseEntity<>(softDelete,HttpStatus.OK);		
	}

}
