package dk.cphbusiness.flightdemo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightReaderTest {
    FlightReader reader = new FlightReader();
    FlightWriter writer = new FlightWriter();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

//    @Test // This test requires an API key and a free account only has 100 requests per month, so run this test with caution.
    @DisplayName("Test if url writer method works")
    void reader() {
        try {
            List<DTOs.FlightDTO> flightList = writer.writeFlightsToFile(1, 10);
            assertEquals(10, flightList.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("Test getting stream info from collection")
    void getInfo() {
        try {
            List<DTOs.FlightDTO> flights = reader.getFlightsFromFile("flights.json");
            List<DTOs.FlightInfo> flightInfoList = reader.getFlightInfoDetails(flights);
            assertEquals("Royal Jordanian", flightInfoList.get(0).getAirline());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}