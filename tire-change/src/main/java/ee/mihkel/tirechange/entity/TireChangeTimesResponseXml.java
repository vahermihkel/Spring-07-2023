package ee.mihkel.tirechange.entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Setter;

import java.util.List;

@XmlRootElement(name = "tireChangeTimesResponse")
@Setter
public class TireChangeTimesResponseXml {

    private List<Time> availableTimes;

    @XmlElement(name = "availableTime")
    public List<Time> getAvailableTimes() {
        return availableTimes;
    }
}
