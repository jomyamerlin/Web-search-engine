package searchengine;

import java.util.Scanner;

public class search {
			
	public static void main(String[] args) throws Exception {

		System.out.println("COMP8547 – Advanced Computing Concepts\nWeb Search Engine Project\n\n");
		System.out.println("Make your selection: ");
		System.out.println("1. Start web crawler\n2. Start search\n");

		Scanner sc = new Scanner(System.in);
		int option = sc.nextInt();

		if (option == 1) {

			webCrawl crawler = new webCrawl();
			Scanner ss = new Scanner(System.in);
			System.out.println("Enter a link:");
			String link = ss.nextLine();
			crawler.getLinks(link);
			convertToText.generateTextFiles();
			System.out.println("");
			searchBoyer.searchFor();
		}

		if (option == 2) {
				searchBoyer.searchFor();
		}

		if (option == 3) {
			convertToText.saveDict();
		}

		if (option == 4) {
			convertToText.generateTextFiles();
		}

		sc.close();
	}
}
