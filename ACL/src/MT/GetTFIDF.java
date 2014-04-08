
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
 * Get Structured Data Set
 * 
 * Each data set file is in the following format:
 * Line1: vocabulary list for one source's references,translations and editions
 * Line2: ref1
 * Line3: TFIDF bag of word models of ref1
 * Line4: ref2
 * Line5: TFIDF bag of word models of ref2
 * Line6: ref3
 * Line7: TFIDF bag of word models of ref3
 * Line8: ref4
 * Line9: TFIDF bag of word models of ref4
 * Line10: TransID_1
 * Line11: trans_1
 * Line12: TFIDF bag of word models of trans_1
 * Line13: TransID_2
 * Line14: trans_2
 * Line15: TFIDF bag of word models of trans_2
 * Line16: TransID_13
 * Line17: trans_3
 * Line18: TFIDF bag of word models of trans_3
 * Line19: TransID_4
 * Line20: trans_4
 * Line21: TFIDF bag of word models of trans_4
 * Line22: EditID_1_1
 * Line23: edit_1_1
 * Line24: TFIDF bag of word models of edit_1_1
 * Line25: EditID_1_2
 * Line26: edit_1_2
 * Line27: TFIDF bag of word models of edit_1_2
 * Line28: EditID_1_3
 * Line29: edit_1_3
 * Line30: TFIDF bag of word models of edit_1_3
 * Line31: EditID_2_1
 * Line32: edit_2_1
 * Line33: TFIDF bag of word models of edit_2_1
 * Line34: EditID_2_2
 * Line35: edit_2_2
 * Line36: TFIDF bag of word models of edit_2_2
 * Line37: EditID_2_3
 * Line38: edit_2_3
 * Line39: TFIDF bag of word models of edit_2_3
 * Line40: EditID_3_1
 * Line41: edit_3_1
 * Line42: TFIDF bag of word models of edit_3_1
 * Line43: EditID_3_2
 * Line44: edit_3_2
 * Line45: TFIDF bag of word models of edit_3_2
 * Line46: EditID_3_3
 * Line47: edit_3_3
 * Line48: TFIDF bag of word models of edit_3_3
 * Line49: EditID_4_1
 * Line50: edit_4_1
 * Line51: TFIDF bag of word models of edit_4_1
 */
public class GetTFIDF extends pagerank {
	public int getWord(String s, int startindex){
		int i = startindex;
		while(i < s.length()) {
			
			if(s.charAt(i) == ' ' || s.charAt(i) == '\t'|| s.charAt(i) == '\n')
				break;
			i++;
			
		}
		return i;
	}
	public double cosine(ArrayList<Double> a, ArrayList<Double> b){
		int lenA = a.size();
		int lenB = b.size();
		if(lenA != lenB){
			System.out.println("Error");
			return -2;
		}
		int i = 0;
		double sum = 0.0;
		double sumA = 0.0;
		double sumB = 0.0;
		for(;i < lenA;i++){
			sum += a.get(i)*b.get(i);
			sumA += a.get(i)*a.get(i);
			sumB += b.get(i)*b.get(i);
		}
		return sum/(Math.sqrt(sumA)*Math.sqrt(sumB));
	}
	
