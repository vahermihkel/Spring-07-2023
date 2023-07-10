package ee.mihkel.cardgame.model;

import java.util.Random;

public enum Rank {
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
    NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(10);

    private final int value;

    Rank(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }

    public static Rank getRandom() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
