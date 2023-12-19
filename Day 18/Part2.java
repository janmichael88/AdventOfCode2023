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

public class Part2 {
    public static void main(String[] args) throws IOException{
        /*
         * just find the outer points and use the shoe lace formula
         * part 2, we need to decode the hex, first 5 digits are hex the last is direction
         * also no need to keep track of all the points in the boundary
         */
        String file = "input.txt";
        List<String> lines = getLines(file);
        //List<int[]> points = new ArrayList<>();
        int[] current = {0,0};
        int[] next_point = {0,0};
        //points.add(start);
        long ans = 0;
        long points = 1;

        for (String l : lines){
            String hex_code = l.split(" ")[2];
            char dir = hex_code.charAt(hex_code.length()-2);
            int steps = Integer.parseInt(hex_code.substring(2, hex_code.length() - 2),16);
            //System.out.println(dir+" "+steps);
            //generate new point
            //need to add points in between!
            if (dir == '0'){
                for (int i = 1; i <= steps; i++){
                    next_point[0] = current[0];
                    next_point[1] = current[1] + 1;
                    ans += (current[0] * next_point[1] - next_point[0] * current[1]);
                    //update current
                    current[0] = next_point[0];
                    current[1] = next_point[1];
                    points++;


                }
            }
            else if (dir == '1'){
                for (int i = 1; i <= steps; i++){
                    next_point[0] = current[0] + 1;
                    next_point[1] = current[1];
                    ans += (current[0] * next_point[1] - next_point[0] * current[1]);
                    //update current
                    current[0] = next_point[0];
                    current[1] = next_point[1];
                    points++;
                }
            }
            else if (dir == '2'){
                for (int i = 1; i <= steps; i++){
                    next_point[0] = current[0];
                    next_point[1] = current[1] - 1;
                    ans += (current[0] * next_point[1] - next_point[0] * current[1]);
                    //update current
                    current[0] = next_point[0];
                    current[1] = next_point[1];
                    points++;
                }
            }
            else if (dir == '3'){
                for (int i = 1; i <= steps; i++){
                    next_point[0] = current[0] - 1;
                    next_point[1] = current[1];
                    ans += (current[0] * next_point[1] - next_point[0] * current[1]);
                    //update current
                    current[0] = next_point[0];
                    current[1] = next_point[1];
                    points++;
                }
            }

        }
        //last point
        next_point[0] = 0;
        next_point[1] = 0;
        ans += (current[0] * next_point[1] - next_point[0] * current[1]);
        ans = Math.abs(ans) / 2;
        System.out.println(ans + (points/2) + 1);
        /*
        long ans = 0;
        int N = points.size();
        //shoe lace formulate
        for (int i = 0; i < points.size(); i++){
            int[] current = points.get(i);
            //System.out.println(Arrays.toString(current));
            int[] next_point = points.get((i+1) % N);
            ans += (current[0] * next_point[1] - next_point[0] * current[1]);

        }
        ans = Math.abs(ans) / 2;
        */

        
    
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
}
