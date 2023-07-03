package ee.mihkel.lemmikloomad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
