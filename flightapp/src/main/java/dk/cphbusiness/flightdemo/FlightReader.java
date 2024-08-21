package dk.cphbusiness.flightdemo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dk.cphbusiness.utils.Utils;
import lombok.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class FlightReader {

    public static void main(String[] args) {
        FlightReader flightReader = new FlightReader();
        try {
            List<DTOs.FlightDTO> flightList = flightReader.getFlightsFromFile("flights.json");

            //Calculate the average flight time for Lufthansa by calling the calculateAverageFlightTimeForAirline method.
            double averageFlightTime = flightReader.calculateAverageFlightTimeForAirline(flightList, "Lufthansa");
            System.out.println("Average flight time for Lufthansa: " + averageFlightTime + " minutes");

            long totalFlightTime = flightReader.calculateTotalFlightTimeForASpecificAirline(flightList, "Lufthansa");
            System.out.println("Total Flight time for Lufthansa: " + totalFlightTime + " minutes");

//            List<DTOs.FlightInfo> flightInfoList = flightReader.getFlightInfoDetails(flightList);
//            flightInfoList.forEach(f->{
//                System.out.println("\n"+f);
//            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


//    public List<FlightDTO> jsonFromFile(String fileName) throws IOException {
//        List<FlightDTO> flights = getObjectMapper().readValue(Paths.get(fileName).toFile(), List.class);
//        return flights;
//    }


    public List<DTOs.FlightInfo> getFlightInfoDetails(List<DTOs.FlightDTO> flightList) {
        List<DTOs.FlightInfo> flightInfoList = flightList.stream().map(flight -> {
            Duration duration = Duration.between(flight.getDeparture().getScheduled(), flight.getArrival().getScheduled());

            DTOs.FlightInfo flightInfo = DTOs.FlightInfo.builder()
                    .name(flight.getFlight().getNumber())
                    .iata(flight.getFlight().getIata())
                    .airline(flight.getAirline().getName())
                    .duration(duration)
                    .departure(flight.getDeparture().getScheduled().toLocalDateTime())
                    .arrival(flight.getArrival().getScheduled().toLocalDateTime())
                    .origin(flight.getDeparture().getAirport())
                    .destination(flight.getArrival().getAirport())
                    .build();
            return flightInfo;
        }).toList();
        return flightInfoList;
    }

    //____________________________________________ASSIGNMENT 5.1_______________________________________________________
    public double calculateAverageFlightTimeForAirline(List<DTOs.FlightDTO> flightList, String airlineName) {
        List<DTOs.FlightDTO> filteredFlights = flightList.stream()
                //Filter out the flights that are not from the airline we are looking for
                //The condition != null, checks if the airline's name is not null. Only if this condition is true
                //will it go on to compare the name with airlineName.
                .filter(flight -> flight.getAirline().getName() != null && flight.getAirline().getName().equalsIgnoreCase(airlineName))
                .collect(Collectors.toList());

        //Calculate the average duration of the filtered flights. The result is wrapped
        //in an OptionalDouble because there might be no flights to calculate an average for (e.g., if no flights match the airline name).
        OptionalDouble averageDuration = filteredFlights.stream()
                //We convert each flight's duration into a double value representing the flight time in minutes.
                .mapToDouble(flight -> Duration.between(flight.getDeparture().getScheduled(), flight.getArrival().getScheduled()).toMinutes())
                //We calculate the average of the flight times.
                .average();

        //Return the average duration if it is present, otherwise return 0.0.
        return averageDuration.orElse(0.0);
    }

    //____________________________________________ASSIGNMENT 5.2_______________________________________________________

    public Long calculateTotalFlightTimeForASpecificAirline(List<DTOs.FlightDTO> flightList, String airlineName) {
        return flightList.stream()
                .filter(flight -> flight.getAirline().getName() != null && flight.getAirline().getName().equalsIgnoreCase(airlineName))
                .mapToLong(flight -> Duration.between(flight.getDeparture().getScheduled(), flight.getArrival().getScheduled()).toMinutes())
                .sum();
    }







    public List<DTOs.FlightDTO> getFlightsFromFile(String filename) throws IOException {
        DTOs.FlightDTO[] flights = new Utils().getObjectMapper().readValue(Paths.get(filename).toFile(), DTOs.FlightDTO[].class);

        List<DTOs.FlightDTO> flightList = Arrays.stream(flights).toList();
        return flightList;
    }


}
