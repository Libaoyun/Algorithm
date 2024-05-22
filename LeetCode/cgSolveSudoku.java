import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 37. 解数独
 * 编写一个程序，通过填充空格来解决数独问题。
 * 数独的解法需 遵循如下规则：
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
 * 数独部分空格内已填入了数字，空白格用 '.' 表示。
 * 和前题规则一样，就是这一题要解数独，上一题是判断否是有效数独
 */
public class cgSolveSudoku {

    // TODO 这里放到类下的私有变量是为了便于使用参数，否则每次回溯都要传递这些参数很麻烦
    // 如果想节省空间可以是[9][9]，但是记录每行、列、块对应的数字的时候就要-1了，这样才能下标0-8对应数字1-9
    static boolean[][] row = new boolean[9][10];
    // 这里用static如果在力扣执行的时候会报错，因为类变量是静态的，所以每次执行完都会被清空，在力扣需要private
    static boolean[][] col = new boolean[9][10];
    static boolean[][] box = new boolean[9][10];
    static boolean valid = false;
    static List<int[]> spaces = new ArrayList<int[]>();

    public static void main(String[] args) {
        // TODO 要求求解数独后原地填充数组
        char[][] board = new char[][]{
//                {'5','3','.','.','7','.','.','.','.'},
//                {'6','.','.','1','9','5','.','.','.'},
//                {'.','9','8','.','.','.','.','6','.'},
//                {'8','.','.','.','6','.','.','.','3'},
//                {'4','.','.','8','.','3','.','.','1'},
//                {'7','.','.','.','2','.','.','.','6'},
//                {'.','6','.','.','.','.','2','8','.'},
//                {'.','.','.','4','1','9','.','.','5'},
//                {'.','.','.','.','8','.','.','7','9'},
                {'.', '.', '9', '7', '4', '8', '.', '.', '.'},
                {'7', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '2', '.', '1', '.', '9', '.', '.', '.'},
                {'.', '.', '7', '.', '.', '.', '2', '4', '.'},
                {'.', '6', '4', '.', '1', '.', '5', '9', '.'},
                {'.', '9', '8', '.', '.', '.', '3', '.', '.'},
                {'.', '.', '.', '8', '.', '3', '.', '2', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.', '6'},
                {'.', '.', '.', '2', '7', '5', '9', '.', '.'}
        };
        solveSudoku(board);
        System.out.println(Arrays.deepToString(board));
    }


    /** 93%， 40%
     * 方法1：回溯算法
     * 先两层for循环整个遍历一遍数独，定义space记录每个空格的位置以及row.col.box
     * 分别记录row行的数字x，col列的数字x，以及第box个九宫格的数组x并进行标记
     * 然后递归遍历空格，从1-9尝试填充，如果合法就继续递归遍历下一个空格，否则回溯，并将当前已填充数字的数量-1，即回溯到上一个状态
     * 回溯或递归终止条件就是当前已填充数字的数量和空格的数量相同，也就意味着全都填充完毕了。
     */
    public static void solveSudoku(char[][] board) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') {
                    spaces.add(new int[]{i, j});
                } else {
                    // 同理这里也可以再-1，这里不减一只是为了方便展示对应的数字
                    int num = board[i][j] - '0';
                    row[i][num] = true;
                    col[j][num] = true;
                    box[(i / 3) * 3 + j / 3][num] = true;
                }
            }
        }

        backTrack(board, 0);
    }

    // 回溯算法
    public static void backTrack(char[][] board, int count) {
        if (count == spaces.size()) {
            valid = true;
            return;
        }
        // 获取当前待填充位置的下标i,j
        int i = spaces.get(count)[0], j = spaces.get(count)[1];
        // valid用于判断当前是否已经填充完毕并且已经符合，如果是true则不应该执行下边的代码
        for (int digit = 1; digit <= 9 && !valid; digit++) {
            if (!row[i][digit] && !col[j][digit] && !box[(i / 3) * 3 + j / 3][digit]) {
                // 如果满足当前for循环的数字digit，在空格位置的行、列、九宫格都不重复该数字，那么就可以转成字符插入空格
                board[i][j] = (char) (digit + '0');
                // 插入后不要忘记置为true，否则后面还能插入这个数字就不对了
                row[i][digit] = col[j][digit] = box[(i / 3) * 3 + j / 3][digit] = true;
                // 然后继续向下递归查找，看看插入当前digit后，后边的的数独是不是也都能符合
                backTrack(board, count + 1);

                // 每次深层执行完以后都要回溯，再将对应的行列九宫格置为false，然后换一个digit继续再次深层递归
                // 这里不需要再将当前位置还原成'.'了，因为后面反正会覆盖，当前给定的board一定是能够求解出数独的所以肯定会覆盖
                // 不然的话最后遍历完符合的求解后也会变成'.'，这样就相当于啥也没干了
                row[i][digit] = col[j][digit] = box[(i / 3) * 3 + j / 3][digit] = false;
                // TODO 每次都会回溯，但是终止条件是valid == true，也就是说在唯一的符合的递归分支上执行完以后，valid变成true，
                //  这样不管在哪一层递归都不会继续for循环到下一次了。
            }
        }
    }


    /** 100%， 80%
     * 方法2：回溯 + 状态压缩
     * 可用状态压缩，将数字1-9用从低位到高位二进制表示，比如某一行出现过了3和5，那么压缩状态就为row[i]=000010100这9位，
     * 从低到高分别代表1-9是否已经存在。同理col和box也是一样
     */
    public static void solveSudoku2(char[][] board) {

    }

}
