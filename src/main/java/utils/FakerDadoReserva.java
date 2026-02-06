package utils;

import net.datafaker.Faker;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FakerDadoReserva {

    public static Faker faker = new Faker(new Locale("pt-BR"));

    public static String getFakerFirstName() {
        return faker.name().firstName();
    }

    public static String getFakerLastName() {
        return faker.name().lastName();
    }

    public static double getFakerTotalPrice() {
        return faker.number().randomDouble(2,0, 1000);
    }

    public static boolean getFakerDepositPaid() {
        return faker.bool().bool();
    }

    public static Date getFakerCheckIn() {
        return faker.date().past(365, TimeUnit.DAYS);
    }

    public static String getFakerAdditionalNeeds() {
        return faker.lorem().word();
    }

}
