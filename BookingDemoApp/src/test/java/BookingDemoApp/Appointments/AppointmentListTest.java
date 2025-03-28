package BookingDemoApp.Appointments;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.*;
import BookingDemoApp.*;

public class AppointmentListTest {
  
    @Test
    public void testAppointmentList() {
        AppointmentList appointmentList = new AppointmentList();
               
        assertEquals(0, appointmentList.getAppointmentListSize());

        Appointment appointment1 = new Appointment(AppointmentType.appointmentTypeCheckin, LocalDateTime.of(2025,03,20, 11,0,0));
        Appointment appointment2 = new Appointment(AppointmentType.appointmentTypeCheckin, LocalDateTime.of(2025,03,20, 10,0,0));
        Appointment appointment3 = new Appointment(AppointmentType.appointmentTypeCheckin, LocalDateTime.of(2025,03,20, 12,0,0));

        appointmentList.addAppointment(appointment1);
        assertEquals(1, appointmentList.getAppointmentListSize());
        assertTrue(appointmentList.getAppointment(0).getAppointmentStartDateTime().equals(LocalDateTime.of(2025,03,20, 11,0,0)));
        
        //add a second appointment.  Verify that there's two appointments in the list, and the time of each in order and of the expected value
        appointmentList.addAppointment(appointment2);
        assertEquals(2, appointmentList.getAppointmentListSize());
        assertTrue(appointmentList.getAppointment(0).getAppointmentStartDateTime().equals(LocalDateTime.of(2025,03,20, 10,0,0)));
        assertTrue(appointmentList.getAppointment(1).getAppointmentStartDateTime().equals(LocalDateTime.of(2025,03,20, 11,0,0)));

        //add a third appointment.  Verify that there's 3 appointments in the list, and the time of each in order and of the expected value
        appointmentList.addAppointment(appointment3);
        assertEquals(3, appointmentList.getAppointmentListSize());
        assertTrue(appointmentList.getAppointment(0).getAppointmentStartDateTime().equals(LocalDateTime.of(2025,03,20, 10,0,0)));
        assertTrue(appointmentList.getAppointment(1).getAppointmentStartDateTime().equals(LocalDateTime.of(2025,03,20, 11,0,0)));
        assertTrue(appointmentList.getAppointment(2).getAppointmentStartDateTime().equals(LocalDateTime.of(2025,03,20, 12,0,0)));


    }
    

}
