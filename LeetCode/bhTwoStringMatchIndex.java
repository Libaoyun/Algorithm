import utils.CommonUtils;

import java.util.Arrays;

/**
 * 28. 找出字符串中第一个与模式串匹配项的下标
 * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。
 * 如果 needle 不是 haystack 的一部分，则返回  -1 。
 * 示例 1：
 * 输入：haystack = "sadbutsad", needle = "sad"
 * 输出：0
 * 解释："sad" 在下标 0 和 6 处匹配。第一个匹配项的下标是 0 ，所以返回 0 。
 * 示例 2：
 * 输入：haystack = "leetcode", needle = "leeto"
 * 输出：-1
 * 解释："leeto" 没有在 "leetcode" 中出现，所以返回 -1 。
 */
public class bhTwoStringMatchIndex {

    public static void main(String[] args) {
        String haystack = "aabaaabaaac", needle = "aabaaac";
        int result = strStr(haystack, needle);
        System.out.println(result);
    }

    public static int strStr(String haystack, String needle) {
        /** O((m - n) * m)  O(1)  实际运行时间超100%
         * 方法一：朴素解法，最容易想到的暴力解法
         */
        char[] s =  haystack.toCharArray(), p = needle.toCharArray();
        /*int m = haystack.length(), n = needle.length();
        // 转成数组，方便根据下表操作，也可以不转，看自己

        for (int i = 0; i <= m - n; i++) {
            int j = 0;
            // 根据当前的i判断p串是否与s的子串相同，长度为子串的长度
            while(j < n && s[i+j] == p[j]){
                j++;
            }
            if (j == n){
                return i;
            }
        }
        return -1;*/


        /** O(m+n)  O(m)  实际运行时间超39%
         * 方法二：KMP 解法
         */
        if (needle.isEmpty()){
            return 0;
        }
        if (haystack.length() == 1){
            if (needle.equals(haystack)){
                return 0;
            }else {
                return -1;
            }
        }
        int m = haystack.length(), n = needle.length();
        // 先求KMP的next数组
        int[] next = new int[n+1];
        CommonUtils.findNextArray(needle, next);
        System.out.println(Arrays.toString(next));
        // TODO 如果s与p字符串前面各加上一个空格，使数组从1开始，下面的代码就不需要考虑+1还是-1了，直接按正常KMP思路即可
        for (int i = 0, j = 0; i < m; i++) {
            // 如果j=0那就不用求next了直接走下面的判断是否第一位都一样，要么是第一次匹配要么就是后面到next[1]也匹配不上
            // 这个判断目的是找到最靠后的与当前i能匹配的模式串的下标
            while (j > 0 && s[i] != p[j]){
                j = next[j+1]-1; //这里要+1与-1是因为s和p数组从0开始，与next数组从1开始不一样，-1即可
            }
            // 如果当前i与模式串j一致，那就各+1进行下一字符比较
            if (s[i] == p[j]){
                j++;
            }
            if (j == n){
                // 因为这时候i还没有移动因为还没到下个循环自增，但是j却已经+1了，比如 s = "abcd", p = "abc"，此时i=2,j=3
                return i + 1 - j;
            }
        }
        return -1;
    }
}