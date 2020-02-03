package com.easyfly.booking.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitialContext {
	public Object lookup(String name) 
    { 
        if (name.equalsIgnoreCase("Paytm")) { 
        	log.info("Creating a new Paytm object");
            System.out.println("Creating a new Paytm object"); 
            return new Paytm(); 
        } 
        else if (name.equalsIgnoreCase("Phonepe")) { 
        	log.info("Creating a new Phonepe object");
            System.out.println("Creating a new Phonepe object"); 
            return new Phonepe(); 
        } 
        return null; 
    } 
}
