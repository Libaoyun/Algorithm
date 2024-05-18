/**
 * 36. 有效的数独
 *
 * 请你判断一个 9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
 * 其实就是给一个9*9的二维数组，判断是否满足上述条件(行、列、及3*3的九宫格不能出现重复数字)，返回true或false
 * 输入内容中，空白的字符用'.'表示
 */
public class cfIsValidSudoku {

    public static void main(String[] args) {

         // TODO 只需将数组第一个元素'5'改成'8'即可辩证
        char[][] board = new char[][]{
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'},
            };

        System.out.println(isValidSudoku(board));
    }


    /** O(1)   O(1)   因为是9*9数组，所以一定就只是81，是常数级因此时间复杂度1，空间复杂度也同理
     * 方法1：直接一次遍历，
     * 定义三个二维数组，分别记录x行、x列、x九宫格是否已出现过当前数字
     * 比如row[2][3]表示第二行是否出现过数字3，col[2][3]表示第二列是否出现过数字3，box[2][3]表示第二个九宫格是否出现过数字3
     * 这里的关键是怎么表示九宫格的索引，比如[1][1]肯定是第一个九宫格，[2][8]是第三个九宫格，[4][3]是第五个九宫格(3+2)
     * 因此可以总结出，当前的九宫格的索引可以用(i/3)*3 + j/3计算得出，每隔三行就会多加3(因为3行9列有3个九宫格)，因此(i/3)*3
     * 然后j/3表示当前行第几个九宫格
     */
    public static boolean isValidSudoku(char[][] board) {
        // 这里定义二维数组分别记录是否出现，10是因为希望数字0-9都能直接与下标对应，那下标必须要10才能表示9
        int[][] row = new int[9][10];
        int[][] col = new int[9][10];
        int[][] box = new int[9][10];

        // 一共只会执行9*9=81次，是常数级
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // 如果当前是空格就不用管，因为不会影响结果
                if (board[i][j] == '.'){
                    continue;
                }
                int num = board[i][j] - '0';
                if (row[i][num] == 1){
                    return  false;
                }
                if (col[j][num] == 1){
                    return  false;
                }
                if (box[(i/3)*3 + j/3][num] == 1){
                    return  false;
                }
                row[i][num] = 1;
                col[j][num] = 1;
                box[(i/3)*3 + j/3][num] = 1;
            }
        }
        return true;
    }
}