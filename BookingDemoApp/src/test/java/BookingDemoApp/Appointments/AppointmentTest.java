package BookingDemoApp.Appointments;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.*;
import BookingDemoApp.*;

public class AppointmentTest {

  
    @Test
    public void testAppointment() {

        //verify that creating an appointment retains the same appointment type and date/time
        LocalDateTime appointment1DateTime = LocalDateTime.of(2025,3,20,11,0,0);
        Appointment appointment = new Appointment(AppointmentType.appointmentTypeCheckin, appointment1DateTime);
        assertTrue(AppointmentType.appointmentTypeCheckin.equals(appointment.getAppointmentType()));
        assertTrue(appointment.getAppointmentStartDateTime().equals(LocalDateTime.of(2025,3,20,11,0,0)));
        assertTrue(appointment.getAppointmentStartTime().equals(LocalTime.of(11,0,0)));
    }
    

}
