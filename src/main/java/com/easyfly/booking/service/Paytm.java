package com.easyfly.booking.service;

import com.easyfly.booking.constant.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Paytm implements PaymentService{
	public String execute() 
    { 
		log.info("Payment succedded using PAYTM");
        System.out.println("Executing PayTm:Payment succedded using PAYTM");
		return Constant.PAYTM_MSG; 
    } 
  
    @Override
    public String getName() 
    { 
        return "Paytm"; 
    } 
}
