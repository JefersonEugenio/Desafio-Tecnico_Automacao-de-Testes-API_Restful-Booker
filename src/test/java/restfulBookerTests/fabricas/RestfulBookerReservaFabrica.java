package restfulBookerTests.fabricas;

import restfulBookerTests.modelos.reserva.ReservaDataRequest;
import restfulBookerTests.modelos.reserva.ReservaRequest;
import utils.FakerDadoReserva;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RestfulBookerReservaFabrica {

    public static ReservaRequest criarReserva() {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedTime = dateTimeFormatter.format(localDateTime);

        ReservaDataRequest reservaDataRequest = new ReservaDataRequest();
        reservaDataRequest.setCheckin(FakerDadoReserva.getFakerCheckIn());
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
