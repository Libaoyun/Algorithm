/**
 * 7、 整数反转
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 * 如果反转后整数超过 32 位的有符号整数的范围 [−2^31,  2^31 − 1] ，就返回 0。
 * 假设环境不允许存储 64 位整数（有符号或无符号）。
 * 示例 1：
     * 输入：x = 123
     * 输出：321
 * 示例 2：
     * 输入：x = -123
     * 输出：-321
 * 示例 3：
     * 输入：x = 120
     * 输出：21
 * 示例 4：
     * 输入：x = 0
     * 输出：0
 */
public class gIntegerConvert {

    public static void main(String[] args) {
        int  x = 123;
//        System.out.println(-123 % 10);
//        System.out.println(String.valueOf(x).re);
        int result = reverse(x);
        System.out.println(result);
    }

    public static int reverse(int x) {
        /**
         * 方法1：O(LogN)，N为x，logN代表位数
         */
        int max = Integer.MAX_VALUE;   // 2147483647
        int min = Integer.MIN_VALUE;   // -2147483648
        int res = 0;
        while(x != 0){
            // 求模取最后一位，负数求模也是负数，比如-123 % 10 = -3，因此无论正负数都可以累计
            int lastNum = x % 10;
            // 判断是否上溢出，切记不能直接用res>max，因为如果这样res已经不能正常展示且会报错，因为已经溢出了，所以要判断最后一位
            if(res > max / 10 || (res == max / 10 && lastNum > max % 10)){
                return 0;
            }
            // 下溢出
            if(res < min / 10 || (res == min / 10 && lastNum < min % 10)){
                return 0;
            }
            res = res * 10 + lastNum;
            x /= 10;
        }
        return res;

        /**
         * 方法2. O(LogN)或者O(1)
         * 也可以直接用String.valueOf(x).reverse()，但是要注意溢出的情况，但是好像没有.reverse()方法
         * 先反转后判断位数是否超过最大值位数，是则return，
         * 然后再判断是否小于最大位数，是的话就可以反转
         * 如果相等，那仍要逐位判断
         */
    };
}
