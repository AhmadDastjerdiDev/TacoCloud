package tacos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.CustomLog;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Table("Taco_Cloud_Order")
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("placed_at")
    private Date placedAt;

    @Column("delivery_name")
    @NotBlank(message = "Delivery name is required")
    private String deliveryName;

    @Column("delivery_street")
    @NotBlank(message = "Street is required")
    private String deliveryStreet;

    @Column("delivery_city")
    @NotBlank(message = "City is required")
    private String deliveryCity;

    @Column("delivery_state")
    @NotBlank(message = "State is required")
    private String deliveryState;

    @Column("delivery_zip")
    @NotBlank(message = "Postal code is required")
    private String deliveryZip;


    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])(\\/)([2-9][0-9])$",
            message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        tacos.add(taco);
    }
}
