/**
 * This class is used to create Book objects
 * @author Dillon Evans
 */
public class Book 
{
    //Instance Variables
    private String title;
    private String ISBN;
    private String DOI; 
    private String subjectCategory;
    private String expectedPublicationDate;
    private String firstAuthor;
    private String secondAuthor;
    private String firstAuthorAffiliation;
    private String secondAuthorAffiliation;
    private int numberOfCopies;

    //Lists to maintain
    private ElementList<Student> waitlist = new ElementList<>();
    private ElementList<Student> renterList = new ElementList<>();

    /**
     * Sets the title of the book
     * @param title The title of the book
     */
    public void setTitle(String title) {this.title = title;}
    
    /**
     * Sets the DOI of the book
     * @param dOI The DOI of the book
     */
    public void setDOI(String dOI) {DOI = dOI;}

    /**
     * Sets the expected publication date of the book
     * @param expectedPublicationDate The expected publication date of the book
     */
    public void setExpectedPublicationDate(String expectedPublicationDate) {this.expectedPublicationDate = expectedPublicationDate;}

    /**
     * Sets the first author of the book
     * @param firstAuthor The first author of the book
     */
    public void setFirstAuthor(String firstAuthor) {this.firstAuthor = firstAuthor;}

    /**
     * Sets the affiliation of the first author
     * @param firstAuthorAffiliation The affiliation of the first author
     */
    public void setFirstAuthorAffiliation(String firstAuthorAffiliation) {this.firstAuthorAffiliation = firstAuthorAffiliation;}

    /**
     * Sets the ISBN of the book
     * @param iSBN The ISBN of the book
     */
    public void setISBN(String iSBN) {ISBN = iSBN;}

    /**
     * Sets the number of copies of the book available
     * @param numberOfCopies The number of copies of the book 
     */
    public void setNumberOfCopies(int numberOfCopies) {this.numberOfCopies = numberOfCopies;}

    /**
     * Sets the second author of the book
     * @param secondAuthor The second author of the book
     */
    public void setSecondAuthor(String secondAuthor) {this.secondAuthor = secondAuthor;}

    /**
     * Sets the affiliation of the second author
     * @param secondAuthorAffiliation The affiliation of the second author
     */
    public void setSecondAuthorAffiliation(String secondAuthorAffiliation) {this.secondAuthorAffiliation = secondAuthorAffiliation;}

    /**
     * Sets the subject of the book
     * @param subjectCategory The subject of the book
     */
    public void setSubjectCategory(String subjectCategory) {this.subjectCategory = subjectCategory;}

    /**
     * Returns the DOI of the book
     * @return The DOI of the book
     */
    public String getDOI() {return DOI;}

     /**
      * Returns the expected publication date of the book
      * @return The DOI of the book
      */
    public String getExpectedPublicationDate() {return expectedPublicationDate;}

    /**
     * Returns the first author of the book
     * @return The first author of the book
     */
    public String getFirstAuthor() {return firstAuthor;}

    /**
     * Returns the affiliation of the first author
     * @return The affiliation of the first author
     */
    public String getFirstAuthorAffiliation() {return firstAuthorAffiliation;}

    /**
     * Returns the ISBN of the book
     * @return The ISBN of the book
     */
    public String getISBN() {return ISBN;}

    /**
     * Returns the number of copies remaining
     * @return The number of copies remaining
     */
    public int getNumberOfCopies() {return numberOfCopies;}

    /**
     * Returns the second author
     * @return The second author
     */
    public String getSecondAuthor() {return (!secondAuthor.isBlank() ? secondAuthor : "N/A");}

    /**
     * Returns the affiliation of the second author
     * @return The affiliation of the second author
     */
    public String getSecondAuthorAffiliation() {return secondAuthorAffiliation;}

    /**
     * Returns the subject of the book
     * @return The subject of the book
     */
    public String getSubjectCategory() {return subjectCategory;}

    /**
     * Returns the title of the book
     * @return The title of the book
     */
    public String getTitle() {return title;}
    
    /**
     * Return true if the number of copies > 0
     * @return True if the number of copies > 0
     */
    public boolean isAvailable(){return numberOfCopies > 0;}

    /**
     * Adds a student to the waiting list for the book
     * @param student The student to add to the waiting list
     */
    public void addToWaitlist(Student student) {waitlist.append(student.getName(), student);}

    /**
     * Returns the list of students renting the book
     * @return The list of students renting the book
     */
    public ElementList<Student> getRenterList() {return renterList;}

     /**
     * Returns the list of students waiting to check out the book
     * @return The list of students waiting to check out the book
     */
    public ElementList<Student> getWaitlist() {return waitlist;}   

    /**
     * Checks the book in to the library 
     * @param student The student returning the book
     */
    public void checkIn(Student student)
    {
        student.getRentedBookList().removeElement(this.getTitle());
        renterList.removeElement(student.getName());
        numberOfCopies++;

        //The first person on the wait list automatically checks out the book
        if (!waitlist.isEmpty())
        {
            waitlist.removeFirst().rentBook(this);
        }
    }

    /**
     * Checks the book out of the library
     * @param renter The student renting the book
     */
    public void checkOut(Student renter)
    {
        if (!renterList.contains(renter.getName()))
        {
           if (isAvailable())
           {
               renter.getRentedBookList().append(this.getTitle(), this);
               renterList.append(renter.getName(), renter);
               renter.setBooksRented(renter.getBooksRented() - 1);
               numberOfCopies--;
           }
           else
           {
                if (!waitlist.contains(renter.getName()))
                waitlist.append(renter.getName(), renter);
           }
        }
    }

    /**
     * Returns all the information pertaining to the book when called
     */
    @Override
    public String toString() 
    {
        StringBuilder output = new StringBuilder();
        output.append(String.format("Title: %s\n", getTitle()));
        output.append(String.format("First Author: %s, %s\n", getFirstAuthor(), getFirstAuthorAffiliation()));
        if (!secondAuthor.isBlank()) 
        {
            output.append(String.format("Second Author: %10s, %s\n", getSecondAuthor(), getSecondAuthorAffiliation()));
        }
        output.append(String.format("Expected Publication Date: %s\n", getExpectedPublicationDate()));
        output.append(String.format("ISBN: %-30s\nDOI:  %-30s\n", getISBN(),getDOI()));
        output.append(String.format("Subject: %-30s\nAvailable: %-30s\n", getSubjectCategory(),getNumberOfCopies()));
        if (!renterList.isEmpty())
        {
            output.append("Renter List:\n");
            for (Student s : renterList)
            {
                output.append(s);
                output.append("\n");
            }
        }
        return output.toString();
    }

    /**
     * Prints the list of students waiting to check out the book.
     */
    public void viewWaitlist()
    {
        System.out.println("Waitlist: ");
        if (!waitlist.isEmpty()) {waitlist.print();}
        else {System.out.println("Empty.");}
    }
}