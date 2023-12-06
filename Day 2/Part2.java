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

public class Part2 {
    public static void main(String[] args) {
        // Specify the path to the text file
        String filePath = "input.txt";

        // Call the readFile method to read and print the file contents
        /*
        for part 2, what is the fewest number of cubes of ech color that could have bee in the bag?
        power = sum of minimum cubes needed for that game
        return sum for all power
         */
        try {
            List<String> lines = readFile(filePath);
            int ans = 0;
            List<List<Integer>> counts = new ArrayList<>();
            for (String line : lines){
                // Define the pattern for matching games and entries
                int[] mins = {0,0,0};
                ans += parseLine(line,mins);
            
            }
            System.out.println(ans);
    }
 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    // Method to read and print the contents of a text file
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

    private static int parseLine(String line, int[] mins){
        String[] parts = line.split(":");
        //get game part
        String game_number = parts[0].split(" ")[1];
        String[] subsets = parts[1].split(";");
        for (String subset : subsets){
            //System.out.println(subset);
            String[] cubes_drawn = subset.split(", ");
            for (String cube_count : cubes_drawn){
                //System.out.println(cube_count.trim());
                cube_count = cube_count.trim();
                String[] cube_count_split = cube_count.split(" ");
                //System.out.println(cube_count_split[0]);
                int count_cubes = Integer.parseInt(cube_count_split[0]);
                String color = cube_count_split[1].trim();
                //keep counts in mins read as [R,G,B]
                if (color.equals("red")){
                    mins[0] = Math.max(mins[0],count_cubes);
                }

                else if (color.equals("green")){
                    mins[1] = Math.max(mins[1],count_cubes);
                }

                else if (color.equals("blue")){
                    mins[2] = Math.max(mins[2],count_cubes);
                }

                
            }
        }
        //product
        int power = 1;
        for (int num : mins){
            if (num != 0){
                power *= num;
            }
        }
        //System.out.println(power);
        return power;
    }
}