package BookingDemoApp.Appointments;
import java.time.*;



/*
 * this class represents an appointment with an appointment date/time and appointment type
 * 
 */
public class Appointment{
    
    protected AppointmentType appointmentType;
    protected LocalDateTime appointmentStartTime;

    /*
     * constuctor - accepts an appointment type and a date/time
     */
    public Appointment(AppointmentType appointmentType, LocalDateTime appointmentStartTime){
        this.appointmentType = appointmentType;
        this.appointmentStartTime = appointmentStartTime;
    }

    /*
     * return appointment type
     */
    public AppointmentType getAppointmentType(){
        return this.appointmentType;
    }

    /*
     * return appointment date/time
     */
    public LocalDateTime getAppointmentStartLocalDateTime(){
        return this.appointmentStartTime;
    }

    /*
     * return appointment date/time
     */
    public LocalTime getAppointmentStartLocalTime(){
        return this.appointmentStartTime.toLocalTime();
    }
}
