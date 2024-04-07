import java.util.HashMap;
import java.util.Map;

/**
 * 8、字符串转换整数(atoi)
 * 请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
 * 函数 myAtoi(string s) 的算法如下：
 * 读入字符并丢弃非数字字符，已知数字字符是连续的，直到遇到非数字字符才终止。在数字之前可能会出现正负号，否则默认为正数。
 * 数字前置的符号只可能是正号、负号或空格，数字也可能是0开头。
 * 输入：s = "   -42"
 * 输出：-42
 * 解释：
 * 第 1 步："___-42"（读入前导空格，但忽视掉）
 * ^
 * 第 2 步："   -42"（读入 '-' 字符，所以结果应该是负数）
 * ^
 * 第 3 步："   -42"（读入 "42"）
 * ^
 * 解析得到整数 -42 。
 * 由于 "-42" 在范围 [-231, 231 - 1] 内，最终结果为 -42 。
 */
public class hStringToNumber {

    public static void main(String[] args) {
        String s = "   -42";
        int result = myAtoi(s);
        System.out.println(result);
    }


    public static int myAtoi(String s) {
        /**
         * 方法1：O(N), O(1)
         */
        int res = 0;
        int sign = 1;
        int len = s.length();
        int i = 0;
        // 读出前置空格
        while(i < len && s.charAt(i) == ' '){
            i++;
        }
        if (i < len && s.charAt(i) == '+'){
            i++;
        } else if (i < len && s.charAt(i) == '-') {
            sign = -1;
            i++;
        }
        // 数字前只能是前置空格
        if (i < len && !Character.isDigit(s.charAt(i))){
            return 0;
        }
        for (; i < len; i++) {
            if (Character.isDigit(s.charAt(i))){
                int num = s.charAt(i) - '0';
                if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && num > Integer.MAX_VALUE % 10)){
                    return Integer.MAX_VALUE;
                }else if (res < Integer.MIN_VALUE / 10 || res == Integer.MIN_VALUE / 10 && -num < Integer.MIN_VALUE % 10){
                    return Integer.MIN_VALUE;
                }
                // 注意这里符号的使用，用在数字上这样就可以正数相加，负数相减了
                res = res * 10 + num * sign;
            }else break;
        }
        return res;

        /**
         * 方法2：(官方题解)自动状态机，定制一个状态表后，每次取出一个字符进行状态判断
         * 自动状态机：一共有四个状态:start,sign,number,end
         * 在每一个状态下，根据当前字符进行判断，然后更新状态
         * 比如当前状态是start，当前字符是空格，则继续,仍是start状态，当前字符是符号，则进入sign状态，当前字符是数字，则进入number状态，当前字符是结束，则直接结束
         * 如果当前状态是sign，当前字符是空格，直接跳出，如果当前仍是sign即正负号，那也跳出，因为不可能有两个符号，如果是数字继续，其他字符也跳出
         * 如果当前状态是number，当前字符是空格，则跳出，如果当前是数字，则继续，其他字符跳出
         * 如果当前状态是end，直接结束。
         * https://leetcode.cn/problems/string-to-integer-atoi/solutions/183164/zi-fu-chuan-zhuan-huan-zheng-shu-atoi-by-leetcode-/
         */

      /*  Automaton automaton = new Automaton();
        int length = s.length();
        for (int i = 0; i < length; ++i) {
            automaton.get(s.charAt(i));
        }
        return (int) (automaton.sign * automaton.ans);*/

    }

    //定义状态机类
    static class Automaton {
        public int sign = 1;
        public long ans = 0;
        private String state = "start";
        // 根据前面解析放状态表信息
        private final Map<String, String[]> table = new HashMap<String, String[]>() {{
            put("start", new String[]{"start", "signed", "in_number", "end"});
            put("signed", new String[]{"end", "end", "in_number", "end"});
            put("in_number", new String[]{"end", "end", "in_number", "end"});
            put("end", new String[]{"end", "end", "end", "end"});
        }};

        public void get(char c) {
            // 获取当前状态情况下当前字符对应的状态并更新当前状态
            state = table.get(state)[get_col(c)];
            // 当前状态必须是正常流程的in_number才能存数字
            if ("in_number".equals(state)) {
                ans = ans * 10 + c - '0';
                ans = sign == 1 ? Math.min(ans, (long) Integer.MAX_VALUE) : Math.min(ans, -(long) Integer.MIN_VALUE);
            } else if ("signed".equals(state)) {
                // 或者如果是正常流程的符号，那也存储
                sign = c == '+' ? 1 : -1;
            }
        }

        // TODO 这就是用来获取每个字符对应的状态的
        private int get_col(char c) {
            if (c == ' ') {
                return 0;
            }
            if (c == '+' || c == '-') {
                return 1;
            }
            if (Character.isDigit(c)) {
                return 2;
            }
            return 3;
        }
    }
}
