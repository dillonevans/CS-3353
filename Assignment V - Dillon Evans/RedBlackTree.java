/**
 * Creates a Red Black Tree
 * @author Dillon Evans
 */
public class RedBlackTree<K extends Comparable<K>,V> implements TreeMap<K,V>
{
    private final RedBlackNode<K,V> NIL = new RedBlackNode<>(null, null, true);
    private RedBlackNode<K,V> root = NIL, toFix;
    private boolean isSimpleCase = false;
    private V min;

    /**
     * Left-Rotate y about its parent x
     * @param x the parent node
     * @return the new parent y
     */
    public RedBlackNode<K,V> leftRotate(RedBlackNode<K,V> x)
    {
        RedBlackNode<K,V> y = x.getRight();
        RedBlackNode<K,V> T2 = y.getLeft();

        if (root == x)
        {
            root = y;
        }
        else
        {
            if (isLeftChild(x))
            {
                x.getParent().setLeft(y);
            }
            else
            {
                x.getParent().setRight(y);
            }
        }
        y.setLeft(x);
        x.setRight(T2);
        return y;
    }

    /**
     * Left-Rotate x about its parent y
     * @param y the parent node
     * @return The new parent x 
     */
    private RedBlackNode<K,V> rightRotate(RedBlackNode<K,V> y)
    {
        RedBlackNode<K,V> x = y.getLeft();
        RedBlackNode<K,V> T2 = x.getRight();

        if (root == y)
        {
            root = x;
        }
        else
        {
            if (isLeftChild(y))
            {
                y.getParent().setLeft(x);
            }
            else
            {
                y.getParent().setRight(x);
            }
        }
        x.setRight(y);
        y.setLeft(T2);
        return x;
    }

    /**
     * @return the maximum height of the tree
     */
    public int height() {return height(root);}
    
    /**
     * Recursive helper method for calculating the maximum height of the tree
     * @param root The root of the current subtree
     */
    private int height(RedBlackNode<K,V> root) {return root == NIL ? 0 : Math.max(height(root.getLeft()), height(root.getRight())) + 1;}

    @Override
    public void put(K key, V value)
    {
        root = put(key, value, root);
        remedyInsertionViolation(toFix);
    }

    /**
     * Recursive helper method for Key-Value pair insertion
     * @param key The key of the node to insert
     * @param value The value of the node to insert
     * @param root The root of the current subtree 
     * @return
     */
    private RedBlackNode<K,V> put(K key, V value, RedBlackNode<K,V> root)
    {
        if (root == NIL)
        {
            toFix = makeNode(key, value);
            return toFix;
        }
        if (key.compareTo(root.getKey()) >= 0)
        {
            root.setRight(put(key, value, root.getRight()));
        }
        else if (key.compareTo(root.getKey()) < 0)
        {
            root.setLeft(put(key, value, root.getLeft()));
        }
        return root;
    }

    /**
     * Ensures that the tree maintains its invariants by resolving any double red cases
     * @param x The previously inserted node
     */
    private void remedyInsertionViolation(RedBlackNode<K,V> x)
    {
        if (root == x)
        {
            x.makeBlack();
            return;
        }
        
        RedBlackNode<K,V> parent = x.getParent();
        RedBlackNode<K,V> grandparent = parent.getParent();
        RedBlackNode<K,V> uncle;
        
        if (grandparent == null) {return;}
        if (isLeftChild(parent)) {uncle = grandparent.getRight();}
        else {uncle = grandparent.getLeft();}

        if (parent.isRed())
        {
            if (uncle.isRed()) 
            {
                parent.makeBlack();
                uncle.makeBlack();
                grandparent.makeRed();
                x = grandparent;
                remedyInsertionViolation(x);
            }
            else
            {
                if (isLeftChild(x))
                {
                    if (isLeftChild(parent)) //Left Left Case
                    {
                        x = rightRotate(grandparent);
                        swapColors(parent, grandparent);
                    }
                    else //Left Right case
                    {
                        rightRotate(parent);
                        x = leftRotate(grandparent);
                        parent.makeRed();
                        grandparent.makeRed();
                        x.makeBlack();
                    }
                }
                else
                {
                    if (isLeftChild(parent)) //Right-Left Case
                    {
                        leftRotate(parent);
                        x = rightRotate(grandparent);
                        parent.makeRed();
                        grandparent.makeRed();
                        x.makeBlack();
                    }
                    else //Right Right case
                    {
                        x = leftRotate(grandparent);
                        swapColors(grandparent, parent);
                    }
                }
            }
        }        
        return; //Recurse up the tree
    }

