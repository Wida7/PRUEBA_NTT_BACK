package com.prueba.spring.app.Services;

import com.prueba.spring.app.Models.Persona;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class DataService {

    //Modelo
    private List<Persona> personas;

    //Servicio
    public DataService() throws IOException {
        loadPersonas();
    }

    //Carga y mapea el json "db", en una lista teniendo el cuenta mi modelo Persona
    private void loadPersonas() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Persona>> typeReference = new TypeReference<List<Persona>>() {};
        InputStream inputStream = new ClassPathResource("data.json").getInputStream();
        personas = mapper.readValue(inputStream, typeReference);
    }

    //Retorna la lista personas
    public List<Persona> getPersonas() {
        return personas;
    }
}
