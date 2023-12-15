package vttp.ssf.assessment.eventmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
@RequestMapping("/events")
public class RegistrationController {
    
    @Autowired
    DatabaseService databaseSvc;

    // TODO: Task 6
    @GetMapping("/register/{index}")
    public String register(@PathVariable(name="index") Integer index, Model model) {
        model.addAttribute("event", databaseSvc.getEvent(index));
        return "eventregister";
    }

    // TODO: Task 7
}
