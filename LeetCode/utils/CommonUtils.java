package utils;

import java.util.Arrays;

/**
 *
 */


public class CommonUtils {

    /**
     * 求KMP算法的next[]数组
     * 参考（视频的14:20图解）：
     * https://www.bilibili.com/video/BV16X4y137qw/?spm_id_from=333.1007.top_right_bar_window_history.content.click&vd_source=3b3734cd7b7d164cd4be3d0c760ac7f0
     * 视频讲解的next是下标从1开始，值从0开始，即next[1]=0,next[2]=1.....
     * 比如模式串在求解17个字符，这就意味着已知next[16]的值，假设next[16]=8，因为8=7+1，意味着第16个字符之的7歌个前后缀都一致的
     * 即模式串：1~7 = 9~15，那么只需要判断ch[16]是否等于ch[8]，如果相等，那么next[17]=next[16]+1=8+1=9
     * 如果ch[16]≠ch[8]，然后假设next[8]=4，那就又意味着4=3+1,即模式串的1~3 = 5~7，这时候只需要判断ch[4]是否等于ch[16],
     * 原理是，如果1~7 = 9~15,又有1~3 = 5~7，那么肯定有1~3 = 13~15,也就是说如果ch[4]=ch[16]，那么就有1~4=13~16，
     * 此时next[17]=next[next[16]]+1=next[8]+1=4+1=5
     * 也就是说有4个公共前缀和后缀了，那么在字符串模式识别时，如果第17字符与原串不匹配，那原串不需要回退，
     * 而是直接从模式串的的next[17],也就是直接从第五个字符重新进行下一个匹配，因为无论是原串还是模式串，1-4都是一致的，无序多余操作
     * 因此也就是说KMP算法其实是利用了已经已经匹配的公共前缀后缀，从而减少不必要的回退操作进行优化
     */

    public static int[] KMPNextArray(String p){
        char[] ch = p.toCharArray();
        int[] next = new int[ch.length+1];
        next[0] = 0;
//        next[1] = 0;
        int i = 1, j = 0;
        while (i < ch.length){
            if (j == 0 || ch[i] == ch[j]){
                next[i] = ++j;
                i++;
            }else {
                j = next[j-1];
            }
        }
        return next;
    }
}
