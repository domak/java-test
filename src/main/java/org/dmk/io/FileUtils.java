package org.dmk.io;

import java.io.File;
import java.io.IOException;

public class FileUtils {

	public static void main(String[] args) throws IOException {
		File file = createFile("./toto/titi/tata.csv");
		file.createNewFile();
	}

	public static File createFile(String path) {
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		return file;
	}
}
