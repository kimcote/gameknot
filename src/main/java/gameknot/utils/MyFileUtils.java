package gameknot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class MyFileUtils {

	public String myReadFile(String fileName) throws IOException, URISyntaxException {
//		System.out.println(fileName);
//		URI uri=MyFileUtils.class.getClass().getClassLoader().getResource(fileName).toURI();
//		System.out.println("path="+uri.getPath());
//		byte[] data = Files.readAllBytes(Paths.get(uri));
//		return new String(data); 
		
		InputStream inputStream = getClass()
				.getClassLoader().getResourceAsStream(fileName);
		String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
		return text;
	}
	


	public static String readFile(String fileName) throws IOException {
	    File file = new File(fileName);
	    return FileUtils.readFileToString(file);
	}
	
	public List<String> getResourceFiles(String path) throws IOException {
		System.out.println(path);
	    List<String> filenames = new ArrayList<>();

	    try (
	            InputStream in = getResourceAsStream(path);
	            BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
	        String resource;

	        while ((resource = br.readLine()) != null) {
	            filenames.add(resource);
	        }
	    }

	    return filenames;
	}

	private InputStream getResourceAsStream(String resource) {
	    final InputStream in
	            = getContextClassLoader().getResourceAsStream(resource);

	    return in == null ? getClass().getResourceAsStream(resource) : in;
	}
	
	private ClassLoader getContextClassLoader() {
	    return Thread.currentThread().getContextClassLoader();
	}
}
