package BookingDemoApp;

/*
 * this class handles booking related exceptions.  It provides a booking error code
 */
public class BookingLibraryException extends Exception{
    public enum BookingLibraryErrorCode {BOOKING_EXCEPTION_GENERAL_FAILURE,BOOKING_EXCEPTION_INVALID_DATETIME}
    

    
    protected BookingLibraryErrorCode bookingLibraryExceptionErrorCode;

    /*
     * constructor - accepts an error code
     */
    public BookingLibraryException(BookingLibraryErrorCode bookingLibraryExceptionErrorCode){
        this.bookingLibraryExceptionErrorCode = bookingLibraryExceptionErrorCode;
    }

    /*
     * return the error code
     */
    public BookingLibraryErrorCode GetBookingLibraryExceptionErrorCode(){

        return bookingLibraryExceptionErrorCode;
    }

    

}
