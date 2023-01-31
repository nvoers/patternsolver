public class Main {

    public static Puzzle stringToPuzzle(String id){
        int x = id.indexOf("x");
        int y = id.indexOf(":");
        int r = Integer.parseInt(id.substring(1, x));
        int c = Integer.parseInt(id.substring(x+1, y));
        int[][] rc = new int[r][];
        int[][] cc = new int[c][];
        String[] subs = id.substring(y+1).split("/");
        for(int i = 0; i < c; i++){
            String[] sub = subs[i].split("\\.");
            int[] temp = new int[sub.length];
            for(int j = 0; j < sub.length; j++){
                temp[j] = Integer.parseInt(sub[j]);
            }
            cc[i] = temp;
        }
        for(int i = c; i < r+c; i++){
            String[] sub = subs[i].split("\\.");
            int[] temp = new int[sub.length];
            for(int j = 0; j < sub.length; j++){
                temp[j] = Integer.parseInt(sub[j]);
            }
            rc[i-c] = temp;
        }
        Puzzle puzzle = new Puzzle(rc, cc);
        return puzzle;
    }

    public static void main(String[] args)  {

        Puzzle puzzle = stringToPuzzle("#30x30:1.7.4.1.1/8.4.2/3.2.4.4.1/5.6.1.5/5.5.8/2.4.4.3.7/2.2.8.3.2/3.3.8.2/5.3.3.4.6/1.3.3.1.3.9/4.9.3.4/3.12.3/5.4.5.1.1/2.3.4.3.2/3.3.3.2.1.6/3.1.2.4.6/3.2.4.6/2.3.2.4/2.5.2/2.5.6.1/1.5.8/1.1.1.1.2.10/2.1.7.4.1.4/11/5.5.1/2.3.1.1/1.1.3.5.4.1.1/1.3.9.1.1/2.1.2.2.5.2/1.1.2.1/5.2.3.2/4.2.8.1/1.1.3.6.3/1.1.2.3.4.3/4.3.1.1.1.3/3.5.3.3/4.6.2.3/12.4.2/4.3.1.3/2.3.3.1/2.7.2/2.2.6.1/4.1.11/5.15/9.5.3.3/6.3.3/6.2.1.2/12.2.3/3.8.3.4/4.18.3/3.3.11.3/2.1.4.2.4.5/1.3.3.3.3/3.2.4.1/4.2.5.4/2.4.2.5.3/5.3.5.2.2/3.5.4.3/9.4/1.13.1.2");
        int[][] rc1 = {{3,6,2},{4,3,3},{3,2,3},{2,5,5},{1,5,5}};
        int[][] cc1 = {{1,2},{2,1},{3},{2,1},{5},{1,2},{1,2},{5},{4},{2},{2},{4},{5},{5},{2}};
        int[][] rc2 = {{1,1},{2},{1}};
        int[][] cc2 = {{2},{2},{1}};
        int[][] rc3 = {{2,1}};
        int[][] cc3 = {{1},{1},{0},{0},{1}};
        int[][] rc4 = {{2,1},{1,1}};
        int[][] cc4 = {{1},{2},{0},{2}};

        System.out.println(puzzle);
        //puzzle.solve();
        System.out.println("SOLUTION:");
        System.out.println(puzzle);
    }
}