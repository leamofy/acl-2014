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
public class Main extends pagerank{

	public static void main(String[] args) throws IOException{
		
		
		
		/*
		 * Get Strucstured Data
		 */
	    /*TFIDFStat sta = new TFIDFStat();
		GetTFIDF tfidf = new GetTFIDF(); 
		sta.getM("F:/NLP/newdata.txt");
		tfidf.getTFIDF("F:/NLP/newdata.txt",sta.wholewords,sta.wholestatic,sta.totalcount);
	   */
		
		
		
		/*
		 * Get  MTurker Matrix using translator
		 */
		
		/*GetPair GP = new GetPair();
		GP.getpairlist();
		String filename = "F:/ACL/NLP/CoLab/colabor.txt";
		File file = new File(filename);
		PrintWriter outwriter = new PrintWriter(file);
		
		for(int i = 0; i < GP.PairArray.size();i++){
			outwriter.println(GP.PairArray.get(i).getTrans() + " " + GP.PairArray.get(i).getEdit()+" "+GP.PairArray.get(i).getTimes());
		}
		System.out.println(GP.Count());
		System.out.println(GP.getSize());
		outwriter.close();
		GP.GetColabMatrix();
		*/
		/*
		 *  Get  MTurker Matrix using co-translator and co-editor
		 */
		/*GetPairEditor GPE = new GetPairEditor();
		GPE.getpairlist();
		String filename = "F:/ACL/NLP/CoLab/editorcolabor.txt";
		File file = new File(filename);
		PrintWriter outwriter = new PrintWriter(file);
		
		for(int i = 0; i < GPE.PairArray.size();i++){
			outwriter.println(GPE.PairArray.get(i).getTrans() + " " + GPE.PairArray.get(i).getEdit()+" "+GPE.PairArray.get(i).getTimes());
		}
		System.out.println(GP.Count());
		System.out.println(GP.getSize());
		outwriter.close();
		GPE.GetColabMatrix();
		*/
		
		
		/*
		 *  Get  MTurker Matrix using co-translator and/or co-editor
		 */
		
		/*GetPairAll GPA = new GetPairAll();
		GPA.getpairlist();
		String filename = "F:/ACL/NLP/CoLab/allcolabor.txt";
		File file = new File(filename);
		PrintWriter outwriter = new PrintWriter(file);
		
		for(int i = 0; i < GPA.PairArray.size();i++){
			outwriter.println(GPA.PairArray.get(i).getTrans() + " " + GPA.PairArray.get(i).getEdit()+" "+GPA.PairArray.get(i).getTimes());
		}
		System.out.println(GP.Count());
		System.out.println(GP.getSize());
		outwriter.close();
		GPA.GetColabMatrix();
		*/
		
		/*
		 * Two Layer Page Rank, get curve
		 */
		//PTLPagerank  ptlp = new PTLPagerank(); 
		//ptlp.Curve();
		
		
		/*GetTERStandardTranslatorIDOrder getterorder = new GetTERStandardTranslatorIDOrder();
		getterorder.getTotalRank();
		getterorder.Sort();
		getterorder.PrintID();
		System.out.print(getterorder.nodelist.size());
		*/
		
		
	
		
		//cM.dataclean("F:/NLP/data.txt");
		//cM.setSRC("F:/NLP/newdata.txt");
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
		String ref1 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTrans/Ref1.txt";
		String ref2 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTrans/Ref2.txt";
	    String ref3 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTrans/Ref3.txt";
		String ref4 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTrans/Ref4.txt";
		String trans = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTrans/Trans.txt";
		String res = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTrans/result.txt";
		System.out.println(bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
		*/
		
		/*
		 * Oracle Segment on Trans and Edits
		 */
		/*OracleSegmentTransEdit OSTE = new OracleSegmentTransEdit();
		OSTE.TotalRank();
		
		computeDocBleu bleu = new computeDocBleu(); 
		String ref1 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTransEditor/Ref1.txt";
		String ref2 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTransEditor/Ref2.txt";
	    String ref3 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTransEditor/Ref3.txt";
		String ref4 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTransEditor/Ref4.txt";
		String trans = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTransEditor/Trans.txt";
		String res = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleSegTransEditor/result.txt";
		System.out.println(bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
		*/
		/*
		 * Get the standard ranking of translators, measured by TER
		 */
		
		/*GetTERStandardTranslatorIDOrder getterorder = new GetTERStandardTranslatorIDOrder();
		getterorder.getTotalRank();
		getterorder.Sort();
		getterorder.PrintID();
		System.out.print(getterorder.nodelist.size());
		*/
		/*
		 * Oracle MTurker on Translators
		 */
		/*OracleTurkTrans OTT = new OracleTurkTrans();
		OTT.TotalRank();
		computeDocBleu bleu = new computeDocBleu(); 
		String ref1 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTrans/Ref1.txt";
		String ref2 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTrans/Ref2.txt";
	    String ref3 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTrans/Ref3.txt";
		String ref4 = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTrans/Ref4.txt";
		String trans = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTrans/Trans.txt";
		String res = "F:/ACL/NLP/Evaluate/PlainText/Oracle/OracleRankTrans/result.txt";
		System.out.println(bleu.computeBleu(ref1, ref2, ref3, ref4, trans,res));
		*/
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
		/*
		 * Get Bad Editions
		 */
		
		//GetBadEdition GBE = new GetBadEdition();
		//GBE.GetBadEditions();
	}

}
