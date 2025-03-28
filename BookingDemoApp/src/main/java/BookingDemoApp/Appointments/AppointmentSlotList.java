package BookingDemoApp.Appointments;
import java.util.*;
import java.time.*;

/*
 * This class represents a list of appointment slots.
 * The list is maintained in a sorted order by time.
 * Elements are accessible by index
 */
public class AppointmentSlotList{
    private ArrayList<AppointmentSlot> appointmentSlots;

    /*
     * Constructor
     */
    public AppointmentSlotList(){

        appointmentSlots = new ArrayList<AppointmentSlot>();
    }

    /*
     * Add an appointment slot to the list
     */
    public void addAppointmentSlot(AppointmentSlot appointmentSlot){
        appointmentSlots.add(appointmentSlot);

        //sorting after each add call isn't optimal from a performance perspective.  However, the number of AppointmentSlots in the list
        //will be small.  As such, simplicity was favoured over performance
        appointmentSlots.sort((AppointmentSlot a, AppointmentSlot b) -> { return a.getAppointmentSlotStartDateTime().compareTo(b.getAppointmentSlotStartDateTime()); } );
    }

    /*
     * Returns how many slots are in the class
     */
    public int getAppointmentSlotListSize(){
        return appointmentSlots.size();
    }

    
    /*
     * Returns the slot at an index.
     * Caller is expected to ensure that index is valid
     */
    public AppointmentSlot getAppointmentsSlot(int index){
        return appointmentSlots.get(index);
    }

    
    

}
