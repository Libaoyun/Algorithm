import java.util.Arrays;

/**
 * 31. 下一个排列
 * 整数数组的一个 排列 就是将其所有成员以序列或线性顺序排列。
 * 比如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1]... 。
 * 下一个排列就是指按字典顺序，比当前排列更大的下一个排列，比如[1,2,3] 的下一个更大排列是[1,3,2],
 * 因为在所有由123组成的排列中，123的下一种更大排列是132。如果当前排列是321，没有更大排列，那就重排成最小排列123
 * 示例 1：
 * 输入：nums = [1,2,3]
 * 输出：[1,3,2]
 * 示例 2：
 * 输入：nums = [3,2,1]
 * 输出：[1,2,3]
 * 示例 3：
 * 输入：nums = [1,1,5]
 * 输出：[1,5,1]
 * 注意： 要求原地修改数组！！！
 */
public class caNextPermutation {

    public static void main(String[] args) {
        int[] arr = {2,3,1};
        nextPermutation(arr);
        System.out.println(Arrays.toString(arr));
    }


    /**
     * 思路：以12385764为例，很明显结果是12386457,
     * 因为最后的764是 降序无法再变大 ，如果要找下一个排列，肯定是在前面 升序 的57做文章，想办法让5764变大
     * 这时候从右往前找到第一个比5大的是6，然后交换，得到6754，现已经确定6的位置替换之前5，因此6后面重新升序排列，就是最终结果6457
     * 然后拼接后12386457。
     * 从右往前找第一个比5大的数是因为末尾原先已经是降序，所以找到的第一个肯定是最符合要求的刚好更大的数
     */
    public static void nextPermutation(int[] nums) {
        int len = nums.length;
        // 第一步，从右往前找第一对升序的两个数(i,j)，那么j~end之间的数肯定是降序，比如21342,34是升序，后面的42肯定是降序
        // 因为不是升序就是降序，如果从右往左找第一个升序，那么右边一定是降序或者末尾。
        for (int i = len - 1; i > 0 ; i--) {
            if (nums[i-1] < nums[i]){
                // 第二步，从右往前找第一个比nums[i-1]大的数，交换，然后升序排列
                for (int j = len - 1; j > i - 1; j--) {
                    if (nums[j] > nums[i - 1]){
                        // 交换 swap
                        int temp = nums[i - 1];
                        nums[i - 1] = nums[j];
                        nums[j] = temp;
                        /**
                         * 交换过后，后面依然是降序，因为找的是从右往左第一个大于[i-1]的数，意味着最右边跳过的都小于[i-1]
                         * 又因为原先右边是降序，因此交换后的i-1放入右边后依然成降序排列，
                         * 交换的位置左边的依然比i-1大，右边的依然比i-1小
                         */
                        // 排序  翻转
                        // TODO 优化，因为已知后面是降序，因此直接倒序即可，而不用重新排序，这样运行效率超过100%
                        reverse(nums, i);
//                        Arrays.sort(nums, i, len);
                        return ;
                    }
                }
            }
        }
        // 如果前面没有执行，说明当前数组已经是类似于[3,2,1]这种降序且最后一种排列了，因此直接按题意重新生序排序即可
        reverse(nums, 0);
//        Arrays.sort(nums);
    }

    public static void reverse(int[] nums, int start) {
        int left = start, right = nums.length - 1;
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

}
