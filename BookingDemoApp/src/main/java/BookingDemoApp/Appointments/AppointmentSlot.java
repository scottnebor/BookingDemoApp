package BookingDemoApp.Appointments;
import java.util.*;
import java.time.*;


/*
 * This class represents a appointment time slot that could potentially be booked.  
 * It lists the start time, and the allowed appointment types that can be booked into the slot
 */
public class AppointmentSlot{
    

    protected ArrayList<AppointmentType> allowedAppointmentTypes;
    protected LocalDateTime appointmentStartTime;

    /*
     * constructor
     * appointmentStartTime: local time of the timeslot
     */
    public AppointmentSlot(LocalDateTime appointmentStartTime){
        allowedAppointmentTypes = new ArrayList<AppointmentType>();
        this.appointmentStartTime = appointmentStartTime;

    }
    
    /*
     * function to add an allowed appointment time
     */
    public void addAllowedAppointmentType(AppointmentType appointmentType ){
        if(!isAppointmentTypeAllowed(appointmentType))
            allowedAppointmentTypes.add(appointmentType);
    }
    
    /*
     * return the appointment time
     */
    public LocalDateTime getAppointmentSlotStartDateTime(){
        return this.appointmentStartTime;
    }

    /*
     * return the appointment time
     */
    public LocalTime getAppointmentSlotStartTime(){
        return this.appointmentStartTime.toLocalTime();
    }


    /*
     * check if an appointment type is allowed
     */
    public boolean isAppointmentTypeAllowed(AppointmentType appointmentType){
        Iterator<AppointmentType> iterator = allowedAppointmentTypes.iterator();
        while(iterator.hasNext()){
            AppointmentType at = iterator.next();
            if(at.equals( appointmentType))
                return true;
        }
        return false;
    }

    /*
     * returns true if there is at least one allowed appointment type
     */
    public boolean hasAppointmentTypes(){
        return (allowedAppointmentTypes.size() > 0);
    }

    /*
     * returns the number of appointmentTypes   
     */
    public int getNumAppointmentTypes(){
        return allowedAppointmentTypes.size();
    }
}
