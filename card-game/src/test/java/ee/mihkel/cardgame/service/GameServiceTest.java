package ee.mihkel.cardgame.service;

import ee.mihkel.cardgame.model.Card;
import ee.mihkel.cardgame.model.Rank;
import ee.mihkel.cardgame.model.Suit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceTest {

    @Autowired
    GameService gameService;

    @BeforeEach
    void setUp() {
    }

            // givenRadius_whenCalculateArea_thenReturnArea
      //   givenGuessedTrue_whenValidating_thenAlreadyAnswered
    @Test
    public void returnsAlreadyAnswered_IfGuessedIsTrue() {
       String result = gameService.validate(true, "higher");
       Assertions.assertEquals("ALREADY_ANSWERED", result);
    }

    @Test
    public void returnsWrongInput_IfInputIsWrong() {
        String result = gameService.validate(false, "random");
        Assertions.assertEquals("WRONG_INPUT", result);
    }

    @Test
    public void returnsTimeout_IfPlayerHasWaited11Seconds() throws InterruptedException {
        gameService.getBaseCard();
        Thread.sleep(11000);
        String result = gameService.validate(false, "higher");
        Assertions.assertEquals("TIME_OUT", result);
    }

    @Test
    public void returnsEmptyString_IfPlayerDoesCorrectly() throws InterruptedException {
        gameService.getBaseCard();
        Thread.sleep(9000);
        String result = gameService.validate(false, "higher");
        Assertions.assertEquals("", result);
    }

    @Test
    public void returnsGameOver_IfPlayerWaitsThreeTimes11Seconds() throws InterruptedException {
        gameService.getBaseCard();
        Thread.sleep(11000);
        gameService.validate(false, "higher");
        gameService.validate(false, "higher");
        String result = gameService.validate(false, "higher");
        Assertions.assertEquals("GAME_OVER", result);
    }

    @Test
    public void returnsCardValueBetween2And10_IfPlayerGetsBaseCard() {
        Card card = gameService.getBaseCard();
        boolean isValueBetween = card.getValue() >=2 && card.getValue() <= 10;
        Assertions.assertTrue(isValueBetween);
    }

    @Test
    public void returnsCardSuitCorrectly_IfPlayerGetsBaseCard() {
        Card card = gameService.getBaseCard();
        List<Suit> suits = new ArrayList<>(Arrays.asList(Suit.values()));
        boolean isSuitCorrect = suits.contains(card.getSuit());
//        Suit[] suits1 = Suit.values();
//        boolean isSuitCorrect1 = Arrays.stream(suits1).anyMatch(e -> e.equals(card.getSuit()));
        Assertions.assertTrue(isSuitCorrect);
    }

    @Test
    public void returnsCardRankCorrectly_IfPlayerGetsBaseCard() {
        Card card = gameService.getBaseCard();
        List<Rank> suits = new ArrayList<>(Arrays.asList(Rank.values()));
        boolean isRankCorrect = suits.contains(card.getRank());
        Assertions.assertTrue(isRankCorrect);
    }

    @Test
    public void returnsWrong_IfPlayerGuessesLower_WhenCardValueIsTwo() {
        Card card = gameService.getBaseCard();
        while (card.getValue() != 2) {
           gameService.baseCard = null;
           card = gameService.getBaseCard();
        }
        String result = gameService.checkIfCorrect("lower");
        Assertions.assertEquals("WRONG", result);
    }

    @Test
    public void returnsWrong_IfPlayerGuessesHigher_WhenCardValueIsTen() {
        Card card = gameService.getBaseCard();
        while (card.getValue() != 10) {
            gameService.baseCard = null;
            card = gameService.getBaseCard();
        }
        String result = gameService.checkIfCorrect("higher");
        Assertions.assertEquals("WRONG", result);
    }

    @Test
    public void returnsGameOver_IfPlayerGuessesThreeTimesHigher_WhenCardValueIsTen() {
        Card card = gameService.getBaseCard();
        while (card.getValue() != 10) {
            gameService.baseCard = null;
            card = gameService.getBaseCard();
        }
        gameService.checkIfCorrect("higher");
        gameService.baseCard = card;
        gameService.checkIfCorrect("higher");
        gameService.baseCard = card;
        String result = gameService.checkIfCorrect("higher");
        Assertions.assertEquals("GAME_OVER", result);
    }
}
