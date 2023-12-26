import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.Comparator;
import java.util.Deque;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Iterator;


public class Part1{

    public static void main(String[] args) throws IOException{
        /*
         * dfs from start back track keep 
         * start is (0,1) end is (rows-1,cols-2)
         * keep path length and seen set
         * keep path in stack
         */

        String file = "test_input.txt";
        List<String> lines = getLines(file);
        char[][] grid = getGrid(lines);
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] seen = new boolean[rows][cols];
        int[] ans = {0};
        Deque<int[]> path = new ArrayDeque<>();
        dfs(grid, seen, ans, 0, 1, path);
        System.out.println(ans[0]);

    
    }

    public static void dfs(char[][] grid, boolean[][] seen, int[] ans, int i, int j, Deque<int[]> path){
        //out of bounds
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length ){
            return;
        }
        //reached the end
        if (i == grid.length-1 && j == grid[0].length - 2){
            ans[0] = Math.max(ans[0], path.size());
            return;
        }
        //no move
        char curr_char = grid[i][j];
        if (curr_char == '#')
            return;
        //mark
        seen[i][j] = true;
        //slopes
        if (curr_char == '>' && seen[i][j+1] == false){
            int[] next = {i,j+1};
            path.add(next);
            dfs(grid, seen, ans, i, j+1, path);
        }
        if (curr_char == 'v' && seen[i+1][j] == false){
            int[] next = {i+1,j};
            path.add(next);
            dfs(grid, seen, ans, i+1, j, path);
        }
        //neighbors for dot
        else{
            int[][] dirrs = {{1,0},{-1,0},{0,1},{0,-1}};
            for (int[] d : dirrs){
                int neigh_x = i + d[0];
                int neigh_y = j + d[1];
                if (neigh_x >= 0 && neigh_x < grid.length && neigh_y >= 0 && neigh_y < grid[0].length && seen[neigh_x][neigh_y] == false){
                    int[] next = {neigh_x,neigh_y};
                    path.add(next);
                    dfs(grid, seen, ans, neigh_x, neigh_y, path);
                }
            }
        }
        //backtrack
        seen[i][j] = false;
        path.removeLast();


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
            for (int j = 0; j < cols; j++){
                grid[i][j] = lines.get(i).charAt(j);
            }
        }

        return grid;
    }
}

