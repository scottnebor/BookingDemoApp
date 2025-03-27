package BookingDemoApp.Appointments;
import java.util.*;
import java.time.*;

/*
 * this class stores a collection of appointments
 */
public class AppointmentList{
    protected ArrayList<Appointment> appointments;

    /*
     * constructor
     */
    public AppointmentList(){

        appointments = new ArrayList<Appointment>();
    }

    /*
     * adds an appointment.  Caller assumes validation checking to ensure that the appointment is valid
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

    //todo - document that it is the callers responsibility to ensure a valid index
    public Appointment getAppointment(int index){
        return appointments.get(index);
    }
    




    
}
