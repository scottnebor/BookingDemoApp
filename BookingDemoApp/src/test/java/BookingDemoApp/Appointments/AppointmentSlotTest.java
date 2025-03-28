package BookingDemoApp.Appointments;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.*;

import BookingDemoApp.*;
import BookingDemoApp.Appointments.*;


public class AppointmentSlotTest {

  
    @Test
    public void testAppointmentSlot() {
        LocalDateTime appointmentTime = LocalDateTime.of(2025,3,20,11,0,0);
        AppointmentSlot appointmentSlot = new AppointmentSlot(appointmentTime);
        assertTrue(appointmentSlot.getAppointmentSlotStartDateTime().equals(LocalDateTime.of(2025,3,20,11,0,0)));
        assertTrue(appointmentSlot.getAppointmentSlotStartTime().equals(LocalTime.of(11,0,0)));
        
        //verify that no appointment types are allowed
        assertFalse(appointmentSlot.hasAppointmentTypes() );
        assertFalse(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin) );
        assertFalse(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard) );
        assertFalse(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult) );
        assertEquals(0,appointmentSlot.getNumAppointmentTypes());


        //add one appointment type, and verify that it's allowed, and other types are not
        appointmentSlot.addAllowedAppointmentType(AppointmentType.appointmentTypeCheckin);
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin) );
        assertFalse(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard) );
        assertFalse(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult) );
        assertEquals(1,appointmentSlot.getNumAppointmentTypes());

        //add remaining appointment types, and verify all are allowed
        appointmentSlot.addAllowedAppointmentType(AppointmentType.appointmentTypeStandard);
        appointmentSlot.addAllowedAppointmentType(AppointmentType.appointmentTypeConsult);
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin) );
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard) );
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult) );
        assertEquals(3,appointmentSlot.getNumAppointmentTypes());

        //re-add the same appointment type.  It shouldn't increase the size
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult) );
        assertEquals(3,appointmentSlot.getNumAppointmentTypes());
        //double check that consult is still allowed
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult) );
        
    }
    

}
