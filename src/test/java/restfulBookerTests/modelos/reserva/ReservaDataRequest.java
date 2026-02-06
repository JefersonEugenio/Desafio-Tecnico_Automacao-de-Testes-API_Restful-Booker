package restfulBookerTests.modelos.reserva;

import lombok.Data;

import java.util.Date;

@Data
public class ReservaDataRequest {

    private Date checkin;
    private String checkout;

}
