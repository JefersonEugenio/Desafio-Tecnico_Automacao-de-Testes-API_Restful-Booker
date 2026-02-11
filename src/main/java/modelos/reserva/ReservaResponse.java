package modelos.reserva;

import lombok.Data;

@Data
public class ReservaResponse {

    private String firstname;
    private String lastname;
    private double totalprice;
    private boolean depositpaid;
    private ReservaDataRequest bookingdates;
    private String additionalneeds;

}
