import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Objects;
import java.util.Queue;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;

public class Part1 {
    public static void main(String[] args) throws IOException{
        /*
         * beams starts upper left, moving through dots only engergizes a the cell at (i,j)
         * starts of going right
         * hitting a mirror 90 degree rotates the incoming direciont dpening on / up or \down
         * | - splits beam when hitting flat side
         * bfs: start at (0,0) with curr direciton (0,1) goin to the right
         * while BFS marking cells as engerized
         * could ther be infinite loops?
         * beams dont interact, though i think in part2, two incoming becoms will be reflected or something
         * keep going until beams go out of bounds, if cell is already energized stop bfs there
         * need to hash states, states are (i,j, and curretn direction), direc is pointer and can only be 1 of 4 keys
         */
        String filepath = "test_input.txt";
        List<String> lines = getLines(filepath);
        char[][] grid = getGrid(lines);
        int[] start = {0,0,1};
        BFS2(grid,start);




    }

    public static void BFS2(char[][] grid, int[] start_entry){
        Queue<int[]> q = new ArrayDeque<>();
        int R = grid.length;
        int C = grid[0].length;
        int[] DR = {-1,0,1,0};
        int[] DC = {0,1,0,-1};
        Map<Integer,Integer> mapp1 = new HashMap<>();
        Map<Integer,Integer> mapp2 = new HashMap<>();
        mapp1.put(0, 1);
        mapp1.put(1, 0);
        mapp1.put(2, 3);
        mapp1.put(3, 2);

        mapp2.put(0, 1);
        mapp2.put(1, 0);
        mapp2.put(2, 3);
        mapp2.put(3, 2);
 
        HashSet<int[]> seen = new HashSet<>();
        HashSet<int[]> seen2 = new HashSet<>();
        q.add(start_entry);

        while (!q.isEmpty()){
            int[] entry = q.poll();
            int sr = entry[0];
            int sc = entry[1];
            int sd = entry[2];

            //bounds
            if (sr >= 0 && sr < R && sc >= 0 && sc < C){
                int[] state = {sr,sc};
                seen.add(state);
                int[] state2 = {sr, sc,sd};
                if (seen2.contains(state2)){
                    continue;
                }
                seen2.add(state2);
                char curr_char = grid[sr][sc];
                if (curr_char == '.'){
                    q.add(step(DR , DC, sr, sc, sd));
                }
                else if (curr_char == '/'){
                    q.add(step(DR,DC,sr,sc,mapp1.get(sd)));

                }
                else if (curr_char == '\\'){
                    q.add(step(DR,DC,sr,sc,mapp2.get(sd)));
                }
                else if (curr_char == '|'){
                    if (sd == 0 || sd == 2){
                        q.add(step(DR, DC, sr, sc, sd));
                    }
                    else{
                        q.add(step(DR, DC, sr, sc, 0));
                        q.add(step(DR, DC, sr, sc, 2));
                    }
                }
                else if (curr_char == '-'){
                    if (sd == 1 || sd == 3){
                        q.add(step(DR, DC, sr, sc, sd));
                    }
                    else{
                        q.add(step(DR, DC, sr, sc, 1));
                        q.add(step(DR, DC, sr, sc, 3));
                    }
                }
                else{
                    continue;
                }

            }
        }
        System.out.println(seen.size());

    }
    public static int[] step(int[] DR, int[] DC, int r, int c, int d){
        int[] ans = {r + DR[d], c + DC[d], d};
        return ans;
    }
    public static void BFS(char[][] grid, boolean[][] eng, boolean[][] seen){
        Queue<int[]> q = new ArrayDeque<>();
        int[] entry = {0,0,1,0}; //(i,j,dx,dy) initally going right
        q.add(entry);

        while (!q.isEmpty()){
            entry = q.poll();
            int x,y,dx,dy;
            x = entry[0];
            y = entry[1];
            dx = entry[2];
            dy = entry[3];
            if (x < 0 || x > grid.length || y < 0 || y > grid[0].length)
                continue;
            //mark as energize
            eng[x][y] = true;
            int neigh_x = x + dx;
            int neigh_y = y + dy;
            //bounds and already energized
            if (neigh_x >= 0 &&  neigh_x < grid.length && neigh_y >= 0 && neigh_y < grid[0].length){
                //splitters first
                if (grid[neigh_x][neigh_y] == '|' && (dy == 1 || dy == -1) ){
                    int[] next_entry = {neigh_x,neigh_y,dx,dy};
                    q.add(next_entry);
                }
                else if (grid[neigh_x][neigh_y] == '|' && (dx == 1 || dx == -1) ){
                    //up
                    int[] next_entry = {neigh_x,neigh_y,0,1};
                    q.add(next_entry);
                    //down
                    int[] next_entry_2 = {neigh_x,neigh_y, 0,-1};
                    q.add(next_entry_2);
                }
                //horiz
                else if (grid[neigh_x][neigh_y] == '-' && (dx == 1 || dx == -1) ){
                    int[] next_entry = {neigh_x,neigh_y,dx,dy};
                    q.add(next_entry);
                }
                else if (grid[neigh_x][neigh_y] == '-' && (dy == 1 || dy == -1) ){
                    //left
                    int[] next_entry = {neigh_x,neigh_y,-1,0};
                    q.add(next_entry);
                    //right
                    int[] next_entry_2 = {neigh_x,neigh_y,1,0};
                    q.add(next_entry_2);
                }
                //mirrors , '/' first
                else if (grid[neigh_x][neigh_x] == '/' && (dx == 1)){
                    //approach from right
                    int[] next_entry = {neigh_x,neigh_y,0,1};
                    q.add(next_entry);
                }
                else if (grid[neigh_x][neigh_x] == '/' && (dx == -1)){
                    //approach from left
                    int[] next_entry = {neigh_x,neigh_y-1,0,-1};
                    q.add(next_entry);
                }
                else if (grid[neigh_x][neigh_x] == '/' && (dy == -1)){
                    //apprach from above
                    int[] next_entry = {neigh_x,neigh_y,-1,0};
                    q.add(next_entry);
                }
                else if (grid[neigh_x][neigh_x] == '/' && (dy == 1)){
                    //approach from below
                    int[] next_entry = {neigh_x,neigh_y,1,0};
                    q.add(next_entry);
                }

                //now '\'
                else if (grid[neigh_x][neigh_x] == '\\' && (dx == 1)){
                    //approach from right
                    int[] next_entry = {neigh_x,neigh_y,0,-1};
                    q.add(next_entry);
                }
                else if (grid[neigh_x][neigh_x] == '\\' && (dx == -1)){
                    //approach from left
                    int[] next_entry = {neigh_x,neigh_y,0,1};
                    q.add(next_entry);
                }
                else if (grid[neigh_x][neigh_x] == '\\' && (dy == -1)){
                    //apprach from above
                    int[] next_entry = {neigh_x,neigh_y,1,0};
                    q.add(next_entry);
                }
                else if (grid[neigh_x][neigh_x] == '\\' && (dy == 1)){
                    //approach from below
                    int[] next_entry = {neigh_x,neigh_y,-1,0};
                    q.add(next_entry);
                }
                //dots!
                else if (grid[neigh_x][neigh_y] == '.'){
                    int[] next_entry = {neigh_x,neigh_y,dx,dy};
                    q.add(next_entry);
                }



            }



        }


    }
    public static List<String> getLines(String filepath) throws FileNotFoundException, IOException{
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            //process each
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public static char[][] getGrid(List<String> lines){
        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j= 0; j < cols; j++){
                grid[i][j] = lines.get(i).charAt(j);
            }
        }
        return grid;
    }
    
}
