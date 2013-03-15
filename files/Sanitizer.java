package com.ravs.data;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import au.com.bytecode.opencsv.CSVReader;

public class Sanitizer {

	static CSVReader reader;
	static ArrayList<String> states = new ArrayList<String>();
	
	public static void main(String[] args) {
		try {
			FileWriter fstream = new FileWriter("matrix.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			getDistinctStateList();
			out.write(states.toString());
			out.write("\n\n");
			for(String from : states){
				ArrayList<Integer> temp = new ArrayList<Integer>();
				for(String to : states){
					temp.add(migrantCount(from, to));
				}
				out.write(temp.toString());
				out.write(",\n");
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int migrantCount(String fromState, String toState) throws IOException{
		int count = 0;
		reader = 
				new CSVReader(
						new FileReader(
								"C:\\workspace\\eclipse\\DataViz\\MigrantDataset\\src\\Migration_Cleaned_Dataset.csv"));
		String[] csvRow;
		while( (csvRow = reader.readNext()) != null ){
			if(csvRow[3].equalsIgnoreCase(fromState) && csvRow[28].equalsIgnoreCase(toState)){
				++count;
			}
		}
		reader.close();
		return count;
	}
	
	public static void getDistinctStateList() throws IOException{
		reader = 
				new CSVReader(
						new FileReader(
								"C:\\workspace\\eclipse\\DataViz\\MigrantDataset\\src\\Migration_Cleaned_Dataset.csv"));
		String[] csvRow;
		reader.readNext();//skip the column name
		while( (csvRow = reader.readNext()) != null ){
			if(!states.contains(csvRow[3]) && !csvRow[3].equals("")){
				states.add(csvRow[3]);
			}
			if(!states.contains(csvRow[28]) && !csvRow[28].equals("")){
				states.add(csvRow[28]);
			}
		}
	}

}
