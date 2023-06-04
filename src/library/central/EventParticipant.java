package library.central;

import java.util.Date;

import library.user.User;

public class EventParticipant extends Event {
    private User Participant;
    private String Position;
    
    public EventParticipant() {

    }

    public EventParticipant(String EventID, String EventName, Date StartDate, Date EndDate, String Status) {
        super(EventID, EventName, StartDate, EndDate, Status);
    }
}