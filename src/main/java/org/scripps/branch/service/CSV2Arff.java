package org.scripps.branch.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class CSV2Arff {

	public static class MyCSVLoader extends CSVLoader {
		protected void initTokenizer(StreamTokenizer tokenizer) {
			tokenizer.resetSyntax();
			tokenizer.whitespaceChars(0, (' ' - 1));
			tokenizer.wordChars(' ', '\u00FF');
			tokenizer.whitespaceChars('\t', '\t');
			// tokenizer.commentChar('#'); //\uD880
			// tokenizer.quoteChar('"');
			// tokenizer.quoteChar('\'');
			tokenizer.eolIsSignificant(true);
		}
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CSV2Arff.class);

	public static void convert(String csvFileName, String arffFileName)
			throws IOException {
		toArff(load(csvFileName), arffFileName);
	}

	public static Instances load(String csvFile) throws IOException {
		// load CSV
		System.out.println("Loading CSV dataset");
		CSVLoader loader = new CSVLoader();
		// loader.setFieldSeperator("\\t");
		loader.setSource(new File(csvFile));
		Instances data = loader.getDataSet();
		return data;
	}


	public static void toArff(Instances data, String arffFileName)
			throws IOException {
		System.out.println("Saving dataset as ARFF");
		// save ARFF
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(arffFileName));
		// saver.setDestination(new File(args[1]));
		saver.writeBatch();
	}


	//		private static String delimiterCheck(String inputPath) {
	//
	//		String line=null;
	//		int noOfLinesToScan=100;
	//		int count =0;
	//		String[]tabs = null ;
	//		String[] comma = null;
	//		int a[] = new int[noOfLinesToScan] ;
	//		int b[] = new int[noOfLinesToScan] ;
	//		try {
	//
	//			BufferedReader fileReader = new BufferedReader(new FileReader(inputPath));
	//
	//			while ((line = fileReader.readLine()) != null && count<noOfLinesToScan)
	//			{
	//				tabs= line.split("\t");
	//				comma=line.split(",");
	//
	//				a[count]=tabs.length;
	//				b[count]=comma.length;	
	//				count++;
	//			}
	//
	//			//			
	//			//			for (int i = 0; i < b.length; i++) {
	//			//				System.out.println(b[i]);
	//			//				System.out.println(a[i]);
	//			//			}
	//			//			System.out.println("tabs"+tabs.length);
	//			//			System.out.println("commas"+comma.length);
	//			//			int i=0,j=0;
	//			//			if (a.length == 0 && b.length== 0) {
	//			//				return "";
	//			//			} 
	//			//			else if(a.length>0){
	//			//				int first = a[0];
	//			//				for (int element : a) {
	//			//					if (element != first) {
	//			//						break;
	//			//					}
	//			//					i++;
	//			//				}
	//			//			}
	//			//			else if(b.length>0){
	//			//				int first = b[0];
	//			//				for (int element : b) {
	//			//					if (element != first) {
	//			//						break;
	//			//					}
	//			//					j++;
	//			//				}
	//			//			}
	//			//			System.out.println("i"+i);
	//			//
	//			//			System.out.println("j"+j);
	//			//			if(i==noOfLinesToScan && i>j)
	//			//				return "tab";
	//			//			else if (j==noOfLinesToScan && j>i)
	//			//				return "comma";
	//			//			else
	//			//				System.out.println("Nothin");
	//
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//
	//
	//		if(tabs.length>comma.length){
	//			return "tab";
	//		}
	//
	//		else
	//			return "comma";
	//
	//
	//
	//	}
	//


	private static String delimiterCheck(String inputPath) {

		String line=null;
		String prevLine=null;
		int noOfLinesToScan=100;
		int count =0;
		String[]tabs = null ;
		String[] comma = null;
		String[] c;
		String[] d;
		int tFlag=0, cFlag=0;

		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(inputPath));

			while ((line = fileReader.readLine()) != null && count<noOfLinesToScan)
			{
				if(prevLine!=null && line.length()>0 && line.charAt(0)!='#')
				{

					tabs=line.split("\t");
					comma=line.split(",");

					if(tabs.length<1 && comma.length<1 && (prevLine.split(",")).length < 1 && (prevLine.split("\t")).length <1) return "invalid";

					if( prevLine.split(",").length == comma.length && comma.length>1 && comma.length> tabs.length){
						cFlag=1;
					}
					else cFlag=0;

					if(prevLine.split("\t").length==tabs.length && tabs.length>1 && tabs.length>comma.length)
						tFlag=1;
					else tFlag=0;
				}
				else{
					prevLine =line;
					fileReader.readLine();
				}				
				//prevLine=line;
				count++;
			}

		} catch (IOException e) {
			LOGGER.debug("Exception Occured while reading file"+e);
			e.printStackTrace();
		}
		if(tFlag==1 && cFlag==0) return "tab";
		else if(tFlag==0 && cFlag==1)return "comma";
		else return "invalid";
	}




	public static void main(String[] args) throws Exception {
		//String csvFile = "/home/bob/gene_mapping.csv";
		String csvFile ="/home/bob/Dataset/1/oslo_mapping.txt";
		//		String arffFileName = "/home/bob/r5x4-with-extra.arff";face
		//		Instances data = load(csvFile);
		//		toArff(data, arffFileName);
		//		LOGGER.debug("Number of Attributes are : " + data.numAttributes());
		//		LOGGER.debug("Number of Instances are : " + data.numInstances());
		//		data.setClassIndex(data.numAttributes() - 1);
		//		Classifier c = new J48();
		//		c.buildClassifier(data);
		//		Evaluation eval = new Evaluation(data);
		//		eval.evaluateModel(c, data);
		//		System.out.println(eval.toSummaryString());

		String a=delimiterCheck(csvFile);
		System.out.println("Return Value: "+ a);
		//System.out.println("Nothing");
	}
}