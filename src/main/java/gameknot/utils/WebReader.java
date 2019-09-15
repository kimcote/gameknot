package gameknot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class WebReader {

	public void directFromURL(String urlStr) throws Exception {

		URL url = new URL(urlStr);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();
	}

	public String jsoup(String url) throws IOException, InterruptedException {

		Thread.sleep(2000);
		Document doc = Jsoup.connect("https://gameknot.com" + url)
				.timeout(2000)
				.get();
		String html = doc.html();

//		System.out.println(html);
		Thread.sleep(1000);
		return html;
	}
}
