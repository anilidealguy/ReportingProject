package com.uniper.utills;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Properties;

import com.uniper.utills.ArchiveFile;

import au.com.bytecode.opencsv.CSVReader;

public class CopyToSharepoint {
	public static HashMap<String, String> reportName_Vs_SharepointPath = new HashMap<String, String>();
	public static HashMap<String, String> reportName_Vs_RemovableChars = new HashMap<String, String>(); 
	public static void populateMap(String fileName){
		try {
			CSVReader csvReader = new CSVReader(new FileReader(fileName),',');
			String headerRow[] = csvReader.readNext();
			String nextLine[];
			while((nextLine = csvReader.readNext()) != null){
				reportName_Vs_SharepointPath.put(nextLine[0], nextLine[1]);
				reportName_Vs_RemovableChars.put(nextLine[0], nextLine[2]);
			}
			csvReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("input.properties"));
			File inputDir = new File(props.getProperty("SrcDirectory"));
			populateMap(props.getProperty("MappingFile"));
			String date = "_" + props.getProperty("ReportingDate");
			
			File files[] = inputDir.listFiles();
			ArchiveFile archive = new ArchiveFile();
			String fileName = "";
			String sharepointPath = "";
			for(File f : files){
				sharepointPath = reportName_Vs_SharepointPath.get(f.getName());
 				fileName = f.getName().substring(0, f.getName().length() - Integer.parseInt(reportName_Vs_RemovableChars.get(fileName)) );
				archive.copyFile(f.getAbsolutePath(), sharepointPath + date + ".twbx");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
