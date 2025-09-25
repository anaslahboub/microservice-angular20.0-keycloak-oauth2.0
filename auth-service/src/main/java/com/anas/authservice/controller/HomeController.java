package com.anas.authservice.controller;

import com.anas.authservice.model.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        addUserInfoToModel(model, authentication);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Retourne la page login.html
    }

    @GetMapping("/auth")
    public Authentication auth(Authentication auth) {
        return auth;
    }

    @GetMapping("/product")
    public String getProducts(Model model, Authentication authentication) {
        try {
            // Récupérer l'authentification
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            DefaultOidcUser oidcUser = (DefaultOidcUser) token.getPrincipal();

            // Récupérer le token d'accès
            String accessToken = oidcUser.getIdToken().getTokenValue();

            // Appeler l'API products
            RestClient restClient = RestClient.create("http://localhost:8091");
            List<Product> products = restClient.get()
                    .uri("/products")
                    .headers(httpHeaders -> httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Product>>() {});

            // Ajouter les produits au modèle
            model.addAttribute("products", products);

            // Ajouter les informations utilisateur
            model.addAttribute("username", oidcUser.getFullName());
            model.addAttribute("userEmail", oidcUser.getEmail());
            model.addAttribute("isAuthenticated", true);

            // Calculer les statistiques
            calculateStatistics(model, products);

        } catch (Exception e) {
            // En cas d'erreur, utiliser une liste vide
            model.addAttribute("products", List.of());
            model.addAttribute("error", "Unable to load products: " + e.getMessage());
            calculateStatistics(model, List.of());
        }

        return "product";
    }

    private void calculateStatistics(Model model, List<Product> products) {
        int totalProducts = products.size();
        int inStock = (int) products.stream().filter(p -> p.getQuantity() > 10).count();
        int lowStock = (int) products.stream().filter(p -> p.getQuantity() > 0 && p.getQuantity() <= 10).count();
        int outOfStock = (int) products.stream().filter(p -> p.getQuantity() == 0).count();

        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("inStock", inStock);
        model.addAttribute("lowStock", lowStock);
        model.addAttribute("outOfStock", outOfStock);
    }
    // Méthode utilitaire pour ajouter les infos utilisateur au modèle
    private void addUserInfoToModel(Model model, Authentication authentication) {
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
    }
}