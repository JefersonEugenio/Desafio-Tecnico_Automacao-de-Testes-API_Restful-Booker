package modelos.reserva;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservaRequest {

    private String firstname;
    private String lastname;
    private Double totalprice;
    private Boolean depositpaid;
    private ReservaDataRequest bookingdates;
    private String additionalneeds;

}
