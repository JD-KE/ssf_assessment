package vttp.ssf.assessment.eventmanagement.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import vttp.ssf.assessment.eventmanagement.models.Event;

@Repository
public class RedisRepository {

	@Autowired
	private RedisTemplate<String,Event> template;

	@Resource(name="RedisEventTemplate")
	private ListOperations<String,Event> listOps;

	@Resource(name="RedisEventTemplate")
	private HashOperations<String,String,Event> hashOps;

	// TODO: Task 2

	public void saveRecord(Event event) {
		
		if (null == listOps.indexOf("events", event)) {
			listOps.rightPush("events", event);
		}
	}

	// TODO: Task 3

	public Integer getNumberOfEvents() {
		return listOps.size("events").intValue();
	}

	// TODO: Task 4
	public Event getEvent(Integer index) {
		return listOps.index("events", Long.valueOf(index));
	}

	public void addParticipants(Integer tickets, Integer index) {
		Event event = getEvent(index);
		event.setParticipants(event.getParticipants() + tickets);
		listOps.set("events", Long.valueOf(index), event);
	}
}
