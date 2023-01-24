import java.util.ArrayList;
import java.util.List;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class Puzzle {

    private final int rows;
    private final int columns;
    private final Cell[][] cells;
    private final List<Constraint> row_constraints;
    private final List<Constraint> column_constraints;

    public Puzzle(int[][] row_constraints, int[][] column_constraints) {
        this.rows = row_constraints.length;
        this.columns = column_constraints.length;
        this.cells = new Cell[this.rows][this.columns];

        this.row_constraints = new ArrayList<>();
        this.column_constraints = new ArrayList<>();
        for(int[] constraint : row_constraints){
            ArrayList<Integer> temp = new ArrayList<>();
            for(int i : constraint){
                temp.add(i);
            }
            this.row_constraints.add(new Constraint(temp, this.columns));
        }
        for(int[] constraint : column_constraints){
            ArrayList<Integer> temp = new ArrayList<>();
            for(int i : constraint){
                temp.add(i);
            }
            this.column_constraints.add(new Constraint(temp, this.rows));
        }

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                this.cells[i][j] = new Cell(CellState.UNKNOWN);
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("row constraints: \n");
        for(Constraint c : this.row_constraints){
            result.append(c.toString()).append("\n");
        }
        result.append("column constraints: \n");
        for(Constraint c : this.column_constraints){
            result.append(c.toString()).append("\n");
        }
        result.append("board: \n");
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                result.append(this.cells[i][j].toString()).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public void solve() throws ContradictionException, TimeoutException {
        for(Constraint rc: this.row_constraints){
            rc.generatePossibleSolutions();
        }
        for(Constraint cc: this.column_constraints){
            cc.generatePossibleSolutions();
        }
        ISolver solver = SolverFactory.newDefault();
        solver.newVar(1000000);
        solver.setExpectedNumberOfClauses(500000);
        int nextfreevariable = this.columns * this.rows + 1;
        for(Constraint rc: this.row_constraints){
            rc.createClauses("row", this.row_constraints.indexOf(rc), this.columns);
            List<List<Integer>> clauses = rc.tseytin(nextfreevariable);
            nextfreevariable = rc.getNextFreeVariable();
            for (List<Integer> clause : clauses) {
                int[] newclause = clause.stream().mapToInt(i->i).toArray();
                solver.addClause(new VecInt(newclause));
            }
        }
        for(Constraint cc: this.column_constraints){
            cc.createClauses("column", this.column_constraints.indexOf(cc), this.columns);
            List<List<Integer>> clauses = cc.tseytin(nextfreevariable);
            nextfreevariable = cc.getNextFreeVariable();
            for (List<Integer> clause : clauses) {
                int[] newclause = clause.stream().mapToInt(i->i).toArray();
                solver.addClause(new VecInt(newclause));
            }
        }
        if(solver.isSatisfiable()){
            System.out.println("Solved!");
            int[] model = solver.model();
            int m = 0;
            for(int i = 0; i < this.rows; i++){
                for(int j = 0; j < this.columns;j++){
                    if(model[m] < 0){
                        this.cells[i][j].setState(CellState.WHITE);
                    }
                    else {
                        this.cells[i][j].setState(CellState.BLACK);
                    }
                    m++;
                }
            }
        }
        else {
            System.out.println("No solution found.");
        }
    }
}
