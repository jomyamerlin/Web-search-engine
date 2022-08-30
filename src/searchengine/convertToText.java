package searchengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.TreeMap;

public class convertToText {

	public final static String htmlFilePath = "crawler/";
	public final static String textFilePath = "text/";
	public final static String dictFile = "dict.txt";

	public static void textFileCreator(String fileName) throws IOException {

		File file1 = new File(htmlFilePath + fileName);
		Document doc = Jsoup.parse(file1, "UTF-8");

		String string = doc.text();
		String wotext = fileName.replaceFirst("[.][^.]+$", "");

		PrintWriter out = new PrintWriter(textFilePath + wotext + ".txt");
		out.println(string);
		out.close();
	}
	
	public static void generateTextFiles() throws IOException {

		File folder = new File(htmlFilePath);
		File[] filesList = folder.listFiles();

		for (File file : filesList) {
			if (file.isFile()) {
				textFileCreator(file.getName());
			}
		}
		System.out.println("HTML to Text converted and saved.");
	}

	public static TreeMap<String,String> dictionary(String filename, TreeMap<String,String> wordList) throws Exception {

		File file2 = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(file2));
		String s;
		String[] words;

		while((s = br.readLine()) != null) {
			String tempStr = s.replaceAll("[^a-zA-Z]", "-").toLowerCase();
			words=tempStr.split("-");

			for (String word : words) wordList.put(word, word);
		}

		return wordList;
	}

	public static void saveDict() {

		File directory = new File(textFilePath);
		String[] filesList = directory.list();
		TreeMap<String,String> aListWords = new TreeMap<>();

		for (String str:filesList) {

			String str1 = str.substring(str.lastIndexOf(".")+1);
			if (str1.equals("txt")) {

				try {
					dictionary(textFilePath + "/"+ str,aListWords);
					FileWriter fw = new FileWriter(dictFile);
					for(String s: aListWords.values()) {
						fw.write(s + System.lineSeparator());
					}
					fw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		generateTextFiles();
	}
}
