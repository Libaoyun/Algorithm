/**
 * 14、最长公共前缀
 *
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * 如果不存在公共前缀，返回空字符串 ""。

 * 示例 1：
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * 示例 2：
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 * 提示：strs[i] 仅由小写英文字母组成
 */
public class adLongestCommonPrefix {

    public static void main(String[] args) {
        String[] strs = {"flower","flow","flight"};
//        String result = longestCommonPrefix(strs);
        String result = halfWay(strs);
        System.out.println(result);
    }


    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0){
            return "";
        }
        return "";
    }

    // TODO 方法一： 横向扫描，因为是公共前缀，每次求前两个串的公共前缀，然后作为下一个串的第一个参数，继续求公共即可
    // O(mn), 其中m是每个串平均长度，n是数组长度，最坏情况是每个串都会比较完，因此规模为O(mn)，空间复杂度为O(1)
    public static String methods1(String[] strs) {
        if (strs == null || strs.length == 0){
            return "";
        }
        String prefix = strs[0];
        int count =  strs.length;
        for (int i = 1; i < count; i++) {
            // 每次拿prefix与另一个str[i]再求公共前缀
            prefix = lcp1(prefix, strs[i]);
        }
        return prefix;
    }
    public static String lcp1(String str1, String str2) {
        // 求出最短的串长度即可，因为公共部分肯定都要有
        int minLen = Math.min(str1.length(), str2.length());
        int index = 0;
        while (index < minLen){
            if (str1.charAt(index) == str2.charAt(index)){
                index++;
            }else {
                break;
            }
        }
        // 因为最后index多加了1，而substring是左闭右开的区间
        return str1.substring(0, index);
    }


    // TODO 方法二： 纵向扫描，因为是公共前缀，每次直接对比所有串的当前字符，如果相等，则继续，否则返回
    // O(mn), 其中m是每个串平均长度，n是数组长度，最坏情况是每个串都会比较完，因此规模为O(mn)，空间复杂度为O(1)
    public static String methods2(String[] strs) {
        if (strs == null || strs.length == 0){
            return "";
        }
        int len = strs[0].length();
        int count =  strs.length;
        for (int i = 0; i < len; i++) {
            // 每次拿prefix与另一个str[i]再求公共前缀
            for (int j = 0; j < count; j++) {
                // 如果当前索引index已经超过了某个串的长度，或者说当前字符与其他串的当前字符不相同，
                // 那直接返回之前的内容，因为之前是都相同的
                if (i == strs[j].length() || strs[j].charAt(i) != strs[0].charAt(i)){
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }

    // TODO 方法三：分治法，因为满足结合律--LCP(S1,Sn) = LCP(LCP(S1, Smid), LCP(Smid+1, Sn))即每次从中间分为两个子串
    //  然后每次递归直至start与end一致，直到只有一个串，则返回即可。但实际上效率不高，不如前两个方法

    // TODO 方法四：二分查找，先找出所有串中最短的，然后折半，然后比较所有串这一半之前是否一致，如果不一致则继续折半
    //      如果一致则start=mid+1 再继续折半，这样效率会高一些
    public static String halfWay(String[] strs) {
        if (strs == null || strs.length == 0){
            return "";
        }
        // 先求出最短的串的长度
        int minLen = Integer.MAX_VALUE;
        for (String str : strs) {
            minLen = Math.min(minLen, str.length());
        }
        int low = 0, high = minLen;
        while (low < high){
            int mid = (high - low + 1) / 2 + low;
            // 然后判断是否前面都一致
            if (isCommonPrefix(strs, low, mid)){
                low = mid;
            }else {
                high = mid - 1;
            }
        }
        // 因为最后的low与high一致，都是上界已经+1了，因此返回low即可，且low与high都是最短的串的长度，所以肯定不会超出strs[0]
        return strs[0].substring(0, low);
    }

    public static boolean isCommonPrefix(String[] strs, int left, int mid){
        // TODO 注意这里只要取到len长度即可，其实还可以优化，传给这个参数left和right然后只要比较left-right之间的即可，否则前面的其实重复判断了很多次
        String str = strs[0].substring(left, mid);
        // 数组求长度用.length，字符串求长度用.length()，要加括号
        int count = strs.length;
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < count; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != str.charAt(i)){
                    return false;
                }
            }
        }
        return true;
    }

}
