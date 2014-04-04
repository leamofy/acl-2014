format long
[a1,a2]=textread('F:\ACL\NLP\Evaluate\PlainText\CURVE\partialpeditrank.txt','%f %f','headerlines',0)
[b1,b2]=textread('F:\ACL\NLP\Evaluate\PlainText\CURVE\partialrank.txt','%f %f','headerlines',0)

plot(a1,a2,'-ro',b1,b2,'-bx')
legend('Trans & Edit','Trans Only','Location','NorthWest');
xlabel('Use of Refs(Percentage)')
ylabel('BLEU Score')
