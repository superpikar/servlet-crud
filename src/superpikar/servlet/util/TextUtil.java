package superpikar.servlet.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;

public class TextUtil {
	/**
	 * Capitalize first letter from a word, not a paragraph/
	 * credit http://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
	 * @param label
	 * @return
	 */
	public static String capitalize(String label) {
		return label.substring(0, 1).toUpperCase() + label.substring(1).toLowerCase();
	}
}
