package scottnebor;
import java.util.*;
import java.time.*;

//List of appointments that are booked

class AppointmentList{
    ArrayList<Appointment> appointments;
    AppointmentList(){

        appointments = new ArrayList<Appointment>();
    }

    void AddAppointment(Appointment appointment){
        appointments.add(appointment);
        appointments.sort((Appointment a, Appointment b) -> { return a.GetAppointmentStartTime().compareTo(b.GetAppointmentStartTime()); } );
    }

    int GetAppointmentListSize(){
        return appointments.size();
    }

    //todo - document that it is the callers responsibility to ensure a valid index
    Appointment GetAppointment(int index){
        return appointments.get(index);
    }
    

    int MinutesFreeAtTimeslot(LocalTime lt, LocalTime clinicCloseTime){
        ;
        if(lt.isAfter(clinicCloseTime))
            return 0;
        int maxMinutesFree = (int)Duration.between(lt, clinicCloseTime).getSeconds()/60;
        
        Iterator<Appointment> iterator = appointments.iterator();
        while(iterator.hasNext()){
            Appointment appointment = iterator.next();
            LocalTime appointmentStartsAtTime = appointment.GetAppointmentStartTime().toLocalTime();
            LocalTime appointmentEndAtTime = appointment.GetAppointmentStartTime().toLocalTime().plusMinutes(appointment.GetAppointmentType().GetLengthMinutes());

      

            if(lt.compareTo(appointmentStartsAtTime) >=0  && lt.compareTo(appointmentEndAtTime) <0)
                maxMinutesFree = 0;
            if(appointmentStartsAtTime.isAfter(lt)){
                int minutesAfter = (int)Duration.between(lt, appointmentStartsAtTime).getSeconds()/60;
                maxMinutesFree = Math.min(minutesAfter, maxMinutesFree);
            }
            if(appointmentEndAtTime.isAfter(lt)){
                int minutesAfter = (int)Duration.between(lt, appointmentEndAtTime).getSeconds()/60;
                maxMinutesFree = Math.min(minutesAfter, maxMinutesFree);
            }
            
                
        }
        return maxMinutesFree;

    }



    
}
