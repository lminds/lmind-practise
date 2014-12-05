package org.lmind.explorer.core;

import java.io.InputStream;

public interface FileItem {
	
	String getName();
	
	String getMimeType();

	InputStream input();
}
