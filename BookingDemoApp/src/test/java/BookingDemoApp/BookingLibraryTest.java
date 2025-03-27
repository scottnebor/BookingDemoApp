package scottnebor;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.*;


public class BookingLibraryTest {

  
    @Test
    public void TestBookAppointmentInvalidTime() {
        BookingLibrary bl = new BookingLibrary();

        //setup a fake current time - 9 AM march 20, 2025
        bl.OverrideCurrentTime(LocalDateTime.of(2025,3,20,9,0,0));
        
        BookingLibraryException e;

        //event from the past
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.BookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,19,10,0,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.GetBookingLibraryExceptionErrorCode());

        //event from today, but with less than 2 hours notice
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.BookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,10,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.GetBookingLibraryExceptionErrorCode());

        //event from today, but at 5 PM
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.BookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,17,0,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.GetBookingLibraryExceptionErrorCode());

        //event from today, but after 5 PM
        e = assertThrows(BookingLibraryException.class, () -> {        
            bl.BookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,17,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.GetBookingLibraryExceptionErrorCode());

        //event from today, not on 30 minute interval
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.BookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,12,31,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.GetBookingLibraryExceptionErrorCode());

        //event from today, but it will end after 5 PM
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.BookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,20,16,0,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.GetBookingLibraryExceptionErrorCode());

        //event from today, but it will end after 5 PM
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.BookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,20,16,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.GetBookingLibraryExceptionErrorCode());

        //event from tomorrow, but too early
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.BookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,21,8,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.GetBookingLibraryExceptionErrorCode());  

        //event from tomorrow, but too late
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.BookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,21,17,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.GetBookingLibraryExceptionErrorCode());  



        //create a valid event
        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,20,13,30,0));
        });

        //event that overlaps with a booked event
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.BookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,13,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.GetBookingLibraryExceptionErrorCode());

        //event that overlaps with a booked event
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.BookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,20,13,0,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.GetBookingLibraryExceptionErrorCode());

        //event that overlaps with a booked event
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.BookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,20,12,30,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.GetBookingLibraryExceptionErrorCode());

        //dump the date forward
        bl.OverrideCurrentTime(LocalDateTime.of(2025,3,25,9,0,0));

        //event from the past
        e = assertThrows(BookingLibraryException.class, () -> {
            bl.BookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,24,9,0,0) );
        });
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME, e.GetBookingLibraryExceptionErrorCode());
    }
    

    @Test
    public void TestGetAvailableAppointmentTimes(){
        BookingLibrary bl = new BookingLibrary();
        //setup a fake current time - 9 AM march 20, 2025
        bl.OverrideCurrentTime(LocalDateTime.of(2025,3,20,9,0,0));

        //verify 0 booking slots yesterday
        assertEquals(0,bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,19)).GetAppointmentSlotListSize());

        //verify 12 booking slots today
        assertEquals(12,bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,20)).GetAppointmentSlotListSize());

        //verify 16 booking slots tomorrow
        assertEquals(16,bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,21)).GetAppointmentSlotListSize());

        AppointmentSlot firstAppointment = bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,20)).GetAppointmentsSlot(0);
        AppointmentSlot thirdAppointment = bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,20)).GetAppointmentsSlot(2);
        AppointmentSlot thirdLastAppointment  = bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,20)).GetAppointmentsSlot(9);
        AppointmentSlot secondLastAppointment  = bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,20)).GetAppointmentsSlot(10);
        AppointmentSlot lastAppointment  = bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,20)).GetAppointmentsSlot(11);
        
        assertEquals(0,firstAppointment.GetAppointmentStartTime().compareTo(LocalTime.of(11,0,0)));

        assertTrue(firstAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(firstAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(firstAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        assertEquals(0,firstAppointment.GetAppointmentStartTime().compareTo(LocalTime.of(11,0,0)));
        
        assertTrue(thirdAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(thirdAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(thirdAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        assertEquals(0,thirdAppointment.GetAppointmentStartTime().compareTo(LocalTime.of(12,0,0)));

        assertTrue(lastAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertFalse(lastAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertFalse(lastAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        assertEquals(0,lastAppointment.GetAppointmentStartTime().compareTo(LocalTime.of(16,30,0)));

        assertTrue(secondLastAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(secondLastAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertFalse(secondLastAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        assertEquals(0,secondLastAppointment.GetAppointmentStartTime().compareTo(LocalTime.of(16,0,0)));

        assertTrue(thirdLastAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(thirdLastAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(thirdLastAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        assertEquals(0,thirdLastAppointment.GetAppointmentStartTime().compareTo(LocalTime.of(15,30,0)));


        firstAppointment = bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,21)).GetAppointmentsSlot(0);
        assertTrue(firstAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(firstAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(firstAppointment.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        assertEquals(0,firstAppointment.GetAppointmentStartTime().compareTo(LocalTime.of(9,0,0)));


        lastAppointment  = bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,21)).GetAppointmentsSlot(15);
        assertEquals(0,lastAppointment.GetAppointmentStartTime().compareTo(LocalTime.of(16,30,0)));
        
        //setup some events
        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,21,11,0,0));
            bl.BookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,21,9,0,0));
            bl.BookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,21,13,0,0));
            //available slots left should be 9:30, 10, 10:30, 12:30, 2, 2:30, 3:00, 3:30, 4, 4:30 - 10 total
            
        });
        assertEquals(10,bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,21)).GetAppointmentSlotListSize());

        //get the 9 AM slot
        AppointmentSlot appointmentSlot = bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,21)).GetAppointmentsSlot(0);
        assertEquals(0,appointmentSlot.GetAppointmentStartTime().compareTo(LocalTime.of(9,30,0)));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));

        //get the 10:00 AM slot 
        appointmentSlot = bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,21)).GetAppointmentsSlot(1);
        assertEquals(0,appointmentSlot.GetAppointmentStartTime().compareTo(LocalTime.of(10,0,0)));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertFalse(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));

        //get the 10:30 AM slot
        appointmentSlot = bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,21)).GetAppointmentsSlot(2);
        assertEquals(0,appointmentSlot.GetAppointmentStartTime().compareTo(LocalTime.of(10,30,0)));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertFalse(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertFalse(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));

        //get the 3:30 PM slot
        appointmentSlot = bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,21)).GetAppointmentsSlot(7);
        assertEquals(0,appointmentSlot.GetAppointmentStartTime().compareTo(LocalTime.of(15,30,0)));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));

        //book one more event
        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,21,14,0,0));
            //available slots left should be 9:30, 10, 10:30, 12:30, 3:00, 3:30, 4, 4:30 - 8 total
            
        });
        assertEquals(8,bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,21)).GetAppointmentSlotListSize());

        //get the 2:30 PM slot
        appointmentSlot = bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,21)).GetAppointmentsSlot(4);
        

        assertEquals(0,appointmentSlot.GetAppointmentStartTime().compareTo(LocalTime.of(15,0,0)));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));

        //get the 4:00 PM slot
        appointmentSlot = bl.GetAvailableAppointmentTimes(LocalDate.of(2025,3,21)).GetAppointmentsSlot(6);
        
        assertEquals(0,appointmentSlot.GetAppointmentStartTime().compareTo(LocalTime.of(16,0,0)));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeCheckin));
        assertTrue(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeStandard));
        assertFalse(appointmentSlot.IsAppointmentTypeAllowed(AppointmentType.appointmentTypeConsult));
        
        //TODO - book at 4, run through a few more tests
    }

    @Test
    public void TestPractionerBookedAppointments(){
        BookingLibrary bl = new BookingLibrary();
        

        //setup a fake current time - 9 AM march 20, 2025
        bl.OverrideCurrentTime(LocalDateTime.of(2025,3,20,7,0,0));
        assertEquals(0,bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,20)).GetAppointmentListSize());
        //setup some events
        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,20,11,0,0));
            bl.BookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,9,0,0));
            bl.BookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,20,13,0,0));

            bl.BookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,21,13,0,0));
        });

        //verify that we have the right number of events on each day
        assertEquals(3,bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,20)).GetAppointmentListSize());
        assertEquals(1,bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,21)).GetAppointmentListSize());
        assertEquals(0,bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,22)).GetAppointmentListSize());
        
        
        //verify that events are sorted in the right order and that each events time is correct
        assertTrue(bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,20)).GetAppointment(0).GetAppointmentStartLocalDateTime().equals(LocalDateTime.of(2025,3,20,9,0,0)));   
        assertTrue(bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,20)).GetAppointment(1).GetAppointmentStartLocalDateTime().equals(LocalDateTime.of(2025,3,20,11,0,0)));
        assertTrue(bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,20)).GetAppointment(2).GetAppointmentStartLocalDateTime().equals(LocalDateTime.of(2025,3,20,13,0,0)));
        assertTrue(bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,21)).GetAppointment(0).GetAppointmentStartLocalDateTime().equals(LocalDateTime.of(2025,3,21,13,0,0)));   

        //verify that each events type
        assertTrue(bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,20)).GetAppointment(0).GetAppointmentType() == AppointmentType.appointmentTypeCheckin);
        assertTrue(bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,20)).GetAppointment(1).GetAppointmentType() == AppointmentType.appointmentTypeConsult);
        assertTrue(bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,20)).GetAppointment(2).GetAppointmentType() == AppointmentType.appointmentTypeStandard);
        assertTrue(bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,21)).GetAppointment(0).GetAppointmentType() == AppointmentType.appointmentTypeStandard);

        

        //set the date to the future. Verify that we can still access old events
        bl.OverrideCurrentTime(LocalDateTime.of(2025,4,20,7,0,0));

        assertEquals(3,bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,20)).GetAppointmentListSize());
        assertEquals(1,bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,21)).GetAppointmentListSize());
        assertEquals(0,bl.GetPractitionerBookedAppointments(LocalDate.of(2025,3,22)).GetAppointmentListSize());
    }

    @Test
    public void TestBookAppointments() {
        BookingLibrary bl = new BookingLibrary();

        //setup a fake current time - 9 AM march 20, 2025
        bl.OverrideCurrentTime(LocalDateTime.of(2025,3,20,7,0,0));

        //book events that don't overlap, and are valid
        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,20,9,0,0));
        });

        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,20,10,30,0));
        });

        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,20,11,0,0));
        });

        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,20,12,0,0));
        });

        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,20,15,30,0));
        });

        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,21,9,0,0));
        });

        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeCheckin,LocalDateTime.of(2025,3,21,16,30,0));
        });
        
        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,22,9,0,0));
        });


        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeStandard,LocalDateTime.of(2025,3,22,16,0,0));
        });

        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,23,9,0,0));
        });

        assertDoesNotThrow(() -> {
            bl.BookAppointment(AppointmentType.appointmentTypeConsult,LocalDateTime.of(2025,3,23,15,30,0));
        });
    }
}
