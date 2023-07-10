package ee.mihkel.cardgame.model;

import java.util.Random;

public enum Suit {
    HEART, SPADE, DIAMOND, CLUB;

    public static Suit getRandom() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
