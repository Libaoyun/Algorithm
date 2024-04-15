import java.util.HashSet;
import java.util.Set;

/**
 * No.3 无重复字符的最长子串
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串的长度。
 * eg:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 */
public class cNoDuplicateLangSubstring {

    public static void main(String[] args) {
        String s = "abcabcbb";
        int length = lengthOfLongestSubstring(s);
        System.out.println(length);
    }
    public static int lengthOfLongestSubstring(String s) {
        /** O(n^2) O(1)
         *  1、自写方法： 滑动窗口，如果0-2不重复，但是0-3重复，虽然3肯定与之前重复，但也可以确定出1-2肯定是不重复的，
         *  因此可以直接判断1-3是否重复，如果重复，则2-3，直到不重复为止，然后判断2-4是否重复，
         *  因此我这里的j也就是右窗口在遇到重复时是不会回退的，可以提高效率
         */
        int len = s.length();
        if (s.isEmpty()){
            return 0;
        }
        int maxLen = 1;
        int j = 0;

        for (int i = 0; i < len; i++) {
            if (j < i){
                j = i + 1;
            }
            // 因为substring是[ )，左闭又开,substring(0, 1)就相当于是s.charAt(0)
            // 这里是比较0 ~ j-1的子串中，是否含有j这个字符，如果有就重复了。
            while(j < len && !s.substring(i,j).contains(s.charAt(j)+"")){
                maxLen = Math.max(maxLen, j-i+1);
                j++;
            }
        }
        return maxLen;

/* 上述substring会占用多余空间，contains会占用多余时间，因此可以利用hashSet，优化后代码：
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // 如果集合中没有字符 j，则将其添加到集合中并移动右指针
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            }
            // 如果集合中已经有字符 j，则移除字符 i 并移动左指针
            else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
*/

        /** O(n^2) O(min(m, n))
         * 2、官方解，与上面思路一致，只是使用了哈希集合来记录是否存在重复字符
 *          // 哈希集合，记录每个字符是否出现过,每次集合移除最前面的字符，就相当于i++
 *          Set<Character> occ = new HashSet<Character>();
 *          int n = s.length();
 *          // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
 *          int rk = -1, ans = 0;
 *          for (int i = 0; i < n; ++i) {
 *          if (i != 0) {
 *          // 左指针向右移动一格，移除一个字符
 *          occ.remove(s.charAt(i - 1));
 *          }
 *          while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
 *          // 不断地移动右指针
 *          occ.add(s.charAt(rk + 1));
 *          ++rk;
 *          }
 *          // 第 i 到 rk 个字符是一个极长的无重复字符子串
 *          ans = Math.max(ans, rk - i + 1);
 *          }
 *          return ans;

         */
    }
}

/**
 * 两者区别：
 * 你的代码和官方题解的主要区别在于如何检查子串中是否有重复字符。你的代码使用了substring和contains方法，
 * 而官方题解使用了哈希集合（HashSet）来存储已经出现过的字符。
 * 在大多数情况下，使用哈希集合的方法会比使用substring和contains方法更高效。
  因为substring和contains方法的时间复杂度都是O(n)，而哈希集合的插入、删除和查找操作的平均时间复杂度都是O(1)。
 * 所以，如果你的字符串长度很大，那么使用哈希集合的方法应该会更快。
 */


/**
 * 最优解：
 * 利用hashMap的put方法来添加或者更新键也就是字符的最新下标，从而可以跳过重复字符，
 *  输入: s = "abcabcbb"  "abba"
 *  输出: 3
 * public int lengthOfLongestSubstring(String s) {
 *         HashMap<Character, Integer> map = new HashMap<>();
 *         int max = 0, start = 0;
 *         for (int end = 0; end < s.length(); end++) {
 *             char ch = s.charAt(end);
 *             if (map.containsKey(ch)){
 *                // 遇到重复字符更新start，用max的原因：带入abba执行到最后一个a就知道了，start2,而get却是0+1=1,会导致输出3
 *                // 目的是确保 start 指向的是重复字符最近的上一次出现位置的下一个位置，从而维护一个无重复的子串。
 *                 start = Math.max(map.get(ch)+1,start);
 *             }
 *             max = Math.max(max,end - start + 1);
 *             map.put(ch,end);
 *         }
 *         return max;
 *         // 比如abcabcb执行到abcbb的时候，abc遇到了下一个b后，直接跳到了cb(b)，相当于跳过了ab的b，因此优化在这
 *     }
 */