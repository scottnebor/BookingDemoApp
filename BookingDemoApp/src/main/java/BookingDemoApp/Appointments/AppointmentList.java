package BookingDemoApp.Appointments;
import java.util.*;
import java.time.*;

/*
 * This class represents a list of appointments.
 * The list is maintained in a sorted order by time.
 * Elements are accessible by index
 */
public class AppointmentList{
    private ArrayList<Appointment> appointments;

    /*
     * constructor
     */
    public AppointmentList(){

        appointments = new ArrayList<Appointment>();
    }

    /*
     * adds an appointment.  
     */
    public void addAppointment(Appointment appointment){
        appointments.add(appointment);

        //sorting after each add call isn't optimal from a performance perspective.  However, the number of Appointments in the list
        //will be small.  As such, simplicity was favoured over performance
        appointments.sort((Appointment a, Appointment b) -> { return a.getAppointmentStartDateTime().compareTo(b.getAppointmentStartDateTime()); } );
    }

    /*
     * returns the number of appointments in the last
     */
    public int getAppointmentListSize(){
        return appointments.size();
    }

    /*
     * Returns the appointment at an index.
     * Caller is expected to ensure that index is valid
     */
    public Appointment getAppointment(int index){
        return appointments.get(index);
    }
    




    
}
