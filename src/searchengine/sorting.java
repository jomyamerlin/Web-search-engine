package searchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

class keyComparatorInt implements Comparator {
	@SuppressWarnings("unchecked")
	@Override
	public int compare(Object obj1, Object obj2) {

		Map.Entry<?, Integer> m1 = (Map.Entry<String, Integer>) obj1;
		Map.Entry<?, Integer> m2 = (Map.Entry<String, Integer>) obj2;

		int comp = Integer.compare(m1.getValue(), m2.getValue());
		if (comp < 0) {
			return -1;
		} else if (comp == 0) {
			return 0;
		}

		else return 1;
	}
}

public class sorting {
	@SuppressWarnings("unchecked")
	public static void rankFiles(Hashtable<String, Integer> fileName, int foundNo) {

		ArrayList<Map.Entry<String, Integer>> my_list = new ArrayList<>(fileName.entrySet());
		Collections.sort(my_list, new keyComparatorInt());
		Collections.reverse(my_list);

		if (foundNo != 0) {
			System.out.println("Search results:");
			for(int i = 0; i < 10; i++) {
				Map.Entry<String, Integer> e = (Entry<String, Integer>) my_list.get(i);
				if(e.getValue()!=0)
				{
					System.out.println("The word occurs " + e.getValue() + " times in " + e.getKey());
				}
			}
		} 
		
	}

}