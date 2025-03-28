package BookingDemoApp;

import java.time.*;
import BookingDemoApp.Storage.*;
import BookingDemoApp.Appointments.*;

/*
 * This is the main class that integrators would use for the booking library.  It has three core functions
 *  bookAppointment
 *  getAvailableAppointmentTimes
 *  getPractitionerBookedAppointments
 * 
 * Appointment booking only last the lifetime of this class.  It does not persisently store anything
 */
public class BookingLibrary{
    private static final int CLINIC_OPEN_TIME_HOUR    = 9;   //9 AM - clinic start time
    private static final int CLINIC_OPEN_TIME_MIN     = 0;   
    private static final int CLINIC_OPEN_TIME_SECOND  = 0;   
    
    private static final int CLINIC_CLOSE_TIME_HOUR    = 17;   //5 PM - clinic close time
    private static final int CLINIC_CLOSE_TIME_MIN     = 0;   
    private static final int CLINIC_CLOSE_TIME_SECOND  = 0;   

    private static final int BOOKING_INTERVAL_MINUTES  = 30;// appointments must be booked on 30 minute intervals
    
    private static final int MIN_BOOKING_TIME_HOURS  = 2;// appointments must be booked at least 2 hours in advance
    


    private AbstractStorage bookingLibraryStorage;
    private LocalTime clinicOpenTime;
    private LocalTime clinicCloseTime;
    private LocalDateTime currentDateTime;

    /*
     * constructor
     */
    public BookingLibrary(){
        bookingLibraryStorage = new MemoryStorage();
        clinicOpenTime = LocalTime.of(CLINIC_OPEN_TIME_HOUR, CLINIC_OPEN_TIME_MIN, CLINIC_OPEN_TIME_SECOND);
        clinicCloseTime = LocalTime.of(CLINIC_CLOSE_TIME_HOUR, CLINIC_CLOSE_TIME_MIN, CLINIC_CLOSE_TIME_SECOND);
        currentDateTime = LocalDateTime.now();
    }

    
    /*
     * function should only be using by junit.  This function can be used to simulate a different date/time than the current time
     */
    protected synchronized void overrideCurrentTime(LocalDateTime overrideTime){
        currentDateTime = overrideTime;
    }

    /*
     * Books an appointment.  Throws an exception if the appointment cannot be booked for the given date/time.
     * Throws an exception if the appointment can't be booked
     */
    public synchronized void bookAppointment(AppointmentType appointmentType, LocalDateTime appointmentStartDateTime) throws BookingLibraryException{
        

        AppointmentSlotList appointmentSlotList = getAvailableAppointmentTimes(appointmentStartDateTime.toLocalDate());
        
        if(canbookAppointment(appointmentSlotList,appointmentType, appointmentStartDateTime)){
            Appointment appointment = new Appointment(appointmentType, appointmentStartDateTime);
            bookingLibraryStorage.storeAppointment(appointment); 
        }
        else{
            throw new BookingLibraryException(BookingLibraryException.BookingLibraryErrorCode.BOOKING_EXCEPTION_INVALID_DATETIME);
        }
    }

    

