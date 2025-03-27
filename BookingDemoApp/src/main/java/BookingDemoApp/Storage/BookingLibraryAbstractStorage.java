package BookingDemoApp.Storage;
import java.time.*;

import BookingDemoApp.*;
import BookingDemoApp.Appointments.*;

/*
 * Interface for storing bookings for a single practitioner.  Supports Adding an appointment, and getting appointments 
 */
public abstract class BookingLibraryAbstractStorage {

    /*
     * function to store an appointment
     */
    public abstract void StoreAppointment(Appointment appointment);

    /*
     * function to retrieve an appointment
     */
    public abstract AppointmentList GetAppointments(LocalDate appointmentDates);
}
