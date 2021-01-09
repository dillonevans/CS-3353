/**
 * Used to represent Customers by storing their information in an
 * instance of this class.
 * @author Dillon Evans
 */
public class Customer 
{
    private int id;
    private String phone, name, service;

    /**
     * 
     * @param id The ID of the customer
     * @param phone The Phone # of the customer
     * @param name The Name of the customer
     * @param service The customer's requested service
     */
    public Customer(int id, String phone, String name, String service)
    {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.service = service;
    }

    @Override
    public String toString() {
        return String.format("Customer ID: %d, Name: %-5s, Phone: %-5s, Service: %5s", id, name, phone, service);
    }

    /**
     * Sets the customer's ID
     * @param id The ID to assign to the customer
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Sets the customer's Name
     * @param name The Name to assign to the customer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the customer's Phone Number
     * @param phone The Phone Number to assign to the customer
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Sets the customer's service
     * @param service The customer's requested service
     */
    public void setService(String service) {
        this.service = service;
    }
    
    /**
     * Returns the customer's ID
     * @return The customer's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the customer's Name
     * @return The customer's Name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the customer's Phone Number
     * @return The customer's Phone Number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the customer's Requested Service
     * @return The customer's Requested Service
     */
    public String getService() {
        return service;
    }
}
