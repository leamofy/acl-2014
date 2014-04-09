package MT;

/**
 * 
 *@author Mingkun Gao, <gmingkun@seas.upenn.edu>
 *@version $LastChangedDate$
 *
 */
/*
 * In this file, we implement class Pair and class GetPair with methods to get the 
 * working pairs(translator-editor) in the corpus, and get the Turkers Matrix under the definition
 * of collaboration as pairs only with the same post-editors
 *  
 *  
 */

import java.io.BufferedReader;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


/*
 * class GetPairEditor
 */
public class GetPairOnlyEditor {
    ArrayList<Pair> PairArray = new ArrayList<Pair> ();
  //get size of pair arrays
    public int getSize(){
    	return PairArray.size();
    }
    public int Count(){
    	int count = 0;
		for(int i = 0; i < PairArray.size();i++){
			count += PairArray.get(i).getTimes();
		}
		return count;
	}
  //find pair with the same translator name and editor name
    public int Find(String t, String e){
    	for(int i = 0; i < PairArray.size();i++){
    		if(PairArray.get(i).Same(t, e) == true){
    			//PairArray.get(i).SetTimes(PairArray.get(i).getTimes()+1);
    			return i;
    		}
    	}
    	return -1;
    }
 // Add the working pair in i'th sentence's data set to pair list s
    public void AddPair(int dataid ) throws NumberFormatException, IOException{
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
		 
		 int exists;
		 for(int i = 4; i < 7;i++){
			 exists = Find(IDs.get(0),IDs.get(i));
			 if(exists == -1){
				 Pair p = new Pair(IDs.get(0),IDs.get(i),1);
				 PairArray.add(p);
			 }
			 else{
				 PairArray.get(exists).SetTimes(PairArray.get(exists).getTimes()+1);
			 }
		 }
		 for(int i = 7; i < 10;i++){
			 exists = Find(IDs.get(1),IDs.get(i));
			 if(exists == -1){
				 Pair p = new Pair(IDs.get(1),IDs.get(i),1);
				 PairArray.add(p);
			 }
			 else{
				 PairArray.get(exists).SetTimes(PairArray.get(exists).getTimes()+1);
			 }
		 }
		 for(int i = 10; i < 13;i++){
			 exists = Find(IDs.get(2),IDs.get(i));
			 if(exists == -1){
				 Pair p = new Pair(IDs.get(2),IDs.get(i),1);
				 PairArray.add(p);
			 }
			 else{
				 PairArray.get(exists).SetTimes(PairArray.get(exists).getTimes()+1);
			 }
		 }
		 for(int i = 13; i < 14;i++){
			 exists = Find(IDs.get(3),IDs.get(i));
			 if(exists == -1){
				 Pair p = new Pair(IDs.get(3),IDs.get(i),1);
				 PairArray.add(p);
			 }
			 else{
				 PairArray.get(exists).SetTimes(PairArray.get(exists).getTimes()+1);
			 }
		 }
    }
 // Construct Pair Lists
    public void getpairlist() throws IOException{
		int i = 1;
		while( i < 1684  ){  
		      AddPair(i);
		      i++;
		}
    }
  //find the position of a pair
    public int findposition(Pair p){
    	for(int i = 0; i < PairArray.size();i++){
    		if(PairArray.get(i).Same(p.getTrans(), p.getEdit()) == true)
    			return i;
    	}
    	return -1;
    }
  //for the i'th sentence's data set, scan the co-working relation, and count 
    //the times of co-working at the corresponding position 
    public void Scan(int dataid,double[][] CLab) throws NumberFormatException, IOException{
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
		 
		 //boolean exists;
		 
		 //boolean issame = false;
		 //int[] countid = new int[4];
		/* ArrayList<String> IDtmp = new ArrayList<String>();
		 ArrayList<String> IDuse = new ArrayList<String>();
		 
		 for(int i = 0; i < 4;i++){
			 String t = IDs.get(i);
			 if(IDtmp.indexOf(t) == -1)
				 IDtmp.add(t);
			 
		 }
		 for(int i = 0; i < 4;i++){
			String t = IDs.get(i);
			IDuse.add(t);
			 
		 }
		 
		 
		 for(int m = 0; m < IDtmp.size();m++){
			 int IDcount = 0;
			 String t = IDtmp.get(m);
			 for(int j = 0; j < IDuse.size();j++){
				 if(IDuse.get(j).equals(t)==true)
					 IDcount++;
			 }
			 ArrayList<Integer> rows = new ArrayList<Integer>();
			 
			 for(int j = 0; j < 4;j++){
				 if(t.equals(IDuse.get(j))==true)
					 rows.add(j);
			 }
			 if(rows.size()!= IDcount)
				 System.out.println("Error");
			 
			 ArrayList<Pair> tmp = new ArrayList<Pair>();
			 for(int pos : rows){
				 if(pos == 0){
					 for(int j = 4; j < 7;j++){
						 Pair ptmp = new Pair(IDs.get(0),IDs.get(j),0);
						 tmp.add(ptmp);
					 }
				 }
				 if(pos == 1){
					 for(int j = 7; j < 10;j++){
						 Pair ptmp = new Pair(IDs.get(1),IDs.get(j),0);
						 tmp.add(ptmp);
					 }
				 }
				 if(pos == 2){
					 for(int j = 10; j < 13;j++){
						 Pair ptmp = new Pair(IDs.get(2),IDs.get(j),0);
						 tmp.add(ptmp);
					 }
				 }
				 if(pos == 3){
					 //for(int j = 10; j < 13;j++){
						 Pair ptmp = new Pair(IDs.get(3),IDs.get(13),0);
						 tmp.add(ptmp);
					 //}
				 }
			 }
			 
			 if(tmp.size() > 1){
				 for(int i = 0; i < tmp.size(); i++){
					 for(int j = 0; j < tmp.size();j++){
						 if(i != j){
							 int l = findposition(tmp.get(i));
							 int k = findposition(tmp.get(j));
							 if(l != k)
							 CLab[l][k]++;
						 }
					 }
				 }
			 }
			 
		 }
		 */
		 ArrayList<String> IDtmp = new ArrayList<String>();
		 ArrayList<String> IDuse = new ArrayList<String>();
		 for(int i = 4; i < 14;i++){
			 String t = IDs.get(i);
			 if(IDtmp.indexOf(t) == -1)
				 IDtmp.add(t);
			 
		 }
		 for(int i = 4; i < 14;i++){
			String t = IDs.get(i);
			IDuse.add(t);
			 
		 }
		 
		 for(int m = 0; m < IDtmp.size();m++){
			 int IDcount = 0;
			 String t = IDtmp.get(m);
			 for(int j = 0; j < IDuse.size();j++){
				 if(IDuse.get(j).equals(t)==true)
					 IDcount++;
			 }
			 ArrayList<Integer> rows = new ArrayList<Integer>();
			 
			 for(int j = 0; j < IDuse.size();j++){
				 if(t.equals(IDuse.get(j))==true)
					 rows.add(j+4);
			 }
			 if(rows.size()!= IDcount)
				 System.out.println("Error");
			 
			 ArrayList<Pair> tmp = new ArrayList<Pair>();
			 for(int pos : rows){
				 if(4<=pos && pos<=6){
					 //for(int j = 4; j < 7;j++){
						 Pair ptmp = new Pair(IDs.get(0),IDs.get(pos),0);
						 tmp.add(ptmp);
					 //}
				 }
				 if(7<=pos && pos <= 9){
					 //for(int j = 7; j < 10;j++){
						 Pair ptmp = new Pair(IDs.get(1),IDs.get(pos),0);
						 tmp.add(ptmp);
					 //}
				 }
				 if(10<=pos && pos<=12){
					 //for(int j = 10; j < 13;j++){
						 Pair ptmp = new Pair(IDs.get(2),IDs.get(pos),0);
						 tmp.add(ptmp);
					 //}
				 }
				 if(pos == 13){
					 //for(int j = 10; j < 13;j++){
						 Pair ptmp = new Pair(IDs.get(3),IDs.get(13),0);
						 tmp.add(ptmp);
					 //}
				 }
			 }
			 
			 if(tmp.size() > 1){
				 for(int i = 0; i < tmp.size(); i++){
					 for(int j = 0; j < tmp.size();j++){
						 if(i != j){
							 int l = findposition(tmp.get(i));
							 int k = findposition(tmp.get(j));
							 if(l!=k)
							 CLab[l][k]++;
						 }
					 }
				 }
			 }
			 
		 }
    	
    	
    }
  //Construct Turkers Matrix
    public double [][] GetColabMatrix() throws NumberFormatException, IOException{
    	double[][] CoLab = new double[PairArray.size()][PairArray.size()];
    		
    	for(int i = 0; i < PairArray.size();i++){
    		for(int j = 0; j < PairArray.size();j++)
    			CoLab[i][j] = 0;
    	}
    	/*for(int i = 0; i < PairArray.size();i++){
    		for(int j = 0; j < PairArray.size();j++){
    			if( i == j) CoLab[i][j] = 0;
    			else{
    				if(PairArray.get(i).CoLab(PairArray.get(j)) == true)
    					CoLab[i][j]++;
    			}
    		}
    	}*/
    	int ii = 1;
		while( ii < 1684  ){  
		      Scan(ii,CoLab);
		      ii++;
		}
    	String filename = "F:/ACL/NLP/CoLab/onlyeditorclabormatrix.txt"; //colabor matrix with editor
		File file = new File(filename);
		PrintWriter outwriter = new PrintWriter(file);
		
		for(int i = 0; i < PairArray.size();i++){
			for(int j = 0; j < PairArray.size();j++)
				outwriter.print(CoLab[i][j] +" ");
			outwriter.println();
		}
		outwriter.close();
		filename = "F:/ACL/NLP/CoLab/nonlyeditorclabormatrix.txt";
		file = new File(filename);
		outwriter = new PrintWriter(file);
		double[] sums = new double[PairArray.size()];
		for(int i = 0; i < PairArray.size();i++)
			sums[i] = 0.0;
		for(int i = 0; i < PairArray.size();i++){
			for(int j = 0; j < PairArray.size();j++){
				sums[i] += CoLab[j][i];
			}
		}
		for(int i = 0; i < PairArray.size();i++){
			for(int j = 0; j < PairArray.size();j++)
				if(sums[j] !=0){
					CoLab[i][j] = CoLab[i][j]/sums[j]; 
				}
				
		}
		for(int i = 0; i < PairArray.size();i++){
			for(int j = 0; j < PairArray.size();j++)
			outwriter.print(CoLab[i][j] +" ");
			outwriter.println();
		}
		outwriter.close();
    	return CoLab;
    }
   
}
