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
import java.util.PriorityQueue;
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
         * BFS from starting points, and only take steps onto dots.
         * we need to do BFS 64 times
         * we can walk back from the same step he come from
         * keep grid of where the elf is currently staningd on, mark it initally
         * when moving away, makr next step but un makr previous
         * then return sum of all marked
         * bfs, entries are (steps, x,y), when steps == 64 intcrment count
         * cache states as (x,y,steps)
         * 
         * for part2, examine the first three values with your input, then fit to quadratic to find
         * need steps for 26501365
         * we can decompose this into 
         * 26501365 = 202300 * 131 + 65 where 131 is the dimension of the grid
         * let f(n) be the number of spaces you can reach after n steps (our bfs function)
         * let X by the length of your grid input f(n), f(n+x), f(n + 2x) is qudratic
         * exmiane the points and notice the qudartic depenednce
         * could have don recursively, but the cost of evalutating the function is too big
         * 131 is grid size, 65 is limit
         * 26501365 = 65 + 131*k
         * k = (26501365 - 65) / 131
         * solve for first three steps, and get polynomial coeffcients and get answer
         */
        String file_path = "test_input.txt";
        List<String> lines = getLines(file_path);
        char[][] grid = getGrid(lines);
        int a0 = BFS(grid,65 );
        int a1 = BFS(grid, 65 + 131);
        int a2 = BFS(grid, 65 + 131*2);
        System.out.println(ans);
    }
    public static int BFS(char[][] grid, int steps){
        int rows = grid.length;
        int cols = grid[0].length;
        int start_x = -1;
        int start_y = -1;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if (grid[i][j] == 'S'){
                    start_x = i;
                    start_y = j;
                }
            }
        }
        Deque<int[]> q = new ArrayDeque<>();
        HashSet<int[]> seen = new HashSet<>();
        HashSet<int[]> s64 = new HashSet<>();
        int[] entry = {0,start_x,start_y};
        q.add(entry);
        int[][] dirrs = {{1,0}, {-1,0}, {0,1},{0,-1}};
        while (!q.isEmpty()){
            int[] curr = q.poll();
            if (seen.contains(curr)){
                continue;
            }
            seen.add(curr);
            int curr_steps = curr[0];
            int curr_x = curr[1];
            int curr_y = curr[2];
            if (curr_steps == steps){
                int[] temp_entry = {curr_x,curr_y};
                s64.add(temp_entry);
            }
            else{
                for (int[] d : dirrs){
                    int neigh_x = curr_x + d[0];
                    int neigh_y = curr_y + d[1];
                    if (neigh_x >= 0 && neigh_x < rows && neigh_y >= 0 && neigh_y < cols){
                        if (grid[neigh_x][neigh_y] != '#'){
                            int[] neigh_entry = {curr_steps + 1, neigh_x,neigh_y};
                            q.add(neigh_entry);
                        }
                    }
            }
            }

        }


        return s64.size();

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
