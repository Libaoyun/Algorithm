import java.util.ArrayList;
import java.util.List;

/**
 * 22、括号生成
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 * 示例 1：
 * 输入：n = 3
 * 输出：["((()))","(()())","(())()","()(())","()()()"]
 * 示例 2：
 * 输入：n = 1
 * 输出：["()"]
 */
public class bbGenerateParenthesis {

    public static void main(String[] args) {
        int n = 3;
        List<String> result = generateParenthesis(n);
        System.out.println(result.toString());
    }

    public static List<String> generateParenthesis(int n) {
        /**
         * 方法一：回溯法
         * 本题的思路就是比如n=3,那可以是(),也可以是(()...总第一个括号是左括号，那么第二个括号可以是左或者右；
         * 然后同理，如果第二个是左，那么第三个依然可以是左/右，而如果第二个是右，那第三个还是可以左/右，直到左括号数量共有n个
         */

        List<String> result = new ArrayList<String>();
        backTrack(result, new StringBuffer(), 0, 0, n);
        return result;
    }

    public static void backTrack(List<String> result, StringBuffer s, int left, int right, int n) {
        // 如果当前的数量已经是n*2即左右括号都是n了，1就说明这一种情况可以结束了。
        if(s.length() == n*2){
            result.add(s.toString());
            return;
        }
        // 如果左括号数量还没到达目标括号的对数（有n对就一定是有n个左括号与n个右括号）
        // 那就继续往后添加左括号
        if(left < n){
            s.append("(");
            backTrack(result, s, left+1, right, n);
            // 添加完以后，及上面回溯到底执行完后，依次删除s的最后一个字符
            s.deleteCharAt(s.length()-1);
        }
        // 上面的左括号执行并回溯到底过后，再对每种情况尝试右括号，只要当前右括号个数小于左括号，那就可以继续添加右括号
        if (right < left){
            s.append(")");
            backTrack(result, s, left, right+1, n);
            s.deleteCharAt(s.length()-1);
        }
    }


}
