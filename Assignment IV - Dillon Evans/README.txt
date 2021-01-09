To Compile: 	javac *.java
To Run:     	java Main

    Every Class with the exception of Iterable and Iterator is written solely by me. I implemented
    my own ArrayList class so that I could provide the Hash Table with a key based iterator.

    I wrote a Dictionary class for polymorphism, this allows comparing the hashing methods in the same method
    because they are derived from the same base class.

    When prompted for an ISBN-13, the input must be 13 characters long. 

    I used MATLAB to plot the data previously obtained from the comparison method. The .m files are found under "Graphing"
    as is the PDF with all the graphs.

    When comparing the method times for a specific operation, I subtract the time 
    it takes to parse the data so only the actual insertion/deletion/searching time is considered. 
    I loop over the interval [1,1500] and everytime i % 100 = 0, I store the 
    current running time. This way I can measure how long it takes to insert 
    100,200,...,1,500 elements using a single for loop.

    There is little to no documentation in the derived classes because the documentation is present 
    in the base class/interface.

    I added an extra option that allows the user to view every book present in the Hash Table. 
    This way they can see the result of insertions/deletions as well as view ISBN-13's.