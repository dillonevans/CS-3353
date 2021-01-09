/** Interface for a key-value pair. */
public interface Entry<K,V> { 

	/**
	 * @return the key stored in this entry
	 */
	K getKey(); 

	/**
	 * @return the key stored in this entry
	 */
	V getValue();
}