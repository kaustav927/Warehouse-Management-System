
package ca.uwo.proxies;
import java.util.Map;
import java.util.Scanner;

import ca.uwo.client.Buyer;
import ca.uwo.client.Supplier;
import ca.uwo.frontend.Facade;

public class LowQuantityProxy extends Proxy {
	public void placeOrder(Map<String, Integer> orderDetails, Buyer buyer) {
		int quantity = orderDetails.size();
		//if the order quantity is above 10, forward the placement of the order to the high quantity proxy
		if (quantity > 10) {
			HighQuantityProxy highQuantProxy = new HighQuantityProxy();
			highQuantProxy.placeOrder(orderDetails, buyer);
		}
		// else the buyer is authenticated and the order is placed with the current proxy 
		else {
			//if the buyer is authenticated, the order is placed with this proxy 
			if (authenticate(buyer)) {
				Facade facade = new Facade();
				facade.placeOrder(orderDetails, buyer);
			}
			//if the credentials were not correct or if they don't match, user is asked to try again 
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
	}

	
//never called 
	@Override
	public void restock(Map<String, Integer> restockDetails, Supplier supplier) {
		Facade facade = new Facade(); //creating the facade object to forward to
		facade.restock(restockDetails, supplier);//forwards to the facade 
		
	}
	
	// doesnt include pin verification or agent calling option 
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