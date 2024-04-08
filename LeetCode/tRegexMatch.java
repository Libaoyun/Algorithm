/**
 * 10、 正则表达式匹配(困难)
 * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
     '.' 匹配任意单个字符
     '*' 匹配零个或多个前面的那一个元素
 * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
 * 示例 1：
 * 输入：s = "aa", p = "a"
 * 输出：false
 * 解释："a" 无法匹配 "aa" 整个字符串。除非a*或者.*，这样才可行，否则a只能匹配a
 * 示例 2:
 * 输入：s = "aa", p = "a*"
 * 输出：true
 * 解释：因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
 * 示例 3:
 * 输入：s = "ab", p = ".*"
 * 输出：true
 * 解释：".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
 * s 只包含从 a-z 的小写字母。
 * p 只包含从 a-z 的小写字母，以及字符 . 和 *。
 * 保证每次出现字符 * 时，前面都匹配到有效的字符
 *
 */
public class tRegexMatch {
    public static void main(String[] args) {
        String s = "aa";
        String p = "a*";
        Boolean result = isMatch("abbbbcd", "ab*bq*c.");
        System.out.println(result);
    }

    /**
     * s = abcdefg,   p = abc*de*
     */
    public static boolean isMatch(String s, String p) {
        // eg: s=abcdefg, p=abd*cdq*e.g，结果是true
        // eg: s=abbbbcd, p=ab*q*c. 结果也是true
        /**
         * 使用动态规划方法，dp[i][j]表示s[i]之前字符和p[j]之前的内容是否匹配
         * 1： 如果p[j]不是*，那么dp[i][j] = dp[i-1][j-1] && s[i] == p[j]，
         *    表示当前如果不是*并且当前字符与s[i]与p[j]相同，那就看之前的内容是否也匹配
         * 2: 如果p[j]是*，那要考虑：
         *    2.1: *匹配0个字符的情况：
         *          既可以考虑s[i]与p[j-2]，即是否与星号前的字符匹配；
         *         也可以考虑s[i-1]与p[j]，表示跳过p的*与前一个字符，将s回退一格再匹配是否与p[j]匹配，比如s=abcd, p=abq*cd，
         *    2.2: *匹配1个或多个字符的情况：
         *          考虑s[i-1]与p[j-1]是否匹配，如果匹配，则看s[i-1]
         *          与p[j]是否匹配，如果匹配，则看s[i-1]与p[j-1]是否匹配
         *          如果s[i-1]与p[j-1]不匹配，则看s[i-1]与p[j]是否匹配
         *
         *   综上：p[j]!='*'时，dp[i][j] = dp[i-1][j-1] && s[i] == p[j]
         *   p[j]=='*'时，dp[i][j] = dp[i][j-2] || dp[i-1][j] && s[i] == p[j-1]
         */
        int m = s.length();
        int n = p.length();
        boolean[][] f = new boolean[m + 1][n + 1];
        f[0][0] = true;
        // f[m][n]是结果，是推算出来的
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*'){
                    f[i][j] = f[i][j - 2];
                    // 如果匹配上了，
                    if (matches(s, p, i, j - 1)){
                        // 判断上面是否已经为true，如果是则不变，如果不是再校验f[i - 1][j]
                        f[i][j] = f[i][j] || f[i - 1][j];
                    }
                }else{
                    if (matches(s, p, i, j)){
                        f[i][j] = f[i - 1][j - 1];
                    }
                }
            }
            
        }
        return f[m][n];
    }

    public static boolean matches(String s, String p, int i, int j){
        // 因为要判断的是i - 1,所以如果i == 0，则返回false
        if (i == 0 ){
            return false;
        }
        if (p.charAt(j - 1) == '.'){
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }
}


/**
 * 用大白话翻译一下官方题解：
 * 子串匹配的题目，不由自主的先想DP。。。
 * DP分两步
 * 定义状态
 * 定义状态转移方程
 * 1. 状态
 * dp[i][j]表示s[..i]与p[..j]是否能够匹配。dp[s.size()-1][p.size()-1]即为本题答案。
 * 2. 状态转移
 * 明显能看出状态dp[i][j]和前面的状态有关系，我们逐个条件分析。
 * 2.1 s[i]==p[j] 或者 p[j]=='.'
 * 此时dp[i][j] = dp[i-1][j-1]
 * 2.2 s[i]与p[j]不匹配，并且p[j] != '*'
 * 如果2.1不成立，并且p[j]也不是通配符'*'，那没辙了，直接返回匹配失败。
 * 2.3 s[i]与p[j]虽然不匹配，但是p[j] == '*'
 * 这就要分两种情况了，要看p[j-1]和s[i]是否匹配。
 * 如果p[j-1]和s[i]不匹配，那'*'只能把前元素p[j-1]匹配成0个
 * 如果p[j-1]和s[i]可以匹配，那'*'就可以把前元素p[j-1]匹配成0个，1个，多个。
 */