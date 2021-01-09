import java.util.Iterator;
@SuppressWarnings("unchecked")

/**
 * A dynamically resizable array
 * @author Dillon Evans
 */
public class ArrayList<E> implements Iterable<E> {
    private final int DEFAULT_CAPACITY = 10;
    private int size = 0, capacity = 0;
    private E[] arrayList;

    /**
     * Initializes an ArrayList object with default capacity
     */
    public ArrayList() {
        capacity = DEFAULT_CAPACITY;
        arrayList = (E[]) new Object[capacity];
    }

    /**
     * Initializes an ArrayList object with the specified capacity
     * @param initialCapacity The initial capacity
     */
    public ArrayList(int initialCapacity) {
        capacity = initialCapacity;
        arrayList = (E[]) new Object[capacity];
    }

    /**
     * Adds an element to the ArrayList
     * @param element The element to add.
     */
    public void add(E element) {
        if (size == capacity) {
            resize();
        }
        arrayList[size++] = element;
    }

    /**
     * Resizes the array
     */
    private void resize() {
        int newCapacity = (int) (1.5 * capacity + 1);
        E[] resizedArrayList = (E[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            resizedArrayList[i] = arrayList[i];
        }

        capacity = newCapacity;
        arrayList = resizedArrayList;
    }

    /**
     * Allows for the ArrayList to be utilized in a foreach loop.
     */
    private class ArrayListIterator implements Iterator<E>
    {
        int index = 0;
        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            return arrayList[index++];
        }

    }
    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    /**
     * Removes (if it is present) the specified element from the ArrayList
     * @param element The element to remove
     */
    public void remove(E element)
    {
        remove(indexOf(element));
    }

    /**
     * Removes the element at the specified index
     * @param index the specified index
     */
    public void remove(int index) throws IndexOutOfBoundsException
    {
        int shifted = 0;

        if (index < 0 || index > size)
        {
            throw new IndexOutOfBoundsException();
        }
        else
        {
            for (int i = 0; i < size; i++)
            {
                if (i != index)
                {
                    arrayList[shifted++] = arrayList[i];
                }
            }
            arrayList[size--] = null;
        }
    }

    /**
     * Returns the index of the specified element.
     * @param element The element to search for
     * @return the index of the specified element if it is found, otherwise -1.
     */
    public int indexOf(E element)
    {
        for (int i = 0; i < size; i++)
        {
            if (arrayList[i].equals(element))
            {
                return i;
            }
        }
        return -1;
    }
}
