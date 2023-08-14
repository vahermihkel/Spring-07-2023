package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.dto.security.LoginData;
import ee.mihkel.webshop.dto.security.Token;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    PersonRepository personRepository;

    // TokenParser     JwtAuthFilter
    // TokenGenerator   JwtAuthService
    @Autowired
    TokenGenerator tokenGenerator;

    @PostMapping("login")
    public ResponseEntity<Token> login(@RequestBody LoginData loginData) {
        // Login
        Person person = personRepository.findByPersonalCode(loginData.getPersonalCode());
        return new ResponseEntity<>(tokenGenerator.getToken(person), HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<Token> signup(@RequestBody Person person) {
        Person savedPerson = personRepository.save(person);
        return new ResponseEntity<>(tokenGenerator.getToken(savedPerson), HttpStatus.OK);
    }
}
