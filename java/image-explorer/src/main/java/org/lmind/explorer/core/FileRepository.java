package org.lmind.explorer.core;

import java.util.List;

public interface FileRepository {

	List<String> list();
	
	FileItem getFile(String name);
}
