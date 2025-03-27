package scottnebor;
import java.time.*;

/*
 * this class represents an appointment with an appointment date/time and appointment type
 * 
 */
class Appointment{
    
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
    public AppointmentType GetAppointmentType(){
        return this.appointmentType;
    }

    /*
     * return appointment date/time
     */
    public LocalDateTime GetAppointmentStartLocalDateTime(){
        return this.appointmentStartTime;
    }

    /*
     * return appointment date/time
     */
    public LocalTime GetAppointmentStartLocalTime(){
        return this.appointmentStartTime.toLocalTime();
    }
}
