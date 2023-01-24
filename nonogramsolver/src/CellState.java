public enum CellState {
    BLACK,
    WHITE,
    UNKNOWN;

    public String toString(){
        switch(this){
            case BLACK:
                return "B";
            case WHITE:
                return "W";
            default:
                return "U";
        }
    }
}
