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
        String file_path = "input.txt";
        /*
        hardest part is what he means by expanding
        first identify what rows and cols dont have any galaxies
        expanion is wrong at first glance
        its just a shift of one, i.e on additonl row and one additionl col
        say we are at some cell (i,j) and want to go to (k,l)
        if there are rows between (i and k) that expanded, then the distance from i to k inceasines by (the number of rows between)
        offset is now 100000
        
         */
        List<String> lines = getLines(file_path);
        //convert to char Array
        char[][] grid = charArray(lines);
        //find rows and cols with no galaxies
        HashSet<Integer> rows = countRows(grid);
        HashSet<Integer> cols = countCols(grid);
        //for each row, col, add a row or col of dots
        List<int[]> gxs = galaxies(grid);
        //int test = getDist(gxs.get(7), gxs.get(8), rows, cols);
        long ans = 0;
        int num_gals = gxs.size();
        for (int i = 0; i < num_gals; i++){
            for (int j = i+1; j < num_gals; j++){
                ans += getDist(gxs.get(i), gxs.get(j), rows, cols);
            }
        }

        System.out.println(ans);
    }

    public static long getDist(int[] u, int[] v, HashSet<Integer> rows, HashSet<Integer> cols){
        int u_x = u[0];
        int u_y = u[1];
        int v_x = v[0];
        int v_y = v[1];

        //get manhat
        long manhat = Math.abs(u_x - v_x) + Math.abs(u_y - v_y);
        //offsets
        long row_offset = 0;
        for (int row = Math.min(u_x,v_x); row <= Math.max(u_x,v_x); row++){
            if (rows.contains(row))
                row_offset += 1000000-1;
        }

        long col_offset = 0;
        for (int col = Math.min(u_y,v_y); col <= Math.max(u_y,v_y); col++){
            if (cols.contains(col))
                col_offset += 1000000-1;
        }

        return manhat + row_offset+ col_offset;
    }
    public static List<String> getLines(String filepath) throws FileNotFoundException, IOException{
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                lines.add(line);
            }
        }
        return lines;
    }
    public static char[][] charArray(List<String> lines){
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

    public static Map<int[],Character> convertToMap(char[][] grid){
        Map<int[],Character> mapp = new HashMap<>();
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                int[] entry = {i,j};
                char curr_char = grid[i][j];
                mapp.put(entry, curr_char);
            }
        }
        return mapp;
    }

    public static HashSet<Integer> countRows(char[][] grid){
        HashSet<Integer> rows = new HashSet<>();
        int curr_row = 0;
        for (char[] row : grid){
            int count_galaxies = 0;
            for (char ch : row){
                if (ch == '#')
                    count_galaxies++;
            }
            if (count_galaxies == 0)
                rows.add(curr_row);
            curr_row++;
        }
        return rows;
    }
    public static HashSet<Integer> countCols(char[][] grid){
        HashSet<Integer> cols = new HashSet<>();
        for (int curr_col = 0; curr_col < grid[0].length; curr_col++ ){
            int count_galaxies = 0;
            for (int curr_row = 0; curr_row < grid.length; curr_row++){
                char curr_char = grid[curr_row][curr_col];
                if (curr_char == '#')
                    count_galaxies++;
            }
            if (count_galaxies == 0)
                cols.add(curr_col);
        }

        return cols;
    }

    public static List<int[]> galaxies(char[][] grid){
        List<int[]> gx = new ArrayList<>();
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                int[] entry = {i,j};
                char curr_char = grid[i][j];
                if (curr_char == '#')
                    gx.add(entry);
            }
        }
        return gx;
    }

}