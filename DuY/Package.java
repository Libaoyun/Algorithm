import java.util.Arrays;

/**
 * 背包问题就是：给出一堆物品以及物品的重量和价值，再给定一个背包的容量，
 * 问如何选择装入背包的物品，使得在不超过背包容量的前提下，物品的总价值最大。
 */
public class Package {
    public static void main(String[] args) {
        int[] values = {5, 10, 3, 6, 3};
        int[] weights = {2, 5, 1, 4, 3};
        int capacity = 6;
        int result = calculatePackage(values, weights, capacity);
        System.out.println("最大价值为：" + result);
    }

    /**
    eg:   values = {5, 10, 3, 6, 3}    capacity = 6
          weight = {2, 5, 1, 4, 3}
          那么就可以制作出一个5*6矩阵(二维数组，5行6列)，每一行代表第0-row范围的物品，
          dp[row][col]表示在当前行(前0-row范围物品)然后容量col时的最大价值是多少

       row/col   0  1  2  3  4  5  6
         0~0     0  0  5  5  5  5  5   第一行代表只能选0~0也就是第一个物品，然后根据容量与物品重量来确认价值
         0~1     0  0  5  5  5  10 10
         0~2     0  3  5  8  8  10
         0~3
         0~4
        根据上表，第二行开始，col=weight=0时很明显dp[1][0]的值为dp[0][0]因为第二件物品重量5>0，
        那么值也就变成了第一件物品同重量下的值，也就是dp[i-1][0]
        再比如， dp[1][4]，weight=4时，第一件物品容量5依然大于4，因此第一件物品肯定不能选，那范围就又变成了0~0也就是dp[i-1][4]
        同理下面每行每列别的情况也一样，因为第一行能确定，然后就可以根据第一行计算第二行，
        然后再根据第二行计算第三行，很明显当前行只与上一行有关

     然后留意dp[2][2]，虽然2>1,且values[2]=3,但是可以发现前面有个values[0]也符合条件，也就是dp[i-1][2]=5要大于当前的3，
     因此可以得到当前值可以是Max(values[j] , dp[i-1][j]),前提是当前values[j]的重量满足条件的话，否则就直接dp[i-1][j]，
     然后dp[2][3]也要留意，如果取了values[3]=3,但是容量还剩余3-1=2格，因此还可以再加上dp[i-1][2],即dp[i-1][j-weight[j]]
     表示除了当前物品，还可以选择前面的满足剩余重量的物品，
     */
    public static int calculatePackage(int[] values, int[] weights, int capacity){
        int[] line = new int[capacity+1];
        // line就相当于dp[i-1],即上一行的数据
        // 先计算第一行
        for (int i = 0; i <= capacity; i++) {
            if (weights[0] <= i){
                line[i] = values[0];
            }else {
                line[i] = 0;
            }
        }
        System.out.println(Arrays.toString(line));

        // i为行，即物品个数；j为假设当前背包的最大容量；注意i要从1开始了row0已经算过了
        for (int i = 1; i < weights.length; i++) {
            int[] next = new int[capacity+1];
            for (int j = 0; j <= capacity; j++) {
                // 如果当前物品的重量小于当前背包容量，那就利用公式计算
                if (weights[i] <= j){
                    next[j] = Math.max(values[i]+line[j-weights[i]], line[j]);
                }else {
                    // 否则直接等于上一行，也就是等于当前项前面的所有物品的最大价值
                    next[j] = line[j];
                }
            }
            System.out.println(Arrays.toString(next));
            line = next; // 将当前行作为下次计算的上一行即可
        }
        return line[line.length-1];
    }
}

/**
 * 这是动态规划的重要思想，可根据背包问题的不同，写出不同的动态规划代码。
 * 公式为：当前格子：dp[i][j] = max(value[j]+dp[i-1][j-weight[j]] , dp[i-1][j])
 * https://v.douyin.com/iFvWTfro/
 */

