package library.central;

import java.sql.Date;

public class Borrow {
	private int BorrowID;
	private String StudentID;
	private Integer PublicationID;
	private String Title;
	private int BorrowQuantity;
	private Date StartDate;
	private Date DueDate;
	private Date ReturnedDate;
	private boolean FineStatus;
	private boolean ReturnedStatus;

	public Borrow(int borrowID, String studentID, Integer publicationID, String title, int borrowQuantity,
			Date startDate, Date dueDate, Date returnedDate, boolean fineStatus, boolean returnedStatus) {
		BorrowID = borrowID;
		StartDate = startDate;
		DueDate = dueDate;
		ReturnedDate = returnedDate;
		StudentID = studentID;
		PublicationID = publicationID;
		Title = title;
		BorrowQuantity = borrowQuantity;
		FineStatus = fineStatus;
		ReturnedStatus = returnedStatus;
	}

	public int getBorrowID() {
		return BorrowID;
	}

	public void setBorrowID(int borrowID) {
		BorrowID = borrowID;
	}

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getDueDate() {
		return DueDate;
	}

	public void setDueDate(Date dueDate) {
		DueDate = dueDate;
	}

	public Date getReturnedDate() {
		return ReturnedDate;
	}

	public void setReturnedDate(Date returnedDate) {
		ReturnedDate = returnedDate;
	}

	public String getStudentID() {
		return StudentID;
	}

	public void setStudentID(String studentID) {
		StudentID = studentID;
	}

	public Integer getPublicationID() {
		return PublicationID;
	}

	public void setPublicationID(Integer publicationID) {
		PublicationID = publicationID;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public int getBorrowQuantity() {
		return BorrowQuantity;
	}

	public void setBorrowQuantity(int borrowQuantity) {
		BorrowQuantity = borrowQuantity;
	}

	public boolean isFineStatus() {
		return FineStatus;
	}

	public void setFineStatus(boolean fineStatus) {
		FineStatus = fineStatus;
	}

	public boolean isReturnedStatus() {
		return ReturnedStatus;
	}

	public void setReturnedStatus(boolean returnedStatus) {
		ReturnedStatus = returnedStatus;
	}

}
