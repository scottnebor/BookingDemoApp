package BookingDemoApp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.*;

import BookingDemoApp.*;
import BookingDemoApp.Appointments.*;

public class BookingLibraryTest {

  
    @Test
    public void testbookAppointmentInvalidTime() {
        BookingLibrary bl = new BookingLibrary();

        //setup a fake current time - 9 AM march 20, 2025
        bl.overrideCurrentTime(LocalDateTime.of(2025,3,20,9,0,0));
        
        BookingLibraryException e;

        //event from the past
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.bookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,19,10,0,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.getBookingLibraryExceptionErrorCode());

        //event from today, but with less than 2 hours notice
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.bookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,10,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.getBookingLibraryExceptionErrorCode());

        //event from today, but at 5 PM
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.bookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,17,0,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.getBookingLibraryExceptionErrorCode());

        //event from today, but after 5 PM
        e = assertThrows(BookingLibraryException.class, () -> {        
            bl.bookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,17,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.getBookingLibraryExceptionErrorCode());

        //event from today, not on 30 minute interval
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.bookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,12,31,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.getBookingLibraryExceptionErrorCode());

        //event from today, but it will end after 5 PM
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.bookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,20,16,0,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.getBookingLibraryExceptionErrorCode());

        //event from today, but it will end after 5 PM
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,20,16,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.getBookingLibraryExceptionErrorCode());

        //event from tomorrow, but too early
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,21,8,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.getBookingLibraryExceptionErrorCode());  

        //event from tomorrow, but too late
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,21,17,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.getBookingLibraryExceptionErrorCode());  



        //create a valid event
        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,20,13,30,0));
        });

        //event that overlaps with a booked event
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.bookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,13,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.getBookingLibraryExceptionErrorCode());

        //event that overlaps with a booked event
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,20,13,0,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.getBookingLibraryExceptionErrorCode());

        //event that overlaps with a booked event
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.bookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,20,12,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.getBookingLibraryExceptionErrorCode());

        //dump the date forward
        bl.overrideCurrentTime(LocalDateTime.of(2025,3,25,9,0,0));

        //event from the past
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.bookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,24,9,0,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.getBookingLibraryExceptionErrorCode());
    }
    

    @Test
    public void testgetAvailableAppointmentTimes(){
        BookingLibrary bl = new BookingLibrary();
        //setup a fake current time - 9 AM march 20, 2025
        bl.overrideCurrentTime(LocalDateTime.of(2025,3,20,9,0,0));

        //verify 0 booking slots yesterday
        assertEquals(0,bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,19)).getAppointmentSlotListSize());

        //verify 12 booking slots today
        assertEquals(12,bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,20)).getAppointmentSlotListSize());

        //verify 16 booking slots tomorrow
        assertEquals(16,bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,21)).getAppointmentSlotListSize());

        AppointmentSlot firstAppointment = bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,20)).getAppointmentsSlot(0);
        AppointmentSlot thirdAppointment = bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,20)).getAppointmentsSlot(2);
        AppointmentSlot thirdLastAppointment  = bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,20)).getAppointmentsSlot(9);
        AppointmentSlot secondLastAppointment  = bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,20)).getAppointmentsSlot(10);
        AppointmentSlot lastAppointment  = bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,20)).getAppointmentsSlot(11);
        

        //verify that appointmentTimes match expectations
        assertEquals(0,firstAppointment.getAppointmentSlotStartTime().compareTo(LocalTime.of(11,0,0)));
        assertTrue(firstAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(firstAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(firstAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        
        assertTrue(thirdAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(thirdAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(thirdAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        assertEquals(0,thirdAppointment.getAppointmentSlotStartTime().compareTo(LocalTime.of(12,0,0)));

        assertTrue(lastAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertFalse(lastAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertFalse(lastAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        assertEquals(0,lastAppointment.getAppointmentSlotStartTime().compareTo(LocalTime.of(16,30,0)));

        assertTrue(secondLastAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(secondLastAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertFalse(secondLastAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        assertEquals(0,secondLastAppointment.getAppointmentSlotStartTime().compareTo(LocalTime.of(16,0,0)));

        assertTrue(thirdLastAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(thirdLastAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(thirdLastAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        assertEquals(0,thirdLastAppointment.getAppointmentSlotStartTime().compareTo(LocalTime.of(15,30,0)));


        firstAppointment = bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,21)).getAppointmentsSlot(0);
        assertTrue(firstAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(firstAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(firstAppointment.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        assertEquals(0,firstAppointment.getAppointmentSlotStartTime().compareTo(LocalTime.of(9,0,0)));


        lastAppointment  = bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,21)).getAppointmentsSlot(15);
        assertEquals(0,lastAppointment.getAppointmentSlotStartTime().compareTo(LocalTime.of(16,30,0)));
        
        //setup some events
        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,21,11,0,0));
            bl.bookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,21,9,0,0));
            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,21,13,0,0));
            //available slots left should be 9:30, 10, 10:30, 12:30, 2, 2:30, 3:00, 3:30, 4, 4:30 - 10 total
            
        });
        //verify that the correct number of events are listed
        assertEquals(10,bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,21)).getAppointmentSlotListSize());

        //get the 9 AM slot
        AppointmentSlot appointmentSlot = bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,21)).getAppointmentsSlot(0);
        assertEquals(0,appointmentSlot.getAppointmentSlotStartTime().compareTo(LocalTime.of(9,30,0)));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));

        //get the 10:00 AM slot 
        appointmentSlot = bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,21)).getAppointmentsSlot(1);
        assertEquals(0,appointmentSlot.getAppointmentSlotStartTime().compareTo(LocalTime.of(10,0,0)));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertFalse(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));

        //get the 10:30 AM slot
        appointmentSlot = bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,21)).getAppointmentsSlot(2);
        assertEquals(0,appointmentSlot.getAppointmentSlotStartTime().compareTo(LocalTime.of(10,30,0)));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertFalse(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertFalse(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));

        //get the 3:30 PM slot
        appointmentSlot = bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,21)).getAppointmentsSlot(7);
        assertEquals(0,appointmentSlot.getAppointmentSlotStartTime().compareTo(LocalTime.of(15,30,0)));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));

        //book one more event
        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,21,14,0,0));
            //available slots left should be 9:30, 10, 10:30, 12:30, 3:00, 3:30, 4, 4:30 - 8 total
            
        });
        assertEquals(8,bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,21)).getAppointmentSlotListSize());

        //get the 2:30 PM slot
        appointmentSlot = bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,21)).getAppointmentsSlot(4);
        

        assertEquals(0,appointmentSlot.getAppointmentSlotStartTime().compareTo(LocalTime.of(15,0,0)));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));

        //get the 4:00 PM slot
        appointmentSlot = bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,21)).getAppointmentsSlot(6);
        
        assertEquals(0,appointmentSlot.getAppointmentSlotStartTime().compareTo(LocalTime.of(16,0,0)));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertFalse(appointmentSlot.isAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        

        //book one at the tail end of the day
        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,21,16,0,0));
            //available slots left should be 9:30, 10, 10:30, 12:30, 3:00, 3:30 - 6 total
            
        });
        assertEquals(6,bl.getAvailableAppointmentTimes(LocalDate.of(2025,3,21)).getAppointmentSlotListSize());

    }

    @Test
    public void testPractionerBookedAppointments(){
        BookingLibrary bl = new BookingLibrary();
        

        //setup a fake current time - 9 AM march 20, 2025
        bl.overrideCurrentTime(LocalDateTime.of(2025,3,20,7,0,0));
        assertEquals(0,bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,20)).getAppointmentListSize());
        //setup some events
        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,20,11,0,0));
            bl.bookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,9,0,0));
            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,20,13,0,0));

            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,21,13,0,0));
        });

        //verify that we have the right number of events on each day
        assertEquals(3,bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,20)).getAppointmentListSize());
        assertEquals(1,bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,21)).getAppointmentListSize());
        assertEquals(0,bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,22)).getAppointmentListSize());
        
        
        //verify that events are sorted in the right order and that each events time is correct
        assertTrue(bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,20)).getAppointment(0).getAppointmentStartDateTime().equals(LocalDateTime.of(2025,3,20,9,0,0)));   
        assertTrue(bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,20)).getAppointment(1).getAppointmentStartDateTime().equals(LocalDateTime.of(2025,3,20,11,0,0)));
        assertTrue(bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,20)).getAppointment(2).getAppointmentStartDateTime().equals(LocalDateTime.of(2025,3,20,13,0,0)));
        assertTrue(bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,21)).getAppointment(0).getAppointmentStartDateTime().equals(LocalDateTime.of(2025,3,21,13,0,0)));   

        //verify that each events type
        assertEquals(AppointmentType.appointmentTypeCheckin,bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,20)).getAppointment(0).getAppointmentType());
        assertEquals(AppointmentType.appointmentTypeConsult,bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,20)).getAppointment(1).getAppointmentType());
        assertEquals(AppointmentType.appointmentTypeStandard,bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,20)).getAppointment(2).getAppointmentType());
        assertEquals(AppointmentType.appointmentTypeStandard,bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,21)).getAppointment(0).getAppointmentType());

        

        //set the date to the future. Verify that we can still access old events
        bl.overrideCurrentTime(LocalDateTime.of(2025,4,20,7,0,0));

        assertEquals(3,bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,20)).getAppointmentListSize());
        assertEquals(1,bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,21)).getAppointmentListSize());
        assertEquals(0,bl.getPractitionerBookedAppointments(LocalDate.of(2025,3,22)).getAppointmentListSize());
    }

    @Test
    public void testbookAppointments() {
        BookingLibrary bl = new BookingLibrary();

        //setup a fake current time - 9 AM march 20, 2025
        bl.overrideCurrentTime(LocalDateTime.of(2025,3,20,7,0,0));

        //book events that don't overlap, and are valid
        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,20,9,0,0));
        });

        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,10,30,0));
        });

        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,20,11,0,0));
        });

        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,20,12,0,0));
        });

        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,20,15,30,0));
        });

        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,21,9,0,0));
        });

        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,21,16,30,0));
        });
        
        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,22,9,0,0));
        });


        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,22,16,0,0));
        });

        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,23,9,0,0));
        });

        assertDoesNotThrow(() -> {
            bl.bookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,23,15,30,0));
        });
    }
}
