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

public class Part2 {
    public static void main(String[] args) throws IOException{
        /*
          we only need to move rocks up, only O's will move and #s will stay in place
          go down each column, and mark the position it would fall too
          if . mark this as the new fall too spot
          if O swap it with dot, and move the last fall to spot
          if #, this becomes the new fall to sport, but + 1

          for part 2, we have cycles now that can moved north, west, south, east
          want grid amuont after 1000000000 cycles,
          there must be a pattern, that number is too big
          find the length of the cycle, we haash loads

           
         */
        String file_path = "input.txt";
        char[][] grid = convertArray(getLines(file_path));
        int rows = grid.length;
        int cols = grid[0].length;
        HashMap<String,int[]> cache = new HashMap<>();
        int goal = 1000000000;
        int idx = 1;
        
        while (true){
            cycle(grid);
            String cached_grid = convertToString(grid);
            if (cache.containsKey(cached_grid)){
                int cycle_len = idx - cache.get(cached_grid)[0];
                for (int[] vals : cache.values()){
                    int a = vals[0];
                    int b = vals[1];
                    if (a >= cache.get(cached_grid)[0] && a % cycle_len == goal % cycle_len){
                        System.out.println(b);
                    }
                }
                break;
            }
            int[] entry = {idx, calcLoad(grid)};
            cache.put(cached_grid, entry);
            idx++;
        }


    }

    public static String convertToString(char[][] grid){
        StringBuilder ans = new StringBuilder();
        for (char[] row : grid){
            for (char c : row){
                ans.append(c);
            }
            ans.append('\n');
        }
        return ans.toString();
    }

    public static int calcLoad(char[][] grid){
        int ans = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if (grid[i][j] == 'O')
                    ans += (rows - i);
            }
        }
        return ans;

    }

    public static void cycle(char[][] grid){
        //rorate north first
        int rows = grid.length;
        int cols = grid[0].length;
        for (int col = 0; col < cols; col++){
            north(grid, col);
        }
        //west
        for (int row = 0; row < rows; row++){
            west(grid, row);
        }

        //south
        for (int col = 0; col < cols; col++){
            south(grid, col);
        }

        //east
        for (int row = 0; row < rows; row++){
            east(grid, row);
        }
    }

    public static void west(char[][] grid, int row){
        int cols = grid[0].length;
        int drop_to = -1;
        for (int col = 0; col < cols; col++){
            if (grid[row][col] == 'O'){
                grid[row][col] = '.';
                drop_to++;
                if (drop_to < cols)
                    grid[row][drop_to] = 'O';
            }
            else if (grid[row][col] == '#'){
                drop_to = col;
            }
        }
    }

    public static void east(char[][] grid, int row){
        int cols = grid[0].length;
        int drop_to = cols;
        for (int col = cols - 1; col >= 0; col--){
            if (grid[row][col] == 'O'){
                grid[row][col] = '.';
                drop_to--;
                if (drop_to >= 0)
                    grid[row][drop_to] = 'O';
            }
            else if (grid[row][col] == '#'){
                drop_to = col;
            }
        }

    }

    public static void south(char[][] grid, int col){
        int rows = grid.length;
        int drop_to = rows;
        for (int row = rows - 1; row >= 0; row--){
            if (grid[row][col] == 'O'){
                grid[row][col] = '.';
                drop_to--;
                if (drop_to >= 0)
                    grid[drop_to][col] = 'O';
            }
            else if (grid[row][col] == '#'){
                drop_to = row;
            }
        }
    }

    public static void north(char[][] grid, int col){
        int rows = grid.length;
        int drop_to = -1;
        for (int row = 0; row < rows; row++){
            if (grid[row][col] == 'O'){
                grid[row][col] = '.';
                drop_to++;
                if (drop_to < rows)
                    grid[drop_to][col] = 'O';
            }
            else if (grid[row][col] == '#'){
                drop_to = row;
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

    public static char[][] convertArray(List<String> grid){
        int rows = grid.size();
        int cols = grid.get(0).length();
        char[][] ans = new char[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                ans[i][j] = grid.get(i).charAt(j);
            }
        }
        return ans;
    }
}
