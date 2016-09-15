plot(epoch,Tsignal1*100,epoch,Fsignal1*100,epoch,Tbackground1*100,epoch,Fbackground1*100);
title('Output Classification progression - 1500 Elliot Symmetric Resilient Prop');
xlabel('epoch');
ylabel('%');
legend('True Signal','Location','east','False Signal','True Background','False Background');
grid on;
