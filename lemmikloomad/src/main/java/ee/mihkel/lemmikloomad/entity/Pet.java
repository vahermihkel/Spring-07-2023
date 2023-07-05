package ee.mihkel.lemmikloomad.entity;

import jakarta.persistence.Entity; // Spring 2.7xxx    javax.persistence.Entity
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Pet {
    @Id
    private String name;
    private double weight;
}

//
//
//    Tee uus veebiprojekt. Tee klass Lemmikloom (nimetus, kaal) ning võimalda teda andmebaasi lisada. Lisa klass Omanik, kes seo Lemmikloomaga üks-mitmele seose kaudu. Võimalda Omanikule juurde lisada lemmikloomi.
//
//        Võimalda küsida Lemmikloomade koguarvu ühe omaniku osas (sisendiks omanik ja väljundiks arv). Võimalda ühe omaniku kõige suurema kaaluga lemmiklooma leida ja kõige väiksema kaaluga. Võimalda sisestada minimaalne kaal ja maksimaalne kaal ning väljasta kõik lemmikloomad selles vahemikus.
//
//        Tee uus andmebaasitabel Kliinik, mis omab üks-mitmele seost lemmikloomaga. Võimalda kliinikust otsida kindlat lemmiklooma tema nimetuse järgi. Võimalda API otspunktist anda vaid üks kliinik - kellel on kõige rohkem lemmikloomi.
