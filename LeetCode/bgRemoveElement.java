import java.util.Arrays;

/**
 * 27. 移除元素
 * 与前面第26题几乎一样，就是变成了数组移除指定元素，并且数组无序，元素顺序可以改变！
 * 示例 1：
 * 输入：nums = [3,2,2,3], val = 3
 * 输出：2, nums = [2,2]
 * 示例 2：
 * 输入：nums = [0,1,2,2,3,0,4,2], val = 2
 * 输出：5, nums = [0,1,3,0,4]
 * 解释：函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。注意这五个元素可为任意顺序。:
 */
public class bgRemoveElement {

    public static void main(String[] args) {
        int[] nums = {0,1,2,2,3,0,4,2};
        int result = removeElement(nums, 2);
        System.out.println(Arrays.toString(nums));
        System.out.println(result);
    }

    public static int removeElement(int[] nums, int val) {
        /** O(n) O(1)
         * 方法一：双指针，左指针一直指向最后一个有效元素，右指针一直右滑
         * 因为题目说过，只要判断返回后长度的数组内容是排好序的即可，后面的内容不会被检测
         * 因此只要最终nums前k个有效即可。后面不会检测
         */
        if (nums.length == 0 || nums == null){
            return 0;
        }
        int len = nums.length;
        int i = 0;
        int j = 0;
        // i永远用于保存下一个不重复元素，在判断之前还没赋值，因此在判断时用i-1
        while (j < len){
            // 如果当前j遇到了与i上一个不同的元素j，那就给当前i赋值，然后再指向下一个
            if (nums[j] != val){
                nums[i] = nums[j];
                i++;
            }
            j++;
        }
        return i;


        /** O(n) O(1)
         * 方法二： 还是双指针，但是是左指针右滑，右指针左滑，直至相遇,主要是利用了元素顺序可以改动的特点
         * 思路与方法一是一致的，并且时空复杂度也类似
         */
       /* int left = 0, right = nums.length;
        while(left < right){
            if (nums[left]==val){
                nums[left] = nums[right - 1];
                right--;
            }else {
                left++;
            }
        }
        return left;*/
    }
}
