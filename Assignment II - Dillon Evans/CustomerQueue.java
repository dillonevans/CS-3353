/**
 * Stores Customer objects in a queue based on their priority.
 * @author Dillon Evans
 * @see Customer
 */
public class CustomerQueue 
{
    private final int C = 10;
    private Customer[] queue;
    private int capacity = 10, front = 0, size = 0;
    private boolean usesGrowthMethod = false;
    
    /**
     * Creates a new CustomerQueue instance
     * @param usesGrowthMethod Determines tight vs growth strategy
     */
    public CustomerQueue(boolean usesGrowthMethod)
    {
        queue = new Customer[capacity];
        this.usesGrowthMethod = usesGrowthMethod;
    }

    /**
     * Overloaded CustomerQueue constructor used for restructuring.
     * @param capacity The new capacity
     * @param usesGrowthMethod Determines tight vs growth strategy
     */
    private CustomerQueue(int capacity, boolean usesGrowthMethod)
    {
        queue = new Customer[capacity];
        this.capacity = capacity;
        this.usesGrowthMethod = usesGrowthMethod;
    }

    /**
     * Appends a customer to the end of the queue 
     * @param customer The customer to append
     */
    public void enqueue(Customer customer)
    {
        int allocated = 0;
        Customer temp = customer;

        try
        {
            if (isFull())
            {
                if (size == 80)
                {
                    throw new QueueOverflowException();
                }
                else
                {
                    dynamicallyResize();
                }
            }
            
            allocated = (front + size) % capacity;
            queue[allocated] = temp;
            size++;

            if (!isEmpty())
            {
                //Ensures that the customer with the smallest ID number is at the front of the Queue.
                if (customer.getId() < first().getId())
                {
                    restoreMin();
                }
            }
        }
        catch (QueueOverflowException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes and returns the first customer in the queue based on their ID number.
     * @param enforceMinimum Determines whether or not to perform a standard dequeue
     * or to maintain the minimum element at the front of the queue.
     * @return The first customer in the queue
     */
    public Customer dequeue(boolean enforceMinimum)
    {
        Customer first = queue[front];
        try
        {
            if (isEmpty())
            {
                throw new QueueUnderflowException();
            }
            else
            {
                first = queue[front]; 
                queue[front] = null; 
                front = (front + 1) % capacity;
                size--; 
                //When resizing the queue this does not need to occur.
                if (enforceMinimum) 
                {
                    restoreMin();
                }
            }
        }
        catch (QueueUnderflowException e)
        {
            System.out.println(e.getMessage());
        }
        return first;
    }


    /**
     * Returns but does not remove the first customer in the queue.
     * @return The first customer in the queue
     */
    public Customer first()
    {
        return queue[front];
    }

    /**
     * Maintains the minimum property: The customer with the smallest ID should be at the front of the list.
     * Recursively rebuilds the list by reinserting all elements (indirect recursion with O(n) time complexity). 
     * @see enqueue
     * @see dequeue
     */
    private void restoreMin()
    {
        if (size > 0) {enqueue(dequeue(true));}
        else {return;}
    }

    /**
     * Returns the size of the queue
     * @return The size of the queue
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Returns true if the number of elements in the queue = 0 
     * @return True if the number of elements in the queue = 0
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Returns true if the number of elements in the queue is at capacity
     * @return True if the number of elements in the queue is at capacity
     */
    public boolean isFull()
    {
        return size == capacity;
    }

    /**
     * Dynamically resizes the array to handle growth
     */
    private void dynamicallyResize()
    {
        int newCapacity = usesGrowthMethod ? capacity * 2 : capacity + C, initialSize = size;
        CustomerQueue resizedQueue = new CustomerQueue(newCapacity, usesGrowthMethod);
        Customer temp;
        for (int i = 0; i < capacity; i++)
        {
            if ((temp = this.dequeue(false)) != null)
            resizedQueue.enqueue(temp);
        }
        setQueue(resizedQueue.getQueue());
        front = 0;
        size = initialSize;
        capacity = newCapacity;
    }

    /**
     * Returns the array used to implement a queue
     * @return The array used to implement a queue
     */
    public Customer[] getQueue() {
        return queue;
    }

    /**
     * Reassigns the queue reference
     * @param queue The new queue array
     */
    public void setQueue(Customer[] queue) {
        this.queue = queue;
    }
}
