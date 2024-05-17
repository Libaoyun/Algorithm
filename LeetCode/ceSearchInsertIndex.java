import java.util.Arrays;

/**
 * 35. 搜索插入位置
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * 请必须使用时间复杂度为 O(log n) 的算法。
 * 示例 1:
 * 输入: nums = [1,3,5,6], target = 5
 * 输出: 2
 * 示例 2:
 * 输入: nums = [1,3,5,6], target = 2
 * 输出: 1
 * 示例 3:
 * 输入: nums = [1,3,5,6], target = 7
 * 输出: 4
 * nums 为 无重复元素 的 升序 排列数组
 */
public class ceSearchInsertIndex {

    public static void main(String[] args) {
        int[] nums = {1,3,5,6};
        int target = 7;
        System.out.println(searchInsert(nums, target));
    }


    /**
     * 方法1： 二分查找
     * 本题说了要求时间复杂度为O(log n)，所以不能用线性遍历，只能用二分查找
     * 其实和正常的二分没有区别，因为二分肯定最终是查找最趋于或者等于目标值的下标，
     * 那么如果二分查找还没有找到目标值，就只需要将最后一次二分的位置mid与target进行对比，如果小于目标值，
     * 比如nums[mid]=4 < 5=target，也就意味着最接近5的值就是4，但是5>4，因此需要插入mid右边即mid+1
     * 反之如果nums[mid]=4 大于等于 5=target,说明mid就应该在当前位置，不管小于还是等于都刚好符合。
     */

    public static int searchInsert(int[] nums, int target) {
        int len = nums.length;
        int left = 0, right = len - 1;
        int mid = 0;

        // 查找左下标（起始下标）
        while(left <= right){
            mid = (left + right) / 2;
            // 在查找左下标时，如果找到了目标值，那么就先记录，作为最新的左下标，然后继续从左边二分找，即right = mid-1;
            if (nums[mid] == target){
                return mid;
            }
            else if (nums[mid] > target){
                right = mid - 1;
            }
            else if (nums[mid] < target){
                left = mid + 1;
            }
        }
        if (nums[mid] < target){
            return mid + 1;
        }else {
            return mid;
        }
    }
}