    /*
     * function that returns a list of AppointmentSlots that could potentially be booked(time, and appointment type) for a given date
     */
    public synchronized AppointmentSlotList getAvailableAppointmentTimes(LocalDate appointmentDate){
        

        
        AppointmentSlotList appointmentSlotList = new AppointmentSlotList();


        ///Get all existing appointments
        AppointmentList bookedAppointmentList = bookingLibraryStorage.getAppointments(appointmentDate);

        //loop through all timeslot in the day starting at the clinic open time
        LocalTime appointmentTime = clinicOpenTime;
        while (appointmentTime.isBefore(clinicCloseTime)){

            //is the event within 2 hours or less.   If so, skip to the next slot
            LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);
            if(appointmentDateTime.isBefore(currentDateTime.plusHours(MIN_BOOKING_TIME_HOURS))){
                appointmentTime = appointmentTime.plusMinutes(BOOKING_INTERVAL_MINUTES);
                continue;
            }
            
            

            //build an appointmentSlot
            AppointmentSlot appointmentSlot = new AppointmentSlot(appointmentDateTime);


            //how many minutes are free at the current appointmentSlotTime
            int numMinutesFree = minutesFreeAtTimeslot(bookedAppointmentList,appointmentTime,clinicCloseTime);
            
            //check if we have enough time for each appointmentType
            if(numMinutesFree>= AppointmentType.appointmentTypeCheckin.getLengthMinutes()){
                appointmentSlot.addAllowedAppointmentType(AppointmentType.appointmentTypeCheckin);
            }
            if(numMinutesFree>= AppointmentType.appointmentTypeStandard.getLengthMinutes()){
                appointmentSlot.addAllowedAppointmentType(AppointmentType.appointmentTypeStandard);
            }
            if(numMinutesFree>= AppointmentType.appointmentTypeConsult.getLengthMinutes()){
                appointmentSlot.addAllowedAppointmentType(AppointmentType.appointmentTypeConsult);
            }
        
            //did we add at least one appointmentType.  If so, then add the appointmentSlot to the list
            if(appointmentSlot.hasAppointmentTypes()){
                appointmentSlotList.addAppointmentSlot(appointmentSlot);
            }
            
            appointmentTime = appointmentTime.plusMinutes(BOOKING_INTERVAL_MINUTES);

        }
        return appointmentSlotList;
    }

    /*
     * function lists all booked appointments for a day
     */
    public synchronized AppointmentList getPractitionerBookedAppointments(LocalDate appointmentDate){
        return bookingLibraryStorage.getAppointments(appointmentDate);
    }


    /*
     * given a list of slots, determine if there's one with a matching time and an appointment type that's allowed
     */
    protected boolean canbookAppointment(AppointmentSlotList appointmentSlotList,AppointmentType appointmentType, LocalDateTime appointmentDateTime){
        for(int counter=0;counter<appointmentSlotList.getAppointmentSlotListSize();counter++){
            
            AppointmentSlot sl = appointmentSlotList.getAppointmentsSlot(counter);
            if(!sl.getAppointmentSlotStartDateTime().equals(appointmentDateTime))
                continue;
            
            if(sl.isAppointmentTypeAllowed(appointmentType))
                return true;
        }
        return false;
    
    }
    
    /*
     * function returns how many minutes are free starting at a specific time, accounting for existing appointments.
     * function assumes that all appointments in AppointmentList are for the same date
     */
    protected int minutesFreeAtTimeslot(AppointmentList bookedAppointmentList, LocalTime timeSlotStart, LocalTime clinicCloseTime){
        ;
        if(timeSlotStart.isAfter(clinicCloseTime))
            return 0;

        //how many minutes are free from timeSlotStart to clinicClose time
        int maxMinutesFree = (int)Duration.between(timeSlotStart, clinicCloseTime).getSeconds()/60;
        
        for(int counter=0;counter<bookedAppointmentList.getAppointmentListSize();counter++){
        
            Appointment appointment = bookedAppointmentList.getAppointment(counter);
            LocalTime appointmentStartsAtTime = appointment.getAppointmentStartTime();
            LocalTime appointmentEndAtTime = appointment.getAppointmentStartTime().plusMinutes(appointment.getAppointmentType().getLengthMinutes());
     
      

            //does the timeSlot fall within a booked appointment.  If so, then return immediately that there's no time free
            if(timeSlotStart.compareTo(appointmentStartsAtTime) >=0  && timeSlotStart.compareTo(appointmentEndAtTime) <0)
                return 0;

            //check if the appointment starts after timeSlotStart.  If so, potentially reduce maxMinutesFree
            if(appointmentStartsAtTime.isAfter(timeSlotStart)){
                int minutesAfter = (int)Duration.between(timeSlotStart, appointmentStartsAtTime).getSeconds()/60;
                maxMinutesFree = Math.min(minutesAfter, maxMinutesFree);
            }
            
            
                
        }
        return maxMinutesFree;

    }


}
