import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 20、有效的括号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 每个右括号都有一个对应的相同类型的左括号。
 * 示例 1：
 * 输入：s = "()"
 * 输出：true
 * 示例 2：
 * 输入：s = "()[]{}"
 * 输出：true
 * 示例 3：
 * 输入：s = "(]"
 * 输出：false
 */
public class btAvailableBrackets {

    public static void main(String[] args) {
        String s = "()[]{}";
        Boolean result = isValid(s);
        System.out.println(result);

    }

    public static boolean isValid(String s) {
        /** O(n) O(n)
         * 肯定是先遇到左括号然后遇到就近的右括号进行匹配，但是比如{[(，那么肯定最先匹配)才能成功匹配(，也就是说，
         * 左括号后进先出，这就可以利用栈的特性，因此用栈存储左括号，遇到就入栈，遇到右括号则匹配与栈顶的左括号是否是一对
         * 每次遇到右括号，就要找到最近/最后的一个左括号，然后进行配对消除左右括号继续匹配，
         */
        int len = s.length();
        if (len == 0){
            return  true;
        }
        // 如果奇数个，那肯定不能完全匹配
        if (len % 2 == 1){
            return  false;
        }
        Map<Character, Character> pairs = new HashMap<Character, Character>(){{
            put('(', ')');
            put('[', ']');
            put('{', '}');
        }};

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (pairs.containsKey(c)){
                stack.push(c);
            }else {
                if (stack.isEmpty() || c != pairs.get(stack.pop())){
                    return false;
                }
            }

        }
        // 如果栈为空，说明完全匹配
        return stack.isEmpty();
        // 如果还有剩余，表示仍有左括号未匹配完
    }
}
