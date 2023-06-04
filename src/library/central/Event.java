package library.central;

import java.util.Date;

public class Event {
    private String EventID;
    private String EventName;
    private Date StartDate;
    private Date EndDate;
    private String Status;

    public Event() {
        this.EventID = " ";
        this.EventName = " ";
        this.StartDate = null;
        this.EndDate = null;
        this.Status = " ";
    }

    public Event(String EventID, String EventName, Date StartDate, Date EndDate, String Status) {
        this.EventID = EventID;
        this.EventName = EventName;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.Status = Status;
    }

    public String getEventID() {
        return this.EventID;
    }

    public void setEventID(String EventID) {
        this.EventID = EventID;
    }

    public String getEventName() {
        return this.EventName;
    }

    public void setEventName(String EventName) {
        this.EventName = EventName;
    }

    public Date getStartDate() {
        return this.StartDate;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    public Date getEndDate() {
        return this.EndDate;
    }

    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }

    public String getStatus() {
        return this.Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

}