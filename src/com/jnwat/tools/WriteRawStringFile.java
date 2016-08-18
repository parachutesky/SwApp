package com.jnwat.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteRawStringFile {

	public static void write(File file,String result){
		try {
		       if(!file.exists()){
			
					file.createNewFile();
					
			
		       	}
		        byte[] btt = result.getBytes();
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(btt);
				fos.flush();
				fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
