package it.uniroma3.siw.freshgame.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.print.DocFlavor.READER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.freshgame.model.Credentials;
import it.uniroma3.siw.freshgame.model.Reader;
import it.uniroma3.siw.freshgame.service.CredentialsService;
import it.uniroma3.siw.freshgame.service.ReaderService;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private ReaderService readerService;

    @GetMapping("/loginPage")
    public String getLoginPage() {
        return "loginPage.html";
    }

    @GetMapping(value = "/registrationPage")
    public String getRegistrationPage(Model model) {
		model.addAttribute("credentials", new Credentials());
        return "registrationPage.html";
    }

    @PostMapping("/registrationData")
    public String registerUser(@RequestParam(required = false, name = "name") String name, 
                                @RequestParam(required = false, name = "surname") String surname, 
                                @RequestParam(required = false, name = "gamertag") String gamertag, 
                                //@RequestParam(required = false, name = "dateOfBirth") LocalDate dateOfBirth,
                                @RequestParam(required = false, name = "image") MultipartFile file,
                                @Valid @ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult,
                                //@RequestParam("role") String role,
                                Model model) {

        //se name è presente nell'invio al controller ma senza valore equivale ad una stringa vuota, se non è proprio presente equivale a NULL

        //provvisorio
        String role = "READER";
        
        // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!credentialsBindingResult.hasErrors()) {
            if(role.equals("READER")){
                Reader reader = new Reader(name, surname, gamertag);
                readerService.saveReader(reader);
                credentials.setReader(reader);
            }
            /*
            else if(role.equals("CHEF")){
                Chef chef = new Chef(name, surname, email, dateOfBirth);

                try {
                byte[] byteFoto = file.getBytes();
                chef.setImageBase64(Base64.getEncoder().encodeToString(byteFoto));
                chefService.saveChef(chef);
                credentials.setChef(chef);
                } catch (IOException e) {
                    model.addAttribute("message", "Chef upload failed!");
                    return "/registrationPage";
                }

            }
            */
            credentialsService.saveCredentials(credentials, role);
            return "redirect:/loginPage?registration=true";
        }
        return "registrationPage.html";
    }

}
