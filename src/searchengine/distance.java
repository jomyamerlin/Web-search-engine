package searchengine;

public class distance {

	public static int editDist(String str1, String str2) {

        int s1 = str1.length();
        int s2 = str2.length();
        
        int[][] cost = new int[s1 + 1][s2 + 1];
        for(int i = 0; i <= s1; i++) {
            cost[i][0] = i;
        }

        for(int i = 1; i <= s2; i++) {
            cost[0][i] = i;
        }

        for(int i = 0; i < s1; i++) {

            for(int j = 0; j < s2; j++) {

                if(str1.charAt(i) == str2.charAt(j))
                    cost[i + 1][j + 1] = cost[i][j];

                else {
                    int c1 = cost[i][j];
                    int c2 = cost[i][j + 1];
                    int c3 = cost[i + 1][j];

                    cost[i + 1][j + 1] = c1 < c2 ? (Math.min(c1, c3)) : (Math.min(c2, c3));
                    cost[i + 1][j + 1]++;
                }
            }
        }
        return cost[s1][s2];
    }
}