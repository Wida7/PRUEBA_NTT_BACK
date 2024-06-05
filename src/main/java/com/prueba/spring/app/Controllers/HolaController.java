package com.prueba.spring.app.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HolaController {
    @GetMapping("/")
    String hola(){
        return "Hola mundo";
    }
    
}
