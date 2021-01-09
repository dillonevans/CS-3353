/**
 * Represents a book
 * @author Dillon Evans
 */
public class Book
{
    private int bookID, pageCount, ratingsCount, textReviewCount;
    private double averageRating;
    private String title, authors, publicationDate, publisher, languageCode, ISBN, ISBN13;

    /**
     * Creates a new Book object
     * @param data The information of the book
     */
    public Book(String[] data) 
    {
        setBookID(Integer.parseInt(data[0]));
        setTitle(data[1].replaceAll(" +", " "));
        setAuthors(data[2]);
        setAverageRating(Double.parseDouble(data[3]));
        setISBN(data[4]);
        setISBN13(data[5]);
        setLanguageCode(data[6]);
        setPageCount(Integer.parseInt(data[7]));
        setRatingsCount(Integer.parseInt(data[8]));
        setTextReviewCount(Integer.parseInt(data[9]));
        setPublicationDate(data[10]);
        setPublisher(data[11]);
    }
    
    /**
     * Sets the author(s) of the book
     * @param authors The author(s) of the book
     */
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    /**
     * Sets the average rating of the book
     * @param averageRating The average rating of the book
     */
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * Sets the ID of the book
     * @param bookID The ID of the book
     */
    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    /**
     * Sets the ISBN of the book
     * @param ISBN The ISBN of the book
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

     /**
     * Sets the ISBN-13 of the book
     * @param ISBN13 The ISBN-13 of the book
     */
    public void setISBN13(String ISBN13) {
        this.ISBN13 = ISBN13;
    }

    /**
     * Sets the language code of the book
     * @param languageCode The language code of the book
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Sets the page count of the book
     * @param pageCount The page count of the book.
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * Sets the publication date of the book
     * @param publicationDate The publication date of the book
     */
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Sets the publisher of the book
     * @param publisher The publisher of the book
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Sets the ratings count of the book
     * @param ratingsCount The ratings count of the book
     */
    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    /**
     * Sets the text review count of the book
     * @param textReviewCount the text review count of the book
     */
    public void setTextReviewCount(int textReviewCount) {
        this.textReviewCount = textReviewCount;
    }

    /**
     * Sets the title of the book
     * @param title The title of the book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the author(s) of the book
     * @return the author(s) of the book
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * Returns the average rating of the book
     * @return the average rating of the book
     */
    public double getAverageRating() {
        return averageRating;
    }

    /**
     * Returns the ID of the book
     * @return the ID of the book
     */
    public int getBookID() {
        return bookID;
    }

    /**
     * Returns the ISBN of the book
     * @return the ISBN of the book
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * Returns the ISBN-13 of the book
     * @return the ISBN-13 of the book
     */
    public String getISBN13() {
        return ISBN13;
    }

    /**
     * Returns the language code of the book
     * @return the language code of the book
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Returns the page count of the book
     * @return the page count of the book
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * Returns the publication date of the book
     * @return the publication date of the book
     */
    public String getPublicationDate() {
        return publicationDate;
    }

    /**
     * Returns the publisher of the book
     * @return the publisher of the book
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Returns the ratings count of the book
     * @return the ratings count of the book
     */
    public int getRatingsCount() {
        return ratingsCount;
    }

    /**
     * Returns the text review count of the book
     * @return the text review count of the book
     */
    public int getTextReviewCount() {
        return textReviewCount;
    }

    /**
     * Returns the title of the book
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        String titleString = String.format("\"%s\" by %s\n", getTitle(), getAuthors());
        String publicationString = String.format("Published by %s on %s. Language: %s. ID #%d\n", getPublisher(), getPublicationDate(), getLanguageCode(), getBookID());
        String pageCountString = String.format("Page count: %d\n", getPageCount());
        String ISBNString = String.format("ISBN: %s, ISBN-13: %s\n", getISBN(), getISBN13());
        String ratingsString = String.format("Average Rating: %.2f/5 according to %d ratings\n", getAverageRating(), getRatingsCount());

        output.append(titleString);
        output.append(publicationString);
        output.append(pageCountString);
        output.append(ISBNString);
        output.append(ratingsString);
        return output.toString();
    }

}