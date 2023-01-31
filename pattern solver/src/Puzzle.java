import java.util.ArrayList;
import java.util.List;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class Puzzle {

    public static final String ANSI_BLACK = "\u001B[40m";
    public static final String ANSI_WHITE = "\u001B[47m";
    public static final String ANSI_RESET = "\u001B[0m";
    private final int rows;
    private final int columns;
    private final boolean[][] cells;
    private final List<List<Integer>> row_constraints;
    private final List<List<Integer>> column_constraints;
    private final List<List<Integer>> row_blocks;
    private final List<List<Integer>> column_blocks;

    public Puzzle(int[][] row_constraints, int[][] column_constraints) {
        this.rows = row_constraints.length;
        this.columns = column_constraints.length;
        this.cells = new boolean[this.rows][this.columns];

        this.row_constraints = new ArrayList<>();
        this.column_constraints = new ArrayList<>();
        this.row_blocks = new ArrayList<>();
        this.column_blocks = new ArrayList<>();
        for(int[] constraint : row_constraints){
            ArrayList<Integer> temp = new ArrayList<>();
            for(int i : constraint){
                temp.add(i);
            }
            this.row_constraints.add(temp);
            this.row_blocks.add(new ArrayList<>());
        }
        for(int[] constraint : column_constraints){
            ArrayList<Integer> temp = new ArrayList<>();
            for(int i : constraint){
                temp.add(i);
            }
            this.column_constraints.add(temp);
            this.column_blocks.add(new ArrayList<>());
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

    public List<List<Integer>> getRow_constraints() {
        return this.row_constraints;
    }

    public List<List<Integer>> getColumn_constraints() {
        return this.column_constraints;
    }

    public boolean[][] getBoard() {
        return this.cells;
    }

    public List<Integer> getRow_constraint(int i){
        return this.row_constraints.get(i);
    }

    public List<Integer> getColumn_constraint(int i){
        return this.column_constraints.get(i);
    }

    public int getRow_block(int i, int j){
        return this.row_constraints.get(i).get(j);
    }

    public int getColumn_block(int i, int j){
        return this.column_constraints.get(i).get(j);
    }

    public int getRows(){
        return this.rows;
    }

    public int getColumns(){
        return this.columns;
    }

    public boolean field(int r, int c){
        //returns true if field r,c is black
        return this.cells[r][c];
    }

    public boolean begin_of_block_row(int b, int r, int c){
        //returns whether the b-th block in row r starts at field r,c
        return this.row_blocks.get(r).get(b) == c;
    }

    public boolean begin_of_block_column(int b, int r, int c){
        //returns whether the b-th block in column c starts at field r,c
        return this.column_blocks.get(c).get(b) == r;
    }

    public boolean part_of_block_row(int b, int r, int c){
        //returns whether field r,c is part of the b-th block in row r
        return this.row_blocks.get(r).get(c) == b;
    }

    public boolean part_of_block_column(int b, int r, int c){
        //returns whether field r,c is part of the b-th block in column c
        return this.column_blocks.get(c).get(r) == b;
    }

}
