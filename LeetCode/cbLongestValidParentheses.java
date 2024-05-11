import java.util.Stack;

/**
 * 32. 最长有效括号
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
 * 示例 1：
 * 输入：s = "(()"
 * 输出：2
 * 解释：最长有效括号子串是 "()"
 * 示例 2：
 * 输入：s = ")()())"
 * 输出：4
 * 解释：最长有效括号子串是 "()()"
 * 示例 3：
 * 输入：s = ""
 * 输出：0
 */
public class cbLongestValidParentheses {

    public static void main(String[] args) {
        String s = ")()())";
        System.out.println(longestValidParentheses4(s));
    }

    /** 26.93%  O(2n), O(n)
     * 方法一：最简单的方式，利用栈判断是否是有效括号，然后利用数组如果是有效括号就标记1，最终统计最长连续1的个数
     */
    public static int longestValidParentheses(String s) {
        // 栈用来匹配有效括号
        Stack<Integer> stack = new Stack<>();
        // arr用来标记有效括号并便于后面统计
        int[] arr = new int[s.length()];
        int count = 0;
        int max = 0;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            // 遇到左括号入栈，但是记录的是下标而不是(，方便对相应位置标记
            if (s.charAt(i) == '('){
                stack.push(i);
            }
            // 否则就是')'但是注意此时栈不能为空，否则肯定匹配不上，说明直接遇到了右括号
            else if (!stack.isEmpty()){
                int top = stack.pop();
                if (s.charAt(top) == '('){
                    // 当前是‘)’，然后栈顶是'('，这样就说明是匹配上了，两个位置都标记
                    arr[i] = 1;
                    arr[top] = 1;
                }
            }
        }
        for (int i = 0; i < len; i++){
            if (arr[i] == 1){
                count = count + 1;
            }else {
                max = Math.max(max, count);
                count = 0;
            }
        }
        // 最后再求一次最大值，避免最后几次arr[i]=1没有统计最大值
        max = Math.max(max, count);
        return max;
    }


    /** 99.92% 73.57%  O(N), O(N)
     * 方法二：动态规划   通过找出特定规律求出状态转移方程
     * 比如 s1 = ")()())"; s2 = ")()(())(";
     * 首先只遇到左括号肯定判断不了也就是直接0，如果遇到了右括号，那么就判断前一个是不是左括号，如果是则直接对右括号给出+2也就是2
     * 同理s2 = ")()(())("的情况每个符号对应的值为0,0,2,0,0,2,6。最后一个dp[6] = 6是因为
     * dp[6-1]+dp[6-dp[6-1]-2] + 2 = 2 + 2 + 2=6。最后一个+2是因为当前是右括号且xx是右括号，那就意味着一定能匹配上，所以+2
     * dp[6-1] + dp[6-dp[6-1]-2]是想叠加前面连续有效的括号，如果dp[6-1]是左括号或者是0，就意味着0+dp[4],这里的dp[4]就是
     * 想看看紧跟在前面的字符是不是有效括号，如果不是0就是有效。如果dp[6-1]也是右括号且有值，那就求出这个右括号的有效长度
     * 然后加上他之前是否也是有效的，-2是因为当前dp[6]这个右括号再减去和他对应的外层左括号，那么再前面一个就能判断是否连续有效
     *
     */
    public static int longestValidParentheses2(String s) {
        int len = s.length();
        int[] dp = new int[len];
        int max = 0;
        for (int i = 1; i < len; i++) {
            // 肯定只会在遇到右括号的时候才操作
            if (s.charAt(i) == ')'){
                if (s.charAt(i-1) == '('){
                    // 如果前一个刚好是左括号，也就说当前这对括号刚好匹配且长度为2，那么再根据i-2是否也有效判断连续然后+2即可
                    dp[i] = (i - 2 >= 0 ? dp[i-2] : 0) + 2;
                }
                // 否则就是当前i是右括号，前一个也是右括号，看看前面一个右括号是否有效,要判断之前有没有对应左括号
                // 只会是xx?())这种情况，因为在这里已经确定了s.charAt(i-1) != '(', 如果或者xx)()))这种就不满足前一个是左括号了
                else if (i - dp[i-1] - 1 >=0 && s.charAt(i - dp[i-1] - 1) == '('){
                    // TODO 都需要先考虑数组下标越界情况
                    /**
                     * 在下标不越界的情况下加上前一个有效的长度以及前前一个是否连续，这两种都要判断是因为目前是))，
                     * 当前i是右括号，需要寻找对应的i-dp[i-1]位也就是对应当前i的左括号，如果匹配那就是2，
                     * 但是这之间内部的可能还有连续有效的，因此需要加上当前i的前一位)的长度也就是内部长度即可。
                     * 那么就很可能会遇到()(())这种情况，前一个只能判断内部，前前一个才能判断外部。()((()))也是一样因为都是只有内外两个
                     */
                    dp[i] = dp[i-1] + (i - dp[i-1] - 2 >=0 ? dp[i - dp[i-1] - 2] : 0) + 2;
                }
                max = Math.max(max, dp[i]);
            }
        }
        return max;
    }


    /** 23.29%   O(N) O(N)
     * 方法3： 优化栈
     * 遇到左括号入栈，遇到右括号出栈，先在栈头放入-1，然后用当前下标减去栈头元素得到长度然后求最大值
     * 比如 s1 = "()())"; s2 = ")()(())(";
     * s1[1]时max=1-(-1)=2，s1[3]时max=3-(-1)=4，
     * s2[0]时会将-1出栈，就变成空栈，然后再将当前的0放入栈头，这样下次出栈时就用i-0，然后求最大长度
     * 这样的好处就是会分隔不连续有效的括号，然后就可以分开求最大长度，从而得到最后的连续有效长度
     */
    public static int longestValidParentheses3(String s) {
        int len = s.length();
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int max = 0;
        for (int i = 0; i < len; i++) {
            // 左括号入栈
            if (s.charAt(i) == '('){
                stack.push(i);
            }
            else {
                // 每遇到右括号先将栈顶出栈
                stack.pop();
                if (stack.isEmpty()){
                    // 如果出栈后就空了，说明已经被右括号分隔成不连续了，就将当前右括号放入栈头，下次遇到匹配的时候就可以求长度了
                    stack.push(i);
                }else {
                    max = Math.max(max, i - stack.peek());
                }
            }
        }
        return max;
    }

    /** 99.92%  95.06% O(N)  O(1)
     * 方法4：正逆向结合法,  没遇到括号记录左/右括号的个数，每次遇到左右个数都相同时x2就是个数，一旦右括号大于左括号那就归0
     * 比如s = "())(())"
     * 循环到1时，左右各为1，那么长度为2，与最大值求个最大值即可。然后循环到2时，右括号数量2大于左括号1，此时要重新置0
     * 然后最后循环到6时，左右括号各为2，长度就为4。
     * 但是还要再走一遍逆向的循环，否则比如s = "()((())"这样最终结果就为2了，因为后面左括号始终比右括号多导致不相等也就不能x2
     * 逆向循环时，同理遇到括号就+1，但是遇到左括号大于右括号时就要置零，和正向相反。第二次循环时参与求解最大值的max依然用之前的
     *
     * 利用的就是左括号和右括号匹配并且右括号数量不能大于左括号这个原理，在这个原理上面不会遇到其他无效括号情况
     * ()((()(比如这种特殊情况，虽然左到右不行，但是再反向来一遍就可以。
     * 如果)))))()这种情况也没问题，右大于左那就都置零直到先遇到左。就像方法3一样一直遇到右括号那就将栈头放入最后一个右括号下标即可
     */
    public static int longestValidParentheses4(String s) {
        int len = s.length();
        int left = 0;
        int right = 0;
        int max = 0;
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) == '('){
                left++;
            }else {
                right++;
            }
            if (right > left){
                left = right = 0;
            }
            if (left == right){
                max = Math.max(max, left * 2);
            }
        }
        left = right = 0;
        // 逆向再来一次
        for (int i = len - 1; i >= 0; i--) {
            if (s.charAt(i) == '('){
                left++;
            }else {
                right++;
            }
            if (left > right){
                left = right = 0;
            }
            if (left == right){
                max = Math.max(max, left * 2);
            }
        }
        return max;
    }
}
