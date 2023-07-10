package ee.mihkel.cardgame.service;

import ee.mihkel.cardgame.entity.Game;
import ee.mihkel.cardgame.entity.Player;
import ee.mihkel.cardgame.model.Card;
import ee.mihkel.cardgame.repository.GameRepository;
import ee.mihkel.cardgame.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class GameService {
    Card baseCard;
    int correctAnswers;
    LocalDateTime roundStartTime;

    Player currentPlayer;

    long gameStartTime;

    int lives;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    public void getPlayer(String playerName) {
        Optional<Player> player = playerRepository.findById(playerName);
        if (player.isPresent()) {
            currentPlayer = player.get();
        } else {
            Player newPlayer = new Player();
            newPlayer.setCreationDate(new Date());
            newPlayer.setName(playerName);
            newPlayer.setHighestScore(0);
            currentPlayer = playerRepository.save(newPlayer);
        }
    }

    public String validate(boolean guessed, String input) {
        if (guessed) {
            return "ALREADY_ANSWERED";
        }
        if (!input.equals("lower") && !input.equals("equals")
                && !input.equals("higher")) {
            return "WRONG_INPUT";
        }
        LocalDateTime guessTime = LocalDateTime.now();
        if (guessTime.isAfter(roundStartTime.plusSeconds(10))) {
            lives--;
            if (lives == 0) {
                saveGameToDatabase();
                return "GAME_OVER";
            }
            return "TIME_OUT";
        }
        return "";
    }

    public Card getBaseCard() {
        roundStartTime = LocalDateTime.now();
        if (baseCard == null) {
            baseCard = new Card();
            gameStartTime = System.currentTimeMillis();
            correctAnswers = 0;
            lives = 3;
        }
        return baseCard;
    }

    public String checkIfCorrect(String input) {
        Card newCard = new Card();
        if (input.equals("lower") && baseCard.getValue() > newCard.getValue() ||
                input.equals("equals") && baseCard.getValue() == newCard.getValue() ||
                input.equals("higher") && baseCard.getValue() < newCard.getValue()) {
            correctAnswers++;
            baseCard = newCard;
            return "CORRECT";
        }
        lives--;
        if (lives == 0) {
            saveGameToDatabase();
            return "GAME_OVER";
        }
        baseCard = newCard;
        return "WRONG";
    }

    private void saveGameToDatabase() {
        Game game = new Game();
        game.setCorrectAnswers(correctAnswers);
        game.setDuration(System.currentTimeMillis() - gameStartTime);
        game.setCreationDate(new Date());
        game.setPlayer(currentPlayer);
        gameRepository.save(game);
    }
}
