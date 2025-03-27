package scottnebor;
import java.util.*;
import java.time.*;


/*
 * this class represents a list of appointment slots.
 * the list is maintained in a sorted order by time
 */
class AppointmentSlotList{
    protected ArrayList<AppointmentSlot> appointmentSlots;
    public AppointmentSlotList(){

        appointmentSlots = new ArrayList<AppointmentSlot>();
    }

    public void AddAppointmentSlot(AppointmentSlot appointmentSlot){
        appointmentSlots.add(appointmentSlot);

        //sorting after each add call isn't optimal from a performance perspective.  However, the number of AppointmentSlots in the list
        //will be small.  As such, simplicity was favoured over performance
        appointmentSlots.sort((AppointmentSlot a, AppointmentSlot b) -> { return a.GetAppointmentStartTime().compareTo(b.GetAppointmentStartTime()); } );
    }

    public int GetAppointmentSlotListSize(){
        return appointmentSlots.size();
    }

    
    public AppointmentSlot GetAppointmentsSlot(int index){
        return appointmentSlots.get(index);
    }

    
    

}