    /**
     * Returns true if x is a left child
     * @param x The child node
     * @return true if x is a left child
     */
    private boolean isLeftChild(RedBlackNode<K,V> x) {return x == x.getParent().getLeft();}

    /**
     * Swaps the colors of two RedBlackNodes a and b.
     * @param a Node a
     * @param b Node B
     */
    private void swapColors(RedBlackNode<K,V> a, RedBlackNode<K,V> b)
    {
        byte temp = a.getColor();
        a.setColor(b.getColor());
        b.setColor(temp);
    }

    /**
     * Returns true if a node with the specified key exists in the tree
     * @param key The key to search for
     * @return True if a node with the specified key exists in the tree
     */
    public boolean contains(K key){return get(key) != NIL;}

    /**
     * Finds and returns the minimum in-order successor
     * @param root The root of the subtree T
     * @return The minimum in-order successor
     */
    private RedBlackNode<K,V> findInorderSuccessor(RedBlackNode<K,V> root) {return root.getRight() != NIL ? findInorderSuccessor(root.getRight()) : root;}

    @Override
    public void delete(K key)
    {
        root = delete(key, root);
        if (!isSimpleCase) {remedyDeletionViolation(toFix);}
        isSimpleCase = false;
    }

    /**
     * Recursive Helper method for removing a node by its key
     * @param key The key to remove
     * @param root The current node
     * @return The tree after deletion
     */
    private RedBlackNode<K,V> delete(K key, RedBlackNode<K,V> root)
    {
        if (root == NIL)
        {
            isSimpleCase = true;
            return NIL;
        }
        if (key.compareTo(root.getKey()) > 0)
        {
            root.setRight(delete(key, root.getRight()));
        }
        else if (key.compareTo(root.getKey()) < 0)
        {
            root.setLeft(delete(key, root.getLeft()));
        }
        else
        {           
            if (root.getLeft() == NIL)
            {
                if (root.getRight().isRed() || root.isRed())
                {
                    isSimpleCase = true;
                    root.getRight().makeBlack();
                }
                toFix = root.getRight();
                return root.getRight();
            }
            else if (root.getRight() == NIL)
            {
                toFix = root.getLeft();
                if (root.getLeft().isRed() || root.isRed())
                {
                    isSimpleCase = true;
                    root.getLeft().makeBlack();
                }
                return root.getLeft();
            }
            else
            {
                RedBlackNode<K,V> temp = findInorderSuccessor(root.getLeft());
                toFix = temp;
                root.setKey(temp.getKey());
                root.setValue(temp.getValue());
                root.setLeft(delete(temp.getKey(), root.getLeft()));
            }
        }
        return root;
    }

