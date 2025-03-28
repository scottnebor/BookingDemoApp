package BookingDemoApp.Appointments;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.*;
import BookingDemoApp.*;

public class AppointmentTypeTest {

  
    @Test
    public void testAppointmentType() {

        //verify that each appointmentType has the correct duration
        assertEquals(30,AppointmentType.appointmentTypeCheckin.getLengthMinutes());
        assertEquals(60,AppointmentType.appointmentTypeStandard.getLengthMinutes());
        assertEquals(90,AppointmentType.appointmentTypeConsult.getLengthMinutes());
    }
    

}
