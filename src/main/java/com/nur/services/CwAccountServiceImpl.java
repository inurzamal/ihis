package com.nur.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nur.utils.EmailUtils;
import com.nur.entity.CaseWorkersAcctEntity;
import com.nur.repository.CaseWorkersAcctRepository;

@Service
public class CwAccountServiceImpl implements CwAccountService {
	
	Logger logger = LoggerFactory.getLogger(CwAccountServiceImpl.class);
	
	@Autowired
	private CaseWorkersAcctRepository accRepo;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String upsertAccount(CaseWorkersAcctEntity entity) {
		entity.setPwd(generateRandomPassword(6));
		CaseWorkersAcctEntity saved= accRepo.save(entity);
		
		String email = entity.getEmail();
		String subjects = "Case Workers Account Creation : IHIS";
		String fileName = "TMP-PAZZ-EMAIL-BODY-TEMPLATE.txt";
		String body = readMailBodyContent(fileName, entity);
		boolean isSent = emailUtils.sendEmail(email, subjects, body);
		
		if(saved.getAcctId() != null && isSent) {
			return "SUCCESS";
		}
		
		return "Fail";
	}

	@Override
	public List<CaseWorkersAcctEntity> getAllAccount() {	
		return accRepo.findAll();
	}

	@Override
	public CaseWorkersAcctEntity getAccount(int id) {
		Optional<CaseWorkersAcctEntity> acc = accRepo.findById(id);
		if(acc.isPresent()) {
			return acc.get();
		}
		return null;
	}

	@Override
	public String deleteAccount(int id) {
		accRepo.deleteById(id);
		return "Account Deleted";
	}

	@Override
	public String deleteSoft(int id) {
		Optional<CaseWorkersAcctEntity> acc = accRepo.findById(id);
		if(acc.isPresent()) {
			CaseWorkersAcctEntity accEntity = acc.get();
			accEntity.setActiveSw('N');
		}
		return "Account Deactivated..";
	}
	
	
	
	// Generating Random Password
    private static String generateRandomPassword(int len)
    {
        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
 
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
 
        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance
 
        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
 
        return sb.toString();
    }
    
    
    
	private String readMailBodyContent(String fileName, CaseWorkersAcctEntity entity) {

		String mailBody = null;

		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		String line = null;

		try {

			br = new BufferedReader(new FileReader(fileName));
			line = br.readLine(); // reading first line data

			while (line != null) {
				sb.append(line); // appending line data to buffer obj
				line = br.readLine(); // reading next line data
			}

			mailBody = sb.toString();

			mailBody = mailBody.replace("{NAME}", entity.getFullName());
			mailBody = mailBody.replace("{TEMP-PWD}", entity.getPwd());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		finally {
			// this block will be executed in every case, success or caught exception
			if (br != null) {
				// again, a resource is involved, so try-catch another time
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		return mailBody;
	}

}
