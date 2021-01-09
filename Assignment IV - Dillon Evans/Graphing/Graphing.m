numEntries = 100:100:1500;

doubleHashingInsertionTimes = [12,22,28,35,42,49,57,61,70,74,78,82,87,89,91];
linearProbingInsertionTimes = [1,4,5,6,8,11,13,15,18,20,22,25,28,30,32];
compareMethods(numEntries,doubleHashingInsertionTimes,linearProbingInsertionTimes,'Insertion','Insertion vs Time');

doubleHashingSearchingTimes = [3,5,9,10,12,18,21,23,24,26,28,29,31,33,34];
linearProbingSearchingTimes = [1,3,4,5,6,16,17,18,19,20,21,21,22,22,23];
compareMethods(numEntries,doubleHashingSearchingTimes,linearProbingSearchingTimes,'Searching', 'Searching vs Time');

doubleHashingDeletionTimes = [1,2,4,5,6,7,8,10,11,12,13,17,18,19,20];
linearProbingDeletionTimes = [2,3,5,6,8,9,12,13,15,17,18,20,22,23,25];
compareMethods(numEntries,doubleHashingDeletionTimes,linearProbingDeletionTimes,'Deletion', 'Deletion vs Time');


