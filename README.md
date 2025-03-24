# Plane Management System

## Project Overview
This Java application manages seat bookings for a plane, allowing users to buy, cancel, and search for seats, as well as view seating plans and ticket information. The system includes features like email validation, file handling for ticket storage, and dynamic seat pricing.

## Features
- **Buy a Seat**: Book a seat by providing row, seat number, and personal details (name, surname, email).
- **Cancel a Seat**: Cancel a booked seat by specifying the row and seat number.
- **Find First Available Seat**: Locate the first available seat in the plane.
- **Show Seating Plan**: Display the current seating arrangement using 'O' for available seats and 'X' for booked seats.
- **Print Ticket Information**: View details of all sold tickets and the total sales.
- **Search Ticket**: Search for a specific ticket by row and seat number.
- **Email Validation**: Ensures the provided email address is valid before booking.
- **File Handling**: Saves ticket details to `.txt` files for persistence.

## Classes
1. **PlaneManagement**: Main class handling user interactions and menu operations.
2. **Ticket**: Manages ticket details (row, seat, price, person) and file operations.
3. **Person**: Stores personal details (name, surname, email) of the ticket holder.

## How to Run
1. Ensure you have Java installed on your system.
2. Clone the repository:
   ```bash
   git clone https://github.com/msakiththiyan/Plane-Management-Seat-Booking-System.git
