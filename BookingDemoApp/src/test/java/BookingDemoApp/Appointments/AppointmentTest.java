package BookingDemoApp.Appointments;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.*;

import BookingDemoApp.*;
import BookingDemoApp.Appointments.*;
import BookingDemoApp.Storage.MemoryStorage;


public class AppointmentTest {

  
    @Test
    public void testAppointment() {
        LocalDateTime appointment1DateTime = LocalDateTime.of(2025,3,20,11,0,0);
        Appointment p = new Appointment(AppointmentType.appointmentTypeCheckin, appointment1DateTime);
        assertTrue(AppointmentType.appointmentTypeCheckin.equals(p.getAppointmentType()));
        assertTrue(p.getAppointmentStartDateTime().equals(LocalDateTime.of(2025,3,20,11,0,0)));
        assertTrue(p.getAppointmentStartTime().equals(LocalTime.of(11,0,0)));
    }
    

}
