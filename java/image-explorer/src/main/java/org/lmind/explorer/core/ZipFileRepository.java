package org.lmind.explorer.core;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileRepository implements FileRepository {
	
	private ZipFile zip;

	public ZipFileRepository(ZipFile zip) {
		super();
		this.zip = zip;
	}

	@Override
	public List<String> list() {
		ArrayList<String> list = new ArrayList<String>();
		Enumeration<? extends ZipEntry> entries = zip.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			list.add(entry.getName());
		}
		list.sort((a, b) -> a.compareToIgnoreCase(b));
		return list;
	}

	@Override
	public FileItem getFile(String name) {
		ZipEntry entry = zip.getEntry(name);
		if (entry == null) {
			return null;
		}
		return new ZipEntryFile(zip, entry);
	}

}
