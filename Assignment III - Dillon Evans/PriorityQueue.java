/** Interface for the priority queue ADT. */
public interface PriorityQueue<K,V> { 
	
	/**
	 * Returns the number of elements in the PriorityQueue
	 * @return the number of elements in the PriorityQueue
	 */
	int size();

	/**
	 * Returns true if the PriorityQueue is empty
	 * @return true if the PriorityQueue is empty
	 */
	boolean isEmpty();

	/**
	 * Inserts a Key-Value pair into the PrioritQueue
	 * @param key The entry's key
	 * @param value The entry's value
	 * @return The inserted Entry
	 * @throws IllegalArgumentException
	 */
	Entry<K,V> insert(K key, V value) throws IllegalArgumentException;

	/**
	 * Returns the maximum entry in the PriorityQueue
	 * @return the maximum entry in the PriorityQueue
	 */
	Entry<K,V> max();	
	
	/**
	 * Removes the element with highest priority
	 * @return the element with highest priority
	 */
	Entry<K,V> removeMax();

	/**
	 * Updates the priority of the maximum element based on the new key. 
	 * @param oldKey The old key
	 * @param newKey The new key
	 * @return the specified Entry
	 * @throws IllegalArgumentException
	 */
	Entry<K,V> update(K oldKey, K newKey) throws IllegalArgumentException;
}