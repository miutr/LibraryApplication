package business;

import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord {
	List<Payment> payments = new ArrayList<>();
	List<CheckoutEntry> checkoutEntries = new ArrayList<>();
	
	public List<Payment> getPayments() {
		return payments;
	}
	
	public List<CheckoutEntry> getCheckoutEntries() {
		return checkoutEntries;
	}
}
