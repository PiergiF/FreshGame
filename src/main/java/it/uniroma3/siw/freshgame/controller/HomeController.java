package it.uniroma3.siw.freshgame.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.freshgame.model.Credentials;
import it.uniroma3.siw.freshgame.model.Editor;
import it.uniroma3.siw.freshgame.model.Genres;
import it.uniroma3.siw.freshgame.model.Journalist;
import it.uniroma3.siw.freshgame.model.Platforms;
import it.uniroma3.siw.freshgame.model.Reader;
import it.uniroma3.siw.freshgame.model.Tags;
import it.uniroma3.siw.freshgame.service.ArticleService;
import it.uniroma3.siw.freshgame.service.CredentialsService;
import it.uniroma3.siw.freshgame.service.EditorService;
import it.uniroma3.siw.freshgame.service.JournalistService;
import it.uniroma3.siw.freshgame.service.ReaderService;
import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private JournalistService journalistService;

    @Autowired
    private EditorService editorService;

    @GetMapping(value = "/")
    public String getHomePage(Model model){
        //per la sub navbar
        model.addAttribute("genres", Genres.values());
        model.addAttribute("tags", Tags.values());
        model.addAttribute("platforms", Platforms.values());
        //model.addAttribute("couroselArticles", this.articleService.getRandomArticles(5));
        //model.addAttribute("orderedArticles", this.articleService.getAllArticlesOrderedByDate());
        //model.addAttribute("couroselArticles", this.articleService.getNArticlesOrderedByDate(5));
        model.addAttribute("couroselArticles", this.articleService.getNArticlesOrderedByDateTime(5));
        return "homePage.html";
        //return "homePage2.html";
    }

    @GetMapping("/logged/settingsPage/{loggedId}/{role}")
    public String getSettingsPage(@PathVariable("loggedId") Long userId, @PathVariable("role") String role, Model model) {
        
        UserDetails userDetails = GlobalController.getUserDetails(); //(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails != null){
            Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());

            if(role.equals("READER")){
                Reader reader = readerService.getReaderById(userId);
                if(reader != null){
                    model.addAttribute("user", reader);
                }
            }else if(role.equals("JOURNALIST")){
                Journalist journalist = journalistService.getJournalistById(userId);
                if(journalist!=null){
                    model.addAttribute("user", journalist);
                }
            }else if(role.equals("EDITOR")){
                Editor editor = editorService.getEditorById(userId);
                if(editor != null){
                    model.addAttribute("user", editor);
                }
            }

            model.addAttribute("credentials", credentials);
            model.addAttribute("newCredentials", new Credentials());
            return "logged/settingsPage.html";
        }
        else{
            return "/";
        }
    }


    @PostMapping("/settingsData")
    public String changeSettings(@Valid @ModelAttribute("credentials") Credentials nCredentials, BindingResult nCredentialsBindingResult,
                                @Valid @ModelAttribute("newCredentials") Credentials newCredentials, BindingResult newCredentialsBindingResult,
                                @RequestParam("changePassword") String changePassword, @RequestParam("changeUsername") String changeUsername,
                                @RequestParam(required = false, value = "name") String name, @RequestParam(required = false, value = "surname") String surname,
                                @RequestParam(required = false, value = "journalistImage") MultipartFile journalistImage) //@RequestParam("removeImage") String remove, 
    {
        //@ModelAttribute("user") Customer cuUser, @ModelAttribute("user") Chef cUser, @ModelAttribute("user") Administrator aUser)
        
    //passwordEncoder.matches cripta la password inserita e verifica se è uguale a quella già criptata sul database
        if(!nCredentialsBindingResult.hasErrors() && !newCredentialsBindingResult.hasErrors()) {

            UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());

            String role = credentials.getRole();

            if(role.equals("READER")){
                Reader reader = credentials.getReader();
                reader.setName(name);
                reader.setSurname(surname);
                readerService.saveReader(reader);
            }
            else if(role.equals("JOURNALIST")){
                Journalist existingJournalist = credentials.getJournalist();
                existingJournalist.setName(name);
                existingJournalist.setSurname(surname);

                //System.out.println("1111AOOOOOO");
                //System.out.println(file);
                if(journalistImage != null && !journalistImage.isEmpty()){
                //if(remove.equals("true")){
                    try {
                        byte[] byteFoto = journalistImage.getBytes();
                        existingJournalist.setImageBase64(Base64.getEncoder().encodeToString(byteFoto));
                    }catch (IOException e) {
                        //model.addAttribute("message", "Chef upload failed!");
                        return "redirect:/logged/settingsPage/" + existingJournalist.getId() + "/" + role ;
                    }
                }

                journalistService.save(existingJournalist);
            }
            else if(role.equals("EDITOR")){
                Editor editor = credentials.getEditor();
                editor.setName(name);
                editor.setSurname(surname);
                editorService.save(editor);
            }

            if(changePassword.equals("true")){
                credentials.setPassword(newCredentials.getPassword());
                credentialsService.saveCredentials(credentials, credentials.getRole());
            }
            

            if(changeUsername.equals("true")){
                credentials.setUsername(newCredentials.getUsername());
                credentialsService.saveCredentialsNoPassword(credentials);
                return "redirect:/logout";
            }
        }
        
        return "redirect:/";
    }

}
