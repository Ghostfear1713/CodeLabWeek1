package dk.cphbusiness.flightdemo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class DTOs {
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    protected static class FlightCollectionDTO {
        private FlightDTO[] data;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    protected static class FlightDTO {
        private String flight_date;
        private String flight_status;
        private AirportTime departure;
        private AirportTime arrival;
        private AirplaneDTO flight;
        private AirlineDTO airline;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    protected static class AirportTime {
        private String airport;
        private String timezone;
        private String iata;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private OffsetDateTime scheduled;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    protected static class AirlineDTO {
        private String name;
        private String iata;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    protected static class AirplaneDTO {
        private String number;
        private String iata;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    protected static class FlightInfo {
        private String name;
        private String iata;
        private String airline;
        private Duration duration;
        private LocalDateTime departure;
        private LocalDateTime arrival;
        private String origin;
        private String destination;
    }
}
