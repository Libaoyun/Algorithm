import java.util.Arrays;

/**
 * 16、最接近的三数之和
 * 给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
 * 返回这三个数的和。
 * 假定每组输入只存在恰好一个解。
 * 示例 1：
 * 输入：nums = [-1,2,1,-4], target = 1
 * 输出：2
 * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。

  示例 2：
 * 输入：nums = [0,0,0], target = 1
 * 输出：0
 */
public class afThreeSumCloses {

    // TODO: 解法与15题类似，都是排序+双指针
    public static void main(String[] args) {
        int[] nums = {-1,2,1,-4};
        int target = 1;
        int result = threeSumClosest(nums, target);
        System.out.println(result);
    }


    /**
     * O(n^2)  O(logN)
     * 方法1： 排序 + 双指针 ,
     * 先将数组nums排序，然后从头到尾遍历，要求是三数之和，最外层每次遍历当做first即第一个数，然后确认目标值为-first
     */
    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int len = nums.length;
        int result = Integer.MAX_VALUE;
        // -2是因为后面还有两个数，可以优化直接少两次循环
        for (int first = 0; first < len - 2; first++) {
            // TODO: 优化一：和前面第15题三数之和一样，如果当前三个最小的已经比target大了，那后面的数只会更大，因此可以终止
            if (nums[first] + nums[first+1] + nums[first+2] > target){
                if (Math.abs(nums[first] + nums[first+1] + nums[first+2] - target) < Math.abs(result - target)){
                    return nums[first] + nums[first+1] + nums[first+2];
                }
            }

            // TODO: 优化二：同上，如果当前first与最后两个最大的数之和还比target小，那两个指针也不用移动了，只会更小，因此可以跳过当前循环
            if (nums[first] + nums[len-1] + nums[len-2] < target){
                if (Math.abs(nums[first] + nums[len-1] + nums[len-2] - target) < Math.abs(result - target)){
                    result =  nums[first]  + nums[len-1] + nums[len-2];
                    continue;
                }
            }

            // 如果第一个数遇到与之前相同的数，也可以跳过，因为最终的组合肯定还是一样
            if (first > 0 && nums[first] == nums[first - 1]){
                continue;
            }

            int second = first + 1;
            int third = len - 1;
            while(second < third){
                if (second > first + 1 && nums[second] == nums[second - 1]){
                    second++;
                    continue;
                }
                int sum = nums[first] + nums[second] + nums[third];
                if (sum == target){
                    return sum;
                }
                if (Math.abs(sum - target) < Math.abs(result - target)){
                    result = sum;
                }
                // 双指针移动
                if (sum > target){
                    third--;
                }else {
                    second++;
                }
            }
        }
        return result;
    }
}
