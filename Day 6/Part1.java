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

public class Part1 {
    public static void main(String[] args) throws IOException{
        String file_path = "input.txt";
        /*
        for each time,simulate pressing and relasing, inputs are small enough
         */
        List<String> lines = getLines(file_path);
        int[] times = extractNumbers(lines.get(0), "\\d+");
        int[] dists = extractNumbers(lines.get(1), "\\d+");
        int ans = 1;
        for (int i = 0; i < times.length; i++){
            int ways = countWays(times[i], dists[i]);
            if (ways != 0){
                ans *= ways;
            }
        }

        System.out.println(ans);

    }
    public static List<String> getLines(String filepath) throws FileNotFoundException, IOException{
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;

            // Read and print each line from the file
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                lines.add(line);
            }
        }
        return lines;
    }

    public static int countWays(int time, int distance){
        int ways = 0;

        for (int i = 0; i <= time; i++){
            if (i*(time - i) > distance){
                ways++;
            }
        }

        return ways;
    }
    
    private static int[] extractNumbers(String input, String patt) {
        Pattern pattern = Pattern.compile(patt);
        Matcher matcher = pattern.matcher(input);
        List<Integer> numbersList = new ArrayList<>();

        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group());
            numbersList.add(number);
        }

        // Convert the list to an array
        return numbersList.stream().mapToInt(Integer::intValue).toArray();
    }





};