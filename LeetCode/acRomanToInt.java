import java.util.HashMap;
import java.util.Map;

/**
 * 13、罗马数字转整数
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * ；例如：2=II,3=III，4=IV即5-1，只有在差1的时候才会小的值写在前面
 * I 可以放在 V (5) 和 X (10) 的左边，来表示IV 4 和 IX 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示XL 40 和 CL 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示CD 400 和CM 900。
 *
 * 给定一个罗马数字，将其转换成整数。
 * 示例 1:
 * 输入: s = "III"
 * 输出: 3

 * 示例 2:
 * 输入: s = "MCMXCIV"
 * 输出: 1994
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * 提示：1 <= s <= 3999
 */
public class acRomanToInt {

    public static void main(String[] args) {
        String s = "MCMXCIV";
        int result = romanToInt(s);
        System.out.println(result);
    }
    public static int romanToInt(String s) {
        /** O(N) O(1)   N为s长度，与上一题不一样是因为上一题可以枚举出有限情况，而s是未知的
         * 方法1：模拟，将s从左往右遍历，如果当前字符比下一个字符大，则加上当前字符，否则减去当前字符
            比如：XXV=X+X+V=10+10+5=25
            比如：XIV=X-I+V=10-1+5=14
         */
        // FIXME 可以将hashMap换成最后免得switch，时间和空间效率都会提升！
        Map<Character, Integer> symbols = new HashMap<Character, Integer>(){
            {
                put('I', 1);
                put('V', 5);
                put('X', 10);
                put('L', 50);
                put('C', 100);
                put('D', 500);
                put('M', 1000);
            }
        };
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            int val = symbols.get(s.charAt(i));
            // 如果当前字符对应的值 小于 下一个字符对应的值，那就减
            if (i + 1 < s.length() && val < symbols.get(s.charAt(i + 1))){
                result -= val;
            }else {
                result += val;
            }
        }
        return result;
    }

    // 使用switch可以提升效率
    private int getValue(char ch) {
        switch(ch) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }
}
