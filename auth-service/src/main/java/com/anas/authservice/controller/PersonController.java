package com.anas.authservice.controller;

import com.anas.authservice.entites.Person;
import com.anas.authservice.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;

    @GetMapping
    public String persons(Model model, Authentication authentication) {
        List<Person> persons = personRepository.findAll();
        model.addAttribute("persons", persons);

        // Ajouter les infos utilisateur
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());

            if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
                model.addAttribute("userEmail", oidcUser.getEmail());
                model.addAttribute("userFullName", oidcUser.getFullName());
                model.addAttribute("userPicture", oidcUser.getAttribute("picture"));
            }
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }

        return "persons";
    }
}