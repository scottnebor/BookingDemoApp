package scottnebor;

/*
 * this class handles booking related exceptions.  It provides a booking error code
 */
class BookingLibraryException extends Exception{
    public enum BookingLibraryErrorCode {BOOKING_EXCEPTION_GENERAL_FAILURE,BOOKING_EXCEPTION_INVALID_DATETIME}
    

    
    BookingLibraryErrorCode bookingLibraryExceptionErrorCode;

    /*
     * constructor - accepts an error code
     */
    BookingLibraryException(BookingLibraryErrorCode bookingLibraryExceptionErrorCode){
        this.bookingLibraryExceptionErrorCode = bookingLibraryExceptionErrorCode;
    }

    /*
     * return the error code
     */
    BookingLibraryErrorCode GetBookingLibraryExceptionErrorCode(){

        return bookingLibraryExceptionErrorCode;
    }

    

}
