import java.util.ArrayList;
import java.util.List;

/**
 * 6、Z字形变换
 * 将一个给定字符串根据Z字形的排列方式进行重新排列。
 * eg: 输入s = PAYPALISHIRING, numRows = 3:  输出PAHNAPLSIIGYIR
 *    P   A   H   N
 *    A P L S I I G
 *    Y   I   R
 *
 *    numRows = 4:
 *    P     I     N
 *    A   L S   I G
 *    Y A   H R
 *    P     I
 */
public class fConvertZShapedString {

    public static void main(String[] args) {
        String str = "PAYPALISHIRING";
        int numRows = 3;
        String result = convert(str, numRows);
        System.out.println(result);
    }

    public static String convert(String s, int numRows) {

        /** O(N) O(N)
         * 方法1：利用flag的正负来控制行，然后利用StringBuilder的append方法来拼接字符串
         */
/*

        if (numRows < 2){
            return s;
        }
        List<StringBuilder> rows = new ArrayList<StringBuilder>();
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder());
        }
        int flag = -1;
        int i = 0;
        for (char c: s.toCharArray()){
            // 给第i行的数组追加字符，这个追加就相当于是j+1
            rows.get(i).append(c);
            // 考虑边界情况进行变向
            if (i == 0 || i == numRows - 1){
                flag = -flag;
            }
            i = i + flag;
        }
        // 最后将二维数组顺序按行扁平化一维即可
        StringBuilder res = new StringBuilder();
        for (StringBuilder row: rows){
            res.append(row);
        }
        return res.toString();
*/
        /** O(N) O(1)
         * 方法2. 直接构造
         * 比如s = "PAYPALISHIRING" numRows = 4:
         *    P     I     N
         *    A   L S   I G
         *    Y A   H R
         *    P     I
         可找到规律，竖着数，每次往下到底后在网上到顶，这样的流程就是一个周期，比如P-I之间的就是一个周期，
         而P-I之间一共有2r-2个字符，2r-2是由一下一上原本应该是2r，但是往下的过程中顶和底已经算上了，因此要-2
         得到 t = 2r - 2后，然后就可以得出：
         * 0             0+t                    0+2t                     0+3t
         * 1      t-1    1+t            0+2t-1  1+2t            0+3t-1   1+3t
         * 2  t-2        2+t  0+2t-2            2+2t  0+3t-2             2+3t
         * 3             3+t                    3+2t                     3+3t
         * 然后直接根据周期一行一行的取，每行在每个周期内只有1或者2个字符，1是顶和底的时候。
         * 最后再加上边界条件不要越界，jt + t - i < n即可，jt表示当前是第几次周期，j从0开始
         */

        if (numRows < 2){
            return s;
        }

        StringBuilder res = new StringBuilder();
        int len = s.length();
        int t = 2 * numRows - 2;
        for (int i = 0; i < numRows; i++) {
            // len - i是因为j每次按周期数增加，但是不一定能被整除，因为第一行可能可以凑满一个周期都有值，但是后面的每行可能会有缺
            // 因此要算好 每行最后一个周期 的边界情况
            /**
             *    P     I     N
             *    A   L S   I G
             *    Y A   H R   缺
             *    P     I     缺
             */
            for (int j = 0; j < len - i; j=j+t) {
                res.append(s.charAt(j + i));
                // t - i就是周期内除了顶和底的其他元素的情况，j + t - i表示第j个周期的情况
                if (i != 0 && i < numRows - 1 && j + t - i < len){
                    res.append(s.charAt(j + t - i));
                }
            }
        }
        return res.toString();

    }
}
