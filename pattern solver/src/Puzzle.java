import java.util.ArrayList;
import java.util.List;

public class Puzzle {

    public static final String ANSI_BLACK = "\u001B[40m";
    public static final String ANSI_WHITE = "\u001B[47m";
    public static final String ANSI_RESET = "\u001B[0m";
    private final int rows;
    private final int columns;
    private final boolean[][] cells;
    private final List<List<Integer>> row_constraints;
    private final List<List<Integer>> column_constraints;

    public Puzzle(int[][] row_constraints, int[][] column_constraints) {
        this.rows = row_constraints.length;
        this.columns = column_constraints.length;
        this.cells = new boolean[this.rows][this.columns];

        this.row_constraints = new ArrayList<>();
        this.column_constraints = new ArrayList<>();
        for(int[] constraint : row_constraints){
            ArrayList<Integer> temp = new ArrayList<>();
            for(int i : constraint){
                temp.add(i);
            }
            this.row_constraints.add(temp);
        }
        for(int[] constraint : column_constraints){
            ArrayList<Integer> temp = new ArrayList<>();
            for(int i : constraint){
                temp.add(i);
            }
            this.column_constraints.add(temp);
        }
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("row constraints: \n");
        for(List<Integer> c : this.row_constraints){
            result.append(c.toString()).append("\n");
        }
        result.append("column constraints: \n");
        for(List<Integer> c : this.column_constraints){
            result.append(c.toString()).append("\n");
        }
        result.append("board: \n");
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if(this.cells[i][j]){
                    result.append(ANSI_BLACK).append("   ");
                } else {
                    result.append(ANSI_WHITE).append("   ");
                }
            }
            result.append(ANSI_RESET).append("\n");
        }
        return result.toString();
    }

}
