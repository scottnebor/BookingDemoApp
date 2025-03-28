package BookingDemoApp;

/*
 * this class handles booking related exceptions.  It provides a booking error code
 */
public class BookingLibraryException extends Exception{

    //for simplicity, only a couple exception codes were created.  Further codes can could be added to provide specific reasons for a failure
    public enum BookingLibraryErrorCode {BOOKING_EXCEPTION_GENERAL_FAILURE,BOOKING_EXCEPTION_INVALID_DATETIME}
    

    
    private BookingLibraryErrorCode bookingLibraryExceptionErrorCode;

    /*
     * constructor - accepts an error code
     */
    public BookingLibraryException(BookingLibraryErrorCode bookingLibraryExceptionErrorCode){
        this.bookingLibraryExceptionErrorCode = bookingLibraryExceptionErrorCode;
    }

    /*
     * return the error code
     */
    public BookingLibraryErrorCode getBookingLibraryExceptionErrorCode(){

        return bookingLibraryExceptionErrorCode;
    }

    

}
