package scottnebor;

class BookingLibraryException extends Exception{
    
    public final static int BOOKING_EXCEPTION_GENERAL_FAILURE = 1;
    public final static int BOOKING_EXCEPTION_INVALID_DATETIME = 2;

    
    int bookingLibraryExceptionErrorCode;
    BookingLibraryException(int bookingLibraryExceptionErrorCode){
        this.bookingLibraryExceptionErrorCode = bookingLibraryExceptionErrorCode;
    }

    int GetBookingLibraryExceptionErrorCode(){

        return bookingLibraryExceptionErrorCode;
    }

    

}
