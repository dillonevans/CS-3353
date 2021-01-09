/**
 * Stores Key-Value pairs
 * @author Dillon Evans
 */
public class EntryNode<K,V> implements Entry<K,V>
{
    private K key;
    private V value;

    /**
     * Creates a new EntryNode object
     * @param key The key of the EntryNode
     * @param value The value of the EntryNode
     */
    public EntryNode(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    @Override
    public V getValue()
    {
        return value;
    }

    @Override
    public K getKey()
    {
        return key;
    }
}