package library.central;

import java.sql.Date;

import library.publication.Publication;
import library.user.Student;

public class Borrow {
	private String BorrowID;
	private Date StartDate;
	private Date DueDate;
	private Date ReturnedDate;
	private Student Borrower;
	private Publication BorrowedPub;
	private int BorrowQuantity;
	private boolean FineStatus;
	private boolean ReturnedStatus;

	public Borrow() {
		this.BorrowID = " ";
		this.StartDate = null;
		this.DueDate = null;
		this.ReturnedDate = null;
		this.Borrower = null;
		this.BorrowedPub = null;
		this.BorrowQuantity = -1;
		this.FineStatus = false;
		this.ReturnedStatus = false;
	}

	public Borrow(String BorrowID, Date StartDate, Date DueDate, Date ReturnedDate, Student Borrower, Publication BorrowedPub, int BorrowQuantity, boolean FineStatus, boolean ReturnedStatus) {
		this.BorrowID = BorrowID;
		this.StartDate = StartDate;
		this.DueDate = DueDate;
		this.ReturnedDate = ReturnedDate;
		this.Borrower = Borrower;
		this.BorrowedPub = BorrowedPub;
		this.BorrowQuantity = BorrowQuantity;
		this.FineStatus = FineStatus;
		this.ReturnedStatus = ReturnedStatus;
	}

	public String getBorrowID() {
		return this.BorrowID;
	}

	public void setBorrowID(String BorrowID) {
		this.BorrowID = BorrowID;
	}

	public Date getStartDate() {
		return this.StartDate;
	}

	public void setStartDate(Date StartDate) {
		this.StartDate = StartDate;
	}

	public Date getDueDate() {
		return this.DueDate;
	}

	public void setDueDate(Date DueDate) {
		this.DueDate = DueDate;
	}

	public Date getReturnedDate() {
		return this.ReturnedDate;
	}

	public void setReturnedDate(Date ReturnedDate) {
		this.ReturnedDate = ReturnedDate;
	}

	public Student getBorrower() {
		return this.Borrower;
	}

	public void setBorrower(Student Borrower) {
		this.Borrower = Borrower;
	}

	public Publication getBorrowedPub() {
		return this.BorrowedPub;
	}

	public void setBorrowedPub(Publication BorrowedPub) {
		this.BorrowedPub = BorrowedPub;
	}

	public int getBorrowQuantity() {
		return this.BorrowQuantity;
	}

	public void setBorrowQuantity(int BorrowQuantity) {
		this.BorrowQuantity = BorrowQuantity;
	}

	public boolean getFineStatus() {
		return this.FineStatus;
	}

	public void setFineStatus(boolean FineStatus) {
		this.FineStatus = FineStatus;
	}

	public boolean getReturnedStatus() {
		return this.ReturnedStatus;
	}

	public void setReturnedStatus(boolean ReturnedStatus) {
		this.ReturnedStatus = ReturnedStatus;
	}

	public void setBorrow(Borrow Update) {
        this.StartDate = Update.StartDate;
		this.DueDate = Update.DueDate;
		this.ReturnedDate = Update.ReturnedDate;
		this.Borrower = Update.Borrower;
		this.BorrowedPub = Update.BorrowedPub;
		this.BorrowQuantity = Update.BorrowQuantity;
		this.FineStatus = Update.FineStatus;
		this.ReturnedStatus = Update.ReturnedStatus;
    }

}
