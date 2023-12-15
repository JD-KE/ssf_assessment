package vttp.ssf.assessment.eventmanagement.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Registration {
    
    @NotBlank(message="Please enter your full name.")
    @Size(min=5, max = 25, message = "Full name must be between 5-25 characters")
    private String fullName;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    
    @Email
    @NotBlank(message = "Email is required.")
    @Size(max = 50, message = "Maximum of 50 characters allowed.")
    private String email;

    @Pattern(regexp = "(8|9)[0-9]{7}", message = "Invalid phone number entered.")
    private String phone;

    @Min(value = 1, message = "Minimum of 1 ticket to be requested.")
    @Max(value = 3, message = "Maximum of 3 tickets allowed to be requested.")
    private Integer tickets;
}
