package ee.mihkel.webshop.dto.everypay;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentCheck {
    public String account_name;
    public String order_reference;
    public Object email;
    public String customer_ip;
    public String customer_url;
    public Date payment_created_at;
    public double initial_amount;
    public double standing_amount;
    public String payment_reference;
    public String payment_link;
    public String api_username;
    public ProcessingError processing_error;
    public Object warnings;
    public int stan;
    public int fraud_score;
    public String payment_state;
    public String payment_method;
    public ObDetails ob_details;
    public Date transaction_time;
}

@Data
class ObDetails{
    public Object error;
    public Object debtor_iban;
    public String creditor_iban;
    public String ob_payment_reference;
    public String ob_payment_state;
}

@Data
class ProcessingError{
    public int code;
    public String message;
}
