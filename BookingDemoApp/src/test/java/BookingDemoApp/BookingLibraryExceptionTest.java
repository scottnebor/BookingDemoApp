package BookingDemoApp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.*;
import BookingDemoApp.Appointments.*;

public class BookingLibraryExceptionTest {

  
    @Test
    public void testBookingLibraryException() {

        //double check that the exception code is reporting correctly
        BookingLibraryException e = new BookingLibraryException(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_GENERAL_FAILURE);
        assertEquals(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_GENERAL_FAILURE,e.getBookingLibraryExceptionErrorCode() );
        
    }
    

}
