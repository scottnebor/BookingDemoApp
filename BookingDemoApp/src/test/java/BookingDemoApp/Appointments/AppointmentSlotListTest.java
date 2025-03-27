package BookingDemoApp.Appointments;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.*;

import BookingDemoApp.*;
import BookingDemoApp.Appointments.*;
import BookingDemoApp.Storage.MemoryStorage;


public class AppointmentSlotListTest {

  
    @Test
    public void testAppointmentSlotList() {
        AppointmentSlotList appointmentSlotList = new AppointmentSlotList();

        assertEquals(0, appointmentSlotList.getAppointmentSlotListSize());


        AppointmentSlot appointmentSlot1 = new AppointmentSlot(LocalTime.of(11,0,0));
        AppointmentSlot appointmentSlot2 = new AppointmentSlot(LocalTime.of(10,0,0));
        AppointmentSlot appointmentSlot3 = new AppointmentSlot(LocalTime.of(12,0,0));
        
        //add an appointment.  Verify that there's one appointment in the list, and the time of the appointment matches the expected value
        appointmentSlotList.addAppointmentSlot(appointmentSlot1);
        assertEquals(1, appointmentSlotList.getAppointmentSlotListSize());
        assertTrue(appointmentSlotList.getAppointmentsSlot(0).getAppointmentSlotStartTime().equals(LocalTime.of(11,0,0)));

        //add a second appointment.  Verify that there's two appointments in the list, and the time of each in order and of the expected value
        appointmentSlotList.addAppointmentSlot(appointmentSlot2);
        assertEquals(2, appointmentSlotList.getAppointmentSlotListSize());
        assertTrue(appointmentSlotList.getAppointmentsSlot(0).getAppointmentSlotStartTime().equals(LocalTime.of(10,0,0)));
        assertTrue(appointmentSlotList.getAppointmentsSlot(1).getAppointmentSlotStartTime().equals(LocalTime.of(11,0,0)));

        //add a third appointment.  Verify that there's 3 appointments in the list, and the time of each in order and of the expected value
        appointmentSlotList.addAppointmentSlot(appointmentSlot3);
        assertEquals(3, appointmentSlotList.getAppointmentSlotListSize());
        assertTrue(appointmentSlotList.getAppointmentsSlot(0).getAppointmentSlotStartTime().equals(LocalTime.of(10,0,0)));
        assertTrue(appointmentSlotList.getAppointmentsSlot(1).getAppointmentSlotStartTime().equals(LocalTime.of(11,0,0)));
        assertTrue(appointmentSlotList.getAppointmentsSlot(2).getAppointmentSlotStartTime().equals(LocalTime.of(12,0,0)));

    }
    

}
