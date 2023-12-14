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
        String file_path = "test_input.txt";
        /*    
        we need to to find the reflections in each pattern
        idea is that each reflection line should be a palindrome
        the ans for the pattern is:
            left_reflection_columns + 100*above_horizontal reflection cols
        to test for a vertical reflection:
            for each middle (cols - 1)
            need left = middle, right = middle + 1
            going down all rows, pattern[row][middle] == patten[row_middle + 1]
            if we get to the bottom, we can expand out pointers
        its just the oppostie for horizontal
         */
        List<String> lines = getLines(file_path);
        List<char[][]> patterns = getPatterns(lines);
        char[][] test_pattern = patterns.get(0);

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

    public static boolean testCol(char[][], int col)

    public static List<char[][]> getPatterns(List<String> lines){
        List<char[][]> patterns = new ArrayList<>();
        List<char[]> curr_pattern = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++){
            String curr_line = lines.get(i);
            if (!curr_line.trim().isEmpty()){
                curr_pattern.add(curr_line.toCharArray());
            }
                
            else{
                //process curr_pattern
                int rows = curr_pattern.size();
                int cols = curr_pattern.get(0).length;
                char[][] converted_pattern = new char[rows][cols];
                for (int j = 0; j < rows; j++){
                    for (int k = 0; k < cols; k++ ){
                        converted_pattern[j][k] = curr_pattern.get(j)[k];
                    }
                }
                patterns.add(converted_pattern);
                //clear
                curr_pattern.clear();

            }
        }
        int rows = curr_pattern.size();
        int cols = curr_pattern.get(0).length;
        char[][] converted_pattern = new char[rows][cols];
        for (int j = 0; j < rows; j++){
            for (int k = 0; k < cols; k++ ){
                converted_pattern[j][k] = curr_pattern.get(j)[k];
            }
        }
        patterns.add(converted_pattern);
        //clear
        curr_pattern.clear();
        

        return patterns;
    }


}

