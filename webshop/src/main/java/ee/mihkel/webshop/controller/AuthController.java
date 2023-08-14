package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.dto.security.LoginData;
import ee.mihkel.webshop.dto.security.AuthToken;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    BCryptPasswordEncoder encoder;

    @PostMapping("login")
    public ResponseEntity<AuthToken> login(@RequestBody LoginData loginData) throws RuntimeException {
        // Login
        Person person = personRepository.findByPersonalCode(loginData.getPersonalCode());
        if (!encoder.matches(loginData.getPassword(),person.getPassword())) {
            throw new RuntimeException("Parool ei ole õige");
        }
        return new ResponseEntity<>(tokenGenerator.getToken(person), HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<AuthToken> signup(@RequestBody Person person) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        person.setPassword(encoder.encode(person.getPassword()));
        Person savedPerson = personRepository.save(person);
        return new ResponseEntity<>(tokenGenerator.getToken(savedPerson), HttpStatus.OK);
    }
}

// Õigused
// Refresh token

// OAuth
// Facebooki ja Google kontoga sisselogimine
// Unit testing

// Frontend:
// 1. Errorite handle-mine - kui juhtub backendist tulles error, siis näitab seda
// 2. Globaalne muutuja - Subject() rxjs
// 3. URLi kaitse - Pipe
// 4. Ostukorvi kogusumma globaalses muutujas
