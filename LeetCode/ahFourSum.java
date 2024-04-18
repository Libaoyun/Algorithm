import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 18、 四数之和
 * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。
 * 请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] （若两个四元组元素一一对应，则认为两个四元组重复）：
 * a、b、c 和 d 互不相同
 * nums[a] + nums[b] + nums[c] + nums[d] == target
 * 你可以按 任意顺序 返回答案 。

 * 示例 1：
 * 输入：nums = [1,0,-1,0,-2,2], target = 0
 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 * 示例 2：
 * 输入：nums = [2,2,2,2,2], target = 8
 * 输出：[[2,2,2,2]]
 */
public class ahFourSum {

    public static void main(String[] args) {

        int[] nums = new int[] {1,0,-1,0,-2,2};
        int target = 0;
        List<List<Integer>> result = fourSum(nums, target);
        System.out.println(result);
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        /**  O(n^3)  O(log n)
         * 方法：排序 + 双指针   此题与15、16的三数之和方法几乎一样，区别就是多一层循环作为第二个数
         */

        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (nums == null || nums.length < 4){
            return result;
        }
        int len = nums.length;
        Arrays.sort(nums);
        for (int i = 0; i < len - 3; i++) {
            // 还是先注意要转成long类型否则可能会整形溢出，
            // 如果目前最小的四个数都超过了target，那直接break，因为后面只会比现在还大
            if ((long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target){
                break;
            }
            // 如果当前i与后面最大的三个数加起来还小于target，那直接下一个循环，不然当前循环只会更小
            if ((long) nums[i] + nums[len - 1] + nums[len - 2] + nums[len - 3] < target){
                continue;
            }
            // 去重
            if (i > 0 && nums[i] == nums[i - 1]){
                continue;
            }
            for (int j = i + 1; j < len - 2; j++) {
                // TODO 与上面逻辑一样，优化与去重
                if ((long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    // 这里只需要break内层循环，因为可能是i=0,j=3，不能代表i=1,j=2时还是大于target
                    break;
                }
                if ((long) nums[i] + nums[j] + nums[len - 2] + nums[len - 1] < target) {
                    continue;
                }
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                // 双指针
                int left = j + 1, right = len - 1;
                while(left < right){
                    // 去重
                    if (left > j + 1 && nums[left] == nums[left - 1]){
                        left++;
                        continue;
                    }
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target){
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // TODO 下面这几行代码，两个while可以不加，但是left++必须要加，否则可能会死循环，没有left++就没有变化
                        //  至于left++，right--，是因为确保了左右指针的数字都与之前不一样，那如果left与之前不一样了，就意味着
                        //  nums[left]肯定变大了，也就意味着之前的nums[right]+nums[left]肯定是大于target的，因此right--是可以的，
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        left++;
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        right--;

                    }else if (sum < target){
                        left++;
                    }else if (sum > target){
                        right--;
                    }
                }
            }
        }
        return result;
    }
}
