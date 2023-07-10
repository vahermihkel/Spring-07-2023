package ee.mihkel.cardgame.model;

import lombok.Data;

@Data
public class Card {
    private Suit suit;
    private Rank rank;
    private int value;

    public Card() {
        this.suit = Suit.getRandom();
        this.rank = Rank.getRandom();
        this.value = this.rank.getValue();
    }
}
