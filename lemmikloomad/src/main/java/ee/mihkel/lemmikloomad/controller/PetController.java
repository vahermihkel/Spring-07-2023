package ee.mihkel.lemmikloomad.controller;

import ee.mihkel.lemmikloomad.dto.OwnerDTO;
import ee.mihkel.lemmikloomad.entity.Owner;
import ee.mihkel.lemmikloomad.repository.OwnerRepository;
import ee.mihkel.lemmikloomad.entity.Pet;
import ee.mihkel.lemmikloomad.repository.PetRepository;
import ee.mihkel.lemmikloomad.service.OwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PetController {

    @Autowired // otseühendus selle failiga ehk kui tekitatakse see praegune fail, siis
    PetRepository petRepository;// tekitatakse koheselt ka autowire-rdatud fail siia külge
                    // Dependency Injection

    @Autowired
    OwnerRepository ownerRepository;

    @GetMapping("pet/add") // localhost:8080/pet/add?name=Koer&weight=2000
    public List<Pet> addPet(
            @RequestParam String name,
            @RequestParam double weight
    ) {
        Pet pet = new Pet(name, weight);
        // lisame andmebaasi
        petRepository.save(pet);
        //returnime: võtta kõik mis on juba andmebaasis
        return petRepository.findAll();
    }

    @GetMapping("owner/add") // localhost:8080/owner/add?name=Ott
    public List<Owner> addOwner(
            @RequestParam String name
    ) {
        Owner owner = new Owner();
        owner.setName(name);
        // lisame andmebaasi
        ownerRepository.save(owner);
        //returnime: võtta kõik mis on juba andmebaasis
        return ownerRepository.findAll();
    }

    @GetMapping("owner/add-pet") // localhost:8080/owner/add-pet?owner=Ott&pet=Koer
    public Owner addPetToOwner(
            @RequestParam String owner,
            @RequestParam String pet
    ) {
        Owner found = ownerRepository.findById(owner).get();
        Pet foundPet = petRepository.findById(pet).get();

        List<Pet> pets = found.getPets();
        pets.add(foundPet);
        found.setPets(pets);

        // lisame andmebaasi
        return ownerRepository.save(found); // .save nii lisab uue andmebaasi kui ka asendab andmebaasis
    }

//    @GetMapping("owners") // localhost:8080/owners
//    public List<OwnerDTO> getAllOwners() {
//        List<Owner> owners = ownerRepository.findAll();
//        List<OwnerDTO> ownerDTOs = new ArrayList<>();
//        for (Owner o: owners) {
//            OwnerDTO ownerDTO = new OwnerDTO();
//            ownerDTO.setName(o.getName());
//            ownerDTO.setPets(o.getPets());
//            ownerDTOs.add(ownerDTO);
//        }
//        return ownerDTOs;
//    }
    @Autowired
    OwnerService ownerService;

    @GetMapping("owners") // localhost:8080/owners
    public List<OwnerDTO> findAllOwners() {
        return ownerService.findAllOwners();
    }

}


// DTO

// Services
//   Controller - Võtab vastu päringuid ja tagastab vastuse
//   Service - Teeb musta tööd, õhendab Controller

// @Autowired --> Beanide loogika

// Custom Repository käsud

// PUT, POST, GET, DELETE - Postman

// API otspunktide reeglid - Best practices

