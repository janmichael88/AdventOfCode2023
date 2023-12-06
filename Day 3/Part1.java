import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
    public static void main(String[] args) {
        String filePath = "input.txt";

        /*
         * travers grid and look in all directions from come cell (i,j)
         * two poinets left and right, then subset
         * after finding number turn them
         */

        try {
            List<String> lines = readFile(filePath);
            //convert to string array
            int rows = lines.size();
            int cols = lines.get(0).length();
            int[][] dirrs = {
                {1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,-1},{1,-1},{-1,1}
            };
            char[][] grid = new char[rows][cols];
            int sum_parts = 0;

            for (int i = 0; i < rows; i++){
                for (int j = 0; j < cols; j++){
                    grid[i][j] = lines.get(i).charAt(j);
                }
            }

            for (int i = 0; i < rows; i++){
                for (int j = 0; j < cols; j++){
                    char curr_char = grid[i][j];
                    if (curr_char != '.'  && !(Character.isDigit(curr_char))){
                        //System.out.println(curr_char);
                        for (int[] d : dirrs){
                            String num = "";
                            int start = -1;
                            int end = -1;
                            int neigh_x = i + d[0];
                            int neigh_y = j + d[1];
                            //check left
                            //make sure cell is digit first
                            if (Character.isDigit(grid[neigh_x][neigh_y])){
                                while (neigh_x >= 0 && neigh_x < rows && neigh_y >= 0 && neigh_y < cols && Character.isDigit(grid[neigh_x][neigh_y])){
                                neigh_y--;
                            }
                            start = neigh_y;
                            //move once again
                            neigh_y++;

                            //check right
                            while (neigh_x >= 0 && neigh_x < rows && neigh_y >= 0 && neigh_y < cols && Character.isDigit(grid[neigh_x][neigh_y])){
                                neigh_y++;
                            }
                            end = neigh_y;
                            for (int k = start + 1; k < end; k++){
                                num += grid[neigh_x][k];
                                grid[neigh_x][k] = '.';
                            }
                            if (!num.isEmpty()){
                                sum_parts += Integer.parseInt(num);
                            }
                            }

                            


                        }

                    }
                }
            }

            System.out.println(sum_parts);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read and print each line from the file
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                lines.add(line);
            }
        }
        return lines;
    }


}
