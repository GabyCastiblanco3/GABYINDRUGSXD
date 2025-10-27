package com.example.Indrugs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomePageController {
    @GetMapping({"/","/index"})
    public String mostrarInicio(){
        return "index";
    }
    @GetMapping({"/10.pagina_mapa_nav"})
    public String mostrarMapaNav(){
        return "10.pagina_mapa_nav";
    }
    @GetMapping({"/7.pagina_sobre_nosotros"})
    public String mostrarNosotros(){
        return "7.pagina_sobre_nosotros";
    }
}
