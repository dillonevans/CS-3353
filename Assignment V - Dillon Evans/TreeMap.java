/**
 * The Tree Map ADT
 * @author Dillon Evans
 */
public interface TreeMap<K,V>
{
    /**
     * Inserts a new node into the tree with the specified key and value.
     * @param key The key of the node
     * @param value The value of the node
     */
    public void put(K key, V value);
     
    /**
     * Deletes the node with the specified key.
     * @param key The key of the node to delete.
     */
    public void delete(K key);

    /**
     * Removes and returns the element with the minimum key
     * @return The element with the minimum key
     */
    public V removeMinimum();

    /**
     * @return True if the tree is empty
     */
    public boolean isEmpty();
}
