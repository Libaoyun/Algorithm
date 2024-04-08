/**
 * 9、回文数
 * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
 * 回文数
 * 是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * 例如，121 是回文，而 123 不是。
 * 示例 1：
 * 输入：x = 121
 * 输出：true
 * 示例 2：
 * 输入：x = -121
 * 输出：false
 * 解释：从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 */
public class iIsPalindrome {

    public static void main(String[] args) {
        int num = 121;
        Boolean result = isPalindrome(num);
        System.out.println(result);
    }

    public static boolean isPalindrome(int x) {
        // 如果x是负数或者说x最后一位是0但自身不是0，那必然不是回文，因为100的回文数得是001，但显然0不可能开头
        if (x < 0 || x % 10 == 0 && x != 0){
            return false;
        }
        // 原本自己想每次拿头和尾进行比较，但是不合适，因为获取不了头
        // 于是可以考虑将数字折半后对比即可，那如何知道已经到了一半了呢，其实只要拿逆序后的数据判断是否比当前数字大即可
        int reverse = 0;
        int temp = x;
        // 比如12345，t=123,r=54，继续循环,t=12,r=543，很显然不是回文
        while (temp > reverse) {
            reverse = reverse * 10 + temp % 10;
            temp /= 10;
        }
        // 比如12321，最后x=12, reverse=123，
        // 再比如123321，最后x=123,reverse=123，因此是否除10取决于 位数的奇偶
        return  reverse == temp || reverse / 10 == temp;
    }
}
