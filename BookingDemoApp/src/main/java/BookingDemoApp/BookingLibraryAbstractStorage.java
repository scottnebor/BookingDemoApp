package scottnebor;
import java.time.*;

public abstract class BookingLibraryAbstractStorage {
    abstract void StoreAppointment(Appointment appointment);
    abstract AppointmentList GetAppointments(LocalDate appointmentDates);
}
