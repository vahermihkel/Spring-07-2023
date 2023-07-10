package ee.mihkel.cardgame.controller;

import ee.mihkel.cardgame.model.Card;
import ee.mihkel.cardgame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class GameController {

    boolean guessed;

    @Autowired
    GameService gameService;

    @GetMapping("start/{playerName}") // localhost:8080/start/Mihkel
    public Card startGame(@PathVariable String playerName) {
        guessed = false;
        gameService.getPlayer(playerName);
        return gameService.getBaseCard();
    }

    @GetMapping("guess/{input}")
    public String guess(@PathVariable String input) {
        String error = gameService.validate(guessed, input);
        if (!error.equals("")) {
            return error;
        }
        guessed = true;
        return gameService.checkIfCorrect(input);
    }
}

// a. Uus kontroller: DatabaseController
// b. Tehke ühendus nii GameRepository'ga kui ka PlayerRepository'ga
// Custom päringud järgmiselt:
// 1. Tagastatakse kõik mängijad
// 2. Tagastatakse kõik mängud
// 3. Tagatatakse kõik mängud correctanswers järjekorras
// 4. Tagatatakse kõik mängijad high-score järjekorras
// 5. Tagastataske kõik selle mängija mängud
// 6. Tagastataske kõik selle mängija mängud correctAnswers järjekorras
// 7. Tagasta kõik mängud millel on vähemalt 2 õiget vastust
// 8. Kõige suurema correctanswers mäng
// 9. Kõige suurema highscore-ga mängija
// 10. Top3 correctanswers mängud
