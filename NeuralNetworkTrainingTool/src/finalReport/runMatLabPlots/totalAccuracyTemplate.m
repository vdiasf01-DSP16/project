plot(epoch,TotalAccuracy,epoch,SignalAccuracy,epoch,BackgroundAccuracy);
title('Accuracy - 1500 Elliot Symmetric Resilient Propagation');
xlabel('epoch');
ylabel('Number of correct classification per 100 events');
legend('Total Accuracy','Location','southeast','Correct Signal','Correct Background');
grid on;
