package com.nur.services;

import java.util.List;

import com.nur.entity.CaseWorkersAcctEntity;

public interface CwAccountService {
	
	String upsertAccount(CaseWorkersAcctEntity entity);
	
	List<CaseWorkersAcctEntity> getAllAccount();
	
	CaseWorkersAcctEntity getAccount(int id);
	
	String deleteAccount(int id);
	
	String deleteSoft(int id);
	
}
