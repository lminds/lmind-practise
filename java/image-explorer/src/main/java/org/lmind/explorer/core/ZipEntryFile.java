package org.lmind.explorer.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipEntryFile implements FileItem {
	
	private ZipFile zip;

	private ZipEntry entry = null;

	public ZipEntryFile(ZipFile zip, ZipEntry entry) {
		super();
		this.zip = zip;
		this.entry = entry;
	}

	@Override
	public String getName() {
		return entry.getName();
	}
	
	@Override
	public String getMimeType() {
		return Mime.getMimeType(getName());
	}

	@Override
	public InputStream input() {
		try {
			return zip.getInputStream(entry);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


}
