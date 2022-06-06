package com.nur.bindings;

import java.util.Date;

import lombok.Data;

//@Component
@Data
public class AppPlanBinding {
	
	private String planName;
	
	private Date planStDate;	

	private Date planEnDate;
	
	private Integer categoryId;

}
