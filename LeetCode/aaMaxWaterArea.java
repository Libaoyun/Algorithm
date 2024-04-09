/**
 * 11、盛最多水的容器
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 返回容器可以储存的最大水量。
 * 说明：你不能倾斜容器。
 * 其实就是，在n条线中(每条线都有高度)，找出两条线，使其形成的矩形面积是最大的
 * eg:
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 * 示例 2：
 * 输入：height = [1,1]
 * 输出：1
 */
public class aaMaxWaterArea {
    public static void main(String[] args) {
        int[] height = {1,8,6,2,5,4,8,3,7};
        int result = maxArea(height);
        System.out.println(result);
    }

    public static int maxArea(int[] height) {

        // FIXME 这种最简单的方法会超时，因为O(n^2)，这里只是为了便于理解
        /*
        int len = height.length;
        int maxArea = 0;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i+1; j < len; j++) {
                int area = Math.min(height[i],height[j]) * (j-i);
                maxArea = Math.max(maxArea,area);
            }
        }
        return maxArea;*/

        /** O(n) O(1)
         * 官方最优解：两指针法，一个在头，一个在尾，每次移动短的一边，直至两指针重合
         * https://leetcode.cn/problems/container-with-most-water/solutions/11491/container-with-most-water-shuang-zhi-zhen-fa-yi-do/
         在每个状态下，无论长板或短板向中间收窄一格，都会导致水槽 底边宽度 −1变短：
         若向内 移动短板 ，水槽的短板 min(h[i],h[j])可能更新变大，因此下个水槽的面积 可能增大 。
         若向内 移动长板 ，水槽的短板 min(h[i],h[j]不变或变小，因此下个水槽的面积 一定变小 。
         */

        int i = 0, j = height.length - 1, res = 0;
        while(i < j){
            int area = (j - i) * Math.min(height[i], height[j]);
            res = Math.max(res, area);
            if(height[i] < height[j]){
                i++;
            }else j--;
        }
        return res;
    }
    /**
     * 证明过程：
     * 比如当前为S(i, j)，然后此时h[i] < h[j]，即左窗口高度小于右窗口，要求是i++即左窗口右滑变成S(i+1, j)
     * 也就是说，丢失了S(i, j-1/j-2/j-3/...i+1)这些个比较过程，即丢失了左窗口不变但是右窗口左滑的比较过程
     * 但是我们肯定知道：1. 左窗口不变右窗口左滑，那么底边肯定相比S(i,j)的更短
     *               2. 因为面积肯定是取两根线最短的即共有的那个作为高，但是有窗口左滑，不管是右窗口比h[j]高还是低，最短的还是左窗口高度
     *              因此右窗口左滑，要么最短边更短了，要么就还是左窗口那个短边高度，但是更短了也就意味着面积更小，就算一样那底边也短了。
     *              因此证毕，跳过的那些比较过程，全是冗余的，可以忽略。
     */
}
