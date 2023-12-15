package vttp.ssf.assessment.eventmanagement.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Service
public class DatabaseService {

    @Autowired
    RedisRepository redisRepo;

    public void saveRecord(Event event) {
        redisRepo.saveRecord(event);
    }

    public Integer getNumberOfEvents() {
        return redisRepo.getNumberOfEvents();
    }

    public Event getEvent(Integer index) {
        return redisRepo.getEvent(index);
    }
    
    // TODO: Task 1
    public List<Event> readFile(String fileName) throws Exception {
        File file = new File(fileName);
        List<Event> events = new ArrayList<>();
        try (FileReader fileReader = new FileReader(file)) {
            JsonReader jsonReader = Json.createReader(fileReader);
            JsonArray eventArray = jsonReader.readArray();
            for (JsonValue eventValue: eventArray) {
                JsonObject eventObject = eventValue.asJsonObject();
                
                // System.out.println(Long.valueOf(eventObject.get("eventDate").toString()));
                // System.out.println(new Date(Long.valueOf(eventObject.get("eventDate").toString())));
                Event event = new Event(eventObject.getInt("eventId"), eventObject.getString("eventName"), eventObject.getInt("eventSize"), Long.valueOf(eventObject.get("eventDate").toString()), eventObject.getInt("participants"));
                events.add(event);
            }
        } catch (FileNotFoundException e) {
            //remember to remove and replace with
            // System.out.println("File not found");
            e.printStackTrace();
        }

        return events;
    }

    public boolean ageValid(Date birthDate) {
        Instant birthDateIns = birthDate.toInstant();
        Instant now = Instant.now();
        Long days = ChronoUnit.DAYS.between(birthDateIns, now);
        Long age = days/365L;

        return age >= 21L;
    }

    public boolean sizeExceeded(Integer tickets, Integer index) {
        Event event = redisRepo.getEvent(index);
        Integer participants = event.getParticipants();
        Integer size = event.getEventSize();
        return (participants + tickets) > size;
    }

    public void addParticipants(Integer tickets, Integer index) {
        redisRepo.addParticipants(tickets, index);
    }
    

}
