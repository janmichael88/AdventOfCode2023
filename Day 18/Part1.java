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
         * just find the outer points and use the shoe lace formula
         */
        String file = "input.txt";
        List<String> lines = getLines(file);
        List<int[]> points = new ArrayList<>();
        int[] start = {0,0};
        points.add(start);

        for (String l : lines){
            char dir = l.split(" ")[0].charAt(0);
            int steps = Integer.parseInt(l.split(" ")[1]);
            //generate new point
            //need to add points in between!
            if (dir == 'R'){
                for (int i = 1; i <= steps; i++){
                    int[] prev_point = points.get(points.size() - 1);
                    int[] next_point = {prev_point[0], prev_point[1] + 1};
                    points.add(next_point);
                }
            }
            else if (dir == 'D'){
                for (int i = 1; i <= steps; i++){
                    int[] prev_point = points.get(points.size() - 1);
                    int[] next_point = {prev_point[0] + 1, prev_point[1]};
                    points.add(next_point);
                }
            }
            else if (dir == 'L'){
                for (int i = 1; i <= steps; i++){
                    int[] prev_point = points.get(points.size() - 1);
                    int[] next_point = {prev_point[0], prev_point[1] - 1};
                    points.add(next_point);
                }
            }
            else if (dir == 'U'){
                for (int i = 1; i <= steps; i++){
                    int[] prev_point = points.get(points.size() - 1);
                    int[] next_point = {prev_point[0] - 1, prev_point[1]};
                    points.add(next_point);
                }
            }

        }
        double ans = 0;
        int N = points.size();
        //shoe lace formulate
        for (int i = 0; i < points.size(); i++){
            int[] current = points.get(i);
            //System.out.println(Arrays.toString(current));
            int[] next_point = points.get((i+1) % N);
            ans += (current[0] * next_point[1] - next_point[0] * current[1]);

        }
        ans = Math.abs(ans) / 2;

        System.out.println(ans + (points.size()/2) + 1);

        
    
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
