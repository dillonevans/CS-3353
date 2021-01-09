@SuppressWarnings("unchecked")

/**
 * Creates a HashTable using the Double Hashing technique. 
 * @author Dillon Evans
 */
public class DoubleHashedHashTable<K, V> extends Dictionary<K, V> {
    private Entry<K, V>[] buckets;
    private final int PRIMES[] = {29, 59, 127, 257, 479, 521, 1049, 2099, 4201, 8419, 16843, 33703, 67409 };
    private final int DEFAULT_CAPACITY = 13;
    private final double LAMBDA = 0.5;
    private final Entry<K, V> EMPTY = new EntryNode<>(null, null);
    private int p = 2147483647, q = 11, numElements, capacity, primeIndex = 0;
    private ArrayList<K> keySet;

    /**
     * Creates a DoubleHashedHashTable object
     */
    public DoubleHashedHashTable() {
        capacity = DEFAULT_CAPACITY;
        buckets = new EntryNode[capacity];
    }

    @Override
    public int hashFunction(K key) {
        int j = 0;
        int hash = h1(key);
        //Loops until either an empty bucket is found OR the key is found
        while (buckets[hash] != null && buckets[hash] != EMPTY) {
            hash = (h1(key) + j * h2(key)) % capacity;
            j++;
            if (buckets[hash] != null && buckets[hash] != EMPTY) {
                if (buckets[hash].getKey().equals(key)) {
                    return hash;
                }
            }
        }
        return hash;
    }

    /**
     * Uses MAD compression to compute a hash code based on the given key.
     * @param key The key of the Entry
     * @return the hash code based on the given key.
     */
    private int h1(K key) {
        int k = key.hashCode();
        int a = 3, b = 11;
        return (Math.abs(a * k + b) % p) % capacity;
    }

    /**
     * Secondary hash function used for Double Hashing
     * @param key The key of the Entry
     * @return the secondary hash code used for double hashing;
     */
    private int h2(K key) {
        int k = key.hashCode();
        return q - (k % q);
    }

    @Override
    public void put(K key, V value) {
        double loadFactor = numElements / capacity;
        if (loadFactor >= LAMBDA) {
            rehash();
        }
        int index = hashFunction(key);
        Entry<K, V> element = new EntryNode<K, V>(key, value);
        if (buckets[index] == null || buckets[index] == EMPTY) {numElements++;}
        buckets[index] = element;
    }

    @Override
    public V get(K key) {
        int index = hashFunction(key);
        Entry<K, V> temp = buckets[index];
        return temp != null ? temp.getValue() : null;
    }

    @Override
    public void rehash() {
        Entry<K, V> temp;
        int newCapacity = PRIMES[++primeIndex];
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
    public V remove(K key) {
        int index = hashFunction(key);
        V removed = null;
        if (buckets[index] != null && buckets[index] != EMPTY) {
            if (buckets[index].getKey().equals(key)) {
                removed = buckets[index].getValue();
                buckets[index] = EMPTY;
                numElements--;
            }
        }
        return removed;
    }

    @Override
    public ArrayList<K> keySet()
    {
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