package scottnebor;
import java.util.*;
import java.time.*;

//List of appointments that are booked on a given day

class AppointmentSlotList{
    ArrayList<AppointmentSlot> appointmentSlots;
    AppointmentSlotList(){

        appointmentSlots = new ArrayList<AppointmentSlot>();
    }

    void AddAppointmentSlot(AppointmentSlot appointmentSlot){
        appointmentSlots.add(appointmentSlot);
    }

    int GetAppointmentSlotListSize(){
        return appointmentSlots.size();
    }

    //todo - document that it is the callers responsibility to ensure a valid index
    AppointmentSlot GetAppointmentsSlot(int index){
        return appointmentSlots.get(index);
    }

    
    boolean CanBookAppointment(AppointmentType appointmentType, LocalTime lt){
        Iterator<AppointmentSlot> iterator = appointmentSlots.iterator();
        while(iterator.hasNext()){
            AppointmentSlot sl = iterator.next();
            if(!sl.GetAppointmentStartTime().equals(lt))
                continue;
            if(sl.IsAppointmentTypeAllowed(appointmentType))
                return true;
        }
        return false;
    
    }
    

}
