/**
 * The Dictionary ADT
 * @author Dillon Evans
 */
public abstract class Dictionary<K,V> {

    /**
     * Computes the hash function of the given key
     * @param key The key to compute the hash for
     * @return the hash function of the given key
     */
    protected abstract int hashFunction(K key);

    /**
     * Rehashes every Entry in the table when needed.
     */
    protected abstract void rehash();

    /**
     * Inserts an Entry into the dictionary the specified key and value
     * @param key the key of the Entry
     * @param value the value of the Entry
     */
    public abstract void put(K key, V value);

    /**
     * Returns and removes (if it exists) the Entry with the specified key
     * @param key The key to search for
     * @return the element with the specified key. 
     */
    public abstract V remove(K key);
   
    /**
     * Returns the value of the Entry with the specified key. If there is not a
     * match, then a value of null is returned.
     * @param key The key of the Entry being searched for.
     * @return the value of the Entry with the specified key or null.
     */
    public abstract V get(K key);

    /**
     * Returns a list of all the keys present in the table.
     * @return a list of all the keys present in the table.
     */
    public abstract ArrayList<K> keySet();
}
