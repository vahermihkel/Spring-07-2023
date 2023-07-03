package ee.mihkel.lemmikloomad;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController // kirjutamise ajal enter /  alt+enter ja valida õige
public class StringController {
    List<String> cars = new ArrayList<>(Arrays.asList("BMW", "Nobe", "Tesla"));

//    String[] cars2 = {"BMW", "Nobe", "Tesla"}; READ-ONLY

    @GetMapping("car/add/{newCar}")
    public List<String> addCar(@PathVariable String newCar) {
        cars.add(newCar);
        return cars;
    }

    @GetMapping("car/delete/{index}") // localhost:8080/car/delete/1
    public List<String> deleteCar(@PathVariable int index) {
        cars.remove(index);
        return cars;
    }

    @GetMapping("car/view")
    public List<String> viewCars() {
        return cars;
    }

    @GetMapping("hi") // localhost:8080/hi
    public String helloWorld() {
        return "Hello world at " + new Date();
    }

    @GetMapping("hi/{name}") // localhost:8080/hi/Mihkel
    public String helloName(@PathVariable String name) {
        return "Hello " + name;
    }

    @GetMapping("hi/{name}/{telephone}/{address}/{height}/{weight}") // localhost:8080/hi/Mihkel/51321/Tammsaare11/180/80
    public String helloPerson(
            @PathVariable String name,
            @PathVariable String telephone,
            @PathVariable String address,
            @PathVariable String height,
            @PathVariable String weight
    ) {
        return "Hello " + name;
    }

    @GetMapping("hello") // localhost:8080/hello?name=Mihkel&telephone=51321&address=Tammsaare11&height=180&weight=80
    public String helloPerson2(
            @RequestParam String name,
            @RequestParam String telephone,
            @RequestParam String address,
            @RequestParam String height,
            @RequestParam String weight
    ) {
        //Person person = new Person(name,telephone,address, height, weight);
        Person person = new Person();
        person.setName(name);
        person.setAddress(address);
        return "Hello " + name;
    }
}
