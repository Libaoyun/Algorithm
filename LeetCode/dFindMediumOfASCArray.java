import java.util.Arrays;

/**
  4. 寻找两个正序数组中的位数

 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 *  算法的时间复杂度应该为 O(log (m+n)) 。
 *  示例 1：
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 * 示例 2：
 * 输入：nums1 = [1,2], nums2 = [3,4]
 * 输出：2.50000
 * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
 *
 * 1 3 5 7 9
 * 2 4 6 8 10
 */
public class dFindMediumOfASCArray {

    public static void main(String[] args) {
        int[] nums1 = {1, 3, 5, 7 ,9};
        int[] nums2 = {2, 4, 6, 8, 10};
        double result = findMedianSortedArrays(nums1, nums2);
        System.out.println(result);
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        /**  时间：O(m+n)；空间：O(1)
         * 方法1： 既然只要知道中位数，就只要获得两个数组排序中的中间数即可，因此只要循环到两数组长度一半的位置即可
         * 然后考虑奇偶数情况，以防万一直接再定义一个中位数左边的数，用来将偶数情况合并/2即可
         */
        /*int m = nums1.length;
        int n = nums2.length;
        int len =  m + n;
        int left = -1;
        int right = -1;
        int mIndex = 0;
        int nIndex = 0;
        for (int i = 0; i <= len/2 ; i++) {
            // left就是中位数左边那个数，用来应对偶数的情况
            left = right;
            // 如果nums1数组的当前数小于nums2数组的当前数或者nums1数组已经遍历完了，
            // 那就记录nums1，因为要排序所以肯定记录小的，因为数组本身有序
            if (mIndex < m && (nIndex == n || nums1[mIndex] < nums2[nIndex])){
                right = nums1[mIndex];
                mIndex++;
            }else {
                // 反之就是nums1数组小于nums2数组当前数或者nums1数组已经遍历完了，就直接取nums2的
                right = nums2[nIndex];
                nIndex++;
            }
        }
        if (len % 2 == 1){
            return right;
        }else {
            return (double) (left + right) / 2 ;
        }
*/

        /** 时间复杂度是 O(logmin(m, n)); 空间复杂度O(1)
         * 方法2：官方题解，使用二分查找，使用分割线划分两数组为左右两部分。两边元素分别为(m+n+1)/2个，这里加一是为了让左边数量>=右边元素数量
         * 最终结果就是分割线左边元素的最大值；或者偶数情况就是分割线左边元素的最大值以及右边元素的最小值和除2
         * https://leetcode.cn/problems/median-of-two-sorted-arrays/
         */
        // 先使得数组一的长度是较短的数组，因为如果这样，那么下边数组二的分割线就不会有边界的情况，
        // 如果i=0，那么j一定是一半但是nums2长度大于一半，如果i就是nums1.length，那么j也不会是0因为第一个数组比第二个短
        if (nums1.length > nums2.length){
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }
        int m = nums1.length;
        int n = nums2.length;
        // 分割线左边的元素数量满足
        int totalLeft = (m+n+1)/2;

        // 在[0, m]之间确定分割线，这样下边的分割线也确定了因为i+j=totalLeft
        int left = 0;
        int right = m;
        while (left < right){
            int i = left + (right - left + 1) / 2;
            int j = totalLeft - i;
            // 如果不符合分割线左边元素都要小于分割线右边元素，那就继续二分查找
            // 这里只要判断一个条件而不用再加上或者nums2[i - 1] > nums2[j]是因为两数组本身有序，
            // 而且这里二分查找本身就是为了寻找最合适的两分界线，再加上totalLeft = (m+n+1)/2;目标就是找到最大的符合条件的i,因此分界线只能有一个
            if (nums1[i - 1] > nums2[j]){
                right = i - 1;
            }else {
                left = i;
            }
        }

        int i = left;
        int j = totalLeft - i;
        int nums1LeftMax = i == 0 ? Integer.MIN_VALUE : nums1[i - 1];
        int nums1RightMin = i == m ? Integer.MAX_VALUE : nums1[i];
        int nums2LeftMax = j == 0 ? Integer.MIN_VALUE : nums2[j - 1];
        int nums2RightMin = j == n ? Integer.MAX_VALUE : nums2[j];

        if ((m+n) % 2 == 1){
            return Math.max(nums1LeftMax, nums2LeftMax);
        }else {
            return (double) (Math.max(nums1LeftMax, nums2LeftMax) + Math.min(nums1RightMin, nums2RightMin)) / 2;
        }
    }
}
