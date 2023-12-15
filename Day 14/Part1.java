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

public class Part1 {
    public static void main(String[] args) throws IOException{
        /*
          we only need to move rocks up, only O's will move and #s will stay in place
          go down each column, and mark the position it would fall too
          if . mark this as the new fall too spot
          if O swap it with dot, and move the last fall to spot
          if #, this becomes the new fall to sport, but + 1

           
         */
        String file_path = "input.txt";
        char[][] grid = convertArray(getLines(file_path));
        int rows = grid.length;
        int cols = grid[0].length;
        for (int col = 0; col < cols; col++){
            drop(grid, col);
        }
        //now count em up!
        int ans = 0;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if (grid[i][j] == 'O')
                    ans += (rows - i);
            }
        }
        System.out.println(ans);

    }

    public static void drop(char[][] grid, int col){
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
