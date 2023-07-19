package ee.mihkel.webshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders") // PostgreSQL sees on "order" ja "user" reserveeritud
@SequenceGenerator(name = "seq", initialValue = 5123123, allocationSize = 1)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq") // EI TULEKS 1,2,3,4,5   5123123
    private Long id;
    private Date creationDate;
    private String paymentState;
    private double totalSum;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<OrderRow> orderRow;
    @ManyToOne
    private Person person;

}
