package vttp.ssf.assessment.eventmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.assessment.eventmanagement.models.Registration;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
@RequestMapping
public class RegistrationController {
    
    @Autowired
    DatabaseService databaseSvc;

    // TODO: Task 6
    @GetMapping("/events/register/{index}")
    public String register(@PathVariable(name="index") Integer index, Model model, HttpSession session) {
        model.addAttribute("event", databaseSvc.getEvent(index-1));
        model.addAttribute("registration", new Registration());
        session.setAttribute("index", index - 1);
        return "eventregister";
    }

    // TODO: Task 7
    @PostMapping("/registration/register")
    public ModelAndView processRegistration(@Valid @ModelAttribute Registration registration, BindingResult result, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        Integer index = (Integer) session.getAttribute("index");
        if(result.hasErrors()){
            mav.addObject("event", databaseSvc.getEvent(index));
            mav.setViewName("eventregister");
            return mav;
        }
        

        if (!databaseSvc.ageValid(registration.getBirthDate())) {
            mav.addObject("error", "User is below 21 years old. Only users who are 21 and above can register for events.");
            mav.setViewName("ErrorRegistration");
            session.invalidate();
            return mav;
        }

        if (databaseSvc.sizeExceeded(registration.getTickets(), index)) {

            mav.addObject("error", "Your request for tickets exceeded the event size.");
            mav.setViewName("ErrorRegistration");
            session.invalidate();
            return mav;
        }

        databaseSvc.addParticipants(registration.getTickets(), index);
        mav.addObject("event", databaseSvc.getEvent(index));
        mav.setViewName("SuccessfulRegistration");
        session.invalidate();

        return mav;
    }
}
