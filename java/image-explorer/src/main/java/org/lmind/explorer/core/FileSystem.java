package org.lmind.explorer.core;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

public class FileSystem {
	
	private java.io.File root;

	public java.io.File getRoot() {
		return root;
	}

	public void setRoot(java.io.File root) {
		this.root = root;
	}

	public FileRepository getRepository(String name) {
		
		File f = new File(root, name);
		if (!f.isFile()) {
			return null;
		}
		ZipFile zip;
		try {
			zip = new ZipFile(f);
		} catch (IOException e) {
			return null;
		}
		return new ZipFileRepository(zip);
	}
}
