import java.io.File;
import java.io.FileWriter;

public class Ticket {
    private  char row;
    private  int seat;
    double  price;
    Person  Person;

    //Constructor
    public Ticket(char row, int seat, double price, Person Person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.Person = Person;
    }

    //Seat - Getter, Setter
    public  void setSeat(int seatNo){
        this.seat = seatNo;
    }

    public  int getSeat(){
        return seat;
    }

    //Row - Getter, Setter
    public  void setRow(char RowNo){
        row = RowNo;
    }

    public  char getRow(){
        return Character.toUpperCase(row);
    }

    //price - Getter, Setter
    public  double getPrice() {
        return price;
    }

    public  void setPrice(double price) {
        this.price = price;
    }

    //person - Getter, Setter
    public  Person getPerson() {
        return Person;
    }

    public  void setPerson(Person person) {
        Person = person;
    }

    public static void save(Ticket ticket) {

        try {
            File soldTicket = new File("./src/resources/"+ ticket.row + ticket.seat + ".txt");

            //Checking if a ticket already exists.
            if (soldTicket.exists()) {
                System.out.println(ticket.row + ticket.seat + " already purchased.");
            }else {
                boolean create = soldTicket.createNewFile();
                FileWriter personDetails = new FileWriter("./src/resources/" + ticket.row + ticket.seat + ".txt");

                //Writing into the file
                personDetails.write("Firstname: " + ticket.Person.name);
                personDetails.write("\nSurname: " + ticket.Person.surname);
                personDetails.write("\nEmail: " + ticket.Person.email);
                personDetails.write("\nSeat: " + ticket.row + ticket.seat);
                personDetails.write("\nPrice: £" + ticket.price);

                personDetails.close();
            }

        }catch (Exception e) {
            System.out.println("Couldn't save Details.");
        }
    }

    //Self-defined method used to cancel a seat in cancel_seat method in PlaneManagement class.
    public static boolean cancel(char row, int seat) {

        try {
            File soldTicket = new File("./src/resources/" + row + seat +".txt");
            if (!soldTicket.exists()) {
                System.out.println(row + seat + " not Purchased.");
                return false;
            } else {
                return soldTicket.delete();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}