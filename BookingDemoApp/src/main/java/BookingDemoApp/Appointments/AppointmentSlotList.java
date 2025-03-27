package BookingDemoApp.Appointments;
import java.util.*;
import java.time.*;


/*
 * this class represents a list of appointment slots.
 * the list is maintained in a sorted order by time
 */
public class AppointmentSlotList{
    protected ArrayList<AppointmentSlot> appointmentSlots;
    public AppointmentSlotList(){

        appointmentSlots = new ArrayList<AppointmentSlot>();
    }

    public void addAppointmentSlot(AppointmentSlot appointmentSlot){
        appointmentSlots.add(appointmentSlot);

        //sorting after each add call isn't optimal from a performance perspective.  However, the number of AppointmentSlots in the list
        //will be small.  As such, simplicity was favoured over performance
        appointmentSlots.sort((AppointmentSlot a, AppointmentSlot b) -> { return a.getAppointmentStartTime().compareTo(b.getAppointmentStartTime()); } );
    }

    public int getAppointmentSlotListSize(){
        return appointmentSlots.size();
    }

    
    public AppointmentSlot getAppointmentsSlot(int index){
        return appointmentSlots.get(index);
    }

    
    

}
