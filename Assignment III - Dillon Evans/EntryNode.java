/**
 * A Key-Value pair used in the insertion of MaxHeap
 */
public class EntryNode<K extends Comparable<K>,V> implements Entry<K,V>, Comparable<K>
{
    private K key;
    private V value;

    /**
     * Constructs a new PQEntry
     * @param key The key of the Entry
     * @param value The value of the Entry
     */
    public EntryNode(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }
    @Override
    public V getValue() {
        return value;
    }

    @Override
    public int compareTo(K o) {
    
        return o.compareTo(key);
    }

    @Override
    public String toString() 
    {
        return value.toString();
    }
}
