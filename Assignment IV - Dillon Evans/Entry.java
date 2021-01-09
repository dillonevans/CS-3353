/**
 * The Entry ADT
 * @author Dillon Evans
 */
public interface Entry<K,V>
{
    /**
     * Returns the Entry's Value
     * @return the Entry's Value
     */
    public V getValue();

    /**
     * Returns the Entry's Key
     * @return the Entry's Key
     */
    public K getKey();
}