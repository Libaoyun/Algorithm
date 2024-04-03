/**
 * 5、最长回文子串
 * 给你一个字符串 s，找到 s 中最长的回文子串
 * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
 * 示例 1：
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * 示例 2：
 * 输入：s = "cbbd"
 * 输出："bb"
 */
public class eLongestPalindrome {

    public static void main(String[] args) {
        String s = "babad";
        String result = longestPalindrome(s);
        System.out.println(result);

    }

    public static String longestPalindrome(String s) {
        /**
         * 1、中心扩散
         * 每次选取一个字符作为中心，然后向左右扩散，区分奇数偶数两种情况
         */

        if (s.length() < 2){
            return s;
        }
        int len = s.length();
        int maxLen = 1;
        int begin = 0;

        // 还是从0开始，虽然0无法向左扩散，但是不会跳过0-1这个串
        for (int i = 0; i < len; i++) {
            int evenLen = expandAroundCenter(s, i, i);
            int oddLen = expandAroundCenter(s, i, i + 1);
            int curLen = Math.max(oddLen, evenLen);
            if (maxLen < curLen){
                maxLen = curLen;
                // 向下取整是为了避免偶数时是以i,i+1为中心扩散，这里只是选了i，所以左边可能多取一个
                begin = i - (curLen - 1) / 2;
            }
        }
        return s.substring(begin, begin + maxLen);


        /** O(n^2) O(n^2)
         * 2、动态规划
         * 根据回文串的性质，可以知道，如果一个字符串是回文串，那么它的子串也是回文串
         * 假设dp[i][j]表示s[i...j]是否是回文串，那么只需要判断s[i] == s[j] && dp[i+1][j-1]即可，
         * 也就是当前的i和j所指向的字符相等，并且i+1到j-1之间的子串也是回文串，比如要判断2-5，那么3-4必须也是回文串
         * 因此就可以得到动态转换方程：dp[i][j] = s[i] == s[j] && dp[i+1][j-1]
         * 比如判断字符串： b a b a d
         *   i\j   0     1     2     3     4
         *    0  true
         *    1        true
         *    2              true
         *    3                    true
         *    4                          true
         * 对角线就表示当前这一个字符，因为是一个所以肯定是回文，然后优先填写列，然后是行
         * 然后要注意，如果s[0]==s[2]，那么dp[0][2]一定是回文，或者s[0]==s[1]，那么dp[0][1]就直接是回文，
         * 因此又可以得出一个规律：即如果s[i]==s[j]并且j-i<=2，那么dp[i][j]一定是回文
         * 由转换方程dp[i+1][j-1]可知， 显然dp[i][j]与他左下方的元素有关，因此我们要优先从列填充，比如填完dp[0][0]再来dp[1][0]
         */
    /*
        if (s.length() < 2){
            return s;
        }
        int len = s.length();
        int maxLen = 1;
        int begin = 0;
        boolean[][] dp = new boolean[s.length()][s.length()];

        // 对角线处理，单个字符肯定是回文。
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        for (int j = 1; j < len; j++) {
            // 这里只要<=即可，因为ij相等就是对角线已经处理了
            for (int i = 0; i < j ; i++) {
                if (s.charAt(i) != s.charAt(j)){
                    dp[i][j] = false;
                }else {
                    if (j - i < 3){
                        dp[i][j] = true;
                    }else {
                        dp[i][j] = dp[i+1][j-1];
                    }
                }
                if (dp[i][j] && maxLen < j - i + 1){
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
        */
    }


    public static int expandAroundCenter(String s, int left, int right){
        int len = s.length();
        int i = left;
        int j = right;
        while(i >= 0 && j < len){
            if (s.charAt(i) != s.charAt(j)){
                break;
            }else {
                i--;
                j++;
            }
        }
        // 这里-2是因为i和j在最后不符合条件的时候都加了一，而我们要的只是符合条件的i和j
        return j - i + 1 - 2;
    }
}
