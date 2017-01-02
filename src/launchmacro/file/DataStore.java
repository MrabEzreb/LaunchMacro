package launchmacro.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class DataStore {

	public String identifier;
	public Properties info;
	public HashMap<String, byte[]> stores;
	
	public DataStore(String identifier, Properties info, HashMap<String, byte[]> stores) {
		this.identifier = identifier;
		this.info = info;
		this.stores = stores;
	}
	
	public static void saveStore(File f, DataStore store) throws IOException {
		if(f.isFile() && f.exists()) {
			f.delete();
			f.createNewFile();
		}
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f));
		ZipEntry idE = new ZipEntry("identifier");
		zos.putNextEntry(idE);
		zos.write(store.identifier.getBytes());
		zos.closeEntry();
		ZipEntry infoE = new ZipEntry("info.properties");
		zos.putNextEntry(infoE);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		store.info.store(baos, "");
		zos.write(baos.toByteArray());
		zos.closeEntry();
		ZipEntry storesFE = new ZipEntry("stores/");
		zos.putNextEntry(storesFE);
		zos.closeEntry();
		Set<String> keys = store.stores.keySet();
		for (String string : keys) {
			ZipEntry storeE = new ZipEntry("stores/" + string);
			zos.putNextEntry(storeE);
			zos.write(store.stores.get(string));
			zos.closeEntry();
		}
		zos.flush();
		zos.close();
	}
	
	private static byte[] readStream(InputStream is) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[0xFFFF];
		for(int len; (len = is.read(buffer)) != -1;) {
			os.write(buffer, 0, len);
		}
		os.flush();
		return os.toByteArray();
	}
	
	public static DataStore loadStore(File f) {
		try {
			ZipFile zf = new ZipFile(f);
			InputStream idis = zf.getInputStream(new ZipEntry("identifier"));
			String identifier = new String(readStream(idis));
			idis.close();
			Properties info = new Properties();
			InputStream infois = zf.getInputStream(new ZipEntry("info.properties"));
			info.load(infois);
			infois.close();
			Enumeration<? extends ZipEntry> entries = zf.entries();
			HashMap<String, byte[]> map = new HashMap<>();
			while (entries.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) entries.nextElement();
				if(zipEntry.getName().startsWith("stores/") && zipEntry.isDirectory() == false) {
					InputStream mapis = zf.getInputStream(zipEntry);
					map.put(zipEntry.getName().substring(7), readStream(mapis));
					mapis.close();
				}
			}
			zf.close();
			return new DataStore(identifier, info, map);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
