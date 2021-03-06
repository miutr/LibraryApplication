package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable{

	private static final long serialVersionUID = -2616918611240886733L;
	List<Payment> payments = new ArrayList<>();
	List<CheckoutEntry> checkoutEntries = new ArrayList<>();

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public void setCheckoutEntries(List<CheckoutEntry> checkoutEntries) {
		this.checkoutEntries = checkoutEntries;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public List<CheckoutEntry> getCheckoutEntries() {
		return checkoutEntries;
	}
}
