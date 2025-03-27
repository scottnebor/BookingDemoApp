package BookingDemoApp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.*;

import BookingDemoApp.*;
import BookingDemoApp.Appointments.*;
import BookingDemoApp.Storage.MemoryStorage;


public class MemoryStorageTest {

  
    @Test
    public void testMemoryStorage() {
        MemoryStorage memoryStorage = new MemoryStorage();
        LocalDateTime appointment1DateTime = LocalDateTime.of(2025,3,20,11,0,0);
        //second time is before the first
        LocalDateTime appointment2DateTime = LocalDateTime.of(2025,3,20,10,0,0);

        //third time is the next day
        LocalDateTime appointment3DateTime = LocalDateTime.of(2025,3,21,10,0,0);
        //fourth time is another day later
        LocalDateTime appointment4DateTime = LocalDateTime.of(2025,3,22,10,0,0);
        Appointment p;
        AppointmentList appointmentList;

        //add one appointment and verify that one is returned with the right details
        p = new Appointment(AppointmentType.appointmentTypeCheckin,appointment1DateTime);
        memoryStorage.storeAppointment(p);

        appointmentList = memoryStorage.getAppointments(appointment1DateTime.toLocalDate());
        assertEquals(1,appointmentList.getAppointmentListSize());
        assertTrue(appointment1DateTime.equals(appointmentList.getAppointment(0).getAppointmentStartLocalDateTime()));
        assertEquals(AppointmentType.appointmentTypeCheckin,appointmentList.getAppointment(0).getAppointmentType());
        
        //add second appointment on the same day.  Verify two appointments in the right order with the right details
        p = new Appointment(AppointmentType.appointmentTypeStandard,appointment2DateTime);
        memoryStorage.storeAppointment(p);
        appointmentList = memoryStorage.getAppointments(appointment1DateTime.toLocalDate());
        assertEquals(2,appointmentList.getAppointmentListSize());

        assertTrue(appointment2DateTime.equals(appointmentList.getAppointment(0).getAppointmentStartLocalDateTime()));
        assertTrue(appointment1DateTime.equals(appointmentList.getAppointment(1).getAppointmentStartLocalDateTime()));
        assertEquals(AppointmentType.appointmentTypeStandard,appointmentList.getAppointment(0).getAppointmentType());
        assertEquals(AppointmentType.appointmentTypeCheckin,appointmentList.getAppointment(1).getAppointmentType());


        //add third appointment on the day 2.  Verify returned details
        p = new Appointment(AppointmentType.appointmentTypeStandard,appointment3DateTime);
        memoryStorage.storeAppointment(p);
        
        appointmentList = memoryStorage.getAppointments(appointment3DateTime.toLocalDate());
        assertEquals(1,appointmentList.getAppointmentListSize());


        //double check that day 1 still has two appointments
        appointmentList = memoryStorage.getAppointments(appointment1DateTime.toLocalDate());
        assertEquals(2,appointmentList.getAppointmentListSize());

        //check that day 3 still has no appointments
        appointmentList = memoryStorage.getAppointments(appointment4DateTime.toLocalDate());
        assertEquals(0,appointmentList.getAppointmentListSize());
    }
    

}
