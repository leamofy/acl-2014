package MT;


/**
 * @author Mingkun Gao <gmingkun@seas.upenn.edu>
 */
/*
 * Implement Oracle Segment method on translations:
 * For every translation, compute the average TER  against 4 references,
 * select the translation with the lowest average TER
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

//import Jama.Matrix;

public class OracleSegmentTrans {
	public static final boolean TER_NORMALIZED = true;
	public static final boolean TER_CASE_ON = true;
	public static final boolean TER_IGNORE_PUNCT = false;
	public static final int TER_BEAM_WIDTH = 20;
	public static final int TER_SHIFT_DIST = 50;
	private static final TERcost TER_COST = new TERcost();
public void Rank(int dataid,ArrayList<PrintWriter> outputlist) throws NumberFormatException, IOException{
	
		TERcalc.setNormalize(TER_NORMALIZED);
		TERcalc.setCase(TER_CASE_ON);
		TERcalc.setPunct(TER_IGNORE_PUNCT);
		TERcalc.setBeamWidth(TER_BEAM_WIDTH);
		TERcalc.setShiftDist(TER_SHIFT_DIST);
		
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
		 
		 int lengthM = 4;
		 double[] score = new double[lengthM];
		 for(int i = 0; i < lengthM;i++)
			 score[i] = 0.0;
		 
		 for(int i = 0; i < lengthM;i++){
			 String hyp = sentences.get(i+4);
			 String ref0 = sentences.get(0);
			 String ref1 = sentences.get(1);
			 String ref2 = sentences.get(2);
			 String ref3 = sentences.get(3);
			 
			 TERalignment ter0 = TERcalc.TER(hyp,ref0,TER_COST);
			 double t0 = ter0.numEdits/ter0.numWords;
			 
			 TERalignment ter1 = TERcalc.TER(hyp,ref1,TER_COST);
			 double t1 = ter1.numEdits/ter1.numWords;
			 
			 TERalignment ter2 = TERcalc.TER(hyp,ref2,TER_COST);
			 double t2 = ter2.numEdits/ter2.numWords;
			 
			 TERalignment ter3 = TERcalc.TER(hyp,ref3,TER_COST);
			 double t3 = ter3.numEdits/ter3.numWords;
			 
			 score[i] = (t0 + t1 + t2 + t3)/4;
		 }
		 
		 int minindex = 0;
    	 double min = 100000.0;
    	 for(int k = 0; k < lengthM;k++){
    		 if(score[k] < min){
    			 min = score[k]; 
    			 minindex = k;
    		 }
    	 }
    	 minindex = minindex+4;
    	
    	
    	 String Trans = sentences.get(minindex);
    	 String Ref1 = sentences.get(0);
    	 String Ref2 = sentences.get(1);
    	 String Ref3 = sentences.get(2);
    	 String Ref4 = sentences.get(3);
    	 
    	
    	 
		 outputlist.get(0).println(Ref1);
		 
		 
		 outputlist.get(1).println(Ref2 );
		 
		 outputlist.get(2).println(Ref3);
		 
		 outputlist.get(3).println(Ref4);
		 
		 outputlist.get(4).println(Trans);
		 
		
	}
public void TotalRank() throws IOException{
	int i = 1;
	
	ArrayList<PrintWriter> outputlist = new ArrayList<PrintWriter> (); 
	 String filename2 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTrans/Ref1.txt";
	 File file2 = new File(filename2);
	 PrintWriter output2 = new PrintWriter(file2);
	 String filename3 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTrans/Ref2.txt";
	 File file3 = new File(filename3);
	 PrintWriter output3 = new PrintWriter(file3);
	 
	 String filename4 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTrans/Ref3.txt";
	 File file4 = new File(filename4);
	 PrintWriter output4 = new PrintWriter(file4);
	 
	 String filename5 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTrans/Ref4.txt";
	 File file5 = new File(filename5);
	 PrintWriter output5 = new PrintWriter(file5);
	 
	 String filename6 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTrans/Trans.txt";
	 File file6 = new File(filename6);
	 PrintWriter output6 = new PrintWriter(file6);
	 
	 
	 
	 
	 outputlist.add(output2);
	 outputlist.add(output3);
	 outputlist.add(output4);
	 outputlist.add(output5);
	 outputlist.add(output6);
	
	while( i < 1684  ){  
			      Rank(i,outputlist);
	      i++;
	      
	
	      
	} 
	
	outputlist.get(0).close();
	
	outputlist.get(1).close();
	outputlist.get(2).close();
	outputlist.get(3).close();
	outputlist.get(4).close();
	
	
			
	
}

}
