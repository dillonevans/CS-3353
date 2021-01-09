/**
 * This class is used to create Student Objects
 * @author Dillon Evans
 */
public class Student 
{
    //Instance variables
    private String name;
    private int booksRented = 0;

    //List to maintain.
    private ElementList<Book> rentedBookList = new ElementList<>();

    /**
     * Sets the number of books rented by the student
     * @param booksRented The number of books rented by the student
     */
    public void setBooksRented(int booksRented) {this.booksRented = booksRented;}

    /**
     * Sets the name of the book
     * @param name The name of the book
     */
    public void setName(String name) {this.name = name;}

    /**
     * Rents the book to the student
     * @param toRent The book to rent
     */
    public void rentBook(Book toRent) {toRent.checkOut(this);}

    /**
     * Returns the book to the library
     * @param toReturn The book to return
     */
    public void returnBook(Book toReturn) {toReturn.checkIn(this);}

    /**
     * Returns the name of the student
     * @return The name of the student
     */
    public String getName() {return name;}

    /**
     * Returns a list of all books rented by the student
     * @return
     */
    public ElementList<Book> getRentedBookList() {return rentedBookList;}

    /**
     * Returns the number of books rented
     * @return The number of books rented
     */
    public int getBooksRented() {return booksRented;}

    /**
     * Returns the student's name when called
     */
    @Override
    public String toString() {return getName();}

}
