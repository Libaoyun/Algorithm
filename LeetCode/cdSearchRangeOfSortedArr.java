import java.util.Arrays;

/**
 * 34. 在排序数组中查找元素的第一个和最后一个位置
 * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
 *
 * 示例 1：
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 * 示例 2：
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 * 示例 3：
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 */
public class cdSearchRangeOfSortedArr {

    public static void main(String[] args) {
        int[] nums = {5,7,7,8,8,10};
        int target = 8;
        System.out.println(Arrays.toString(searchRange(nums, target)));
    }


    /**
     * 方法1： 二分查找
     * 本题说了要求时间复杂度为O(log n)，所以不能用线性遍历，只能用二分查找
     * 可以直接执行两次二分，第一次查找最左侧和目标值相同的下标，第二次查找最右侧和目标值相同的下标
     * 查找与目标值的起始下标时，只需要在二分查找中，找到目标值后记录作为新左下标将right设置为mid-1继续二分
     */
    public static int[] searchRange(int[] nums, int target) {
        int[] arr = new int[2];
        int len = nums.length;
        int leftIndex = -1, rightIndex = -1;
        int left = 0, right = len - 1;

        // 查找左下标（起始下标）
        while(left <= right){
            int mid = (left + right) / 2;
            // 在查找左下标时，如果找到了目标值，那么就先记录，作为最新的左下标，然后继续从左边二分找，即right = mid-1;
            if (nums[mid] == target){
                leftIndex = mid;
                right = mid - 1;
            }
            else if (nums[mid] > target){
                right = mid - 1;
            }
            else if (nums[mid] < target){
                left = mid + 1;
            }
        }

        left = 0;
        right = len - 1;

        // 查找右下标
        while(left <= right){
            int mid = (left + right) / 2;
            if (nums[mid] == target){
                rightIndex = mid;
                left = mid + 1;
            }
            else if (nums[mid] > target){
                right = mid - 1;
            }
            else if (nums[mid] < target){
                left = mid + 1;
            }
        }
        arr[0] = leftIndex;
        arr[1] = rightIndex;
        return arr;
    }
}
