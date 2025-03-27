package scottnebor;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/*
 * This is an memory only storage for booking appointments.  It's used for demo purposes, but would never be used outside of a test environment.
 */
public class BookingLibraryMemoryStorage extends BookingLibraryAbstractStorage{
    
    protected HashMap<String, AppointmentList> appointmentDateListMap;
    protected DateTimeFormatter bookingLibraryDateFormatter;
    public BookingLibraryMemoryStorage(){
        appointmentDateListMap = new HashMap<String, AppointmentList>();
        bookingLibraryDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    }

    /*
     * Store's an appointment for date.   It does no validion on details of the appointment, or whether there are conflicting appointments.
     */
    public void StoreAppointment(Appointment appointment){
        String key = appointment.GetAppointmentStartLocalDateTime().format(bookingLibraryDateFormatter);
        if(!appointmentDateListMap.containsKey(key))
            appointmentDateListMap.put(key, new AppointmentList());
        appointmentDateListMap.get(key).AddAppointment(appointment);
        
    }
    
    /*
     * Returns a list of appointments with no guarantee of order     
    */
    public AppointmentList GetAppointments(LocalDate appointmentDate){
        String key = appointmentDate.format(bookingLibraryDateFormatter);
        if(!appointmentDateListMap.containsKey(key))
            return new AppointmentList();
        return appointmentDateListMap.get(key);
    }
}
