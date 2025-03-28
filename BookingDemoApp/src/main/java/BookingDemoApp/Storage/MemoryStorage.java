package BookingDemoApp.Storage;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import BookingDemoApp.Appointments.*;

/*
 * This is an memory only storage for booking appointments.  It's used for demo purposes, but would never be used outside of a test environment.
 */
public class MemoryStorage extends AbstractStorage{
    
    private HashMap<String, AppointmentList> appointmentDateListMap;
    static private DateTimeFormatter bookingLibraryDateFormatter= DateTimeFormatter.ofPattern("yyyyMMdd");
    public MemoryStorage(){
        appointmentDateListMap = new HashMap<String, AppointmentList>();

    }

    /*
     * Stores an appointment for date.   It does no validion on details of the appointment, or whether there are conflicting appointments.
     */
    public void storeAppointment(Appointment appointment){
        String key = appointment.getAppointmentStartDateTime().format(bookingLibraryDateFormatter);
        if(!appointmentDateListMap.containsKey(key))
            appointmentDateListMap.put(key, new AppointmentList());
        appointmentDateListMap.get(key).addAppointment(appointment);
        
    }
    
    /*
     * Returns a list of appointments. Appointments will be ordered due to AppointmentList keeping things ordered  
    */
    public AppointmentList getAppointments(LocalDate appointmentDate){
        String key = appointmentDate.format(bookingLibraryDateFormatter);
        if(!appointmentDateListMap.containsKey(key))
            return new AppointmentList();
        return appointmentDateListMap.get(key);
    }
}
