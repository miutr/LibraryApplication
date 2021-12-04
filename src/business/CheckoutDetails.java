package business;

import java.util.Date;

public class CheckoutDetails {
	
	private String isbn;
	private String title;
	private Date checkoutDate;
	private int copyNum;
	private String member;
	private Date dueDate;
	
	public CheckoutDetails(String isbn, String title, Date checkoutDate, Date dueDate) {
		this.isbn = isbn;
		this.title = title;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
	}
	
	public CheckoutDetails(String isbn, String title, int copyNum, String member, Date dueDate) {
		this.isbn = isbn;
		this.title = title;
		this.copyNum = copyNum;
		this.member = member;
		this.dueDate = dueDate;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	public int getCopyNum() {
		return copyNum;
	}

	public void setCopyNum(int copyNum) {
		this.copyNum = copyNum;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	
}
