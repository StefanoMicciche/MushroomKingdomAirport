import org.example.Airport.airport.AirportController;
import org.example.Airport.airport.AirportService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
@ExtendsWith(MockitoExtension.class)
public class AirportControllerTest {
    @Mock
    private AirportService airportService;
    @InjectMocks
    private AirportController airportController;

    private AirportRequestDTO airportRequestDTO;
}
