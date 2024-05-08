import java.util.*;

/**
 * 30. 串联所有单词的子串
 * 给定一个字符串 s 和一个字符串数组 words。 words 中所有字符串 长度均相同。
 *  s 中的 串联子串 是指一个包含 words 中所有字符串以任意顺序排列连接起来的子串。
 * 例如，如果 words = ["ab","cd","ef"]， 那么 "abcdef"， "abefcd"，"cdabef"， "cdefab"，"efabcd"， 和 "efcdab" 都是串联子串。
 * "acdbef" 不是串联子串，因为他不是任何 words 排列的连接。
 * 返回所有串联子串在 s 中的开始索引。你可以以 任意顺序 返回答案。
 * 示例 1：
 * 输入：s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
 * 输出：[6,9,12]
 * 解释：因为 words.length == 3 并且 words[i].length == 3，所以串联子串的长度必须为 9。
 * 子串 "foobarthe" 开始位置是 6。它是 words 中以 ["foo","bar","the"] 顺序排列的连接。
 * 子串 "barthefoo" 开始位置是 9。它是 words 中以 ["bar","the","foo"] 顺序排列的连接。
 * 子串 "thefoobar" 开始位置是 12。它是 words 中以 ["the","foo","bar"] 顺序排列的连接。
 *  示例 2：
 *  输入：s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
 * 输出：[]
 * 解释：因为 words.length == 4 并且 words[i].length == 4，所以串联子串的长度必须为 16。
 * s 中没有子串长度为 16 并且等于 words 的任何顺序排列的连接。
 * 所以我们返回一个空数组。
 */
public class czFindSeriesSubstring {

    public static void main(String[] args) {
        String s = "barfoofoobarthefoobarman";
        String[] words = {"bar","foo","the"};
        List<Integer> result = findSubstring(s, words);
        assert result != null;
        System.out.println(result.toString());
    }


    /**
     * 本题思路与 第438.找到字符串中所有字母异位词 的方法二基本一致
     * 这里没有abc-z这样的26个字母，因此是将每wordLen个字符分为一组类比为异位词的一个字母，然后分为wdsLen组类比为一个异位词
     * 这里也将异位词解法diff即不同字符差异数量，换了一种思路，改成了用一个map来存储所有遇到过的wordLen长度单词，
     * 如果是目标单词那就-1，如果是当期遍历的单词，那就+1，如果map<x>=0那就移除，从而diff即map的长度为0时就说明是目标组合单词
     * 重点是替换了之前a字符即0，z字符即25从而的思路，换成了map的同理思路，如果是之前遇到过的字母/单词那就进行判断或者堆叠
     * 如果
     */
    public static List<Integer> findSubstring(String s, String[] words) {
        ArrayList<Integer> result = new ArrayList<>();
        int wordLen = words[0].length();
        int wdsLen = words.length;
        int sLen = s.length();

        // 比如一个单词长度是3，那就0-2(3-5...),1-3(4-6...),2-4(5-7...)这样就可以遍历出所有s串字符组成单词情况
        for (int i = 0; i < wordLen; i++) {
            // 如果当前s串长度不够wordLen * wdsLen也就是words的整个长度，那就只能退出
            if (i + wordLen * wdsLen > sLen){
                break;
            }
            Map<String, Integer> diff = new HashMap<>();
            // 先将s串遇到的前wdsLen个字符组成的单词+1
            for (int j = 0; j < wdsLen; j++) {
                // TODO 将每一个单词作为438题异位词的一个字母，思路一致
                String word = s.substring(i + j * wordLen, i + (j + 1) * wordLen);
                // 当前遇到的+1，因为map没有默认值于是这边手动设置0默认值
                diff.put(word, diff.getOrDefault(word, 0) + 1);
            }
            // 再将words串遇到的目标单词-1
            for (String word : words){
                diff.put(word, diff.getOrDefault(word, 0) - 1);
                // 如果为0就说明刚好遇到了目标单词，就移除，因为这样方便diff为0时就可以认为全是目标组合
                if (diff.get(word) == 0){
                    diff.remove(word);
                }
            }
            if (diff.isEmpty()){
                result.add(i);
            }
            /**
             * 注意下面这个循环和之前438题不一样，这里为了避免下标越界，实际用的是先定位到当前单词，然后再滑动(也就是加减)
             * 从而最终result也只要用start而不是start+1。仔细看这里start直接从第二个单词作为起始位置，
             * 因为第一个前面已经在最初赋值时候就完成判断了，然后从第二个开始，再进行滑动上一轮的指针，
             * 这样在滑动右指针时就只要(start+wordLen*wdsLen - wordLen, start+wordLen*wdsLen)
             * 而不是s.substring(start+wordLen*wdsLen, start+wordLen*wdsLen + wordLen)这样容易越界了
             * 这样就可以先在for循环里面start+=wordLen判断通过再继续执行，避免越界
             */
            // 这里+1是因为i是当前单词的起始位置，前面说的已经提前定位好了，所以要+1。然后每次循环多跳过一个单词长度作为起始位置
            for (int start = i + wordLen; start < sLen - wordLen * wdsLen + 1 ; start+=wordLen) {
                // 窗口右滑： 左指针右滑：
                String word = s.substring(start - wordLen, start);
                diff.put(word, diff.getOrDefault(word, 0) - 1);
                if (diff.get(word) == 0){
                    diff.remove(word);
                }

                // 窗口右滑： 右指针右滑,右指针是整个words的字符长度+start：
                word = s.substring(start+wordLen*wdsLen - wordLen, start+wordLen*wdsLen);
                diff.put(word, diff.getOrDefault(word, 0) + 1);
                if (diff.get(word) == 0){
                    diff.remove(word);
                }

                if (diff.isEmpty()){
                    result.add(start);
                }
            }

        }

        return result;
    }
}
