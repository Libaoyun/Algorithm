import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 17、电话号码的字母组合
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 * 比如电话中2可以对应abc,3对应def,9对应wxyz
 * 题目是求所有不同组合：
 * 示例 1：
 * 输入：digits = "23"
 * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * 示例 2：
 * 输入：digits = ""
 * 输出：[]
 * 示例 3：
 * 输入：digits = "2"
 * 输出：["a","b","c"]
 */
public class agLetterCombinations {

    public static void main(String[] args) {

        String digits = "23";
        List<String> res = letterCombinations(digits);
        System.out.println(res.toString());

    }


    public static List<String> letterCombinations(String digits) {
        /** O(3^m* 4^n)  O(m+n)  其中m是map中对应三个字符的数字，n是4个字符的数字
         * 深度与广度优先思路与时空复杂度都一样，
         * 比如23,2是abc，那就将3对应的字符依次往前面追加，就变成ad,ae,af,bd,be,bf,cd....
         * 如果是234，那就再对之前的每个结果再追加4对应的字符即可
         * 注意题目说的是组合，也就是说ad出现了就不能再出现da，因此可以用回溯算法
         * 下面是深度优先搜索代码，每次对从第一个数字到最后一个数字依次遍历出所有情况，比如abc,abd,abe,acd,ace,bcd....这样
         * 相当于每次先把最后一个数字的所有情况遍历。每次遍历都用StringBuffer来暂存情况，然后一轮结束后保存追加到结果中。
         */

        List<String> result = new ArrayList<>();
        if (digits.isEmpty()){
            return result;
        }

        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};

        // 只需一次调用index=0，后续后续会，adgj,adgk.....bdgj....都会遍历到，因为里面index每次都会+1
        backtrack(result, phoneMap, new StringBuffer(), digits, 0);

        return result;
    }

    /**
     * eg: digits = "2345"
     */
    public static void backtrack(List<String> result, Map<Character, String> phoneMap, StringBuffer combination, String digits, int index){
        // 说明某一轮情况已经遍历完了，比如数字是2345，当前是abcd代表这一轮字符已经有四个了，可以存储了，然后开始下一轮abce，此时不能再走else分支，否则会下标越界
        if (index == digits.length()){
            result.add(combination.toString());
            // 注意必须要加if else，如果if执行了即一遍结束了那就不要执行下面代码了。
        }else {
            char digit = digits.charAt(index);
            // 获取数字对应的所有字符比如2=abc
            String letters = phoneMap.get(digit);
            int strLen = letters.length();
            for (int i = 0; i < strLen; i++) {
                // 每次遍历现将当前字符追加到缓存的组合中，然后继续深度遍历下一个数字的情况
                combination.append(letters.charAt(i));
                backtrack(result, phoneMap, combination, digits, index + 1);
                // 在每一种情况结束后，要去除上一种情况的字符，比如abcd结束了，那去除d，因为已经集齐四个字符就意味着当前index肯定是3
                // 那么就删除d，给下一次abce腾出空间。因为是回溯，所以如果abce下一次是acef，那么回溯肯定是按照e->c->b这三个顺序清空缓存
                combination.deleteCharAt(index);
            }
        }

    }
}
