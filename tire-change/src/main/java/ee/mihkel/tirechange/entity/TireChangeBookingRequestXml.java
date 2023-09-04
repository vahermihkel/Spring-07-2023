package ee.mihkel.tirechange.entity;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name = "london.tireChangeBookingRequest")
@Setter
@Getter
public class TireChangeBookingRequestXml {

    private String contactInformation;

}
