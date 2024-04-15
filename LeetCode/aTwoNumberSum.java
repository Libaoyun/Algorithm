import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
  1. 两数之和
 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 你可以按任意顺序返回答案。
 示例 1：

 输入：nums = [2,7,11,15], target = 9
 输出：[0,1]
 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 示例 2：

 输入：nums = [3,2,4], target = 6
 输出：[1,2]

 tips: 只会存在一个有效答案!!!
 */
public class aTwoNumberSum {

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int[] result = twoSum(nums, 9);
        System.out.println(Arrays.toString(result));
    }

    public static int[] twoSum(int[] nums, int target) {
        /**
         * 1. 暴力法 O(N^2)  O(1)
         */
       /* for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target){
                    // 注意new int[]的初始赋值写法
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};*/

        // 2. 更优解则是使用哈希表直接查询是在当前表中含有target-nums[i]的值，如果存在则返回，不存在则将nums[i]放入表中
//        O(N)  O(N)
        Map<Integer, Integer> hashTable = new HashMap<Integer, Integer>(){};
        for (int i = 0; i < nums.length; i++) {
            // 把nums[i]作为键， 把对应下标作为值，因为获取键的速度更快
            if (hashTable.containsKey(target - nums[i])){
                // 如果把下标作为键，nums[i]作为值，上面就用containsValue，但是这里.get获取的是值而不是键，只能把下标作为键，因为题目要求就是返回下标
                return new int[]{hashTable.get(target - nums[i]), i};
            }
            hashTable.put(nums[i], i);
        }
        return new int[0];
    }
}
