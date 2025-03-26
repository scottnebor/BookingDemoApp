package scottnebor;
import java.time.*;

class Appointment{
    
    protected AppointmentType appointmentType;
    protected LocalDateTime appointmentStartTime;

    Appointment(AppointmentType appointmentType, LocalDateTime appointmentStartTime){
        this.appointmentType = appointmentType;
        this.appointmentStartTime = appointmentStartTime;

    }

    AppointmentType GetAppointmentType(){
        return this.appointmentType;
    }
    LocalDateTime GetAppointmentStartTime(){
        return this.appointmentStartTime;
    }
}
