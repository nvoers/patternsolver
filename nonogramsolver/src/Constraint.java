import org.paukov.combinatorics3.Generator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Constraint {

    private List<Integer> constraint;
    private List<List<CellState>> possible_solutions;
    private final int line_length;
    private List<List<Integer>> dnf = new ArrayList<>();
    private int nextfreevariable = 0;

    public Constraint(List<Integer> constraint, int line_length){
        this.constraint = constraint;
        this.line_length = line_length;
    }

    public void initConstraint(List<Integer> constraint){
        this.constraint = constraint;
    }

    public List<Integer> getConstraint(){
        return this.constraint;
    }

    public List<List<CellState>> toPossibilities(List<List<Integer>> list){
        List<List<CellState>> result = new ArrayList<>();
        for (List<Integer> l : list){
            List<CellState> temp = new ArrayList<>();
            int constraint_index = 0;
            for (Integer integer : l) {
                if (integer == 1) {
                    for (int x = 0; x < this.constraint.get(constraint_index); x++) {
                        temp.add(CellState.BLACK);
                    }
                    constraint_index++;
                } else {
                    temp.add(CellState.WHITE);
                }
            }
            result.add(temp);
        }
        return result;
    }

    public void generatePossibleSolutions(){
        List<List<Integer>> initialList = new ArrayList<>();
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> blocks = new ArrayList<>();
        for(int i : this.constraint) {
            if (i > 0) {
                blocks.add(1);
            }
        }
        int crosses = this.line_length - this.constraint.stream().mapToInt(Integer::intValue).sum();
        for(int i = 0; i < crosses; i++){
            blocks.add(0);
        }
        Generator.permutation(blocks).simple().forEach(initialList::add);
        for(List<Integer> option : initialList){
            boolean remove = false;
            for(int i = 1; i < option.size(); i++){
                if (Objects.equals(option.get(i), option.get(i - 1)) && option.get(i) == 1) {
                    remove = true;
                    break;
                }
            }
            if(!remove){
                list.add(option);
            }
        }
        this.possible_solutions = toPossibilities(list);
    }

    public List<List<CellState>> getPossibleSolutions(){
        return this.possible_solutions;
    }

    public void createClauses(String line_type, int line_number, int row_length){
        for(int i = 0; i < this.possible_solutions.size(); i++){
            List<Integer> clause = new ArrayList<>();
            for(int j = 0; j < this.line_length; j++){
                if(this.possible_solutions.get(i).get(j) == CellState.BLACK){
                    if(line_type.equals("row")){
                        clause.add((line_number * row_length) + j + 1);
                    } else {
                        clause.add((line_number + (j * row_length)) + 1);
                    }
                } else {
                    if(line_type.equals("row")){
                        clause.add(-((line_number * row_length) + j + 1));
                    } else {
                        clause.add(-(line_number + (j * row_length) + 1));
                    }
                }
            }
            this.dnf.add(clause);
        }
    }

    public List<List<Integer>> tseytin(int nextFreeVariable) {
        //In: List of list of variables in dnf, so the
        //inner lists are all conjunctions, the outer list
        //contains all disjunctions,
        //e.g. [[1,2],[3,4]] is (1 and 2) or (3 and 4)

        List<List<Integer>> cnf = new ArrayList<>();
        List<Integer> finalClause = new ArrayList<>();

        for(List<Integer> conjunction : this.dnf) {

            int additionalVar = nextFreeVariable;
            //nextFreeVariable is just a global counter to see which
            //variables are available to add

            for(int var : conjunction) {
                //All variables in a clause
                List<Integer> disj = new ArrayList<>();
                disj.add(var);
                disj.add(-additionalVar);
                cnf.add(disj);
                //Already solved the implication arrow of tseytin
            }
            finalClause.add(nextFreeVariable);
            nextFreeVariable++;
        }

        //cnf now contains lists of disjunctions.
        //Need to add new variable for whole clause
        finalClause.add(-nextFreeVariable);
        cnf.add(finalClause);
        List<Integer> superClause = new ArrayList<>();
        superClause.add(nextFreeVariable);
        cnf.add(superClause);

        nextFreeVariable++;
        this.nextfreevariable = nextFreeVariable;
        return cnf;
        //Out: List of List of disjunctions
        //e,g. [[1,2],[3,4]] is (1 or 2) and (3 or 4)
    }

    public int getNextFreeVariable(){
        return this.nextfreevariable;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        for (Integer integer : this.constraint) {
            result.append(integer).append(" ");
        }
        return result.toString();
    }
}
