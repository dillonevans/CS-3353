/**
 * Represents a node stored in an AVL Tree
 * @author Dillon Evans
 */
public class AVLNode<K,V>
{
    private K key;
    private V value;
    private AVLNode<K,V> left, right;
    private int height;

    /**
     * Creates a new AVL Node
     * @param key The node's key
     * @param value The node's value
     */
    public AVLNode(K key, V value)
    {
        this.key = key;
        this.value = value;
        this.height = 1;
    }

    /**
     * @return The key of the node 
     */
    public K getKey() {return key;}

    /**
     * @return The value of the node
     */
    public V getValue() {return value;}

    /**
     * Sets the left child of the node
     * @param left The left child
     */
   public void setLeft(AVLNode<K, V> left) {this.left = left;}

   /**
    * Sets the right child of the node
    * @param right The right child 
    */
    public void setRight(AVLNode<K, V> right) {this.right = right;}

    /**
     * Sets the key of the node
     * @param key The key of the node
     */
    public void setKey(K key) {this.key = key;}

    /**
     * Sets the value of the noode
     * @param value The value of the node
     */
    public void setValue(V value) {this.value = value;}

    /**
     * Updates the height of the node
     * @param height The new height of the node
     */
    public void setHeight(int height) {this.height = height;}

    /**
     * @return The height stored at this node
     */
    public int getHeight() {return height;}

    /**
     * @return The left child of the node
     */
    public AVLNode<K, V> getLeft() {return left;}

    /**
     * @return The right child of the node
     */
    public AVLNode<K, V> getRight() {return right;}   
}