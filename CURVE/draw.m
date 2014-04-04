format long
[a1,a2]=textread('F:\ACL\NLP\Evaluate\PlainText\CURVE\partialpeditrank.txt','%f %f','headerlines',0)

plot(a1,a2,'-ro')
xlabel('Use of Refs(Percentage)')
ylabel('BLEU Score')
