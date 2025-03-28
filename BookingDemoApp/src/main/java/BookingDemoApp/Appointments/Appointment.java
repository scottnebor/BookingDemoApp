package BookingDemoApp.Appointments;
import java.time.*;

/*
 * this class represents an appointment with an appointment date/time and appointment type
 * 
 */
public class Appointment{
    
    private AppointmentType appointmentType;
    private LocalDateTime appointmentStartTime;

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
    public LocalDateTime getAppointmentStartDateTime(){
        return this.appointmentStartTime;
    }

    /*
     * return appointment time
     */
    public LocalTime getAppointmentStartTime(){
        return this.appointmentStartTime.toLocalTime();
    }
}
