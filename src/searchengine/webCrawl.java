package searchengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class webCrawl
{
	int count = 0;
	private final HashSet<String> webLinks;
	private static final int maxLinks = 800;
	public final static String htmlFilePath = "crawler/";

	ArrayList<String> arr = new ArrayList<>();
	
	public webCrawl()
	{
		webLinks = new HashSet<>();
	}
	
	public void getLinks(String myURL)
	{
		int urlCounts = 0;
		try {
			if ((!webLinks.contains(myURL))) {
				if (webLinks.add(myURL)) {
					System.out.println(myURL);
					arr.add(myURL);
					int i = arr.indexOf(myURL);
					String myString = Integer.toString(i);
					saveLinks(myString, myURL);
				}
				Document my_document = Jsoup.connect(myURL).get();
				Elements link_on_page = my_document.select("a[href]");
				for (Element page : link_on_page) {
					if (count != maxLinks) {
						count++;
						getLinks(page.attr("abs:href"));

					}
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void saveLinks(final String filename, final String urlString) {


			try
			{
				URL url = new URL(urlString);
				BufferedReader buf_Reader = new BufferedReader(new InputStreamReader(url.openStream()));
				
				String str = filename + ".html";
		
				BufferedWriter buf_Writer = new BufferedWriter(new FileWriter(htmlFilePath + str));
		
				String line;
				while((line = buf_Reader.readLine()) != null)
				{
					buf_Writer.write(line);
				}
				buf_Reader.close();
				buf_Writer.close();
				System.out.println("Web Crawling successful");
		
			}
	
			catch(MalformedURLException mue) {

				System.out.println("Malformed URL Exception");
			}

			catch(IOException ie) {

				System.out.println("IOException");
			}

	}

	public static void main(String[] asd) {
		new webCrawl().getLinks("https://cbc.ca/");
	}
}