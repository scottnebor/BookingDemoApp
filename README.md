## Overview
This is a java codebase that implements a basic clinic scheduling application based on the following rules

- The clinic is open from 9am until 5pm.
- The clinic offers three types of appointments, a 90 minutes initial consultation, standard 60 minute appointments and 30 minute check-ins.
- Appointments do not overlap. There can only be one booked appointment at any time.
- Appointments start on the hour or half-hour.
- Bookings can only be made for appointments that start and end within the clinic hours.
- Bookings cannot be made within 2 hours of the appointment start time.

**The application implements the following**

- Provides the patient with a list of available appointment times. Inputs are the appointment type and a date, either today or in the future. The 2 hour booking deadline applies for today’s appointments.
- Allows the patient to book an appointment.
- Provides the practitioner with a list of scheduled appointments for the current day.

## Instructions
- install JDK 17 or higher
- install maven
- open a terminal
- checkout this git repository
- cd BookingDemoApp/BookingDemoApp
- mvn clean test
- review output and confirm that all unit tests pass

Note: this code base does not have a terminal, UI or callable API.  Functionality is verified only through unit tests

## Confirmed Test Environments
- This application has been confirmed as working on Linux Mint 20 running openjdk 21

## Code Notes and Information

**Core Functional Requirements**
- The core functional requirements are exposed via three functions in BookingLibrary.java in the BookingLibrary class
  - bookAppointment
  - getAvailableAppointmentTimes
  - getPractitionerBookedAppointments

**Core Classes**
- The following classes are used
  - BookingLibrary is the core class that exposes the main business logic functions
  - AppointmentType: class that holds information about different appointment types that can be booked
  - Appointment: this holds an appointment that has been booked
  - AppointmentList: this is a collection of appointments
  - AppointmentSlot: this is timeslot that could potentially be booked
  - AppointmentSlotList: this is a collection of AppointmentSlots
  - BookingLibraryException: class used for exceptions when calling into BookingLibrary
  - AbstractStorage: interface for storing Appointments
  - MemoryStorage: class that stores Appointments in memory

**Multithreading/Multiprocessing**
- The core functions in BookingLibrary are synchronized ensuring thread safety within the BookingLibrary class

**Data Persistence**
- Appointments are only maintained for the lifetime of a BookingLibrary object
- Storage in memory is done via a memory storage class that implements a storage interface.  This allows for swapping to use a persistent storage class later

**Design Choices**
- The Java LocalDate, LocalTime, and LocalDateTime classes were chosen to represent dates/times
  - These classes will not work if timezones need to be considered.  Given that there were no timezone requirements, these classes are appropriate
- BookingLibrary assumes that you are executing a bunch of operations all at one point in time (i.e. time is static and not changing)
- The code assumes that the clinic always opens and closes within the same day (i.e. not something like 10 PM on Monday to 1 AM on Tuesday).  Some refactoring would be needed if this is not the case.
  - For simplicity, this seems like an apppriate design choice given the requirements
- From a performance perspective, some of the classes (ex: AppointmentSlotList) use lists, and some operations require iteration through the list.  i.e. O(n) performance 
  - given that AppointmentSlotList is only used to hold appointment slots for one day, and given the limited number of appointments that can be scheduled in a day, performance is not expected to be a concern.  As such, simplicity was favoured over performance optimizations
