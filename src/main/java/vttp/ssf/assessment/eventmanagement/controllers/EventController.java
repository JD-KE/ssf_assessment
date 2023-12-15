package vttp.ssf.assessment.eventmanagement.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
@RequestMapping("/events")
public class EventController {

	@Autowired
	DatabaseService databaseSvc;

	//TODO: Task 5
	@GetMapping("/listing")
	public String displayEvents(Model model) {

		List<Event> events = new ArrayList<>();

		for (int i = 0; i < databaseSvc.getNumberOfEvents(); i++) {
			events.add(databaseSvc.getEvent(i));
		}
		

		model.addAttribute("events", events);

		return "eventlisting";
	}

	@GetMapping("/listing/register/{index}")
	public String registerEvent(@PathVariable(name="index") Integer index) {
		

		return String.format("redirect:/events/register/%d",index);

	}


}
