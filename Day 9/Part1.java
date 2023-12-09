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
        String file_path = "input.txt";
        /*
        expand array until we get zeros for all consective differences, then project
        notice that the inputs are strictly increasing....i wish he would have said that instead of hiding the fact
         */
        int ans = 0;
        List<String> lines = getLines(file_path);
        for (String l : lines){
            int curr_proj = getProjection(l);
            ans += curr_proj;
        }

        System.out.println(ans);

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

    public static int getProjection(String l){
        List<Integer> nums = convertLine(l);
        List<List<Integer>> pyramid = new ArrayList<>();
        pyramid.add(nums);

        //perform expansion
        while (sumList(pyramid.get(pyramid.size() - 1)) != 0){
            List<Integer> curr_row = pyramid.get(pyramid.size() - 1);
            List<Integer> next_row = new ArrayList<>();
            int size = pyramid.get(pyramid.size() - 1).size();
            for (int i = 0; i < size - 1; i++){
                int diff = curr_row.get(i+1) - curr_row.get(i);
                next_row.add(diff);
            }
            pyramid.add(next_row);
        }

        //now just add up the last numbers in each row
        int curr_projection = 0;
        for (int i = pyramid.size() - 1; i >= 0; i--){
            List<Integer> curr_row = pyramid.get(i);
            curr_projection += curr_row.get( curr_row.size() - 1);
        }
        return curr_projection;

    }
    public static List<Integer> convertLine(String l){
        String[] split_l = l.split(" ");
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < split_l.length; i++){
            ans.add(Integer.parseInt(split_l[i]));
        }

        return ans;
    }

    public static int sumList(List<Integer> nums){
        int ans = 0;
        for (int num : nums){
            ans += num;
        }
        return ans;
    }



   
};
