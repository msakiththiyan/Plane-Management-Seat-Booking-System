import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaneManagement {

    //Standard array to keep track of the seats.
    static char[][] seats = {
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'}
    };

    //Array of Tickets named ticketDetails to store all sold tickets of the session.
    static Ticket[][] ticketDetails = new Ticket[52][];

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int option;

        System.out.println("\n\tWelcome to the Plane Management Application\n");

        //Task 2 - Displaying the Menu
        do {
            System.out.println("*****************************************************");
            System.out.println("*\t\t\t\t\tMENU OPTIONS\t\t\t\t\t*");
            System.out.println("*****************************************************");
            System.out.println("\t1) Buy a Seat");
            System.out.println("\t2) Cancel a Seat");
            System.out.println("\t3) Find first available Seat");
            System.out.println("\t4) Show seating plan");
            System.out.println("\t5) Print Tickets information and total sales");
            System.out.println("\t6) Search ticket");
            System.out.println("\t0) Quit");
            System.out.println("*****************************************************\n");

            System.out.print("Please Select an Option: ");

            //Validation to check if a non-integer input is entered.
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Try again: ");
                scanner.next();
            }

            option = scanner.nextInt();

            System.out.println();

            switch (option) {
                case 1:
                    buy_seat();
                    break;

                case 2:
                    cancel_seat();
                    break;

                case 3:
                    find_first_available();
                    break;

                case 4:
                    show_seating_plan();
                    break;

                case 5:
                    print_tickets_info();
                    break;

                case 6:
                    search_ticket();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Invalid Option\n");
            }
        } while (option != 0);
    }

    public static void buy_seat() {
        Scanner scanner = new Scanner(System.in);

        //Prompting user to enter row,seat and validate accordingly.
        System.out.print("Enter the Row: ");
        char rowLetter = Character.toUpperCase(scanner.next().charAt(0));

        boolean validRow = (rowLetter >= 'A' && rowLetter <= 'D');
        int maxSeats;
        if (validRow && (rowLetter == 'A' || rowLetter == 'D')) {
            maxSeats = 14;
        } else {
            maxSeats = 12;
        }

        if (!validRow) {
            System.out.println("Invalid input. Enter a Row from A,B,C,D");
            return;
        }

        System.out.print("Enter the Seat Number: ");
        int seatNum = scanner.nextInt();


        if (seatNum <= 0 || seatNum > maxSeats) {
            System.out.println("Invalid Seat Number. Try again.\n");
            return;

        }

        int rowIndex = rowLetter - 'A';

        if (seats[rowIndex][seatNum - 1] == 'X') {
            System.out.println("Seat Already Booked. Try another one.\n");
            return;
        }

        double price;
        if (seatNum <= 5) {
            price = 200;
        } else if (seatNum <= 9) {
            price = 150;
        } else {
            price = 180;
        }

        //Letting the user know that they can book a seat now and getting their details
        System.out.println("Seat is available for booking. Enter your details to confirm.\n");

        System.out.print("Enter your Name: ");
        String first_name = scanner.next();

        System.out.print("Enter your Surname: ");
        String surname = scanner.next();

        System.out.print("Enter your e-mail: ");
        String email = scanner.next();

        System.out.println();

        //e-mail validation: Referred from //Referred from https://www.javatpoint.com/java-email-validation
        String emailRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        while (!matcher.matches()) {
            System.out.println("Invalid email address.");
            System.out.print("Enter your e-mail: ");
            email = scanner.next();
            matcher = pattern.matcher(email);
        }

        //Objects person, ticket created from Classes Person, Ticket respectively.
        Person person = new Person(first_name, surname, email);
        Ticket ticket = new Ticket(rowLetter, seatNum, price, person);
        Ticket.save(ticket);

        //updating seats array after booking a seat.
        seats[rowIndex][seatNum - 1] = 'X';
        System.out.println("Seat successfully Booked.\n");

        for (int i = 0; i < ticketDetails.length; i++) {
            if (ticketDetails[i] == null) {
                ticketDetails[i] = new Ticket[]{ticket};
                break;
            }
        }
    }

    public static void cancel_seat() {
        Scanner scanner = new Scanner(System.in);


        //Prompting user to enter row,seat and validate accordingly.
        System.out.print("Enter the Row: ");
        char rowLetter = Character.toUpperCase(scanner.next().charAt(0));

        System.out.print("Enter a Seat Number: ");
        int seatNum = scanner.nextInt();

        int rowIndex;
        int maxSeats;

        switch (rowLetter) {
            case 'A':
                rowIndex = 0;
                maxSeats = 14;
                break;
            case 'B':
                rowIndex = 1;
                maxSeats = 12;
                break;
            case 'C':
                rowIndex = 2;
                maxSeats = 12;
                break;
            case 'D':
                rowIndex = 3;
                maxSeats = 14;
                break;
            default:
                System.out.println("Invalid Row\n");
                return;
        }

        if (seatNum <= 0 || seatNum > maxSeats) {
            System.out.println("Invalid Seat Number. Try again.\n");
            return;
        }

        if (seats[rowIndex][seatNum - 1] == 'O') {
            System.out.println("Unbooked Seat. Try another one.");
            return;
        }

        //Checking if ticket is cancelled properly.
        boolean isTicketCancel = Ticket.cancel(rowLetter, seatNum);

        if (isTicketCancel) {
            seats[rowIndex][seatNum - 1] = 'O';
            System.out.println("Seat cancelled successfully.");
        } else {
            System.out.println("Couldn't cancel the seat.");
        }

        for (int i = 0; i < ticketDetails.length; i++) {
            if (ticketDetails[i] != null) {
                for (int j = 0; j < ticketDetails[i].length; j++) {
                    if (ticketDetails[i][j] != null && ticketDetails[i][j].getRow() == Character.toUpperCase(rowLetter) && ticketDetails[i][j].getSeat() == seatNum) {
                        ticketDetails[i] = null;
                        break;
                    }
                }
            }
        }
    }

    public static void find_first_available() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 'O') {
                    System.out.println("Seat " + (char) ('A' + i) + (j + 1) + " is Available.");
                    return;
                }
            }
        }
        System.out.println("No available seats found.");
    }

    public static void show_seating_plan() {

        System.out.println("Seating Plan: \n");

        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j]);
            }
            System.out.println();
        }
    }

    public static void print_tickets_info() {
        double totalSales = 0;


        //Looking into ticketDetails array and getting seat information to add to total sales.
        System.out.println("Tickets sold during the Session:\n");
        for (Ticket[] tickets : ticketDetails) {
            if (tickets != null) {
                for (Ticket ticket : tickets) {
                    if (ticket != null) {
                        System.out.println(Character.toString(ticket.getRow()).toUpperCase() +
                                ticket.getSeat() +
                                " = £" +
                                ticket.getPrice());
                        totalSales += ticket.getPrice();
                    }
                }
            }
        }
        System.out.println("Total Sales = £" + totalSales);
    }

    public static void search_ticket() {
        Scanner scanner = new Scanner(System.in);

        //Prompting user to enter row,seat and validate accordingly.
        System.out.print("Enter the Row: ");
        char rowLetter = Character.toUpperCase(scanner.next().charAt(0));

        boolean validRow = (rowLetter >= 'A' && rowLetter <= 'D');
        int maxSeats;
        if (validRow && (rowLetter == 'A' || rowLetter == 'D')) {
            maxSeats = 14;
        } else {
            maxSeats = 12;
        }

        if (!validRow) {
            System.out.println("Invalid input. Enter a Row from A,B,C,D");
            return;
        }

        System.out.print("Enter the Seat Number: ");
        int seatNum = scanner.nextInt();

        System.out.println();

        if (seatNum <= 0 || seatNum > maxSeats) {
            System.out.println("Invalid Seat Number. Try again.\n");
            return;
        }

        //Check to ensure a booked ticket's availability.
        boolean seatCheck = false;

        //Check to ensure and update if the given ticket exists.
        for (Ticket[] ticketBooking : ticketDetails) {
            if (ticketBooking != null) {
                for (Ticket ticket : ticketBooking) {
                    if (ticket != null && ticket.getRow() == Character.toUpperCase(rowLetter) && ticket.getSeat() == seatNum) {
                        System.out.println();
                        System.out.println("Ticket Details: \n");
                        System.out.println("Seat: " + Character.toString(ticket.getRow()).toUpperCase() + ticket.getSeat());
                        System.out.println("Price: £" + ticket.getPrice());
                        System.out.println("Name: " + ticket.getPerson().getName());
                        System.out.println("Surname: " + ticket.getPerson().getSurname());
                        System.out.println("Email: " + ticket.getPerson().getEmail());

                        seatCheck = true;
                        break;

                    }
                }
            }
        } if (!seatCheck){
            System.out.println("This seat is available.");
        }
    }
}