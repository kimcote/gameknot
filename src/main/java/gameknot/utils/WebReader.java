package gameknot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class WebReader {

	public void directFromURL(String url) throws Exception {

		URL oracle = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();
	}

	public String jsoup(String url) throws IOException {

		String html = Jsoup.connect(url).get().html();

		System.out.println(html);
		
		return html;
	}
}
