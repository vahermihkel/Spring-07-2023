package ee.mihkel.webshop.entity;

import jakarta.persistence.*;
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
@Table(name = "orders") // PostgreSQL sees on "order" ja "user" reserveeritud
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date creationDate;
    private String paymentState;
    private double totalSum;
    @OneToOne
    private OrderRow orderRow;
    @ManyToOne
    private Person person;

}
