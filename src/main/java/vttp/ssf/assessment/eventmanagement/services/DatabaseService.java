package vttp.ssf.assessment.eventmanagement.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
                Event event = new Event(eventObject.getInt("eventId"), eventObject.getString("eventName"), eventObject.getInt("eventSize"), Long.valueOf(eventObject.getInt("eventDate")), eventObject.getInt("participants"));
                events.add(event);
            }
        } catch (FileNotFoundException e) {
            //remember to remove and replace with
            // System.out.println("File not found");
            e.printStackTrace();
        }

        return events;
    }
}
