import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 438. 找到字符串中所有字母异位词
 * 给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引,不考虑顺序。
 * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）
 * 比如p="abc"那么异位词可以是"acb" "bac" "cab"等等，即可以打乱顺序
 * 示例 1:
 * 输入: s = "cbaebabacd", p = "abc"
 * 输出: [0,6]
 * 解释:
 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
 *  示例 2:
 * 输入: s = "abab", p = "ab"
 * 输出: [0,1,2]
 * 解释:
 * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
 * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
 * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
 */
public class dchFindAnagrams {

    public static void main(String[] args) {
        String s="cbaebabacd";
        String p="abc";
        System.out.println(findAnagrams2(s,p));
    }

    // 实际执行效率貌似方法一更高
    public static List<Integer> findAnagrams(String s, String p) {
        /** O(m + (n-m) * Σ)  O(Σ)  n-m就是for循环的次数，Σ是各种可能组合的的字符数，这里一共就26字母因此Σ=26
         * 方法一：滑动窗口，定义两个数组，分别记录s与p串的频次，其中s需要每次滑动，p串即目标串不动
         * 每次窗口右滑，把左指针指向的字符词频减一，右指针指向的字符词频加一
         * 然后再用两个数组equals方法判断是否仙童，即当前的s串是否与p串字母频次相同，相同就意味着匹配上了异位词
         */
        List<Integer> ans = new ArrayList<>();
        int sLen=s.length();
        int pLen=p.length();

        if(sLen<pLen){
            return ans;
        }
        // TODO 直接对每个字母按字母表都统计词频，这样就不用考虑顺序问题，比如abc，那么三个字母都是一次，
        //建立两个数组存放字符串中字母出现的词频，对p串一次性赋值，并以此作为标准比较
        int [] sCount = new int[26];
        int [] pCount = new int[26];
        for (int i = 0; i < pLen; i++) {
            // 初始先对p串一次性赋值，然后对s串的前pLen也一样初始赋值
            ++sCount[s.charAt(i) - 'a'];
            ++pCount[p.charAt(i) - 'a'];
        }
        // TODO 比如s="cbadeabc"， p="abc"，那么sCount与pCount都是[1,1,1,0,0.....,0]
        if (Arrays.equals(sCount, pCount)){
            // 如果初始的pLen个字符词频都一样，那么肯定匹配了，直接把下标0算进去
            ans.add(0);
        }
        // 左指针只要滑动到sLen - pLen即可，此时就是最后一次
        for (int i = 0; i < sLen - pLen; i++){
            /**
             * 比如说，s="cbadeabc"， p="abc"，此时sCount与pCount都是[1,1,1,0,0.....,0]
             * i==0时，--sCount[s.charAt(i) - 'a']即--sCount[c-a] =[1,1,0,...];
             *      ++sCount[s.charAt(i + pLen) - 'a']即++sCount[1,1,0,1,0...]
             * 其实就相当于每次对滑动窗口移动，把左指针指向的字符词频减一，右指针指向的字符词频加一
             * 然后再判断当前的s串是否与p串字母频次相同，相同就意味着匹配上了异位词
             */
            // TODO 就相当于是每次循环左右指针移动，将两指针之外的字频-1，然后只判断指针中间的是否与p串内容相同
            --sCount[s.charAt(i) - 'a'];
            ++sCount[s.charAt(i + pLen) - 'a'];
            if (Arrays.equals(sCount, pCount)){
                // 这里用i+1是因为这是向右滑动窗口后的下标，0开始的在最前面已经判断过了
                ans.add(i + 1);
            }
        }
        return ans;
    }

    /** O(m + (n-m) * Σ)  O(Σ)
     * 方法2：优化的滑动窗口,利用diff统计当前pLen范围内的s与p的字符不同的有多少，统计差异数量，如果为0表示全匹配上了
     * 每次指针右滑，count[左]--，count[右]++。
     * 如果count[i]为正数表示目前s串的i对应字符与目标串中相比，是多余的，
     * 如果count[i]为负数表示目前s串的i对应字符与目标串中相比，是缺少的，
     * 如果为0表示当前字符的数量刚好与目标串相同，如果当前pLen个字符都0，即diff为0，表示匹配上了
     */
    public static List<Integer> findAnagrams2(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        int sLen=s.length();
        int pLen=p.length();

        if(sLen<pLen){
            return ans;
        }
        //建立两个数组存放字符串中字母出现的词频，并以此作为标准比较
        int[] count = new int[26];

        // 初始赋值，可以理解为count[i]为正数就是目前s串遇到了几次s[i]的这个字符，
        // 如果为负值就可以理解为目前s串还缺几个ch-'a'=i的这个字符；如果为0就是当前字符数量与目标刚好
        for(int i = 0; i < pLen; i++){
            ++count[s.charAt(i) - 'a'];
            --count[p.charAt(i) - 'a'];
        }

        int diff = 0;

        // 统计不同字符的数量，其实只有前pLen个，但是因为p串a-z每个字符都有可能，所以还是要循环26个字符
        for (int i = 0; i < 26; i++) {
            if (count[i] != 0){
                diff++;
            }
        }

        if (diff == 0){
            ans.add(0);
        }

        //开始让窗口进行滑动
        for(int i = 0; i < sLen - pLen; i++){ //i是滑动前的首位
            // 左指针右滑，如果当前i字符对应的数量是1，不是0，代表这个字符不同，目前左指针要右滑，因此直接忽略左边内容
            if (count[s.charAt(i) - 'a'] == 1){
                diff--;
            }
            // 左指针右滑，如果当前i字符对应的数量是0表示之前这个字符用到了，但是目前左指针要右滑，要从后面再重新统计这个，暂时将diff+1
            else if (count[s.charAt(i) - 'a'] == 0){
                diff++;
            }
            // 左指针右滑，将当前左侧字符数量-1
            --count[s.charAt(i) - 'a'];

            // 如果右指针之前为-1，表示之前正好缺少当前这个字符，然后右指针刚好滑到了这个字符，也就意味着匹配上了，因此diff-1
            if (count[s.charAt(i + pLen) - 'a'] == -1){
                diff--;
            }
            // 如果右指针之前为0，表示之前刚好不缺少这个字符，但是现在右指针又遇到了，因此不同数量+1即diff+1
            else if (count[s.charAt(i + pLen) - 'a'] == 0){
                diff++;
            }
            // 右指针右滑，将当前右侧字符数量+1
            ++count[s.charAt(i + pLen) - 'a'];

            // 如果当前diff即不同字符数量为0，表示全匹配上了，放入结果，
            // TODO 注意这里判断的是diff，与方法1的字符串相比较不同，这里时间复杂度就是0(1)了而不是之前的O(26--Σ)
            if (diff == 0){
                ans.add(i + 1);
            }
        }
        return ans;
    }
}
