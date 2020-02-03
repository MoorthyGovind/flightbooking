package com.easyfly.booking.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Phonepe implements PaymentService{
	public void execute() 
    { 
		log.info("Payment succedded using Phonepe");
        System.out.println("Executing PayTm:Payment succedded using Phonepe");
    } 
  
    @Override
    public String getName() 
    { 
        return "Phonepe"; 
    } 
}
