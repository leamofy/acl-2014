
package MT;
import java.io.*;
import java.util.*;


import Jama.*;

/**
 * 
 * 
 * @author Mingkun Gao, <gmingkun@seas.upenn.edu>
 * @version $LastChangedDate$
 */

public class TERPageRank extends constructM{
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
		 
		 int lengthM = 8;
    	 Matrix M = new Matrix(lengthM,lengthM,0);
    	 for(int l = 0; l < lengthM;l++){
    		 for(int j = 0; j < lengthM;j++){
    			 if(l==j)
    				 M.set(j, l, 0.0);
    			 else{
    				 String hyp = sentences.get(l);
    				 String ref = sentences.get(j);
    				 TERalignment ter = TERcalc.TER(hyp,ref,TER_COST);
    				 double t = ter.numEdits/ter.numWords;
    				 double temp = 2*(1 - 1/(1+Math.exp(-t)));
    				 
    				 M.set(j, l,temp);
    				}
    		 }
    			 
    	 }
    	 
    	 
    	/* for(int l = 0; l < lengthM;l++){
		 for(int j = 0; j < lengthM;j++){
			 System.out.print(M.get(l, j)+" ");
		 }
		 System.out.println();
    	 }*/
    	 
    	 
    	 for(int l = 0; l < lengthM;l++){
    		 Matrix tmp = M.getMatrix(0, lengthM-1, l, l);
    		 if(tmp.norm1() != 0)
        		 tmp = tmp.times(1/tmp.norm1());
    		 M.setMatrix(0, lengthM-1, l, l, tmp);
    	 }
    	 
    	 
    	 pagerank pr = new pagerank();
    	 Matrix result = pr.getfirstpage(M, 0.85, 0.001);
    	 
    	 /*for(int i = 0; i < result.getRowDimension();i++){
			 System.out.println(result.get(i, 0));
			 
		 }*/
    	 
    	 int maxindex = 0;
    	 double max = 0.0;
    	 for(int k = 4; k < lengthM;k++){
    		 if(result.get(k, 0) > max){
    			 max = result.get(k, 0); 
    			 maxindex = k;
    		 }
    	 }
    	 maxindex = maxindex;
    	
    	 
    	 String Trans = sentences.get(maxindex);
    	 String Ref1 = sentences.get(0);
    	 String Ref2 = sentences.get(1);
    	 String Ref3 = sentences.get(2);
    	 String Ref4 = sentences.get(3);
    	 
    	
    	 String docid = "document"+dataid;
		 outputlist.get(0).println("<doc docid=\""+ docid + "\" genre=\"nw\">"); 
		 outputlist.get(0).println("<seg id=\"1\">"+Ref1 +"</seg>");
		 outputlist.get(0).println("</doc> ");
		 
		 outputlist.get(1).println("<doc docid=\""+ docid + "\" genre=\"nw\">"); 
		 outputlist.get(1).println("<seg id=\"1\">"+Ref2 +"</seg>");
		 outputlist.get(1).println("</doc> ");
		 
		 outputlist.get(2).println("<doc docid=\""+ docid + "\" genre=\"nw\">"); 
		 outputlist.get(2).println("<seg id=\"1\">"+Ref3 +"</seg>");
		 outputlist.get(2).println("</doc> ");
		 
		 outputlist.get(3).println("<doc docid=\""+ docid + "\" genre=\"nw\">"); 
		 outputlist.get(3).println("<seg id=\"1\">"+Ref4 +"</seg>");
		 outputlist.get(3).println("</doc> ");
		 