    /**
     * Ensures that the tree maintains its invariants by resolving any double black cases.
     * @param x The node that may be subject to causing a violation
     */
    private void remedyDeletionViolation(RedBlackNode<K,V> x)
    {
        if (root == x)
        {
            x.makeBlack();
            return;
        }
        RedBlackNode<K,V> sibling = getSibling(x), r, parent = x.getParent();

        if (sibling.isBlack())
        {
            if (sibling.getLeft().isRed() || sibling.getRight().isRed())
            {
                //If the sibling has two red children, pick the red node in the same direction.
                if (sibling.getLeft().isRed() && sibling.getRight().isRed())
                {
                    r = isLeftChild(sibling) ? sibling.getLeft() : sibling.getRight();
                }
                //Otherwie, pick the red child.
                else
                {
                    r = sibling.getLeft().isRed() ? sibling.getLeft() : sibling.getRight();
                }
                
                if (isLeftChild(sibling))
                {
                    //Left-Left Case
                    if (isLeftChild(r)) 
                    {
                        x = rightRotate(parent);
                    }
                    //Left-Right Case
                    else 
                    {
                        leftRotate(sibling);
                        x = rightRotate(parent);
                    }
                }
                else
                {
                    //Right-Left Case
                    if (isLeftChild(r)) 
                    {
                        rightRotate(sibling);
                        x = leftRotate(parent);
                    }
                    //Right-Right Case
                    else 
                    {
                        x = leftRotate(x.getParent());
                    }
                }
                if (parent.isBlack()) {x.makeBlack();}
                else{x.makeRed();}
                x.getLeft().makeBlack();
                x.getRight().makeBlack();
            }
            else //Sibling Has Two Black Children
            {
                sibling.makeRed();
                if (parent.isRed()) {parent.makeBlack();}
                else if (root != parent){remedyDeletionViolation(parent);}
            }
        }
        else //Sibling is Red
        {
            if(isLeftChild(sibling)) {rightRotate(parent);}
            else {leftRotate(parent);}
            sibling.makeBlack();
            parent.makeRed();
            remedyDeletionViolation(x);   
        }
    }

    /**
     * Returns the Sibling of the given node
     * @param x The node to find the sibling of
     * @return the sibling of the given node
     */
    private RedBlackNode<K,V> getSibling(RedBlackNode<K,V> x)
    {
        if (isLeftChild(x))
        {
            return x.getParent().getRight();
        }
        return x.getParent().getLeft();
    }

    /**
     * Returns the value associated with the specified key (If the node exists)
     * @param key The key of the Key-Value pair 
     * @return the value assciated with the specified key (If the node exists)
     */
    public V get(K key)
    {
        return get(key, root);
    }

    /**
     * Recursive Helper method for retrieving a node by its key
     * @param key The key to search for
     * @param root The current Node
     * @return The value stored at the specified node (if it exists)
     */
    private V get(K key, RedBlackNode<K,V> root)
    {
        if (root == NIL)
        {
            return NIL.getValue();
        }
        if (key.compareTo(root.getKey()) > 0)
        {
            return get(key, root.getRight());
        }
        else if (key.compareTo(root.getKey()) < 0)
        {
            return get(key, root.getLeft());
        }
        else
        {
            return root.getValue();
        }
    }

    /**
     * Returns a new RedBlackNode with the given key and value
     * @param key The key of the node
     * @param value The value of the node
     * @return The newly created node
     */
    private RedBlackNode<K,V> makeNode(K key, V value)
    {
        RedBlackNode<K,V> temp = new RedBlackNode<K,V>(key, value, false);
        temp.setLeft(NIL);
        temp.setRight(NIL);
        return temp;
    }

    /**
     * @return true if the tree is empty
     */
    public boolean isEmpty() {return root == NIL;}

     /**
     * Removes the node with minimum key and returns its value
     * @return the value of the minimum node
     */
    public V removeMinimum()
    {
        root = removeMinimum(root);
        if (!isSimpleCase) {remedyDeletionViolation(toFix);}
        isSimpleCase = false;
        return min;
    }

    /**
     * Removes the node with minimum key in the AVL Tree
     * @param root The root of the Subtree
     * @return The root of the Subtree
     */
    private RedBlackNode<K,V> removeMinimum(RedBlackNode<K,V> root)
    {
        //There is a leaf node yet to be deleted
        if (root.getLeft() != NIL)
        {
            root.setLeft(removeMinimum(root.getLeft()));
        }
        //The leaf node is the current node, return the right child
        else
        {
            if (root.getRight().isRed() || root.isRed())
            {
                isSimpleCase = true;
                root.getRight().makeBlack();
            }
            min = root.getValue();
            toFix = root.getRight();
            return root.getRight();
        }
        return root; //Perform rebalancing if necessary
    }
}