/**
 * 29、两数相除
 * 给出被除数与除数，要求 不使用 乘法、除法和取余运算。并且对小数进行截断
 * 示例 1:
 * 输入: dividend = 10, divisor = 3
 * 输出: 3
 * 解释: 10/3 = 3.33333.. ，向零截断后得到 3 。
 * 示例 2:
 * 输入: dividend = 7, divisor = -3
 * 输出: -2
 * 解释: 7/-3 = -2.33333.. ，向零截断后得到 -2 。
 */
public class biTwoNumberDivide {

    public static void main(String[] args) {
        int dividend = -2147483648;
        int divisor = 2;
        /**
         * dividend = -2147483648 -1;  // 最小数再减后会变成最大数减，比如-2147483648 -1 = 2147483647，
         * -2147483648 -2 = 2147483646
         *  然而 -2147483648 + 1 = -2147483647是正常的，如果超出最小值那会用正数表示
         *  System.out.println(dividend);
         */
        System.out.println(divide(dividend, divisor));
    }


    public static int divide(int dividend, int divisor) {

        /**
         * 方法1：如果暴力解法，也就是while(dividend > divisor){count++;dividend -= divisor;}
         * 这种方法虽然最简单，但是会超时，而且时间复杂度最大会是Integer.MAX_VALUE，即(max/1)所以不能用
         */

        /**
         * 方法2：每次循环的时候除数做移位运算相当于乘法,即每次除数多x2，然后每次的count结果也相应x2，直到dividend小于divisor，
         * 然后再递归调用，将dividend小于divisor之前的dividend再次进行递归，返回count+div(newdividend, newdivisor)
         */
        if (dividend == 0) return 0;
        if (divisor==1){
            return dividend;
        }
        // TODO 如果除数或者被除数为一正一负，那么最终结果肯定是负数
        if (divisor==-1){
            // 如果被除数不是最小的负数值，那就正常返回dividend，加负号是因为divisor==-1，这样不管dividend是正负数都能正常返回
            if (dividend>Integer.MIN_VALUE) return -dividend;
            // 如果被除数是最小的负数值，那就返回最大正数，因为负负得正，但是正数最大值只能到Integer.MAX_VALUE。
            return Integer.MAX_VALUE;
            // 如果超出最小值那会用最大正数表示;
            // 而最小值是Integer.MIN_VALUE要比最大值能多表示一个值，但是int类型能表示最大值只能是Integer.MAX_VALUE
        }
        int a = dividend, b = divisor;
        int sign = (a > 0 && b < 0) || (a < 0 && b > 0) ? -1 : 1;
        // TODO 这里转成负数是因为负数最小值可以比正数多表示一个数
        a = a > 0 ? -a : a;
        b = b > 0 ? -b : b;
        int res = div(a, b);
//        if (sign > 0) return res;
        return sign * res;
    }

    public static int div(int number, int divisor){
        // 如果已经比单个除数小了，那就直接返回，因为count无法加一了
        if (number > divisor) return 0;
        int count = 1;
        int a = number, b = divisor;
        // TODO 这里终止条件的时候一定就要判断是否大于b + b，否则只判断b的话count就会多x2，然后由于目前都是负数所以判断相反
        // 如果(b + b) > 0表示已经溢出了，不这样判断就会死循环，因为负溢出后会展示正数，那么这个循环会一直进行
        while(a <= (b + b) && (b + b) < 0){
            // 下面这两句就和左移运算一样,相当于x2
            b = b + b;   // b = b << 1
            count = count + count;  // count = count << 1
        }
        // 然后将剩下的不能被当前count*2除的数再次递归调用
        return count + div(a - b, divisor);
    }

    /**
     * 还可以选择二分查找，从0~整形最大值对
     */
}

/** 不进行转换负数的比较比较好理解，代码如下：
    public static int divide(int dividend, int divisor) {
        if (dividend == 0) return 0;
        if (divisor==1){
            return dividend;
        }
        // TODO 如果除数或者被除数为一正一负，那么最终结果肯定是负数
        if (divisor==-1){
            // 如果被除数不是最小的负数值，那就正常返回dividend，加负号是因为divisor==-1，这样不管dividend是正负数都能正常返回
            if (dividend>Integer.MIN_VALUE) return -dividend;
            // 如果被除数是最小的负数值，那就返回最大正数，因为负负得正，但是正数最大值只能到Integer.MAX_VALUE。
            return Integer.MAX_VALUE;
            // 而最小值是Integer.MIN_VALUE要比最大值能多表示一个值，但是int类型能表示最大值只能是Integer.MAX_VALUE
        }
        int a = dividend, b = divisor;
        int sign = (a > 0 && b < 0) || (a < 0 && b > 0) ? -1 : 1;
        a = a > 0 ? a : -a;
        b = b > 0 ? b : -b;
        int res = div(a, b);
        return sign * res;
    }

    public static int div(int number, int divisor){
        // 如果已经比单个除数小了，那就直接返回，因为count无法加一了
        if (number < divisor) return 0;
        int count = 1;
        int a = number, b = divisor;
        // TODO 这里终止条件的时候一定就要判断是否大于b + b，否则只判断b的话count就会多x2
        while(a >= (b + b)){
            // 下面这两句就和左移运算一样,相当于x2
            b = b + b;   // b = b << 1
            count = count + count;  // count = count << 1
        }
        // 然后将剩下的不能被当前count*2除的数再次递归调用
        return count + div(a - b, divisor);
    }
 */

// 本题官方题解反倒不合适，目标是想通过二分查找，找到最大的z使得z*y <= x <= (z+1)*y，
// 然后这个z的取值范围是[0,x]，所以二分查找的终止条件是x<y，因为负数可以表示的范围更大，所以除数和被除数就转成了负数：
// 然后目标就变成了z*y >= x >= (z+1)*y，然后求得的z就肯定是正数，z属于[1,maxInt]
// 每次对z进行折半(二分)查找，z∈[1,maxInt]，然后逐渐缩小，然后用一个自定义方法快速乘，求当前是否z*y >= x >= (z+1)*y
// 但其实多此一举，每次快速乘还需要消耗时间，不如直接像方法二一样，直接每次divisor*2，count*2,直到大于被除数，然后减去被除数，
// 这样外层最多31次，然后内部再算。无需多少时间消耗

