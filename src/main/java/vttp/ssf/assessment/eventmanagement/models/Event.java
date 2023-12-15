package vttp.ssf.assessment.eventmanagement.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event implements Serializable {
    
    private Integer eventId;

    private String eventName;

    private Integer eventSize;

    private Date eventDate;

    private Integer participants;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getEventSize() {
        return eventSize;
    }

    public void setEventSize(Integer eventSize) {
        this.eventSize = eventSize;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public Event(Integer eventId, String eventName, Integer eventSize, Long eventDate, Integer participants) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventSize = eventSize;
        // System.out.println(new Date(eventDate).toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try{
            this.eventDate = sdf.parse(sdf.format(new Date(eventDate)));
        } catch (ParseException e) {
            // e.printStackTrace();
        }
        
        this.participants = participants;
    }

    public Event() {}

    @Override
    public String toString() {
        return "Event [eventId=" + eventId + ", eventName=" + eventName + ", eventSize=" + eventSize + ", eventDate="
                + eventDate + ", participants=" + participants + "]";
    };

    
}
