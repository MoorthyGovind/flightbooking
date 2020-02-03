package com.easyfly.booking.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Paytm implements PaymentService{
	public void execute() 
    { 
		log.info("Payment succedded using PAYTM");
        System.out.println("Executing PayTm:Payment succedded using PAYTM"); 
    } 
  
    @Override
    public String getName() 
    { 
        return "Paytm"; 
    } 
}
