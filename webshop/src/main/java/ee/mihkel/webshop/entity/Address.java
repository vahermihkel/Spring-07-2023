package ee.mihkel.webshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank // ei tohi jutumärgid ja selle sees lihtsalt tühik olla
    private String country;

    @NotNull // ei tohi ilma seda võtit lisamata lisada
    private String county;

    @NotEmpty // ei tohi tühjad jutumärgid olla aga ei tohi ka null olla
    private String street;
    private String number;
    private String postalIndex;
}
