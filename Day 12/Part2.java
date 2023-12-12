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
        this is a decoding problem  
        springs are operational '.' or they are damaged '#' or status could be unknown '?'
        we are given the string state followed by a list of numbers (comma seperated)
        each number shows the size of the conitguous group of DAMAGED springs
        for line find the number of difference arrangments of operational and damaged springs
        basically try replacing '?' with '.' or '#' using the contstraints of the sizes of contig damaged springs
        brute force would be to generate all possible permutations of '.#', then decode, 
        2**(len(state))*len(state)
        two pointers (i,j), i into string state and j into sizes
        dp(i,j) is num ways
        if both i and j get to end, valid ways
        call them spring,sizes
        if spring[i] = '?':
            curr_size = sizes[j]
            while i
        
        gah, almost had 

        for part 2, just concat 5 times and add ? between sepeations
        .......
         */
        long ans = 0;
        List<String> lines = getLines(file_path);
        for (String l : lines){
            char[] springs = getSprings(l);
            int[] sizes = getSizes(l);
            Map<List<Integer>, Long> memo = new HashMap<>();
            long ways = dp(springs, sizes, memo, 0, 0, 0);
            //System.out.println(ways);
            ans += ways;
        }
        System.out.println(ans);
    }

    public static long dp(char[] springs, int[] sizes, Map<List<Integer>,Long> memo, int i, int j, int block_size){
        List<Integer> key = List.of(i,j,block_size);
        if (memo.containsKey(key))
            return memo.get(key);
        if (i == springs.length){
            if (j == sizes.length && block_size == 0)
                return 1;
            else if (j == sizes.length - 1 && sizes[j] == block_size)
                return 1;
            else
                return 0;
        
        }
        long ways = 0;
        char[] needed = {'.','#'};
        for (char c : needed){
            if (springs[i] == c || springs[i] == '?'){
                if (c == '.' && block_size == 0)
                    ways += dp(springs, sizes, memo, i+1, j, 0);
                else if (c == '.' && block_size > 0 && j < sizes.length && sizes[j] == block_size)
                    ways += dp(springs, sizes, memo, i+1, j+1, 0);
                else if (c == '#')
                    ways += dp(springs, sizes, memo, i+1, j, block_size+1);
            }
        }

        memo.put(key, ways);
        return ways;
    }

    public static int dp_2(char[] springs, int[] sizes, int[][] memo, int i, int j ){
        //valid way
        if (i == springs.length && j == sizes.length)
            return 1;
        if (i > springs.length || j > sizes.length)
            return 0;
        if (memo[i][j] != -1){
            return memo[i][j];
        }

        if (springs[i] == '?'){
            //must be contiguous, if we continue to take
            int ways = 0;
            int curr_size = sizes[j];
            int take_i = i;
            while ((take_i + 1 < springs.length) && (springs[take_i+1] == '?') && curr_size > 0){
                ways += dp_2(springs,sizes,memo,take_i + 1,j);
                take_i += 1;
                curr_size--;
            }
            //add next subroup
            ways += dp_2(springs,sizes,memo,take_i,j+1);
            //if we dont convert to spring, i.e we leave to . we move pointer but dont decrement the size




        }
        return 0;

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

    public static char[] getSprings(String l){
        char[] springs = l.split(" ")[0].toCharArray();
        //System.out.println(springs);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            result.append(springs);
            result.append("?");
        }

        result.append(springs);
        return result.toString().toCharArray();
    }

    public static int[] getSizes(String l){
        String[] split = l.split(" ")[1].split(",");
        int[] nums = new int[split.length*5];
        for (int i = 0; i < split.length*5; i++){
            nums[i] = Integer.parseInt(split[i % split.length]);
        }
        //System.out.println(Arrays.toString(nums));
        return nums;
    }

}
