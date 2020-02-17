package ca.uwo.proxies;

import java.util.Map;
import java.util.Scanner;

import ca.uwo.client.Buyer;
import ca.uwo.client.Supplier;
import ca.uwo.frontend.Facade;

public class HighQuantityProxy extends Proxy {

	public void placeOrder(Map<String, Integer> orderDetails, Buyer buyer) {

		//if buyer is authenticated, the current proxy forwards the order to the facade 
		if (authenticate(buyer)) {
			Facade facade = new Facade();
			facade.placeOrder(orderDetails, buyer);
		}
		//else the user failed to authenticate and is asked to try again 
		else {
			while (authenticate(buyer)!=true){
				System.out.println("Please try again.");
			}
			if (authenticate(buyer)) {
				Facade facade = new Facade();
				facade.placeOrder(orderDetails, buyer);
			}
		}

	}
	
	
	@Override
	public void restock(Map<String, Integer> restockDetails, Supplier supplier) {
		Facade facade = new Facade(); //creating the facade object to forward to
		facade.restock(restockDetails, supplier);//forwards to the facade 
		
	}
	
	private boolean authenticate(Buyer buyer) {
		//open a scanner, ask the user for their credentials, close the scanner. 
		Scanner credentials = new Scanner(System.in);
		System.out.println("Please enter your username:");
		String username = credentials.next();
		System.out.println("Please enter your password:");
		String password = credentials.next();
		credentials.close();
		
		//test the user's input to authenticate 
		// if both the username and the password are a match, method returns true 
		if (username.equals(buyer.getUserName()) && password.equals(buyer.getPassword())) {
				return true;
		}
		// else if they are not a match, the user is prompted, and the mehtod returns false 
		else {
				System.out.println("The password you have provided is incorrect.");
				return false;
		}
	}



}