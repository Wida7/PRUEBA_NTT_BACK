package com.prueba.spring.app.Controllers;

import com.prueba.spring.app.Models.Persona;
import com.prueba.spring.app.Models.Response;
import com.prueba.spring.app.Services.DataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private DataService dataService;

    @GetMapping
    public List<Persona> getPersonas() {
        return dataService.getPersonas();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/buscar")
    public ResponseEntity<Response> getPersona(
            @RequestParam String tipoIdentificacion,
            @RequestParam String identificacion) {

        // Validación de parámetros (Error 400)
        if (tipoIdentificacion == null || tipoIdentificacion.isEmpty() || identificacion == null
                || identificacion.isEmpty()) {
            throw new InvalidParameterException(
                    "(400) Los parámetros tipoIdentificacion e identificacion son obligatorios y no pueden estar vacíos.");
        }
        try {

            // Instancio mi modelo Persona y filtro por los parametros obteniendo un resultado.
            Optional<Persona> persona = dataService.getPersonas().stream()
                    .filter(p -> p.getTipoIdentificacion().equals(tipoIdentificacion)
                            && p.getIdentificacion().equals(identificacion))
                    .findFirst();

            // Valido si obtengo la persona de lo contrario manejo error 404 Not founf
            if (persona.isPresent()) {
                Response personaEncontrada = new Response();
                personaEncontrada.setResponse("(200) Persona encontrada");
                personaEncontrada.setPersona(persona.get());
                return ResponseEntity.ok(personaEncontrada);
            } else {
                Response personaNoEncontrada = new Response();
                personaNoEncontrada.setResponse("(404) No se encontró la persona con los parámetros proporcionados");
                return ResponseEntity.ok(personaNoEncontrada);
            }
        } catch (Exception e) {
            // Manejo internal server error 500
            throw new InvalidParameterException(
                    "(500) Interno");
        }

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + ex.getMessage());
    }
}