	public double cosineint(ArrayList<Integer> a, ArrayList<Integer> b){
		int lenA = a.size();
		int lenB = b.size();
		if(lenA != lenB){
			System.out.println("Error");
			return -2;
		}
		int i = 0;
		double sum = 0.0;
		double sumA = 0.0;
		double sumB = 0.0;
		for(;i < lenA;i++){
			sum += a.get(i)*b.get(i);
			sumA += a.get(i)*a.get(i);
			sumB += b.get(i)*b.get(i);
		}
		return sum/(Math.sqrt(sumA)*Math.sqrt(sumB));
	}
	// Get structured data of a file
	public void worddivider(String s,int dataid,ArrayList<String> wholewords,ArrayList<Integer> wholestatic,int totalcount) throws FileNotFoundException{
		ArrayList<ArrayList<String>> sentences = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> wordlist = new ArrayList<String>();
		ArrayList<String> idlist = new ArrayList<String>();
		ArrayList<ArrayList<Integer>> wordbag = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Double>> tfidfwordbag = new ArrayList<ArrayList<Double>>();
		int length = s.length();
		
		
		int counttab = 0;
	    int i  = 0;
	  
	    while(i < length && counttab < 3){
	    	if(s.charAt(i)=='\t') {
	    		counttab++;
	    	}
	    	i++;
	    }
	    s = s.substring(i);
	    
	    s = s.replaceAll("[^((a-zA-Z)|\t|0-9) ]", "").toLowerCase();
	    //System.out.println(s);
	    int rear;
	    ArrayList<String> temp = new ArrayList<String>();
	    i = 0;
	    length = s.length();
	    while(i < length){
	    	if(s.charAt(i)!=' ' &&s.charAt(i)!='\t' && (counttab < 7 || counttab%2!=1)){
	    	rear = getWord(s,i);
	    	
	    	String tmpword = s.substring(i, rear);
	    	int wlen = tmpword.length();
	    	/*if (tmpword.length() > 1 &&(tmpword.substring(tmpword.length()-2).equals(".\"")==true||tmpword.substring(tmpword.length()-2).equals(".\'")==true||
	    		tmpword.substring(tmpword.length()-2).equals("?\"")==true||tmpword.substring(tmpword.length()-2).equals("?\'")==true||
	    		tmpword.substring(tmpword.length()-2).equals("..") == true||tmpword.substring(tmpword.length()-2).equals(".)")==true||tmpword.substring(tmpword.length()-2).equals("),") == true))
	    		tmpword = tmpword.substring(0, tmpword.length()-2);
	    	if (tmpword.charAt(0)=='\"' || tmpword.charAt(0)=='\'' || tmpword.charAt(0)==',' || tmpword.charAt(0)==';'|| tmpword.charAt(0)=='(')
	    		tmpword = tmpword.substring(1,tmpword.length());
	    	if (tmpword.length() > 0&&(tmpword.charAt(tmpword.length()-1)=='\"' || tmpword.charAt(tmpword.length()-1)=='\'' ||
	    			tmpword.charAt(tmpword.length()-1)==','|| tmpword.charAt(tmpword.length()-1)==';' ||
	    			tmpword.charAt(tmpword.length()-1)=='?'||tmpword.charAt(tmpword.length()-1)=='.'||tmpword.charAt(tmpword.length()-1)==')'))
	    		tmpword = tmpword.substring(0, tmpword.length()-1);
	    	*/	
	    	i = rear;
	    	if(tmpword != null && tmpword.equals("n/a") == false)
	    	temp.add(tmpword);
	    	
	    	if (wordlist.indexOf(tmpword) == -1 && tmpword.equals("n/a") == false)
	    	wordlist.add(tmpword);
	    	continue;
	    	}
	    	else if(s.charAt(i)!=' ' &&s.charAt(i)!='\t' && (counttab >= 7 && counttab%2==1)){
	    		rear = getWord(s,i);
		    	String ids = s.substring(i, rear);
		    	idlist.add(ids);
		    	i = rear;
	    	}
	    	else if(s.charAt(i)==' ') {
	    		i++;
	    	}
	    	else if(s.charAt(i)=='\t' || s.charAt(i)=='\n') {
	    		if (temp.size() > 0)
	    		{
	    			sentences.add(temp);	    		
	    			temp = new ArrayList<String>();
	    		}
	    		i++;
	    		counttab ++;
	    	}
	    }
	    if (temp.size() > 0)
		{
		sentences.add(temp);
		temp = new ArrayList<String>();
		}
	   /* System.out.println(sentences.size());
	    for(int j = 0;j < sentences.size();j++){
	    	ArrayList<String> as = sentences.get(j);
	    	System.out.println();
	    	for(int k = 0; k < as.size();k++){
	    		System.out.print(as.get(k)+" ");
	    	}
	    }
	    System.out.println();*/
	    
	    	//String as = wordlist.get(j);
	    int senlength = sentences.size();
	    int wordlistlength = wordlist.size();
	    	for(int l = 0;l < senlength;l++){
		    	ArrayList<String> sen = sentences.get(l);
		    	if(sen.get(0).equals("n/a")==false ) {
		    	ArrayList<Integer> t = new ArrayList<Integer>();
		    	for(int j = 0;j < wordlistlength;j++){
					String token = wordlist.get(j);
					int plength = sen.size();
					int k = 0;
					int wcount = 0;
					//if (sen.get(0) != "n/a"){
						for(; k < plength;k++){
		    		      if (token.equals(sen.get(k))== true)
		    		    	  wcount++;
		    			}
		    		t.add(wcount);
				//	}
		    		 
		    	}
		    	wordbag.add(t);
		    	}
	    	}
	    
	    /*************************************TFIDF******************************/
	    int num_document = wordbag.size();
	    int wordslength = wordbag.get(0).size();
	    int [] num_wordlist = new int[wordslength];
	    double [] idf_wordlist = new double[wordslength];
	    int [] termsum = new int[num_document];
	    
	    for (int l = 0; l < wordslength;l++ )
	    	num_wordlist[l] = 0;
	    for (int l = 0; l < num_document;l++ )
	    	termsum[l] = 0;
	    for(int l = 0; l < wordbag.size();l++){
	    	ArrayList<Integer> tmp = wordbag.get(l);
	    	for (int k = 0; k < wordslength;k++){
	    		termsum[l] += tmp.get(k);
	    	
	    	}
	    }
	   // System.out.println("Size:"+totalcount);
	    for(int l = 0; l < wordslength;l++){
	    	String wordtmp = wordlist.get(l);
	    	num_wordlist[l] = wholestatic.get(wholewords.indexOf(wordtmp));
	    }
	    for (int l = 0; l < wordslength;l++ )
	    	idf_wordlist[l] = Math.log((double)totalcount/num_wordlist[l]);
	    
	    for(int l = 0; l<num_document;l++){
	    	ArrayList<Double> arrtmp = new ArrayList<Double>();
	    	ArrayList<Integer> tmp = wordbag.get(l);
	    	for(int k = 0; k < wordslength;k++){
	    		double dtmp = idf_wordlist[k]*tmp.get(k)/termsum[l];
	    		arrtmp.add(dtmp);
	    		}
	        tfidfwordbag.add(arrtmp);	
	    }
	    	
	  
	    	String filename = "F:/ACL/NLP/TFIDFDATA/text"+dataid+".txt";
		    File file = new File(filename);
		  /*  if(file1.exists()){
		    	System.out.println("File already exits");
		    	System.exit(0);
		    }
		    */
		   PrintWriter output = new PrintWriter(file);
		    for(int j = 0;j < wordlist.size();j++){
		    	String tokens = wordlist.get(j);
		    	if (j < wordlist.size() -1)
		    		output.print(tokens+" ");
		    	else if (j == wordlist.size() -1)
		    		output.print(tokens);
		    }
		    output.print('\n');
		    for(int j = 0;j < 4;j++){
	 	    	ArrayList<Double> as = tfidfwordbag.get(j);
	 	    	ArrayList<String> sas = sentences.get(j);
	 	    	for(int k = 0; k < sas.size();k++){
	 	    		if (k < sas.size() -1)
	 	    			output.print(sas.get(k)+" ");
	 	    		else if (k == sas.size() -1)
	 	    			output.print(sas.get(k));
	 	    	}
	 	    	output.print('\n');
	 	    	for(int k = 0; k < as.size();k++){
	 	    		if (k < as.size() -1)
	 	    			output.print(as.get(k)+" ");
	 	    		else if (k == as.size() -1)
	 	    			output.print(as.get(k));
	 	    	}
	 	    	output.print('\n');
	 	    }
	    	 for(int j = 4;j < tfidfwordbag.size();j++){
	    		 output.println(idlist.get(j-4));
	 	    	ArrayList<Double> as = tfidfwordbag.get(j);
	 	    	ArrayList<String> sas = sentences.get(j);
	 	    	for(int k = 0; k < sas.size();k++){
	 	    		if (k < sas.size() -1)
	 	    			output.print(sas.get(k)+" ");
	 	    		else if (k == sas.size() -1)
	 	    			output.print(sas.get(k));
	 	    	}
	 	    	output.print('\n');
	 	    	
	 	    	for(int k = 0; k < as.size();k++){
	 	    		if (k < as.size() -1)
	 	    			output.print(as.get(k)+" ");
	 	    		else if (k == as.size() -1)
	 	    			output.print(as.get(k));
	 	    	}
	 	    	output.print('\n');
	 	    }
	    	 output.close();
	    	 
	    	
	    	
	    	 
	    	
	    }
		
	
	// Get the whole structured data sets for all files
	public void getTFIDF(String filename,ArrayList<String> wholewords,ArrayList<Integer> wholestatic,int totalcount) throws IOException{
		int i = 1;
		/*while(i < 1000000){
			System.out.println("hello");
			i++;
		}
		while(input.hasNext()){
			//if (i %100 == 0) System.out.println();
			System.out.println(input.next());
			i++;
		}*/
	
		 

		BufferedReader br = new BufferedReader(new FileReader(filename));  
		String data = br.readLine();//一次读入一行，直到读入null为文件结束  
		//data = br.readLine();
		
		/*while( data!=null  && i < 18){
			data = br.readLine(); 
				i++;
		}*/

		while( data!=null  ){  
			//System.out.println(data);
		     worddivider(data,i,wholewords,wholestatic,totalcount);
		      i++;
		      
		      data = br.readLine(); //接着读下一行
		      
		} 
		//output2.println("AVG BLEU:"+totalsum/(i-1));
		
		
		
		
		
		
		
		
		
	}
	public int[] getSubStringIndex(String s){
		int counttab = 0;
		
		
		int [] res = new int[2];
		for (int i = 0; i < s.length();i ++){
			if (s.charAt(i) == '\t'){
				counttab++;
			}
			if (counttab == 2){
				res[0] = i;
				break;
			}
			
			
		}
		counttab = 0;
		for (int i = 0; i < s.length();i ++){
			if (s.charAt(i) == '\t'){
				counttab++;
			}
			if (counttab == 3){
				res[1] = i;
				break;
			}
			
			
		}
		
		return res;
		
	}
	//Clean Data:remove sentence with N/A
	public void dataclean(String filename) throws IOException{
		
		int i = 1;
		
		String filename2 = "F:/ACL/NLP/newdata.txt";
		 File file2 = new File(filename2);
		 PrintWriter output2 = new PrintWriter(file2);
		 
		 FileInputStream fis = new FileInputStream(new File(filename));
		 BufferedReader br = new BufferedReader(new InputStreamReader(fis , "UTF-8"));
		String data = br.readLine();//一次读入一行，直到读入null为文件结束  
		data = br.readLine();
		
		System.out.println(data);
		while( data!=null  ){
			
		  if (data.indexOf("N/A") == -1 && data.indexOf('&') == -1)
				output2.println(data);
		      i++;
		      
		      data = br.readLine(); //接着读下一行
		      
		}  
		output2.close();
		
		
	}
public int counttab(String line){
		int i = 0;
		int count = 0;
		while(i < line.length()){
			if (line.charAt(i) =='\t')
				count ++;
			i++;
		}
		return count;
	}
public void setSRC(String filename) throws IOException{
		
		int i = 1;
		
	/* String filename2 = "F:/NLP/newdata.txt";
		 File file2 = new File(filename2);
		 PrintWriter output2 = new PrintWriter(file2);
		BufferedReader br = new BufferedReader(new FileReader(filename));  
		String data = br.readLine();//一次读入一行，直到读入null为文件结束  
		data = br.readLine();
		
		
		while( data!=null  ){
			
		  if (data.indexOf("N/A") == -1)
				output2.println(data);
		      i++;
		      
		      data = br.readLine(); //接着读下一行
		      
		}  
		output2.close();*/
		
		String filename2 = "F:/ACL/NLP/Evaluate/src.xml";
		 File file2 = new File(filename2);
		 PrintWriter output2 = new PrintWriter(file2);
		 
		 output2.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ");
		 output2.println("<!DOCTYPE mteval SYSTEM \"ftp://jaguar.ncsl.nist.gov/mt/resources/mteval-xml-v1.3.dtd\">"); 
		 output2.println("<mteval>");
		 output2.println("<srcset setid=\"source_set\" srclang=\"Urdu\"> ");

		 
		 FileInputStream fis = new FileInputStream(new File(filename));
		 BufferedReader br = new BufferedReader(new InputStreamReader(fis , "UTF-8"));
		String data = br.readLine();//一次读入一行，直到读入null为文件结束  
		//data = br.readLine();
		
		System.out.println(data);
		while( data!=null){
			
		 int [] indices = getSubStringIndex(data);
		
		 String s = data.substring(indices[0], indices[1]);
//		 System.out.println(s);
//		 System.out.println(indices[0]+" " +indices[1]);
		 String docid = "document"+i;
		 output2.println("<doc docid=\""+ docid + "\" genre=\"nw\">"); 
		 output2.println("<seg id=\"1\">"+s +"</seg>");
		 output2.println("</doc> ");
		 i++;     
		 data = br.readLine(); //接着读下一行
		      
		}  
		output2.println("</srcset>");
		output2.println("</mteval> ");

		output2.close();
		
		
	}
	
	

}

