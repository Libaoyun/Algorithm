import java.util.Arrays;

/**
 * 26、 删除有序数组中的重复项
 * 给你一个 非严格递增排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
 * 元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
 * 示例 1：
 * 输入：nums = [1,1,2]
 * 输出：2, nums = [1,2]
 * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
 * 示例 2：
 * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
 * 输出：5, nums = [0,1,2,3,4]
 * 解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。
 */
public class bfRemoveDuplicates {

    public static void main(String[] args) {
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
        int result = removeDuplicates(nums);
        System.out.println(Arrays.toString(nums));
        System.out.println(result);
    }

    public static int removeDuplicates(int[] nums) {
        /** O(n) O(1)
         * 双指针，左指针一直指向最后一个不重复的元素，右指针一直右滑
         *
         * 因为题目说过，只要判断返回后长度的数组内容是排好序的即可，后面的内容不会被检测
         * 因此本题修改后nums为[0, 1, 2, 3, 4, 2, 2, 3, 3, 4]依然是符合的，因为返回5，那么0-4符合即可，后面无视。
         */
        if (nums.length == 0 || nums == null){
            return 0;
        }
        int len = nums.length;
        int i = 1;
        int j = 1;
        // i永远用于保存下一个不重复元素，在判断之前还没赋值，因此在判断时用i-1
        while (j < len){
            // 如果当前j遇到了与i上一个不同的元素j，那就给当前i赋值，然后再指向下一个
            if (nums[j] != nums[i-1]){
                nums[i] = nums[j];
                i++;
            }
            j++;
        }
        return i;
    }

}
