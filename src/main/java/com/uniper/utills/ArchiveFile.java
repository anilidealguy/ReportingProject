package com.uniper.utills;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class ArchiveFile {
	public static void copyFile(String src, String dest) throws Exception{
		File srcFile = new File(src);
		File destFile = new File(dest);
		FileUtils.copyFileToDirectory(srcFile, destFile, true);
	}

}
