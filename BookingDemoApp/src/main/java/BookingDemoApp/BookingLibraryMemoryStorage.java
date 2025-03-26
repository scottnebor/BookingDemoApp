package scottnebor;

import java.time.*;
import java.util.HashMap;

//TODO - sort the list
public class BookingLibraryMemoryStorage extends BookingLibraryAbstractStorage{
    
    protected HashMap<String, AppointmentList> appointmentDateListMap;

    BookingLibraryMemoryStorage(){
        appointmentDateListMap = new HashMap<String, AppointmentList>();
    }

    void StoreAppointment(Appointment appointment){
        String key = appointment.GetAppointmentStartTime().toLocalDate().toString();
        if(!appointmentDateListMap.containsKey(key))
            appointmentDateListMap.put(key, new AppointmentList());
        appointmentDateListMap.get(key).AddAppointment(appointment);
        
    }
    
    AppointmentList GetAppointments(LocalDate appointmentDate){
        String key = appointmentDate.toString();
        if(!appointmentDateListMap.containsKey(key))
            return new AppointmentList();
        return appointmentDateListMap.get(key);
    }
}
