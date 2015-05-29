package de.fau.amos4.web;

import java.util.regex.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.fau.amos4.model.Client;
import de.fau.amos4.service.ClientRepository;
import de.fau.amos4.service.EmployeeRepository;
import de.fau.amos4.util.EmailSender;

public class LoginFormController {

    @Resource
    ClientRepository clientRepository;
    
    @RequestMapping("/Login")
    public String Login(@RequestParam(value = "username", required = true) String userName, @RequestParam(value = "password", required = true) String password)
    {
    	boolean LoginValid = false;
    	
    	// Check username & password: "ClientID", "PassID" format should be accepted. Eg.: "Client32" and "Pass32" are OK. 
        String patternString = "Client([1-9]*)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(userName);
        boolean matches = matcher.matches();
        
        if(matches)
        {
        	// UserName is in correct format -> check password.
        	String ClientNumber = matcher.group(1);
        	
        	// TODO: Implement real username and password handling
        	boolean PasswordIsValid = password.equals("Pass" + ClientNumber);
        	if(PasswordIsValid)
        	{
        		LoginValid = true;
        	}
        	else
        	{
        		LoginValid = false;
        	}
        }
    	
        if(LoginValid)
        {
        	// Valid Login -> Redirect to EmployeeList
    	    return "redirect:/EmployeeList";
        }
        else
        {
        	// Valid Login -> Redirect to Login Page
    	    return "redirect:/";
        }
    }
    

    @RequestMapping("/RegisterClient")
    public String RegisterClient(HttpServletRequest request, @RequestParam(value = "client", required = true) Client client) throws AddressException, MessagingException
    {
    	// Generate new confirmation string for the client
        client.generateConfirmationString();
        // Set client to inactive
        client.setActivated(false);
    	// Save new, unactivated client
        clientRepository.save(client);
        
        // Prepare and send email
        String ServerName = request.getServerName();
        String ConfirmationCode = client.getConfirmationString();
        String Content = "<a href=" + ServerName + "/ClientConfirmation?id=" + client.getId() + "&confirmation=" + ConfirmationCode + ">";        
        EmailSender sender = new EmailSender();        
        sender.SendEmail(client.getEmail(), "PersonalFragebogen 2.0 - Confirmation", Content);
        
        // Display login screen after
        return "RegistrationAlmostReady";
    }

    @RequestMapping("/ClientConfirmation")
    public String RegisterClient(@RequestParam(value = "id", required = true) long clientId, @RequestParam(value = "confirmation", required = true) String enteredConfirmationCode) throws AddressException, MessagingException
    {
    	 Client client = this.clientRepository.findOne(clientId);
    	 if(client.tryToActivate(enteredConfirmationCode))
    	 {
    		 // Save client after successful activation
    		 this.clientRepository.save(client);
    	 }
    
         return "redirect:/Login";
    }
}
