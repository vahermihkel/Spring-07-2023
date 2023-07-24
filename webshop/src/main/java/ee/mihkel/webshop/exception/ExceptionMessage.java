package ee.mihkel.webshop.exception;

import lombok.Data;
import java.util.Date;

@Data
public class ExceptionMessage {
    private Date date;
    private String message;
    private int httpStatusCode;
}
