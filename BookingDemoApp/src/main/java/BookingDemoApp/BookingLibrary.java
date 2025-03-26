package scottnebor;

import java.time.*;

//DON'T FORGET - thread safety 
//explain choice on localdate/localdate/localtime time
public class BookingLibrary{
    protected static final int CLINIC_OPEN_TIME_HOUR    = 9;   //9 AM - clinic start time
    protected static final int CLINIC_OPEN_TIME_MIN     = 0;   
    protected static final int CLINIC_OPEN_TIME_SECOND  = 0;   
    
    protected static final int CLINIC_CLOSE_TIME_HOUR    = 17;   //5 PM - clinic close time
    protected static final int CLINIC_CLOSE_TIME_MIN     = 0;   
    protected static final int CLINIC_CLOSE_TIME_SECOND  = 0;   

    protected static final int BOOKING_INTERVAL_MINUTES  = 30;// appointments must be booked on 30 minute intervals
    


    protected BookingLibraryAbstractStorage bookingLibraryStorage;
    protected LocalTime clinicOpenTime;
    protected LocalTime clinicCloseTime;
    protected LocalDateTime currentTime;
    public BookingLibrary(){
        bookingLibraryStorage = new BookingLibraryMemoryStorage();
        clinicOpenTime = LocalTime.of(CLINIC_OPEN_TIME_HOUR, CLINIC_OPEN_TIME_MIN, CLINIC_OPEN_TIME_SECOND);
        clinicCloseTime = LocalTime.of(CLINIC_CLOSE_TIME_HOUR, CLINIC_CLOSE_TIME_MIN, CLINIC_CLOSE_TIME_SECOND);
        currentTime = LocalDateTime.now();
    }

    
    void OverrideCurrentTime(LocalDateTime overrideTime){
        currentTime = overrideTime;

    }

    void BookAppointment(AppointmentType appointmentType, LocalDateTime appointmentStartDateTime) throws BookingLibraryException{
        

        AppointmentSlotList appointmentSlotList = GetAvailableAppointmentTimes(appointmentStartDateTime.toLocalDate());
        
        if(appointmentSlotList.CanBookAppointment(appointmentType, appointmentStartDateTime.toLocalTime())){
            Appointment appointment = new Appointment(appointmentType, appointmentStartDateTime);
            bookingLibraryStorage.StoreAppointment(appointment); 
        }
        else{
            throw new BookingLibraryException(BookingLibraryException.BOOKING_EXCEPTION_INVALID_DATETIME);
        }
    }


    AppointmentSlotList GetAvailableAppointmentTimes(LocalDate appointmentDate){
        
        if(appointmentDate.isBefore(currentTime.toLocalDate()))
            return new AppointmentSlotList(); //return an empty list

        LocalTime lt = clinicOpenTime;
        AppointmentSlotList appointmentSlotList = new AppointmentSlotList();


        AppointmentList bookedAppointmentList = bookingLibraryStorage.GetAppointments(appointmentDate);
        while (lt.isBefore(clinicCloseTime)){
            LocalDateTime ldt = LocalDateTime.of(appointmentDate, lt);
            if(ldt.isBefore(currentTime.plusHours(2))){
                lt = lt.plusMinutes(BOOKING_INTERVAL_MINUTES);
                continue;
            }
            
            


            AppointmentSlot appointmentSlot = new AppointmentSlot(lt);

            int numMinutesFree = bookedAppointmentList.MinutesFreeAtTimeslot(lt,clinicCloseTime);
            
            if(numMinutesFree>= AppointmentType.appointmentTypeCheckin.GetLengthMinutes()){
                appointmentSlot.AddAllowedAppointmentType(AppointmentType.appointmentTypeCheckin);
            }
            if(numMinutesFree>= AppointmentType.appointmentTypeStandard.GetLengthMinutes()){
                appointmentSlot.AddAllowedAppointmentType(AppointmentType.appointmentTypeStandard);
            }
            if(numMinutesFree>= AppointmentType.appointmentTypeConsult.GetLengthMinutes()){
                appointmentSlot.AddAllowedAppointmentType(AppointmentType.appointmentTypeConsult);
            }
        

            if(appointmentSlot.HasAppointmentTypes()){
                appointmentSlotList.AddAppointmentSlot(appointmentSlot);
            }
            
            lt = lt.plusMinutes(BOOKING_INTERVAL_MINUTES);

        }
        return appointmentSlotList;
        

        

    }

    AppointmentList GetPractitionerBookedAppointments(LocalDate appointmentDate){
        return bookingLibraryStorage.GetAppointments(appointmentDate);
    }



    public static void main(String[] args) {
        
        System.out.println("Hello");
        
    }

}
