import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 15、三数之和
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]]
 * 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
 * 你返回所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 *  示例 1：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 * 解释：
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
 *  不同  的三元组是 [-1,0,1] 和 [-1,-1,2] 。因为[0, 1, -1]与前面的[-1, 0, 1]重复了，所以不能算
 * 注意，输出的顺序和三元组的顺序并不重要。
 * 其实就只是随便找三个数相加为0的，但是同一个数不能重复出现。然后将所有情况组合成一个大数组即可
 */
public class aeThreeSum {

    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
//        nums = new int[]{0, 0, 0};
        List<List<Integer>> result = threeSum(nums);
        System.out.println(result);
    }


    /** O(n^2)  O(1)
     * 方法1： 排序 + 双指针
     * 先将数组nums排序，然后从头到尾遍历，要求是三数之和，最外层每次遍历当做first即第一个数，然后确认目标值为-first
     * 比如first=5,那么目标值肯定就是-5,因为这样加起来就是0，那么直接去找左支针和右指针second+third=-first即可。
     * 由于数组是排过序的，左指针一定小，右指针一定大；
     * 因此如果left+right<-first，那么left++；如果left+right>-first，那么right--，
     * 为避免出现重复组合，因此在第一次之后每次循环的时候都要比较是否和上个数相同，如果相同则跳过。
     * 比如{-2,-1,-1，0，1}，如果第一次有-1，会得到0，1，那如果第二次还是0，不用看后面结果还是一样
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        int len = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        // 定义每个三元数组的first, second , third，先根据first求target=-first
        // -2是因为后面肯定会跟第二和第三个数，所以不用多余循环
        for (int first = 0; first < len - 2; first++) {
            // TODO 优化点1：因为数组已经升序，所以如果nums[first]+nums[first+1]+nums[first+2]>0，那么后面只会更大，直接终止
            if (nums[first] + nums[first + 1] + nums[first + 2] > 0){
                break;
            }
            // TODO 优化点2：如果nums[first]+nums[n-1]+nums[n-2]<0那second也不用走了，只会更小，直接外层让first右滑变大。
            if (first > 2 && nums[first] + nums[len - 1] + nums[len - 2] < 0){
                continue;
            }

            //如果除了第一个数以外，当前数和前一个数相等，则跳过，避免重复组合
            if (first > 0 && nums[first] == nums[first - 1]){
                continue;
            }
            int target = -nums[first];
            int third = len - 1;
            // 这里其实可以直接second<third作为终止条件
            for (int second = first + 1; second < third; second++) {
                // 同理第二个数也要一样，不能选重复的，但是可以走第一次循环
                if (second > first + 1 && nums[second] == nums[second - 1]){
                    continue;
                }
                // 如果当前的second+third大于target，那就说明目标太大了，第三位数左移即可，因为第三位数是较大的数
                while(third > second && nums[second] + nums[third] > target){
                    --third;
                }

                /**
                 * 如果左右指针相遇，已经不可能再根据second与third的基础上凑出新的可能了。
                 * 如果第二个数与第三个数相遇，说明左指针已经右移够了，右指针也左移够了，所有的情况已经组合过了，再组合就重复了
                 * 因为终止条件就是second>=third，所以不用再判断了
                 * 代码可以看出，虽然第二层循环是second，第三层循环是while，但实际上都是一个循环，
                 * 因为左右指针会逐渐靠拢，实际一共只动了len次，因此，时间复杂度为外层for的n次×内层的n次即n^2
                 * !!!  这里的要求不是要组出所有可能的组合，而是要找出符合-nums[first]的组合，
                 * !!!  由于数组已经有序，所以只要判断是大于target还是小于target，然后相应移动两个指针就可以快速找到符合的组合，
                 * 而不用遍历多余的不可能的组合，比如-2, -1, 0, 1, 2, 4，first是-2，那么second=-1，third=4，此时
                 * 很明显-1+4=3>2(target)，那么直接third--，右指针左移，也就是跳过了(0,4),(1,4),(2,4)这些右边的组合，
                 * 很明显只会越来越大，因此肯定可以跳过。就和之前11题求两根线之间水面积最大的题求证类似。
                 *
                 */
                if (second >= third){
                    break;
                }
                if(nums[second] + nums[third] == target){
                    result.add(Arrays.asList(nums[first], nums[second], nums[third]));
                }
            }
        }
        // [[-1, -1, 2], [-1, 0, 1], [-1, 1, 0], [-1, 2, -1], [0, 1, -1]]
        return result;
    }
}
