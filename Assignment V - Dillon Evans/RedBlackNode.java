/**
 * Represents a node found in a Red-Black Tree
 * @author Dillon Evans
 */
public class RedBlackNode<K,V>
{
    private K key;
    private V value;
    private RedBlackNode<K,V> left, right, parent;
    private byte color = 0; //0 = Black, 1 = Red

    /**
     * Creates a new Red Black Node
     * @param key The key of the Node
     * @param value The value of the Node
     */
    public RedBlackNode(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    /**
     * Creates a new Red Black Node
     * @param key The key of the node
     * @param value The value of the node
     * @param isBlack Determines if the node is black or red
     */
    public RedBlackNode(K key, V value, boolean isBlack)
    {
        this.key = key;
        this.value = value;
        this.color = (byte) (isBlack ? 0 : 1);
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
     * @param left The left child of the node
     */
   public void setLeft(RedBlackNode<K, V> left) {
       this.left = left;
       left.parent = this;
   }

   /**
    * Sets the right child of the node
    * @param right
    */
    public void setRight(RedBlackNode<K, V> right) {
        this.right = right;
        right.parent = this;
    }

    /**
     * Sets the key of the node
     * @param key The key of the node
     */
    public void setKey(K key) {this.key = key;}

    /**
     * Sets the value of the node
     * @param value The value of the node
     */
    public void setValue(V value) {this.value = value;}

    /**
     * Recolors the node to black
     */
    public void makeBlack() {this.color = 0;}

    /**
     * Recolors the node to red
     */
    public void makeRed() {this.color = 1;}

    /**
     * @return True if the node is red
     */
    public boolean isRed() {return color == 1;}

    /**
     * @return True if the ndoe is black
     */
    public boolean isBlack() {return color == 0;}

    /**
     * @return The left child node
     */
    public RedBlackNode<K, V> getLeft() {return left;}

    /**
     * @return The right child node
     */
    public RedBlackNode<K, V> getRight() {return right;}   

    /**
     * @return The color of the node
     */
    public byte getColor() {return color;}

    /**
     * Updates the color of the node
     * @param color The new Color
     */
    public void setColor(byte color) {this.color = color;}

    /**
     * @return The parent of the node
     */
    public RedBlackNode<K, V> getParent() {return parent;}

    /**
     * Sets the parent of the node
     * @param parent The parent node
     */
    public void setParent(RedBlackNode<K, V> parent) {this.parent = parent;}

    @Override
    public String toString() {return String.format("<%s, %s, %s> ", key, value, color);}
}