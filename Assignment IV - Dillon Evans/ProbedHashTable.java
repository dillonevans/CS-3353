@SuppressWarnings("unchecked")

/**
 * Creates a HashTable using the Linear Probing technique. 
 * @author Dillon Evans
 */
public class ProbedHashTable<K,V> extends Dictionary<K,V>
{
    private Entry<K,V>[] buckets;
    private final int DEFAULT_CAPACITY = 13;
    private final double LAMBDA = 0.5;
    private final Entry<K,V> EMPTY = new EntryNode<>(null, null);
    private int numElements, capacity;
    private ArrayList<K> keySet;
    
    /**
     * Creates a new ProbedHashTable
     */
    public ProbedHashTable()
    {
       capacity = DEFAULT_CAPACITY;
       buckets = new EntryNode[capacity];
    }

    @Override
    public int hashFunction(K key)
    {
        int j = 0;
        int hash = h1(key);

        while(buckets[hash] != null && buckets[hash] != EMPTY)
        {
            hash = (h1(key) + j) % capacity;
            j++;
            if (buckets[hash] != null && buckets[hash] != EMPTY)
            {
                if (buckets[hash].getKey().equals(key))
                {
                    return hash;
                }
            }
        } 
        return hash;
    }

    /**
     * Uses compression to compute a hash code.
     * @param key
     * @return
     */
    public int h1(K key)
    {
        int k = key.hashCode();
        return Math.abs(k) % capacity;
    }

    @Override
    public void put(K key, V value)
    {
        double loadFactor = numElements / capacity;
        if (loadFactor >= LAMBDA)
        {
            rehash();
        }
        int index = hashFunction(key);
        Entry<K,V> element = new EntryNode<K,V>(key, value);
        if (buckets[index] == null || buckets[index] == EMPTY) {numElements++;}
        buckets[index] = element;
    }

    /**
     * Returns the value of the Entry with the specified key. If there is not a match, then a value of null 
     * is returned.
     * @param key
     * @return
     */
    public V get(K key)
    {
        int index = hashFunction(key);
        Entry<K,V> temp = buckets[index];
        return temp != null ? temp.getValue() : null;
    }

    @Override
    public void rehash() {
        Entry<K, V> temp;
        int newCapacity = capacity * 2;
        Entry<K, V>[] resizedBucketArray = new EntryNode[newCapacity];
        Entry<K, V>[] buffer = new EntryNode[capacity];
        for (int i = 0; i < buckets.length; i++) {
            buffer[i] = buckets[i];
        }
        capacity = newCapacity;
        numElements = 0;
        buckets = resizedBucketArray;
        for (int i = 0; i < buffer.length; i++) {
            temp = buffer[i];
            if (temp != null && temp != EMPTY)
                put(temp.getKey(), temp.getValue());
        }
    }

    @Override
    public V remove(K key)
    {
        int index = hashFunction(key);
        V removed = null;
        if (buckets[index] != null && buckets[index] != EMPTY)
        {
            if (buckets[index].getKey().equals(key))
            {
                removed = buckets[index].getValue();
                buckets[index] = EMPTY;
                numElements--;
            }
        }
        return removed;
    }

    @Override
    public ArrayList<K> keySet() {
        keySet = new ArrayList<>(numElements);
        for (int i = 0; i < capacity; i++)
        {
            if (buckets[i] != null && buckets[i] != EMPTY)
            {
                keySet.add(buckets[i].getKey());
            }
        }
        return keySet;
    }
}