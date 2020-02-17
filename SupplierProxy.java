package ca.uwo.proxies;
import java.util.Map;
import ca.uwo.client.Buyer;
import ca.uwo.client.Supplier;
import ca.uwo.frontend.Facade;

public class SupplierProxy extends Proxy {

	
	// if the placeOrder is invoked within the supplier proxy, the request is forwarded to the next proxy in the sequence 
	public void placeOrder(Map<String, Integer> orderDetails, Buyer buyer) {
		LowQuantityProxy lowQuantProxy = new LowQuantityProxy(); // creating a proxy object for the next proxy in sequence
		lowQuantProxy.placeOrder(orderDetails, buyer); // forwarding the order to the next proxy in the sequence 
	}
	
	
	//Forwards restock operation to the facade 
	public void restock(Map<String, Integer> restockDetails, Supplier supplier) {
		Facade facade = new Facade(); //creating the facade object to forward to
		facade.restock(restockDetails, supplier);//forwards to the facade 
	}
}