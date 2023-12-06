import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
         * for each line split into two lists
         * find insterscion and just get 2**(size)
         */
        int ans = 0;
        List<String> lines = getLines(file_path);
        for (String l : lines){
            ans += getPoints(l);
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
    //function to split line into two arrays, find intsersction and return ans
    public static int getPoints(String line){
        int points = 0;
        String[] split_line = line.split(":")[1].split("\\|");
        String[] winning_numbers = split_line[0].split(" ");
        String[] scratched_numbers = split_line[1].split(" ");
        HashSet<Integer> winning = new HashSet<>();
        HashSet<Integer> scratched = new HashSet<>();
        
        for (String num : winning_numbers){
            if (!num.isEmpty()){
                winning.add(Integer.parseInt(num));
            }
        }
        for (String num : scratched_numbers){
            if (!num.isEmpty()){
                scratched.add(Integer.parseInt(num));
            }
        }

        HashSet<Integer> intersection = new HashSet<>(winning);
        intersection.retainAll(scratched);

        int size = intersection.size();
        if (size == 0){
            return 0;
        }
        return (int) Math.pow(2, size-1);
    }

};
