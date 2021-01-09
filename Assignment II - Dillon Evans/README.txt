To Compile: Javac *.java
To Run:     Java Main

Numbers should be entered just as the integer: 1 instead of 1). 

To initialize or reinitialize the queue, use option 3.

When prompted to enter "any key" to continue, the user still must hit the Enter key after, as Java does not inherently have a method
for detecting key presses. 

When enqueueing, at least 1 customer is enqueued regardless of whether the user enqueues more or not (Given that the queue is not full 
or the end of the file has not been reached.)

When reinitializing, the waitlist is reset as is the file. That is, the next customer enqueued from the file after reinitializing
is the first.

My dequeue method has a parameter that determines whether or not it makes the minimum element the first. This is allows for efficiency when
reisizing the queue.

Notice that there is no array traversal. Only enqueues and dequeues are used. 

In the Time Complexity Document, "T(n)" represents the function I'm analyzing. 