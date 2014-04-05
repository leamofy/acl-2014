package MT;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import BLEU.BLEU;
import BLEU.computeDocBleu;
import Jama.Matrix;

/**
 * 
 * 
 * @author Mingkun Gao, <gmingkun@seas.upenn.edu>
 * @version $LastChangedDate$
 */
public class main extends pagerank{

	public static void main(String[] args) throws IOException{
		
		
		//constructM cM = new constructM();
		//UseRef ur = new UseRef();
		/*
		 * Get Strutured Data
		 */
	   /* TFIDFStat sta = new TFIDFStat();
		GetTFIDF tfidf = new GetTFIDF(); 
		sta.getM("F:/NLP/newdata.txt");
		tfidf.getTFIDF("F:/NLP/newdata.txt",sta.wholewords,sta.wholestatic,sta.totalcount);
	   */
		
		//BaiscPageRank BasicPR = new BaiscPageRank(); 
		//BasicPR.TotalRank();
		//cM.getM("F:/NLP/newdata.txt",sta.wholewords,sta.wholestatic,sta.totalcount);
		//ur.getM("F:/NLP/newdata.txt",sta.wholewords,sta.wholestatic,sta.totalcount);
		
		/*
		 *  Get Base Line
		 */
		//BaseLine BL = new BaseLine(); 
		//BL.TotalRank();
		
		//PBaseLine PBL = new PBaseLine(); 
	    //PBL.TotalRank();
		
		/*
		 * Get Bleu score of baseline
		 * 
		 */
		
		/*computeDocBleu bleu = new computeDocBleu(); 
		String ref1 = "F:/NLP/Evaluate/PlainText/BaseLine/Ref1.txt";
		String ref2 = "F:/NLP/Evaluate/PlainText/BaseLine/Ref2.txt";
		String ref3 = "F:/NLP/Evaluate/PlainText/BaseLine/Ref3.txt";
		String ref4 = "F:/NLP/Evaluate/PlainText/BaseLine/Ref4.txt";
		String trans = "F:/NLP/Evaluate/PlainText/BaseLine/Trans.txt";
		String res = "F:/NLP/Evaluate/PlainText/BaseLine/result.txt";
		System.out.println(bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
		*/
		
		/*
		 * Get TER Page rank
		 */
		//TERPageRank terrank = new TERPageRank();
		//terrank.TotalRank();
		
		//PTERPageRank Pterrank = new PTERPageRank();
		//Pterrank.TotalRank();
		
		/*
		 * Get BLEU score of TER pagerank
		 */
	    /*computeDocBleu bleu = new computeDocBleu(); 
		String ref1 = "F:/NLP/Evaluate/PlainText/TERREF/Ref1.txt";
		String ref2 = "F:/NLP/Evaluate/PlainText/TERREF/Ref2.txt";
		String ref3 = "F:/NLP/Evaluate/PlainText/TERREF/Ref3.txt";
		String ref4 = "F:/NLP/Evaluate/PlainText/TERREF/Ref4.txt";
		String trans = "F:/NLP/Evaluate/PlainText/TERREF/Trans.txt";
		String res = "F:/NLP/Evaluate/PlainText/TERREF/result.txt";
		System.out.println(bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
		*/
		
		/*
		 * Get BLEU SCORE of Cosine pagerank
		 */
		/*PBaiscPageRank PBasicPR = new PBaiscPageRank(); 
		PBasicPR.TotalRank();
		
		computeDocBleu bleu = new computeDocBleu(); 
		String ref1 = "F:/ACL/NLP/Evaluate/PlainText/COSREF/Ref1.txt";
		String ref2 = "F:/ACL/NLP/Evaluate/PlainText/COSREF/Ref2.txt";
	    String ref3 = "F:/ACL/NLP/Evaluate/PlainText/COSREF/Ref3.txt";
		String ref4 = "F:/ACL/NLP/Evaluate/PlainText/COSREF/Ref4.txt";
		String trans = "F:/ACL/NLP/Evaluate/PlainText/COSREF/Trans.txt";
		String res = "F:/ACL/NLP/Evaluate/PlainText/COSREF/result.txt";
		System.out.println(bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
		*/
		/*MakePartition mp = new MakePartition(); 
		int[] datarandom = new int[102];
		datarandom = mp.make_xval_partition(102, 10);
		for(int i = 0; i < 102;i++)
			System.out.println(datarandom[i]);
		*/
		/*
		 * Rank Partial
		 */
		//PPartialRefTERPageRank PPrank = new PPartialRefTERPageRank();
		//PPrank.PartialRank();
		/*
		 * Rank Partial PEdit
		 */
		//PPartialRefPEditTERPageRank PPErank = new PPartialRefPEditTERPageRank();
		//PPErank.PartialRank();
		//Get  Matrix using translator
		/*GetPair GP = new GetPair();
		GP.getpairlist();
		String filename = "F:/ACL/NLP/CoLab/colabor.txt";
		File file = new File(filename);
		PrintWriter outwriter = new PrintWriter(file);
		
		for(int i = 0; i < GP.PairArray.size();i++){
			outwriter.println(GP.PairArray.get(i).getTrans() + " " + GP.PairArray.get(i).getEdit()+" "+GP.PairArray.get(i).getTimes());
		}
		//System.out.println(GP.Count());
		//System.out.println(GP.getSize());
		outwriter.close();
		GP.GetColabMatrix();
		*/
		// Get  Matrix using translator and editor
		/*GetPairEditor GPE = new GetPairEditor();
		GPE.getpairlist();
		String filename = "F:/ACL/NLP/CoLab/editorcolabor.txt";
		File file = new File(filename);
		PrintWriter outwriter = new PrintWriter(file);
		
		for(int i = 0; i < GPE.PairArray.size();i++){
			outwriter.println(GPE.PairArray.get(i).getTrans() + " " + GPE.PairArray.get(i).getEdit()+" "+GPE.PairArray.get(i).getTimes());
		}
		//System.out.println(GP.Count());
		//System.out.println(GP.getSize());
		outwriter.close();
		GPE.GetColabMatrix();
		*/
		// Get the Whole Colabor
		/*GetPairAll GPA = new GetPairAll();
		GPA.getpairlist();
		String filename = "F:/ACL/NLP/CoLab/allcolabor.txt";
		File file = new File(filename);
		PrintWriter outwriter = new PrintWriter(file);
		
		for(int i = 0; i < GPA.PairArray.size();i++){
			outwriter.println(GPA.PairArray.get(i).getTrans() + " " + GPA.PairArray.get(i).getEdit()+" "+GPA.PairArray.get(i).getTimes());
		}
		//System.out.println(GP.Count());
		//System.out.println(GP.getSize());
		outwriter.close();
		GPA.GetColabMatrix();
		*/
		/*
		 * TwoLayer
		 */
		//PTLPagerank  ptlp = new PTLPagerank(); 
		//ptlp.Curve();
		
		//RevisedPTLPagerank  rptlp = new RevisedPTLPagerank(); 
		//rptlp.Curve();
		//PTLTERPagerank  ptlterp = new PTLTERPagerank(); 
		//ptlterp.Curve();
		/*
		 * 
		 * Get the order of turkers, measured by cosine similarity
		 */
		/*GetIDOrder getorder = new GetIDOrder();
		getorder.getTotalRank();
		getorder.Sort();
		getorder.PrintID();
		System.out.print(getorder.nodelist.size());
		*/
		/*
		 * Get the order of turkers, measured by TER
		 */
		
		/*GetTERIDOrder getterorder = new GetTERIDOrder();
		getterorder.getTotalRank();
		getterorder.Sort();
		getterorder.PrintID();
		System.out.print(getterorder.nodelist.size());
		*/
		/*
		 * Get the standard order of turkers, measured by TER
		 */
		/*GetTERStandardIDOrder getterorder = new GetTERStandardIDOrder();
		getterorder.getTotalRank();
		getterorder.Sort();
		getterorder.PrintID();
		System.out.print(getterorder.nodelist.size());
		*/
		
		
		/*GetTERStandardTranslatorIDOrder getterorder = new GetTERStandardTranslatorIDOrder();
		getterorder.getTotalRank();
		getterorder.Sort();
		getterorder.PrintID();
		System.out.print(getterorder.nodelist.size());
		*/
		
		
		/*
		 * Get Curve
		 */
		/*GreedyDelete GD = new GreedyDelete();
		GD.Curve();
		*/
		
		/*
		 * Get Translator Curve
		 */
		/*TranslatorGreedyDelete GD = new TranslatorGreedyDelete();
		GD.Curve();
		*/
		/*BLEU bleu = new BLEU();
		String [] s = new String[1];
		String s1 = "Support of Frances Recommendation";	
		String s2 = "Support for the Proposal of France";
		String s3 = "French Proposal Endorsed";
		String s4 = "French Proposal Supported";
		String t = "defending the thinking of France";
		s[0] = s1;
		System.out.println(bleu.computeSentenceBleu(s,t));
		s[0] = s2;
		System.out.println(bleu.computeSentenceBleu(s,t));
		s[0] = s3;
		System.out.println(bleu.computeSentenceBleu(s,t));
		
		String [] ss = new String[3];
		ss[0] = s1;
		ss[1] = s2;
		ss[2] = s3;
		
		System.out.println(bleu.computeSentenceBleu(ss,t));
		*/
		
		
		//cM.dataclean("F:/NLP/data.txt");
		//cM.setSRC("F:/NLP/newdata.txt");
		//String s  = " یہ تجویز دی گئی کہ";
		//System.out.print(s);
		/*String S = "a&b";
		 System.out.println(S.indexOf('&'));*/
		/*String S = "support of france's	recommendation";
		
		String newS = S.replaceAll("[^((a-zA-Z)|\t|0-9) ]", "").toLowerCase();
		System.out.println(newS);
		if (newS.indexOf('\t') == -1)
			System.out.println("I am not Tab");
		else
		System.out.println("I am a Tab"+newS.indexOf('\t'));*/
		
		/**
		 * 
		 *  Get Oracle
		 *  
		 * 
		 * 
		 */
		// Get Reference Score
		//RefenceBlue RB = new RefenceBlue();
		//RB.TotalRank();
		
		/*
		 * Oracle Segment on Trans
		 */
		/*OracleSegmentTrans OST = new OracleSegmentTrans();
		OST.TotalRank();
		
		computeDocBleu bleu = new computeDocBleu(); 
		String ref1 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTransEditor/Ref1.txt";
		String ref2 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTransEditor/Ref2.txt";
	    String ref3 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTransEditor/Ref3.txt";
		String ref4 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTransEditor/Ref4.txt";
		String trans = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTransEditor/Trans.txt";
		String res = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTransEditor/result.txt";
		System.out.println(bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
		*/
		/*GetTERStandardTranslatorIDOrder getterorder = new GetTERStandardTranslatorIDOrder();
		getterorder.getTotalRank();
		getterorder.Sort();
		getterorder.PrintID();
		System.out.print(getterorder.nodelist.size());
		*/
		/*//OracleTurkTrans OTT = new OracleTurkTrans();
		//OTT.TotalRank();
		computeDocBleu bleu = new computeDocBleu(); 
		String ref1 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTrans/Ref1.txt";
		String ref2 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTrans/Ref2.txt";
	    String ref3 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTrans/Ref3.txt";
		String ref4 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTrans/Ref4.txt";
		String trans = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTrans/Trans.txt";
		String res = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTrans/result.txt";
		System.out.println(bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));*/
		// Get Editor Rankings
		/*GetTERStandardEditorIDOrder getterorder = new GetTERStandardEditorIDOrder();
		getterorder.getTotalRank();
		getterorder.Sort();
		getterorder.PrintID();
		System.out.print(getterorder.nodelist.size());
		*/
		/*
		 * Get Oracle Turkers(Translators&Editors)
		 */
		/*OracleTurkTransEdit OTTE = new OracleTurkTransEdit();
		OTTE.TotalRank();
		computeDocBleu bleu = new computeDocBleu(); 
		String ref1 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTransEdit/Ref1.txt";
		String ref2 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTransEdit/Ref2.txt";
	    String ref3 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTransEdit/Ref3.txt";
		String ref4 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTransEdit/Ref4.txt";
		String trans = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTransEdit/Trans.txt";
		String res = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTransEdit/result.txt";
		System.out.println(bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
		*/
		/*
		 * Get Raw Transation
		 */
		/*OracleRawTrans ORT = new OracleRawTrans();
		ORT.TotalRank();
		computeDocBleu bleu = new computeDocBleu(); 
		String ref1 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRawTrans/Ref1.txt";
		String ref2 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRawTrans/Ref2.txt";
	    String ref3 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRawTrans/Ref3.txt";
		String ref4 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRawTrans/Ref4.txt";
		String trans = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRawTrans/Trans.txt";
		String res = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRawTrans/result.txt";
		System.out.println(bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
		*/
		/*
		 * Lowest TER
		 */
		/*OracleLowestTER OLTER = new OracleLowestTER();
		OLTER.TotalRank();
		computeDocBleu bleu = new computeDocBleu(); 
		String ref1 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleLTERTrans/Ref1.txt";
		String ref2 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleLTERTrans/Ref2.txt";
	    String ref3 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleLTERTrans/Ref3.txt";
		String ref4 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleLTERTrans/Ref4.txt";
		String trans = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleLTERTrans/Trans.txt";
		String res = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleLTERTrans/result.txt";
		System.out.println(bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
		*/
		/*
		 * Get Random Baseline
		 */
		/*int i = 0;
		double Bleu = 0.0;
		while(i < 10){
			PBaseLine PBL = new PBaseLine(); 
		    PBL.TotalRank();
			computeDocBleu bleu = new computeDocBleu(); 
			String ref1 = "F:/ACL/NLP/Evaluate/PlainText/BaseLine/Ref1.txt";
			String ref2 = "F:/ACL/NLP/Evaluate/PlainText/BaseLine/Ref2.txt";
			String ref3 = "F:/ACL/NLP/Evaluate/PlainText/BaseLine/Ref3.txt";
			String ref4 = "F:/ACL/NLP/Evaluate/PlainText/BaseLine/Ref4.txt";
			String trans = "F:/ACL/NLP/Evaluate/PlainText/BaseLine/Trans.txt";
			String res = "F:/ACL/NLP/Evaluate/PlainText/BaseLine/result.txt";
			System.out.println(bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
			Bleu+=bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res);
			i++;
		}
		System.out.println("AVG BLEU "+Bleu/10);
		*/
		/*
		 * Setence Graph Page Rank: Only Use Translators, pagerank thre:0.01
		 */
		/*PBaiscPageRank PBasicPR = new PBaiscPageRank(); 
		PBasicPR.TotalRank();
		
		computeDocBleu bleu = new computeDocBleu(); 
		String ref1 = "F:/ACL/NLP/Evaluate/PlainText/COSREF/Ref1.txt";
		String ref2 = "F:/ACL/NLP/Evaluate/PlainText/COSREF/Ref2.txt";
	    String ref3 = "F:/ACL/NLP/Evaluate/PlainText/COSREF/Ref3.txt";
		String ref4 = "F:/ACL/NLP/Evaluate/PlainText/COSREF/Ref4.txt";
		String trans = "F:/ACL/NLP/Evaluate/PlainText/COSREF/Trans.txt";
		String res = "F:/ACL/NLP/Evaluate/PlainText/COSREF/result.txt";
		System.out.println(bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
		*/
	}

}
