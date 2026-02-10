package fabricas;

import modelos.reserva.ReservaDataRequest;
import modelos.reserva.ReservaRequest;
import utils.FakerDadoReserva;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RestfulBookerReservaFabrica {

    public static ReservaRequest criarReserva() {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedTime = dateTimeFormatter.format(localDateTime);

        ReservaDataRequest reservaDataRequest = new ReservaDataRequest();
        reservaDataRequest.setCheckin(FakerDadoReserva.getFakerCheckIn().toString());
        reservaDataRequest.setCheckout(formattedTime);

        ReservaRequest reservaRequest = new ReservaRequest();
        reservaRequest.setFirstname(FakerDadoReserva.getFakerFirstName());
        reservaRequest.setLastname(FakerDadoReserva.getFakerLastName());
        reservaRequest.setTotalprice(FakerDadoReserva.getFakerTotalPrice());
        reservaRequest.setDepositpaid(FakerDadoReserva.getFakerDepositPaid());
        reservaRequest.setBookingdates(reservaDataRequest);
        reservaRequest.setAdditionalneeds(FakerDadoReserva.getFakerAdditionalNeeds());

        return reservaRequest;
    }
}
