package scottnebor;
import java.util.*;
import java.time.*;



class AppointmentSlot{
    
    ArrayList<AppointmentType> allowedAppointmentTypes;
    protected LocalTime appointmentStartTime;

    AppointmentSlot(LocalTime appointmentStartTime){
        allowedAppointmentTypes = new ArrayList<AppointmentType>();
        this.appointmentStartTime = appointmentStartTime;

    }
    
    void AddAllowedAppointmentType(AppointmentType appointmentType ){
        allowedAppointmentTypes.add(appointmentType);
    }
    
    LocalTime GetAppointmentStartTime(){
        return this.appointmentStartTime;
    }


    boolean IsAppointmentTypeAllowed(AppointmentType appointmentType){
        Iterator<AppointmentType> iterator = allowedAppointmentTypes.iterator();
        while(iterator.hasNext()){
            AppointmentType at = iterator.next();
            if(at == appointmentType)
                return true;
        }
        return false;
    }

    boolean HasAppointmentTypes(){
        return (allowedAppointmentTypes.size() > 0);
    }
}
