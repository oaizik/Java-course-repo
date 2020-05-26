/**
 * The file name uses the strat a input and output
 	streams and read from a given file.
 * i'm use File.class in this project for recieving the data for the Graph.
 * 
 */

package com.nerd.hello;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * File class is responsible for create a file withing 
 * choosen file name.
 * is also resposible to read & write from the file using it
 * public functions.
 * 
 * @author Yaron Daya
 *
 */
public class File {
	
	InputStream is =null;
	DataInputStream dis = null;
	FileOutputStream os = null;
	DataOutputStream dos = null;
	String fileName=null;
	
	
	
	
	/*
	 * ***************Constructor***************
	 * Open input & output streams for writing
	 * and reading from a file.
	 */

	public File(String fileName){
		setFileName(fileName);
		try {
			os =new FileOutputStream(getFileName());
			dos = new DataOutputStream(os);
			is = new FileInputStream(getFileName());
			dis = new DataInputStream(is);
			 
		} catch (FileNotFoundException e) {
			Log.logIt("Unable to open input/output stream.");
		} 
	}

	
	/*********************************************************/
	
	/*
	 * write 1 line to a file.
	 */
	public  synchronized void writeToFile(String str){
		
		if(os!=null){
		try {
			dos.writeUTF(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		}
	}

	/*
	 * Read 1 line or all file
	 */
	public synchronized void readFromFile(){
		
		try {
			String str = dis.readUTF();
			Log.logIt("Success reading from: "+getFileName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public synchronized String readAllFile(){
		String str= null;		
		try {
			while(dis.available()>0)
				if(str!=null)
				str = str +"\n" +dis.readUTF();
				else
					str = dis.readUTF(); 
		} catch (IOException e) {
			Log.logIt("Unable to read all the file: "+getFileName());
		}
		Log.logIt("success reading all the file: "+getFileName());
			return str;
	}
	
	
	
	
	
	/*
	 * Getters & Setters
	 */
	
	private void setFileName(String fileName) {
		this.fileName = fileName;
		
	}

	private String getFileName() {
		return fileName;
		
	}

}
