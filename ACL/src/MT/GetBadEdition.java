
package MT;
import java.io.*;
import java.util.*;

//import Jama.*;

/**
 * 
 * 
 * @author Mingkun Gao, <gmingkun@seas.upenn.edu>
 * @version $LastChangedDate$
 */
/*
 * Find Bad Edition
 */
/*class IDnode{
	String ID;
	double Rank;
	int Count;
	
	IDnode(String id, double rank, int count){
		ID = id;
		Rank = rank;
		Count = count;
	}
	
	public int getCount(){
		return Count;
	}
	public void setCount(int count){
		Count = count;
	}
	public String getID(){
		return ID;
	}
	public void setID(String id){
		ID = id;
	}
	
	public double getRank(){
		return Rank;
	}
	public void setRank(double rank){
		Rank = rank;
	}
}*/

/*
 * class GetBadEdition:
 * 
 */
public class GetBadEdition extends constructM{
	public static final boolean TER_NORMALIZED = true;
	public static final boolean TER_CASE_ON = true;
	public static final boolean TER_IGNORE_PUNCT = false;
	public static final int TER_BEAM_WIDTH = 20;
	public static final int TER_SHIFT_DIST = 50;
	private static final TERcost TER_COST = new TERcost();
	//ArrayList<IDnode> nodelist = new ArrayList<IDnode>();
	
	public void FindBadEdition(int dataid,PrintWriter output ) throws NumberFormatException, IOException{
		
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
		 
		 int lengthM = 18;//tfidfwordbag.size();
		 /*if(lengthM < 18){
    	 System.out.println(lengthM +" "+dataid);
		 }*/
    	 String hyp = "";
    	 String ref = "";
    	 String translator = "";
    	 double [] result = new double[lengthM -8];
    	 for(int i = 8; i < lengthM;i++){
    		 hyp = sentences.get(i);
    		 ref = sentences.get(0);
    		 TERalignment ter1 = TERcalc.TER(hyp,ref,TER_COST);
			 double t1 = ter1.numEdits/ter1.numWords;
			 
			 ref = sentences.get(1);
    		 TERalignment ter2 = TERcalc.TER(hyp,ref,TER_COST);
			 double t2 = ter2.numEdits/ter2.numWords;
			 
			 ref = sentences.get(2);
    		 TERalignment ter3 = TERcalc.TER(hyp,ref,TER_COST);
			 double t3 = ter3.numEdits/ter3.numWords;
			 
			 ref = sentences.get(3);
    		 TERalignment ter4 = TERcalc.TER(hyp,ref,TER_COST);
			 double t4 = ter4.numEdits/ter4.numWords;
			 double teredit =  (double)(t1+t2+t3+t4)/4;
			 double tertrans = 0.0;
			 if(8<=i && i<=10){
				 hyp = sentences.get(4);
				 translator = IDs.get(0);
	    		 ref = sentences.get(0);
	    		 ter1 = TERcalc.TER(hyp,ref,TER_COST);
				 t1 = ter1.numEdits/ter1.numWords;
				 
				 ref = sentences.get(1);
	    		 ter2 = TERcalc.TER(hyp,ref,TER_COST);
				 t2 = ter2.numEdits/ter2.numWords;
				 
				 ref = sentences.get(2);
	    		 ter3 = TERcalc.TER(hyp,ref,TER_COST);
				 t3 = ter3.numEdits/ter3.numWords;
				 
				 ref = sentences.get(3);
	    		 ter4 = TERcalc.TER(hyp,ref,TER_COST);
				 t4 = ter4.numEdits/ter4.numWords;
				 tertrans =  (double)(t1+t2+t3+t4)/4;
				  
			 }
			 if(11<=i && i<=13){
				 hyp = sentences.get(5);
				 translator = IDs.get(1);
	    		 ref = sentences.get(0);
	    		 ter1 = TERcalc.TER(hyp,ref,TER_COST);
				 t1 = ter1.numEdits/ter1.numWords;
				 
				 ref = sentences.get(1);
	    		 ter2 = TERcalc.TER(hyp,ref,TER_COST);
				 t2 = ter2.numEdits/ter2.numWords;
				 
				 ref = sentences.get(2);
	    		 ter3 = TERcalc.TER(hyp,ref,TER_COST);
				 t3 = ter3.numEdits/ter3.numWords;
				 
				 ref = sentences.get(3);
	    		 ter4 = TERcalc.TER(hyp,ref,TER_COST);
				 t4 = ter4.numEdits/ter4.numWords;
				 tertrans =  (double)(t1+t2+t3+t4)/4;
				  
			 }
			 if(14<=i && i<=16){
				 hyp = sentences.get(6);
				 translator = IDs.get(2);
	    		 ref = sentences.get(0);
	    		 ter1 = TERcalc.TER(hyp,ref,TER_COST);
				 t1 = ter1.numEdits/ter1.numWords;
				 
				 ref = sentences.get(1);
	    		 ter2 = TERcalc.TER(hyp,ref,TER_COST);
				 t2 = ter2.numEdits/ter2.numWords;
				 
				 ref = sentences.get(2);
	    		 ter3 = TERcalc.TER(hyp,ref,TER_COST);
				 t3 = ter3.numEdits/ter3.numWords;
				 
				 ref = sentences.get(3);
	    		 ter4 = TERcalc.TER(hyp,ref,TER_COST);
				 t4 = ter4.numEdits/ter4.numWords;
				 tertrans =  (double)(t1+t2+t3+t4)/4;
				  
			 }
			 if(i==17){
				 hyp = sentences.get(7);
				 translator = IDs.get(3);
	    		 ref = sentences.get(0);
	    		 ter1 = TERcalc.TER(hyp,ref,TER_COST);
				 t1 = ter1.numEdits/ter1.numWords;
				 
				 ref = sentences.get(1);
	    		 ter2 = TERcalc.TER(hyp,ref,TER_COST);
				 t2 = ter2.numEdits/ter2.numWords;
				 
				 ref = sentences.get(2);
	    		 ter3 = TERcalc.TER(hyp,ref,TER_COST);
				 t3 = ter3.numEdits/ter3.numWords;
				 
				 ref = sentences.get(3);
	    		 ter4 = TERcalc.TER(hyp,ref,TER_COST);
				 t4 = ter4.numEdits/ter4.numWords;
				 tertrans =  (double)(t1+t2+t3+t4)/4;
				  
			 }
			 
			 result[i-8] = tertrans - teredit;
			 if((tertrans - teredit)<0 ){
				 output.println("Translator: " + translator.toUpperCase());
				 output.println("Editor: "+ IDs.get(i-4).toUpperCase());
				 output.println("Tran:"+ hyp);
				 output.println("Edit:"+sentences.get(i));
			 }
    		 
    	 }
    	 /*for(int l = 0; l < lengthM;l++){
    		 for(int j = 0; j < lengthM;j++){
    			 System.out.print(M.get(l, j)+" ");
    		 }
    		 System.out.println();
    	 }*/
    	 
    				
	}
	// Get the whole node list with every editor's delta TER score
	public void GetBadEditions() throws IOException{
		String filename = "F:/ACL/NLP/Evaluate/PlainText/Oracle/BadEdition/BadEdition.txt";
		File file = new File(filename);
		PrintWriter output = new PrintWriter(file);
		int i = 1;
		while( i < 1684  ){  
			FindBadEdition(i,output);
		      i++;
		}
		output.close();
		
	}
	// Rank the editor's node list according to the average TER score of each editor node
	
}

	


