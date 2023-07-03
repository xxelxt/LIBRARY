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

	public Borrow(int BorrowID, String StudentID, Integer PublicationID, String Title, int BorrowQuantity,
			Date StartDate, Date DueDate, Date ReturnedDate, boolean FineStatus, boolean ReturnedStatus) {
		this.BorrowID = BorrowID;
		this.StudentID = StudentID;
		this.PublicationID = PublicationID;
		this.Title = Title;
		this.BorrowQuantity = BorrowQuantity;
		this.StartDate = StartDate;
		this.DueDate = DueDate;
		this.ReturnedDate = ReturnedDate;
		this.FineStatus = FineStatus;
		this.ReturnedStatus = ReturnedStatus;
	}

	public int getBorrowID() {
		return this.BorrowID;
	}

	public void setBorrowID(int BorrowID) {
		this.BorrowID = BorrowID;
	}

	public String getStudentID() {
		return this.StudentID;
	}

	public void setStudentID(String StudentID) {
		this.StudentID = StudentID;
	}

	public Integer getPublicationID() {
		return this.PublicationID;
	}

	public void setPublicationID(Integer PublicationID) {
		this.PublicationID = PublicationID;
	}

	public String getTitle() {
		return this.Title;
	}

	public void setTitle(String Title) {
		this.Title = Title;
	}

	public int getBorrowQuantity() {
		return this.BorrowQuantity;
	}

	public void setBorrowQuantity(int BorrowQuantity) {
		this.BorrowQuantity = BorrowQuantity;
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

	public boolean isFineStatus() {
		return this.FineStatus;
	}

	public void setFineStatus(boolean FineStatus) {
		this.FineStatus = FineStatus;
	}

	public boolean isReturnedStatus() {
		return this.ReturnedStatus;
	}

	public void setReturnedStatus(boolean ReturnedStatus) {
		this.ReturnedStatus = ReturnedStatus;
	}

	// For table setCellValueFactory (automatic map string "fineStatusText" with the method below)
	public String getFineStatusText() {
		return (FineStatus ? "Bị phạt" : "Không bị phạt");
	}

	public String getReturnedStatusText() {
		return (ReturnedStatus ? "Đã trả sách" : "Chưa trả sách");
	}
}
