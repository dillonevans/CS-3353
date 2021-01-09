To compile: javac *.java
To run:     java Main

For this assignment I have implemented both an AVL tree as well as a Red-Black Tree.
The user is given the option to utilize either. I have included the Red-Black Tree mainly
for practice, but also because Linux uses it in its "Completely Fair Scheduler" which this
program emulates. 

To select AVL or the Red Black Tree, enter AVL or RB without any quotations. 

While there are four possible rotation cases in both trees, only two methods are necessary. 
A left-left rotation is equivalent to a single right rotation, and a right-right rotation is equivalent
to a single left rotation. Furthermore, a left-right and right-left rotation are comprised of only right
and left rotations. So the only rotation methods required are leftRotate and rightRotate. 

Nodes with keys greater than or equal to the parent node's key go in the right subtree. 

Regarding the input file, please use the one provided. When using the text file from canvas,
it presented formatting errors. For instance, " P r o c e s s   J       5               1 3" 
is how the input would be read in. However, in the same vein as assignment III, copying and pasting 
the plaintext without altering anything into a new text document fixed this issue. I have notified Dr. Joshi of this.
