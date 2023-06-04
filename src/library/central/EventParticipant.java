package library.central;

import java.util.Date;

import library.user.User;

public class EventParticipant extends Event {
    private User Participant;
    private String Position;
    
    public EventParticipant() {
        super();
        this.Participant = null;
        this.Position = " ";
    }

    public EventParticipant(User Participant, String Position) {
        super();
        this.Participant = Participant;
        this.Position = Position;
    }

    public EventParticipant(String EventID, String EventName, Date StartDate, Date EndDate, String Status) {
        super(EventID, EventName, StartDate, EndDate, Status);
    }
}