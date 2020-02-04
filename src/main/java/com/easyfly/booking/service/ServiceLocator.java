package com.easyfly.booking.service;


import javax.naming.NamingException;

public class ServiceLocator {
	
     private ServiceLocator() {
		
	}
     
	private static Cache cache; 
	
	
	  
    static
    { 
        cache = new Cache(); 
    } 
  
    public static PaymentService getService(String name) throws NamingException 
    { 
    	PaymentService service = cache.getService(name); 
  
        if (service != null) { 
            return service; 
        } 
  
        InitialContext context = new InitialContext(); 
        PaymentService serviceOne = (PaymentService)context.lookup(name); 
        cache.addService(serviceOne); 
        return serviceOne; 
    } 
}
