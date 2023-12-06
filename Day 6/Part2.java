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

public class Part2 {
    public static void main(String[] args) throws IOException{
        String file_path = "input.txt";
        /*
        now its just one race, with numbers concatenated
         */
        List<String> lines = getLines(file_path);
        long time = extractNumbers(lines.get(0), "\\d+");
        long dist = extractNumbers(lines.get(1), "\\d+");
        long ans = countWays(time, dist);
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

    public static long countWays(long time, long distance){
        long ways = 0;

        for (long i = 0; i <= time; i++){
            if (i*(time - i) > distance){
                ways++;
            }
        }

        return ways;
    }
    
    private static long extractNumbers(String input, String patt) {
        Pattern pattern = Pattern.compile(patt);
        Matcher matcher = pattern.matcher(input);
        String num = "";

        while (matcher.find()) {
            String number = matcher.group();
            num += number;
        }

        // Convert the list to an array
        return Long.parseLong(num);
    }





};