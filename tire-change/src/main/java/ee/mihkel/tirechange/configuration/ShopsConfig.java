package ee.mihkel.tirechange.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties("tire-change")
public class ShopsConfig {

    private List<Shop> shops;

    @Getter
    @Setter
    public static class Shop {
        private String name;
        private String timesUrl;
        private String applicationType;
        private String bookingUrl;
        private String bookMethod;
        private String address;
        private boolean privateCar;
        private boolean truck;
    }
}
