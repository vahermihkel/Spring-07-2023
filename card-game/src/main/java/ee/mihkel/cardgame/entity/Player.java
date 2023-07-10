package ee.mihkel.cardgame.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {
    @Id
    private String name;

    private int highestScore;
    private Date creationDate;
}

// Game1 - Mihkel
// Game2 - Mihkel
// Game3 - Kaarel

// ContactData1 - Mihkel
// ContactData2 - Mihkel
