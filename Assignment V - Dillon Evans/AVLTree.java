/**
 * An AVL Tree class that implements the TreeMap Interface
 * @author Dillon Evans
 */
public class AVLTree<K extends Comparable<K>,V> implements TreeMap<K,V>
{
    private AVLNode<K,V> root;
    private V min;

    /**
     * @return true if the tree is empty
     */
    public boolean isEmpty() {return root == null;}

    /**
     * Computes the height of the tree from the root
     * @return The height of the tree
     */
    public int computeHeight() {return computeHeight(root);}

    /**
     * Returns the maximum height of a given node.
     * @param root The root of the subtree 
     * @return The height of the subtree
     */
    private int computeHeight(AVLNode<K,V> root) {return root == null ? 0 : Math.max(getHeight(root.getLeft()), getHeight(root.getRight())) + 1;}

    /**
     * Returns the height of the specified root
     * @param root The node to retrieve the height for.
     * @return the height of the given node
     */
    public int getHeight(AVLNode<K,V> root) {return root == null ? 0 : root.getHeight();}

    /**
     * Computes the balance factor of a given node
     * @param root The given node
     * @return The balance factor of the given node
     */
    public int balanceFactor(AVLNode<K,V> root) {return getHeight(root.getLeft()) - getHeight(root.getRight());}

     /**
     * Left-Rotate y about its parent x
     * @param x the parent node
     * @return the new parent y
     */
    public AVLNode<K,V> leftRotate(AVLNode<K,V> x)               
    {
        AVLNode<K,V> y = x.getRight();
        AVLNode<K,V> T2 = y.getLeft();
        
        y.setLeft(x);
        x.setRight(T2);

        //Recompute the heights of the Subtrees
        x.setHeight(computeHeight(x));
        y.setHeight(computeHeight(y));

        return y;
    }

    /**
     * Rotates the node x about its parent y
     * @param y The parent node
     * @return the new parent x
     */
    public AVLNode<K,V> rightRotate(AVLNode<K,V> y)
    {
        AVLNode<K,V> x = y.getLeft();
        AVLNode<K,V> T2 = x.getRight();
        
        x.setRight(y);
        y.setLeft(T2); 

		//Recompute the heights of the Subtrees
        y.setHeight(computeHeight(y));
        x.setHeight(computeHeight(x));
        return x;
    }

    @Override
    public void put(K key, V value) {root = put(key, value, root);}

    /**
     * Recursive helper method for inserting a new node into the tree
     * @param key The key of the node to insert
     * @param value The value of the node to insert
     * @param The root of the tree
     */
    private AVLNode<K,V> put(K key, V value, AVLNode<K,V> root)
    {
        //Found where to insert
        if (root == null) {return new AVLNode<K,V>(key, value);}
        //Key is greater than the current node's, need to insert in the right subtree
        if (key.compareTo(root.getKey()) >= 0) {root.setRight(put(key, value, root.getRight()));}
        //Key is less than the current node's, need to insert in the left subtree
        else if (key.compareTo(root.getKey()) < 0) {root.setLeft(put(key, value, root.getLeft()));}
        //Adjust ancestor height after insertion and rebalance if needed
        return enforceAVL(root) ;
    }

    /**
     * Returns the largest value in the subtree
     * @param root The current subtree
     * @return The largest value in the subtree
     */
    private AVLNode<K,V> findInorderSuccessor(AVLNode<K,V> root) {return root.getRight() != null ? findInorderSuccessor(root.getRight()) : root;}

    @Override
    public void delete(K key) {root = delete(key, root);}

    /**
     * Recursive helper for method for deleting a node
     * @param key The key of the node to delete
     * @param root The root of the subtree
     * @return The root of the tree
     */
    private AVLNode<K,V> delete(K key, AVLNode<K,V> root)
    {
        //Element was not present in the tree
        if (root == null) {return null;}
        //Key is greater than the current node's, need to delete from the right subtree
        if (key.compareTo(root.getKey()) > 0) {root.setRight(delete(key, root.getRight()));}
        //Key is greater than the current node's, need to delete from the left subtree
        else if (key.compareTo(root.getKey()) < 0) {root.setLeft(delete(key, root.getLeft()));}
        //The key is equal to the current node's.
        else
        {
            //The left child is null, simply return the right child.
            if (root.getLeft() == null) {return root.getRight();}
            //The right child is nill, simply return the left child
            else if (root.getRight() == null) {return root.getLeft();}
            //The node has two children, return the largest value in the left subtree
            else
            {
                AVLNode<K,V> temp = findInorderSuccessor(root.getLeft());
                root.setKey(temp.getKey());
                root.setValue(temp.getValue());
                root.setLeft(delete(temp.getKey(), root.getLeft()));
            }
        }
        return enforceAVL(root);
    }

    /**
     * Enforces the AVL Tree invariants.
     * @param root The subtree to rebalance
     * @return The root resulting from rebalancing.
     */
    private AVLNode<K,V> enforceAVL(AVLNode<K,V> root)
    {

        root.setHeight(computeHeight(root)); //Adjust the ancestor height
        int balanceFactor = balanceFactor(root);

        //The tree is left leaning
        if (balanceFactor > 1) 
        {
            //The left subtree is left leaning, perform a right rotation to rebalance.
            if (balanceFactor(root.getLeft()) >= 0)
            {
                return rightRotate(root);
            }
            //The left subtree is right leaning, perform a left-right rotation to rebalance.
            else
            {
                root.setLeft(leftRotate(root.getLeft()));
                return rightRotate(root);
            }
        }
        //The tree is right leaning
        else if (balanceFactor < -1) 
        {
            //The right subtree is right leaning, perform a left rotation to rebalance.
            if (balanceFactor(root.getRight()) <= 0)
            {
                return leftRotate(root);
            }
            //The left subtree is left leaning, perform a right-left rotation to rebalance.
            else
            {
                root.setRight(rightRotate(root.getRight())); //Problem occurs in left grandchild
                return leftRotate(root);
            }
        }
        return root;
    }

    /**
     * Removes the node with minimum key and returns its value
     * @return the value of the minimum node
     */
    public V removeMinimum()
    {
        root = removeMinimum(root);
        return min;
    }

    /**
     * Removes the node with minimum key in the AVL Tree
     * @param root The root of the Subtree
     * @return The root of the Subtree
     */
    private AVLNode<K,V> removeMinimum(AVLNode<K,V> root)
    {
        //There is a leaf node yet to be deleted
        if (root.getLeft() != null)
        {
            root.setLeft(removeMinimum(root.getLeft()));
        }
        //The leaf node is the current node, return the right child
        else
        {
            //Stores the value associated with the minimum key in the subtree
            min = root.getValue();
            return root.getRight();
        }
        return enforceAVL(root); //Perform rebalancing if necessary
    }
}