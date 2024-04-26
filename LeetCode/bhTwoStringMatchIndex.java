import utils.CommonUtils;

import java.util.Arrays;

/**
 * 28. 找出字符串中第一个匹配项的下标
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
        String haystack = "butsad", needle = "sad";
        int result = strStr(haystack, needle);
        System.out.println(result);
    }

    public static int strStr(String haystack, String needle) {
        /** O((m - n) * m)  O(1)
         * 方法一：朴素解法，最容易想到的暴力解法
         */
        /*int m = haystack.length(), n = needle.length();
        // 转成数组，方便根据下表操作，也可以不转，看自己
        char[] s =  haystack.toCharArray(), p = needle.toCharArray();
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


        /** O(m+n)  O(m)
         * 方法二：KMP 解法
         */
        // 先求KMP的next数组
        String p ="ababac";
        int[] next = CommonUtils.KMPNextArray(p);
//        char[] ch = p.toCharArray();
//        int[] next = new int[ch.length+1];
//        next[1] = 0;
//        int i = 1, j = 0;
//        while (i < ch.length){
//            if (j == 0 || ch[i] == ch[j]){
//                next[++i] = ++j;
//            }else {
//                j = next[j];
//            }
//        }
        System.out.println(Arrays.toString(next));
        return 0;
    }
}
