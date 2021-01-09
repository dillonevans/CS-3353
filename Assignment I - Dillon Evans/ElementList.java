import java.util.Iterator;

/**
 * An implementation of a Linked List
 * @param <E> The object type to be used
 * @author Dillon Evans
 */
public class ElementList<E> implements Iterable<E>
{
    /**
     * The generic paramterized node class
     */
    public class Node
    {
        private E value;
        private Node next;
        private String elementName;

        /**
         * Creates a new Node object
         * @param elementName the name of the element to store
         * @param value the element to store
         */
        public Node(String elementName, E value)
        {
            this.value = value;
            this.elementName = elementName;
            this.next = null;
        }
        
        /**
         * Returns the element name of the node
         * @return The element name of node
         */
        public String getElementName() {return elementName;}

        /**
         * Returns the value stored in the node
         * @return The value stored in the node
         */
        public E getValue() {return this.value;}

        /**
         * Returns the next node
         * @return The next node
         */
        public Node getNext() {return next;}

        /**
         * Sets the next node 
         * @param next The next node
         */
        public void setNext(Node next) {this.next = next;}

        /**
         * Sets the value of the element
         * @param value The value of the element
         */
        public void setValue(E value) {this.value = value;}

        /**
         * Sets the name of the element
         * @param elementName The name of the element
         */
        public void setElementName(String elementName) {this.elementName = elementName;}
    }

    Node head, tail;
    int elementCount = 0;
    
    /**
     * Adds an element to the end of the list
     * @param name The name of the element for indexing
     * @param element The element to insert at the end of the list
     */
    public void append(String name, E element)
    {
        Node newElement = new Node(name, element);

        if (head == null)
        {
            head = tail = new Node(name, element);
        }
        else
        {
           tail.setNext(newElement);
           tail = tail.getNext();
        }
        elementCount++;
    }

    /**
     * Prints every element in the list
     */
    public void print()
    {
        for (E element : this)
        {
            System.out.printf("%s\n", element);
        }
    }

    /**
     * Returns a specific element from the list based on its name
     * @param elementName The name of the element to search for
     * @return
     */
    public E getElement(String elementName)
    {
        Node current = head;
        while (current != null)
        {
            if(current.elementName.equalsIgnoreCase(elementName))
            {
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * Removes the first element of the list and returns it
     * @return The first element of the list
     */
    public E removeFirst()
    {
        if (!isEmpty())
        {
            Node temp = head;
            head = head.getNext();
            elementCount--;
            return temp.getValue();
        }
        return null;
    }

    /**
     * Removes the last element of the list and returns it
     * @return The last element of the list
     */
    public E removeLast()
    {
        if (!isEmpty())
        {
            Node current = head, temp = current;
            while (current != tail)
            {
                temp = current;
                current = current.getNext();
            }
            tail = temp;
            tail.setNext(null);
            elementCount--;
            return current.getValue();
        }
        else
        {
            return null;
        }
    }

    /**
     * Removes the element of the list with the specified name
     * @param elementName The name of the element to find
     * @return The value of the specified element
     */
    public E removeElement(String elementName)
    {
        if (!isEmpty())
        {
            if (elementName.equals(head.getElementName())) {return removeFirst();}
            else if (elementName.equals(tail.getElementName())) {return removeLast();}
            else {
                Node current = head;
                Node temp = current;
                while (current != null)
                {
                    if (current.getElementName().equals(elementName))
                    {
                        temp.setNext(current.getNext());
                        elementCount--;
                        return current.getValue();
                    }
                    temp = current;
                    current = current.getNext();
                }
            }
        }
        return null;
    }

    /**
     * Returns true if the element with the specified name exists in the list
     * @param elementName The name of the element to search for
     * @return True if the element with the specified name exists in the list
     */
    public boolean contains(String elementName){return getElement(elementName) != null;}

    /**
     * Returns true if the number of elements in the list is = 0
     * @return True if the number of elements in the list is = 0
     */
    public boolean isEmpty(){return elementCount == 0;}

    /**
     * Returns the number of elements in the Linked List
     * @return the number of elements in the Linked List
     */
    public int getElementCount() {return elementCount;}

    /**
     * Returns the Iterator Object for the ElementList class
     */
    public ElementListIterator iterator(){return new ElementListIterator();}

    /**
     * The Iterator for the ElementList class
     */
    public class ElementListIterator implements Iterator<E>
    {
        Node current = head;
        int count = 0;
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (hasNext())
            {
                E value = current.getValue();
                current = current.getNext();
                return value;
            }
            return null;
        }
    }
}
