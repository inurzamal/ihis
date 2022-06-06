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

import com.nur.entity.CaseWorkersAcctEntity;
import com.nur.services.CwAccountService;

@RestController
public class CwAccountRestController {
	
	@Autowired
	private CwAccountService accService;
	
	@PostMapping("/account")
	public ResponseEntity<String> createAccount(@RequestBody CaseWorkersAcctEntity entity){
		String status = accService.upsertAccount(entity);				
		return new ResponseEntity<>(status,HttpStatus.CREATED);		
	}
	
	@GetMapping("/accounts")
	public ResponseEntity<List<CaseWorkersAcctEntity>> getAccount(){		
		List<CaseWorkersAcctEntity> allAccounts = accService.getAllAccount();		
		return new ResponseEntity<>(allAccounts, HttpStatus.OK);
	}
	
	@GetMapping("/account/{id}")
	public ResponseEntity<CaseWorkersAcctEntity> getAccount(@PathVariable int id){
		CaseWorkersAcctEntity account = accService.getAccount(id);
		if(account == null) {
			return new ResponseEntity<>(account,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(account,HttpStatus.OK);		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable int id){		
		String status = accService.deleteAccount(id);
		return new ResponseEntity<>(status,HttpStatus.OK);		
	}
	
	@DeleteMapping("/softDelete/{id}")
	public ResponseEntity<String> softDelete(@PathVariable int id){
		String softDelete = accService.deleteSoft(id);		
		return new ResponseEntity<>(softDelete,HttpStatus.OK);		
	}

}
