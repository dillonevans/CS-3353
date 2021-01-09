/**
 * Max Heap implementation of a PriorityQueue
 * @author Dillon Evans
 */
public class MaxPriorityQueue<K extends Comparable <K>,V> implements PriorityQueue<K,V>
{
    MaxHeap<K, V> priorityHeap = new MaxHeap<>(20);

    @Override
    public int size() {
        return priorityHeap.size();
    }

    @Override
    public boolean isEmpty() {
        return priorityHeap.isEmpty();
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        Entry<K,V> entered = new EntryNode<>(key, value);
        priorityHeap.insert(key, value);
        return entered;
    }

    @Override
    public Entry<K, V> max() {
        return priorityHeap.max();
    }

    @Override
    public Entry<K, V> removeMax() {
        return priorityHeap.removeMax();
    }

    @Override
    public Entry<K, V> update(K oldKey, K newKey) throws IllegalArgumentException {
       Entry<K,V> entry = priorityHeap.max();
       priorityHeap.removeMax();
       priorityHeap.insert(newKey, entry.getValue());
       return entry;
    }
}
