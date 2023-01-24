public class Cell {

    private CellState state;

    public Cell(CellState state){
        this.state = state;
    }

    public CellState getState(){
        return this.state;
    }

    public void setState(CellState state){
        this.state = state;
    }

    public String toString(){
        return this.state.toString();
    }
}
