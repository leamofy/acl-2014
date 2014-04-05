package MT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import MT.OracleTurkTrans;
/*class Turker{
	String ID;
	double TER;
	
	Turker(String s, double d){
		ID = s;
		TER = d;	
	}
	void setID(String s){
		ID = s;
	}
	void setTER(double d){
		TER = d;
	}
	String getID(){
		return ID;
	}
	double getTER(){
		return TER;
	}
	
}*/
public class OracleTurkTransEdit {
	public static final boolean TER_NORMALIZED = true;
	public static final boolean TER_CASE_ON = true;
	public static final boolean TER_IGNORE_PUNCT = false;
	public static final int TER_BEAM_WIDTH = 20;
	public static final int TER_SHIFT_DIST = 50;
	private static final TERcost TER_COST = new TERcost();
	//
	public int FindTranslator(String t, ArrayList<Turker> TransArray){
    	for(int i = 0; i < TransArray.size();i++){
    		if(TransArray.get(i).getID().equals(t) == true) return i;
    	}
    	return -1;
    	
    }
	
	public int FindEditor(String t, ArrayList<Turker> EditArray){
    	for(int i = 0; i < EditArray.size();i++){
    		if(EditArray.get(i).getID().equals(t) == true) return i;
    	}
    	return -1;
    	
    }
public void Rank(int dataid,ArrayList<PrintWriter> outputlist) throws NumberFormatException, IOException{
		
		TERcalc.setNormalize(TER_NORMALIZED);
		TERcalc.setCase(TER_CASE_ON);
		TERcalc.setPunct(TER_IGNORE_PUNCT);
		TERcalc.setBeamWidth(TER_BEAM_WIDTH);
		TERcalc.setShiftDist(TER_SHIFT_DIST);
		
		String filename = "F:/NLP/TFIDFDATA/text"+dataid+".txt";
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String ss = "";
		ArrayList<ArrayList<Double>> tfidfwordbag = new ArrayList<ArrayList<Double>>();
		ArrayList<String> sentences = new ArrayList<String>();
		ArrayList<String> IDs = new ArrayList<String>();
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
				if(count % 3 == 0){
					IDs.add(ss);
				}
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
		 filename = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTrans/OracleTERStandardTranslatorIDrank.txt";
		 in = new BufferedReader(new FileReader(filename));
		 ss = "";
		 ArrayList<Turker> TransArray = new ArrayList<Turker>();
		 while ((ss = in.readLine()) != null){
			 String[] s = ss.split(" ");
			 Turker p = new Turker(s[0],Double.parseDouble(s[1]));
			 TransArray.add(p);
				
		 }
		 in.close();
		 /*for(int i = 0; i < TurkerArray.size();i++){
			 System.out.println(TurkerArray.get(i).getID()+" "+TurkerArray.get(i).getTER());
		 }
		 System.out.print(TurkerArray.size());
		 */
		 filename = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTransEdit/OracleTERStandardEditorIDrank.txt";
		 in = new BufferedReader(new FileReader(filename));
		 ss = "";
		 ArrayList<Turker> EditArray = new ArrayList<Turker>();
		 while ((ss = in.readLine()) != null){
			 String[] s = ss.split(" ");
			 Turker p = new Turker(s[0],Double.parseDouble(s[1]));
			 EditArray.add(p);
				
		 }
		 in.close();
		 
		 int lengthM = 14;
		 double[] score = new double[lengthM];
		 for(int i = 0; i < lengthM;i++){
			 
			 if(0<=i && i<=3){
			 String t = IDs.get(i);
			 int index = FindTranslator(t, TransArray);
			 
			 score[i] = TransArray.get(index).getTER();
			 }
			 if(4<=i&& i <=6){
				 String e = IDs.get(i);
				 String t = IDs.get(0);
				 int indexe = FindEditor(e, EditArray);
				 int indext = FindTranslator(t, TransArray);
				 score[i] = TransArray.get(indext).getTER() - EditArray.get(indexe).getTER();
			 }
			 if(7<=i&& i <=9){
				 String e = IDs.get(i);
				 String t = IDs.get(1);
				 int indexe = FindEditor(e, EditArray);
				 int indext = FindTranslator(t, TransArray);
				 score[i] = TransArray.get(indext).getTER() - EditArray.get(indexe).getTER();
			 }
			 if(10<=i&& i <=12){
				 String e = IDs.get(i);
				 String t = IDs.get(2);
				 int indexe = FindEditor(e, EditArray);
				 int indext = FindTranslator(t, TransArray);
				 score[i] = TransArray.get(indext).getTER() - EditArray.get(indexe).getTER();
			 }
			 if(i==13){
				 String e = IDs.get(i);
				 String t = IDs.get(3);
				 int indexe = FindEditor(e, EditArray);
				 int indext = FindTranslator(t, TransArray);
				 score[i] = TransArray.get(indext).getTER() - EditArray.get(indexe).getTER();
			 }
			 //System.out.println(score[i]);
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
	 String filename2 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTransEdit/Ref1.txt";
	 File file2 = new File(filename2);
	 PrintWriter output2 = new PrintWriter(file2);
	 String filename3 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTransEdit/Ref2.txt";
	 File file3 = new File(filename3);
	 PrintWriter output3 = new PrintWriter(file3);
	 
	 String filename4 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTransEdit/Ref3.txt";
	 File file4 = new File(filename4);
	 PrintWriter output4 = new PrintWriter(file4);
	 
	 String filename5 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTransEdit/Ref4.txt";
	 File file5 = new File(filename5);
	 PrintWriter output5 = new PrintWriter(file5);
	 
	 String filename6 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTransEdit/Trans.txt";
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
