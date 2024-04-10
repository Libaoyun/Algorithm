/**
 * 12、 整数转罗马数字
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
 * 给你一个整数，将其转为罗马数字。
 * 示例 1:
 * 输入: num = 3
 * 输出: "III"

 * 示例 2:
 * 输入: num = 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * 提示：1 <= num <= 3999
 */
public class abIntToRoman {

    public static void main(String[] args) {
        int num = 1994;
        String result = intToRoman(num);
        System.out.println(result);
    }

    public static String intToRoman(int num) {
        /** O(1) O(1)
         * 方法1：从大往小枚举各种组合的 边界 情况，然后每次对num减法，直至为0
         * 必然不会出现如IVIV这种重复的，因为IVIV是10，前面一定会经过然后相减，最终一定小于10
         */
        /*String[] symbols  = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] value = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuffer res = new StringBuffer();

        for (int i = 0; i < value.length; i++) {
            int current = value[i];
            // 罗马组合从大到小，每次对当前组合进行判断是否小于数字，如果大于那就保存，减完以后再判断；
            // 如果还大于那继续，直到num的值比当前组合小，那就进入到下一个组合
            while(num >= current){
                res.append(symbols[i]);
                num -= current;
            }
            if (num == 0){
                break;
            }
        }
        // 最后要toString一下，因为原先是StringBuffer
        return res.toString();*/

        /** O(1) O(1)
         * 方法2：已知num(1~3999)，直接枚举出0到3999的情况，每次从大位数往小位数对应匹配即可，直到4位数结束
         */
        // 列举出各位数0-9的各种情况
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds  = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens      = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones      = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        StringBuffer res = new StringBuffer();
        // 取千位数
        res.append(thousands[num/1000]);
        // 取百位数
        res.append(hundreds[num%1000/100]);
        // 取十位数
        res.append(tens[num%100/10]);
        // 取个位数
        res.append(ones[num%10]);
        return res.toString();
    }
}
