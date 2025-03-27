package BookingDemoApp.Appointments;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.*;

import BookingDemoApp.*;
import BookingDemoApp.Appointments.*;



public class AppointmentTypeTest {

  
    @Test
    public void testAppointmentType() {
        assertEquals(30,AppointmentType.appointmentTypeCheckin.getLengthMinutes());
        assertEquals(60,AppointmentType.appointmentTypeStandard.getLengthMinutes());
        assertEquals(90,AppointmentType.appointmentTypeConsult.getLengthMinutes());
    }
    

}
