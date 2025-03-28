package BookingDemoApp.Storage;
import java.time.*;

import BookingDemoApp.Appointments.*;

/*
 * Interface for storing bookings for a single practitioner.  Supports Adding an appointment, and getting appointments 
 */
public abstract class AbstractStorage {

    /*
     * function to store an appointment
     */
    public abstract void storeAppointment(Appointment appointment);

    /*
     * function to retrieve an appointment
     */
    public abstract AppointmentList getAppointments(LocalDate appointmentDates);
}
