package MT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import BLEU.BLEU;

public class RefenceBlue {
public double Rank(int dataid) throws NumberFormatException, IOException{
		
		String filename = "F:/ACL/NLP/TFIDFDATA/text"+dataid+".txt";
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String ss = "";
		ArrayList<ArrayList<Double>> tfidfwordbag = new ArrayList<ArrayList<Double>>();
		ArrayList<String> sentences = new ArrayList<String>();
		int count = 0;
		while ((ss = in.readLine()) != null ){
			if(count >0 && count < 9){
				if(count % 2 == 0){
					ArrayList<Double> temparray = new ArrayList<Double>();
					String[] s = ss.split(" ");
					for (int i = 0; i < s.length;i++){
						temparray.add(Double.parseDouble(s[i]));
					}
					tfidfwordbag.add(temparray);
				}
				if(count % 2 == 1){
					sentences.add(ss);
				}
			}
			if(count >= 9){
				if(count % 3 == 1){
					sentences.add(ss);
				}
				else if(count % 3 == 2){
					ArrayList<Double> temparray = new ArrayList<Double>();
					String[] s = ss.split(" ");
					for (int i = 0; i < s.length;i++){
						temparray.add(Double.parseDouble(s[i]));
					}
					tfidfwordbag.add(temparray);
				}
			}
		    count++;
		}
		 in.close();
		 
		 
		 String [] refset = new String[3];
		 String ref0 = sentences.get(0);
		 String ref1 = sentences.get(1);
		 String ref2 = sentences.get(2);
		 String ref3 = sentences.get(3);
		 
		 refset[0] = ref1;
		 refset[1] = ref2;
		 refset[2] = ref3;
		 double b1 = BLEU.computeSentenceBleu(refset, ref0);
		 refset[0] = ref0;
		 refset[1] = ref2;
		 refset[2] = ref3;
		 double b2 = BLEU.computeSentenceBleu(refset, ref1);
		 
		 refset[0] = ref0;
		 refset[1] = ref1;
		 refset[2] = ref3;
		 double b3 = BLEU.computeSentenceBleu(refset, ref2);
		 
		 refset[0] = ref0;
		 refset[1] = ref1;
		 refset[2] = ref2;
		 double b4 = BLEU.computeSentenceBleu(refset, ref3);
		  
		 return (b1 + b2 + b3+ b4)/4.0;
		 
	}
public void TotalRank() throws IOException{
	int i = 1;
	
	double blue = 0.0;
	int count = 0;
	while( i < 1684  ){  
		  blue += Rank(i);
		  count++;
	      i++;
	} 
	String filename = "F:/ACL/NLP/Evaluate/PlainText/Oracle/reference.txt";
	File file = new File(filename);
	PrintWriter output = new PrintWriter(file);
	output.println("Blue:"+" "+ blue/count);
	System.out.println("Blue: "+blue/count);
	output.close();
}
	

}
