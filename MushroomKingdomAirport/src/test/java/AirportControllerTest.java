import org.example.Airport.airport.AirportMapper;
import org.example.Airport.airport.AirpotRequestDTO;
import org.example.Airport.airport.AirportResponseDTO;
import org.example.Airport.airport.Enums.*;
import org.example.Airport.airport.AirportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = org.example.Airport.airport.AirportController.class)
@AutoConfigureMockMvc
class AirportControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirportService airportService;

    @Autowired
    private AirportMapper airportMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createAirport_Succes() throws Exception{
        //GIVEN
        AirpotRequestDTO airpotRequestDTO = AirpotRequestDTO.builder()
                .code("MAD")
                .name("Madrid Airport")
                .city("Madrid")
                .country("Spain")
                .type(String.valueOf(AirportType.INTERNATIONAL))
                .size(String.valueOf(AirportSize.LARGE))
                .region(String.valueOf(RegionType.EUROPE))
                .build();

        AirportResponseDTO airportResponseDTO = AirportResponseDTO.builder()
                .id(1L)
                .code("MAD")
                .name("Madrid Airport")
                .city("Madrid")
                .country("Spain")
                .active(true)
                .status(AirportStatus.ACTIVE)
                .type(AirportType.INTERNATIONAL)
                .size(AirportSize.LARGE)
                .region(RegionType.EUROPE)
                .build();

        when(airportService.createAirport(any(AirpotRequestDTO.class)))
                .thenReturn(airportResponseDTO);

        //When & Then
        mockMvc.perform(post("/api/airports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(airportMapper.writeValueAsString(airpotRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("MAD"))
                .andExpect(jsonPath("$.name").value("Madrid Airport"));
    }
}