		 outputlist.get(4).println("<doc docid=\""+ docid + "\" genre=\"nw\">"); 
		 outputlist.get(4).println("<seg id=\"1\">"+Trans +"</seg>");
		 outputlist.get(4).println("</doc> ");

		
	}
	
	public void TotalRank() throws IOException{
		int i = 1;
		
		ArrayList<PrintWriter> outputlist = new ArrayList<PrintWriter> (); 
		 String filename2 = "F:/ACL/NLP/Evaluate/TERREF/Ref1.xml";
		 File file2 = new File(filename2);
		 PrintWriter output2 = new PrintWriter(file2);
		 String filename3 = "F:/ACL/NLP/Evaluate/TERREF/Ref2.xml";
		 File file3 = new File(filename3);
		 PrintWriter output3 = new PrintWriter(file3);
		 
		 String filename4 = "F:/ACL/NLP/Evaluate/TERREF/Ref3.xml";
		 File file4 = new File(filename4);
		 PrintWriter output4 = new PrintWriter(file4);
		 
		 String filename5 = "F:/ACL/NLP/Evaluate/TERREF/Ref4.xml";
		 File file5 = new File(filename5);
		 PrintWriter output5 = new PrintWriter(file5);
		 
		 String filename6 = "F:/ACL/NLP/Evaluate/TERREF/Trans.xml";
		 File file6 = new File(filename6);
		 PrintWriter output6 = new PrintWriter(file6);
		 
		 output2.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ");
		 output2.println("<!DOCTYPE mteval SYSTEM \"ftp://jaguar.ncsl.nist.gov/mt/resources/mteval-xml-v1.3.dtd\">"); 
		 output2.println("<mteval>");
		 output2.println("<refset setid=\"source_set\" srclang=\"Urdu\" trglang=\"English\" refid=\"reference01\">");
		 
		 output3.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ");
		 output3.println("<!DOCTYPE mteval SYSTEM \"ftp://jaguar.ncsl.nist.gov/mt/resources/mteval-xml-v1.3.dtd\">"); 
		 output3.println("<mteval>");
		 output3.println("<refset setid=\"source_set\" srclang=\"Urdu\" trglang=\"English\" refid=\"reference02\">");
		 
		 output4.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ");
		 output4.println("<!DOCTYPE mteval SYSTEM \"ftp://jaguar.ncsl.nist.gov/mt/resources/mteval-xml-v1.3.dtd\">"); 
		 output4.println("<mteval>");
		 output4.println("<refset setid=\"source_set\" srclang=\"Urdu\" trglang=\"English\" refid=\"reference03\">");
		 
		 output5.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ");
		 output5.println("<!DOCTYPE mteval SYSTEM \"ftp://jaguar.ncsl.nist.gov/mt/resources/mteval-xml-v1.3.dtd\">"); 
		 output5.println("<mteval>");
		 output5.println("<refset setid=\"source_set\" srclang=\"Urdu\" trglang=\"English\" refid=\"reference04\">");
		 
		 
		 output6.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ");
		 output6.println("<!DOCTYPE mteval SYSTEM \"ftp://jaguar.ncsl.nist.gov/mt/resources/mteval-xml-v1.3.dtd\">"); 
		 output6.println("<mteval>");
		 output6.println("<tstset setid=\"source_set\" srclang=\"Urdu\" trglang=\"English\" sysid=\"NIST\">");
		 
		 
		 outputlist.add(output2);
		 outputlist.add(output3);
		 outputlist.add(output4);
		 outputlist.add(output5);
		 outputlist.add(output6);
		
		while( i < 1684  ){  
				      Rank(i,outputlist);
		      i++;
		      
		
		      
		} 
		outputlist.get(0).println("</refset>");
		outputlist.get(0).println("</mteval> ");
		outputlist.get(0).close();
		
		outputlist.get(1).println("</refset>");
		outputlist.get(1).println("</mteval> ");
		outputlist.get(1).close();
		outputlist.get(2).println("</refset>");
		outputlist.get(2).println("</mteval> ");
		outputlist.get(2).close();
		outputlist.get(3).println("</refset>");
		outputlist.get(3).println("</mteval> ");
		outputlist.get(3).close();
		outputlist.get(4).println("</tstset>");
		outputlist.get(4).println("</mteval> ");
		outputlist.get(4).close();
		
		
				
		
	}

}
