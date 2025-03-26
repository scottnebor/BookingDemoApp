package scottnebor;

class AppointmentType{
    public static final int APPOINTMENT_LENGTH_CHECKIN  = 30;
    public static final int APPOINTMENT_LENGTH_STANDARD = 60;
    public static final int APPOINTMENT_LENGTH_CONSULT  = 90;

    public final static AppointmentType appointmentTypeCheckin  = new AppointmentType(APPOINTMENT_LENGTH_CHECKIN);
    public final static AppointmentType appointmentTypeStandard = new AppointmentType(APPOINTMENT_LENGTH_STANDARD);
    public final static AppointmentType appointmentTypeConsult  = new AppointmentType(APPOINTMENT_LENGTH_CONSULT);

    protected int appointmentLengthMinutes;

    protected AppointmentType(int appointmentLengthMinutes){

        this.appointmentLengthMinutes = appointmentLengthMinutes;
    }


    int GetLengthMinutes(){

        return appointmentLengthMinutes;
    }

}
