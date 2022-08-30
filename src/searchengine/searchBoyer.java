package searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;


public class searchBoyer {

	public final static String textFilePath = "text/";
	public final static String dictFile = "dict.txt";
	public final static int nchar = 1000000;

	/**
	 *
	 * @throws Exception
	 */
	public static void searchFor() throws Exception {
		searchBoyer websearch = new searchBoyer();
		Hashtable<String, Integer> hashTable = new Hashtable<>();
		Scanner sc = new Scanner(System.in);
		String option = "x";
		while(option.equals("x")) {
			System.out.println("Enter search word: ");
			String searchWord = sc.nextLine();
			int occ;
			int number = 0; 
			try {
				File textfiles = new File(textFilePath);
				File[] fileslist = textfiles.listFiles();
				int i=0;
				while(true) {
					assert fileslist != null;
					if (!(i < fileslist.length)) break;
					occ = websearch.searchWord(fileslist[i], searchWord);
					hashTable.put(fileslist[i].getName(), occ);
					if (occ != 0) {
						number++;
					}
					i++;
				}
				System.out.println("\nTotal no. of files for input " + searchWord + " is : " + number);
				if (number == 0) {
					System.out.println("\nSearch word not found\n");
					convertToText.saveDict();
					websearch.suggestions(searchWord);
				}
				sorting.rankFiles(hashTable, number);
				System.out.println("1. Press 'x' to continue.");
				System.out.println("2. Press 'q' to quit.");
				option = sc.nextLine();	
				if(option.equals("q")) {
					sc.close();
					System.out.println("Quitting project..");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *
	 * @throws IOException
	 */
	public int searchWord(File path, String wordToBeSearched) throws IOException {
		String x1 = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = br.readLine()) != null) {
				x1 = x1 + line;
			}
			br.close();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		char[] txt = x1.toCharArray();
		char[] pat = wordToBeSearched.toCharArray();
		int result = search(txt, pat);
		if (result != 0) {
			System.out.println("\nIn file: " + path.getName());
			System.out.println("----------------------------------------------------------\n");
			System.out.println("----------------------------------------------------------\n");
		}
		return result;
	}
	
	public static void spellCheck(String pattern) throws IOException {
		String filename = dictFile;
		File file = new File(filename);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			ArrayList<String> dictWords = new ArrayList<>();
			String str;
			while((str= br.readLine())!=null) {
				dictWords.add(str);
			}
			int editDistance = 10, editDistanceFirstWord = 10, editDistanceSecondWord = 10;
			int firstSuggestion = 0;
			int secondSuggestion = 0;
			for(int i = 0; i < dictWords.size(); i++){
				String dictionaryWord = dictWords.get(i);
				editDistance = distance.editDist(dictionaryWord, pattern);
					if(editDistance < editDistanceFirstWord) {
						editDistanceFirstWord=editDistance;
						firstSuggestion = i;
					}
			}
			for(int i = 0; i<dictWords.size();i++){
				String dictionaryWord = dictWords.get(i);
				editDistance = distance.editDist(dictionaryWord, pattern);
				if(editDistance < editDistanceSecondWord) {
					editDistanceSecondWord = editDistance;
					if(i != firstSuggestion) secondSuggestion = i;
				}
			}
			System.out.println("Alternate suggestions: "+dictWords.get(firstSuggestion)+" and "+dictWords.get(secondSuggestion));
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void suggestions(String pattern) {
		try {
			spellCheck(pattern);
		}catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}


	static int max (int a, int b) {
		if(a > b) return a;
		return b;
	}
	static void badCharHeuristic(char[] str, int size, int[] badchar) {
		int i;
		for (i = 0; i < nchar; i++)
			badchar[i] = -1;
		for (i = 0; i < size; i++)
			badchar[(int) str[i]] = i;
	}


	static int search(char[] textArray, char[] patternArray) {
		int patternLength = patternArray.length;
		int textLength = textArray.length;
		int count = 0;
		int[] badCharArray = new int[nchar];

		badCharHeuristic(patternArray, patternLength, badCharArray);

		int pos = 0;
		while(pos <= (textLength - patternLength)) {
			int j = patternLength - 1;
			while(j >= 0 && patternArray[j] == textArray[pos+j]) j--;
			if (j < 0) {
				System.out.println("Word found at position: " + pos);
				count++;
				pos += (pos + patternLength < textLength)? patternLength - badCharArray[textArray[pos + patternLength]] : 1;
			}
			else pos += max(1, j - badCharArray[textArray[pos+j]]);
		}
		return count;
	}
}
