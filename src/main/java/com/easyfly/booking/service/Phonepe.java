package com.easyfly.booking.service;

import com.easyfly.booking.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Phonepe implements PaymentService{
	public String execute() 
    { 
		log.info("Payment succedded using Phonepe");
        System.out.println("Executing PayTm:Payment succedded using Phonepe");
		return Constant.PHONEPE_MSG;
    } 
  
    @Override
    public String getName() 
    { 
        return "Phonepe"; 
    } 
}
