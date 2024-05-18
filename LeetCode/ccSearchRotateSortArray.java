/**
 * 33. 搜索旋转排序数组
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 * 本题旋转是指从x位置截断然后[x~n,0~x]这样，例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 * 示例 1：
 * 输入：nums = [4,5,6,7,0,1,2], target = 0
 * 输出：4
 * 示例 2：
 * 输入：nums = [4,5,6,7,0,1,2], target = 3
 * 输出：-1
 * 示例 3：
 * 输入：nums = [1], target = 0
 * 输出：-1
 */
public class ccSearchRotateSortArray {

    public static void main(String[] args) {

        int[] nums= {4,5,6,7,0,1,2};
        int target = 0;
        System.out.println(search(nums, target));
    }

    /** O(n) O(1)
     *  方法1：暴力破解，直接一个for循环找到下标即可，如果没有就返回-1，但是这样不符合题意O(log n)
     *  通过logn就会联想到二分
     */

    /** O(logn) O(1)
     *  方法2：二分查找，平时可能想到二分是在有序数组中查找，但是这道题的数组是经过旋转的，依然基本有序，所以只需要特殊处理
     *  比如旋转后nums=[4,5,6,7,8,0,1],target=0,初始mid=(0+6/2)=3，可以看出4<8即nums[0]<nums[mid]，因此左半有序，
     *  然后判断target是否在左半有序部分即是否大于4小于7，显然不是，那么left=mid+1=4，因为此时left<right,那么继续二分，
     *  此时判断mid=4,显然nums[0]<nums[mid],也就是左半依然有序，但是target还在右半部分，因此left=mid+1=5，继续二分，
     *  此时mid=5，nums[0]>nums[mid]，显然左半无序，那就是右半有序，且target在右半部分，因此可以求出target的下标，
     *  需要注意的是左半和右半判断是否有序必须分别用0和len-1，每次二分仍不变。
     */
    public static int search(int[] nums, int target) {
        int len = nums.length;
        if (len == 0){
            return -1;
        }
        if (len == 1){
            return nums[0] == target ? 0 : -1;
        }
        int left = 0, right = len - 1, mid;
        while (left <= right){
            mid = (left + right) / 2;

            if (target == nums[mid]){
                return mid;
            }

            // mid左半有序，这里从0-mid那么内部也要从[0-mid]区间判断，要么就都left替换0，不影响left和right的继续二分，仅判断是否有序
            // TODO 这里必须要包含等于，因为如果数组长度就2，那么左半就是0-0，肯定是有序，所以要包含
            if (nums[0] <= nums[mid]){
                // mid左半有序 且 如果目标值就在mid左半，这里target <= nums[mid]加不加等于都可以，因为前面最开始就判断了
                // 这里的左端点nums[0]或者nums[left]也都一样符合，前提是上面if是0开始
                if (target >= nums[0] && target < nums[mid]){
                    right = mid - 1;
                }
                // mid左半有序 但 目标值不在mid左半部分，就将left移到mid+1位置，下次从右半找
                else {
                    left = mid + 1;
                }
            }
            // TODO 宗旨就是利用有序的那部分来查找，如果不在有序那一侧，那就往另一侧继续二分，原理和正常的二分一样
            // mid左半无序，即右半有序
            else {
                // mid右半有序 且 如果目标值就在mid右半。这里端点可以是nums[len - 1]也可以是nums[right]都符合
                if (target > nums[mid] && target <= nums[len - 1]){
                    left = mid + 1;
                }
                // mid左半有序 但 目标值不在mid右半部分，就将right移到mid-1位置
                else{
                    right = mid - 1;
                }
            }
        }

        return -1;

        // TODO 也就是说，其实只要是有序分为了两部分，也不影响二分查找，因为要么左边有序要么右边有序，
        //  正常的二分是判断mid是否比目标值大或小，反之调整区间，而这里是判断是否在有序一侧，原理一样。每次二分后还是一样要么左边要么右边
        //  然后如果不在有序的那一边，那就将left或者right进行相应调整继续二分

    }
}
