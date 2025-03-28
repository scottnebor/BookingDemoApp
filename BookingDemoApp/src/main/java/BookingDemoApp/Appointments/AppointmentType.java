package BookingDemoApp.Appointments;

/*
 * This class holds details on appointment types. 
 * It could be re-written an interface with a class for each appointment type.  However, given that all
 * appointment types are the same other than duration, we'll still with one class with a duration property.
 * The class is private and has a static instance for each appointment type
 */
public class AppointmentType{
    /*
     * define appointment lengths
     */
    public static final int APPOINTMENT_LENGTH_CHECKIN  = 30;
    public static final int APPOINTMENT_LENGTH_STANDARD = 60;
    public static final int APPOINTMENT_LENGTH_CONSULT  = 90;

    /*
     * create static appointment types 
     */
    public final static AppointmentType appointmentTypeCheckin  = new AppointmentType(APPOINTMENT_LENGTH_CHECKIN);
    public final static AppointmentType appointmentTypeStandard = new AppointmentType(APPOINTMENT_LENGTH_STANDARD);
    public final static AppointmentType appointmentTypeConsult  = new AppointmentType(APPOINTMENT_LENGTH_CONSULT);

    private int appointmentLengthMinutes;

    /*
     * protected constructor
     */
    private AppointmentType(int appointmentLengthMinutes){

        this.appointmentLengthMinutes = appointmentLengthMinutes;
    }

    /*
     * returns appointment length
     */
    public int getLengthMinutes(){

        return appointmentLengthMinutes;
    }

}
