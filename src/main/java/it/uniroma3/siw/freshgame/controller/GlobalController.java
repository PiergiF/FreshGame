package it.uniroma3.siw.freshgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.freshgame.model.Credentials;
import it.uniroma3.siw.freshgame.service.CredentialsService;

@ControllerAdvice
public class GlobalController {
    @Autowired
    private CredentialsService credentialsService;

    @ModelAttribute("userDetails")

    static public UserDetails getUserDetails() {
        UserDetails userDetails = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return userDetails;
    }

    @ModelAttribute("accountRole")

    public String getUserRole(){
        Credentials credentials = null;
        UserDetails userDetails = getUserDetails();
        if(userDetails!=null){
            credentials = credentialsService.getCredentials(userDetails.getUsername());
            return credentials.getRole();
        }
        return "ROLE_ANONYMOUS";
    }

    @ModelAttribute("loggedId")

    public Long getLoggedId(){
        Credentials credentials = null;
        UserDetails ud=getUserDetails();
        if(ud!=null){
            credentials = credentialsService.getCredentials(ud.getUsername());
            if(getUserRole().equals("READER")){
                return credentials.getReader().getId();
            }else if(getUserRole().equals("JOURNALIST")){
                return credentials.getJournalist().getId();
            }else if(getUserRole().equals("EDITOR")){
                return credentials.getEditor().getId();
            }
            return null;
        }
        return null;
    }
}
