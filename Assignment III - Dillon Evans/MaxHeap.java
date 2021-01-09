
/**
 * Array Based Implementation of Max Heap
 * @author Dillon Evans
 */
@SuppressWarnings("unchecked")

public class MaxHeap<K extends Comparable <K>,V>
{
    private final int DEFAULT_CAPACITY = 10;
    private int capacity, size = 0;
    Entry<K,V> priorityHeap[];

    /**
     * Default constructor
     */
    public MaxHeap()
    {
        capacity = DEFAULT_CAPACITY;
        priorityHeap = new EntryNode[capacity];
    }

    /**
     * Overloaded Constructor
     * @param capacity The initial capacity
     */
    public MaxHeap(int capacity)
    {
        this.capacity = capacity;
        priorityHeap = new EntryNode[capacity];
    }

    /**
     * Swap the entries at position i and position j
     * @param i The ith position
     * @param j The jth position
     */
    private void swap(int i, int j)
    {
        Entry<K,V> temp = priorityHeap[i];
        priorityHeap[i] = priorityHeap[j];
        priorityHeap[j] = temp;
    }

    /**
     * Inserts an entry into the heap
     * @param key The key of the entry
     * @param value The value of the entry
     */
    public void insert(K key, V value)
    {
        if (size == capacity)
        {
            dynamicallyResize();
        }
        priorityHeap[size++] = new EntryNode<K,V>(key, value);
        upheap(size - 1);
    }
    
    /**
     * Returns the parent index of the specified child
     * @param j The child index
     * @return the parent index
     */
    private int parentIndex(int j)
    {
        return (j-1)/2;
    }

    /**
     * Returns the left child index of the specified parent
     * @param j The parent index
     * @return the left child index
     */
    private int leftChildIndex(int j)
    {
        return 2*j + 1;
    }

    /**
     * Returns the right child index of the specified parent
     * @param j The parent index
     * @return the right child index
     */
    private int rightChildIndex(int j)
    {
        return 2*j + 2;
    }

    /**
     * Returns true if the node at index j has a left child
     * @param j the index of the node
     * @return true if the node at index j has a left child 
     */
    private boolean hasLeftChild(int j)
    {
        return leftChildIndex(j) < size;
    }

    /**
     * Returns true if the node at index j has a right child
     * @param j the index of the node
     * @return true if the node at index j has a right child 
     */
    private boolean hasRightChild(int j)
    {
        return rightChildIndex(j) < size;
    }

    /**
     * Starting at the position j, traverse up the heap and ensure that the child node is less
     * than the parent node. 
     * @param j The position
     */
    private void upheap(int j)
    {
        int p = 0;
        while (j > 0)
        {
            p = parentIndex(j);
            if (priorityHeap[j].getKey().compareTo(priorityHeap[p].getKey()) <= 0)
            {
                break;
            }
            else
            {
                swap(p, j);
                j = p;
            }
        }
    }

    /**
     * Starting at the specified position j, traverse down the heap and ensure that each parent
     * node is greater than its children
     * @param j the position
     */
    private void downheap(int j)
    {
        int leftChildIndex = 0, rightChildIndex = 0, maxIndex = 0;
        while (hasLeftChild(j))
        {
            leftChildIndex = leftChildIndex(j);
            maxIndex = leftChildIndex;
            if (hasRightChild(j))
            {
                rightChildIndex = rightChildIndex(j);
                if (get(leftChildIndex).getKey().compareTo(get(rightChildIndex).getKey()) <= 0)
                {
                    maxIndex = rightChildIndex;
                }
            }

            if (get(maxIndex).getKey().compareTo(get(j).getKey()) <= 0) {break;}
            else {swap (j, maxIndex); j = maxIndex;}
        }
    }

    /**
     * Returns and removes the maxiumum element in the heap
     * @return the maximum element in the heap
     */
    public Entry<K,V> removeMax()
    {
        Entry<K,V> val = max();
        swap(0, size - 1);
        priorityHeap[size - 1] = null;
        size--;
        downheap(0);
        return val;
    }

    /**
     * Returns the Entry at position j
     * @param j The jth position
     * @return the Entry at position j
     */
    private Entry<K,V> get(int j)
    {
        return priorityHeap[j];
    }

    /**
     * Returns (but does not remove) the maximum element in the heap
     * @return the maximum element in the heap
     */
    public Entry<K,V> max()
    {
        return priorityHeap[0];
    }

    /**
     * Returns the size of the heap
     * @return the size of the heap
     */
    public int size() {
        return size;
    }

    /**
     * Resizes the array used to implement the heap by a factor of 2.
     */
    private void dynamicallyResize()
    {
        int newCapacity = capacity * 2;
        Entry<K,V>[] resized = new EntryNode[newCapacity];
        for (int i = 0; i < size; i++)
        {
            resized[i] = priorityHeap[i];
        }
        priorityHeap = resized;
        capacity = newCapacity;
    }

    /**
     * Returns true if the heap is empty
     * @return true if the heap is empty
     */
    public boolean isEmpty()
    {
        return size == 0;
    }
}
