package com.qr_generator.qr_code_gen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/prodotti")
public class ProdottoController {

    // Simuliamo un "database"

  

    @GetMapping
    public String getTuttiIProdotti() {
        return "prodotti";
    }

   
}
