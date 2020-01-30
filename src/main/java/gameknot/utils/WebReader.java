package gameknot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class WebReader {

//	public void directFromURL(String urlStr) throws Exception {
//
//		URL url = new URL(urlStr);
//		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//
//		String inputLine;
//		while ((inputLine = in.readLine()) != null)
//			System.out.println(inputLine);
//		in.close();
//	}

	public static String jsoup(String url) {

		try {
			Thread.sleep(1500);
		
			Document doc = Jsoup.connect("https://gameknot.com" + url)
//				.timeout(3000)
				.get();
		
			return doc.html();
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
		
	}
	
	public String getUrlContents(String theUrl)
	  {
	    StringBuilder content = new StringBuilder();

	    // many of these calls can throw exceptions, so i've just
	    // wrapped them all in one try/catch statement.
	    try
	    {
	      // create a url object
	      URL url = new URL(theUrl);

	      // create a urlconnection object
	      URLConnection urlConnection = url.openConnection();
	      urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

	      // wrap the urlconnection in a bufferedreader
	      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

	      String line;

	      // read from the urlconnection via the bufferedreader
	      while ((line = bufferedReader.readLine()) != null)
	      {
	        content.append(line + "\n");
	      }
	      bufferedReader.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return content.toString();
	  }
}
