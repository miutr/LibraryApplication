package business;

import java.io.Serializable;
import java.util.Date;

public class CheckoutEntry implements Serializable {
	
	private static final long serialVersionUID = 3370963134650671133L;
	private Date checkoutDate;
	private Date dueDate;
	
	public CheckoutEntry(Date checkoutDate, Date dueDate) {
		this.setCheckoutDate(checkoutDate);
		this.setDueDate(dueDate);
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	
}
