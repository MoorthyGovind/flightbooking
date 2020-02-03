package com.easyfly.booking.service;

import java.util.ArrayList;
import java.util.List;

public class Cache {
	private List<PaymentService> paymentServices;
	public Cache() 
    { 
		paymentServices = new ArrayList<PaymentService>(); 
    } 
	 public PaymentService getService(String serviceName) 
	    { 
	        for (PaymentService service : paymentServices) { 
	            if (service.getName().equalsIgnoreCase(serviceName)) { 
	                System.out.println("Returning cached "
	                                   + serviceName + " object"); 
	                return service; 
	            } 
	        } 
	        return null; 
	    }
	 public void addService(PaymentService newService) 
	    { 
	        boolean exists = false; 
	        for (PaymentService service : paymentServices) { 
	            if (service.getName().equalsIgnoreCase(newService.getName())) { 
	                exists = true; 
	            } 
	        } 
	        if (!exists) { 
	        	paymentServices.add(newService); 
	        } 
	    } 
}
