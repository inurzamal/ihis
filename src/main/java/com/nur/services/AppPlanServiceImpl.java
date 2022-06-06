package com.nur.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nur.entity.AppPlanEntity;
import com.nur.repository.AppPlanRepository;
import com.nur.repository.PlanCategoryRepository;

@Service
public class AppPlanServiceImpl implements AppPlanService {
	
	@Autowired
	private AppPlanRepository repository;
	
	@Autowired
	private PlanCategoryRepository categoryRepo;
	
	@Override
	public List<String> getPlanCategory() {		
		return categoryRepo.getPlanCategoryName();
	}

	@Override
	public String upsert(AppPlanEntity entity) {
		repository.save(entity);
		return "SUCCESS";
	}

	@Override
	public List<AppPlanEntity> getAllPlans() {		
		return repository.findAll();
	}
	
	@Override
	public AppPlanEntity getPlan(int pid) {
		Optional<AppPlanEntity> findById = repository.findById(pid);
		if(findById.isPresent()) {
			return findById.get();
		}
		return null;
	}


	@Override
	public String delete(int pid) {
		repository.deleteById(pid);
		return "Deleted Successfully";
	}

	@Override
	public String softDelete(int pid) {
		Optional<AppPlanEntity> findById = repository.findById(pid);
		if(findById.isPresent()) {
			AppPlanEntity appPlanEntity = findById.get();
			appPlanEntity.setActiveSw("N");			
		}
		return "Deactivated..";
	}

}
