
package MT;
import java.io.*;
import java.util.*;

import BLEU.computeDocBleu;
import Jama.*;

/**
 * 
 * 
 * @author Mingkun Gao, <gmingkun@seas.upenn.edu>
 * @version $LastChangedDate$
 */

/*
 * Two Layer (MTurkers layer and Sentences layer)Page Rank 
 */
public class PTLPagerank extends constructM{
	/*
	public static final boolean TER_NORMALIZED = true;
	public static final boolean TER_CASE_ON = true;
	public static final boolean TER_IGNORE_PUNCT = false;
	public static final int TER_BEAM_WIDTH = 20;
	public static final int TER_SHIFT_DIST = 50;
	private static final TERcost TER_COST = new TERcost();
	*/
	ArrayList<IDnode> nodelist = new ArrayList<IDnode>();
	
	public int IsExist(String nodeid){
		int length = nodelist.size();
		int i;
		for(i = 0; i < length;i++){
			IDnode tmpnode = nodelist.get(i);
			if(tmpnode.getID().equals(nodeid) == true)
				break;
		}
		if(i < length){
			return i;
		}
		else
			return -1;
		
	}
	//judge whether the translator is in PairArray[i]
	public boolean FindTranslator(String t, ArrayList<Pair> PairArray,int i ){
    		if(PairArray.get(i).getTrans().equals(t) == true) return true;
    		else return false;
    	
    	
    }
	//judge whether the translator-editor pair is in PairArray[i]
	public boolean FindColab(String t, String e, ArrayList<Pair> PairArray,int i ){
    		if(PairArray.get(i).getTrans().equals(t)==true && PairArray.get(i).getEdit().equals(e) == true) return true;
    		else return false;
    }
	//judge whether the editor is in PairArray[i]
	public boolean FindEditor(String e, ArrayList<Pair> PairArray,int i ){
    		if(PairArray.get(i).getEdit().equals(e) == true) return true;
    		else return false;
    }
	//Two Layer Rank on one sentence set
	public void Rank(int dataid,ArrayList<PrintWriter> outputlist,double lambda) throws NumberFormatException, IOException{
		
		/*TERcalc.setNormalize(TER_NORMALIZED);
		TERcalc.setCase(TER_CASE_ON);
		TERcalc.setPunct(TER_IGNORE_PUNCT);
		TERcalc.setBeamWidth(TER_BEAM_WIDTH);
		TERcalc.setShiftDist(TER_SHIFT_DIST);
		*/
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
		 filename = "F:/ACL/NLP/CoLab/allcolabor.txt";
		 in = new BufferedReader(new FileReader(filename));
		 ss = "";
		 ArrayList<Pair> PairArray = new ArrayList<Pair>();
		 while ((ss = in.readLine()) != null){
			 String[] s = ss.split(" ");
			 Pair p = new Pair(s[0],s[1],Double.parseDouble(s[2]));
			 PairArray.add(p);
				
		 }
		 in.close();
		 
		 filename = "F:/ACL/NLP/CoLab/nallclabormatrix.txt";
		 in = new BufferedReader(new FileReader(filename));
		 ss = "";
		 Matrix N = new Matrix(PairArray.size(),PairArray.size(),0);
		 int Ncount = 0;
		 int Nsize = PairArray.size();
		 while ((ss = in.readLine()) != null){
			 String[] s = ss.split(" ");
				for (int i = 0; i < s.length;i++){
					N.set(Ncount, i, Double.parseDouble(s[i])); 
					//if(Double.parseDouble(s[i]) > 0) System.out.println("Big");
				}
				Ncount++;
		 }
		 in.close();
		 
		 
		 /*for(int i = 0; i < 500;i++){
			 for(int j = 0; j < 500;j++){
				 System.out.print(N.get(i, j)+" ");
			 }
			 System.out.println();
		 }
		 System.out.println(Ncount);*/
		 int lengthM = tfidfwordbag.size();
		 lengthM = lengthM - 4;
    	 Matrix M = new Matrix(lengthM,lengthM,0);
    	 for(int l = 0; l < lengthM;l++){
    		 for(int j = 0; j < lengthM;j++){
    			 if(l==j)
    				 M.set(j, l, 0.0);
    			 else{ 
    				 M.set(j, l, cosine(tfidfwordbag.get(l+4),tfidfwordbag.get(j+4)));
    				}
    		 }
    			 
    	 }
    	 
    	 double[][] A = new double[lengthM][Nsize];
    	 for(int i = 0; i < lengthM;i++){
    		 for(int j = 0; j < Nsize;j++)
    			 A[i][j] = 0.0;
    	 }
    	 for(int i = 0; i< IDs.size();i++){
    		 if(i < 4){
    			 String t = IDs.get(i);
    			 for(int j = 0; j < PairArray.size();j++){
    				 if(FindTranslator(t, PairArray,j) == true){
    					 A[i][j] = 1;
    				 }
    			 }
    		 }
    		 if(i >= 4){
    			 String t = "";
    			 String e;
    			 if(i<=6)
    			  t = IDs.get(0);
    			 else if(i>=7 && i <= 9)
    			  t = IDs.get(1);
    			 else if(i>=10 && i <= 12)
    			  t = IDs.get(2);
    			 else if(i == 13)
    			  t = IDs.get(3);
    			 
    			 e = IDs.get(i);
    			 for(int j = 0; j < PairArray.size();j++){
    				 if(FindColab(t,e, PairArray,j) == true){
    					 A[i][j] = 1;
    				 }
    			 }
    			 for(int j = 0; j < PairArray.size();j++){
    				 if(FindTranslator(t, PairArray,j) == true){
    					 A[i][j] = 1;
    				 }
    			 }
    			 for(int j = 0; j < PairArray.size();j++){
    				 if(FindEditor(e, PairArray,j) == true){
    					 A[i][j] = 1;
    				 }
    			 }
    			 
    		 }
    	 }
    	 
    	 /*for(int i = 0; i < lengthM;i++){
    		 for(int j = 0 ; j < Nsize;j++){
    			 System.out.print(A[i][j]+" ");
    		 }
    		 System.out.println();
    	 }*/
    	  
    
    	double[] Csums = new double[PairArray.size()];
 		for(int i = 0; i < PairArray.size();i++)
 			Csums[i] = 0.0;
 		for(int i = 0; i < PairArray.size();i++){
			for(int j = 0; j < lengthM;j++){
				Csums[i] += A[j][i];
			}
		}
 		
 		double[] Rsums = new double[lengthM];
 		for(int i = 0; i < lengthM;i++)
 			Rsums[i] = 0.0;
 		for(int i = 0; i < lengthM;i++){
			for(int j = 0; j < PairArray.size();j++){
				Rsums[i] += A[i][j];
			}
		}
 		
 		Matrix W_ba = new Matrix(lengthM,Nsize,0);
 		Matrix W_hat = new Matrix(lengthM,Nsize,0);
 		
 		//double[][] A = new double[lengthM][Nsize];
   	  for(int i = 0; i < lengthM;i++){
   		 for(int j = 0; j < Nsize;j++){
   			 if(Rsums[i] != 0)
   			  W_ba.set(i, j, A[i][j]/Rsums[i]);
   		 }
   			 
   	  }
   	for(int i = 0; i < lengthM;i++){
  		 for(int j = 0; j < Nsize;j++){
  			 if(Csums[j] != 0)
  			  W_hat.set(i, j, A[i][j]/Csums[j]);
  		 }
  			 
  	 }
    	 /*for(int l = 0; l < lengthM;l++){
    		 for(int j = 0; j < lengthM;j++){
    			 System.out.print(M.get(l, j)+" ");
    		 }
    		 System.out.println();
    	 }*/
    	 for(int l = 0; l < lengthM;l++){
    		 Matrix tmp = M.getMatrix(0, lengthM-1, l, l);
    		// System.out.println(tmp.norm1()+" "+l);
    		 if(tmp.norm1() != 0)
    		 tmp = tmp.times(1/tmp.norm1());
    		/* for(int i = 0; i < tmp.getRowDimension();i++){
    			 System.out.println(tmp.get(i, 0));
    			 
    		 }*/
    		 M.setMatrix(0, lengthM-1, l, l, tmp);
    	 }
    	 
    	 
    	 TwoLayerRank pr = new TwoLayerRank();
    	 Matrix result = pr.getfirstpage(M,N,W_ba,W_hat,lambda, 0.01);
    	 /*System.out.println("Rank");
    	for(int i = 0; i < result.getRowDimension();i++){
			 System.out.println(result.get(i, 0));
			 
		 }*/
    	 int maxindex = 0;
    	 double max = 0.0;
    	 for(int k = 0; k < lengthM;k++){
    		 if(result.get(k, 0) > max){
    			 max = result.get(k, 0); 
    			 maxindex = k;
    		 }
    	 }
    	// maxindex = maxindex;
    	 
    	 String Trans = sentences.get(maxindex+4);
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
	// Two Layer Rank on the corpus
	public void TotalRank(double lambda) throws IOException{
		int i = 1;
		
		ArrayList<PrintWriter> outputlist = new ArrayList<PrintWriter> (); 
		 String filename2 = "F:/ACL/NLP/Evaluate/PlainText/TEMP/Ref1.txt";
		 File file2 = new File(filename2);
		 PrintWriter output2 = new PrintWriter(file2);
		 String filename3 = "F:/ACL/NLP/Evaluate/PlainText/TEMP/Ref2.txt";
		 File file3 = new File(filename3);
		 PrintWriter output3 = new PrintWriter(file3);
		 
		 String filename4 = "F:/ACL/NLP/Evaluate/PlainText/TEMP/Ref3.txt";
		 File file4 = new File(filename4);
		 PrintWriter output4 = new PrintWriter(file4);
		 
		 String filename5 = "F:/ACL/NLP/Evaluate/PlainText/TEMP/Ref4.txt";
		 File file5 = new File(filename5);
		 PrintWriter output5 = new PrintWriter(file5);
		 
		 String filename6 = "F:/ACL/NLP/Evaluate/PlainText/TEMP/Trans.txt";
		 File file6 = new File(filename6);
		 PrintWriter output6 = new PrintWriter(file6);
		 
		
		 
		 outputlist.add(output2);
		 outputlist.add(output3);
		 outputlist.add(output4);
		 outputlist.add(output5);
		 outputlist.add(output6);
		
		while( i < 1684  ){  //1684
				      Rank(i,outputlist,lambda);
		      i++;
		      
		
		      
		} 
		outputlist.get(0).close();
		
		outputlist.get(1).close();
		outputlist.get(2).close();
		outputlist.get(3).close();
		outputlist.get(4).close();
	}
	//Get the curve of BLEU vs.lambda
	public void Curve() throws IOException{

		String writefile = "F:/ACL/NLP/Evaluate/PlainText/CURVE/TwoLayer/result_0.01_0.1_0.01.txt"; //Record the curve(you can change the file name as you like)
		File file = new File(writefile);
		PrintWriter outwriter = new PrintWriter(file);
		double delta = 0.0; // delta is set to be 0.04, every time lambda increase by 0.04
		while(delta <=1.01){
		 TotalRank(delta);
			computeDocBleu bleu = new computeDocBleu(); 
			String ref1 = "F:/ACL/NLP/Evaluate/PlainText/TEMP/Ref1.txt";
			String ref2 = "F:/ACL/NLP/Evaluate/PlainText/TEMP/Ref2.txt";
			String ref3 = "F:/ACL/NLP/Evaluate/PlainText/TEMP/Ref3.txt";
			String ref4 = "F:/ACL/NLP/Evaluate/PlainText/TEMP/Ref4.txt";
			String trans = "F:/ACL/NLP/Evaluate/PlainText/TEMP/Trans.txt";
			String res = "F:/ACL/NLP/Evaluate/PlainText/TEMP/result.txt";
			System.out.println(delta+" "+bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
			outwriter.println(delta+" "+bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
			delta += 0.04;
		 }
		
		outwriter.close();
		
		
		
		
	}	
}

	


