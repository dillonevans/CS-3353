function compareMethods(x, y1, y2,figureName, titleOf)
figure('Name', figureName,'NumberTitle','off');
%y1 = [12,22,28,35,42,49,57,61,70,74,78,82,87,89,91];
%y2 = [1,4,5,6,8,11,13,15,18,20,22,25,28,30,32];
hold on;
grid on;
title(titleOf);
xlabel('Number of Entries') 
ylabel('Time in ms')
xticks(100:100:1500);
plot(x,y1,'-bs', 'MarkerEdgeColor','b', 'MarkerFaceColor', 'b');
plot(x,y2,'-rs', 'MarkerEdgeColor','r', 'MarkerFaceColor', 'r');
legend('Double Hashing', 'Linear Probing');
end