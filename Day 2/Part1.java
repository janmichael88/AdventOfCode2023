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
        // Specify the path to the text file
        String filePath = "input.txt";

        // Call the readFile method to read and print the file contents
        /*
        cubes can be red, green, blue, each time we play he hides a secret number of ubces of each color in the bag
        each game consists of elf drawing certain number of cubes
        is a game possible if we have 12 red, 13 green, 14 blue
        determine which games would have been possible and return the sum of the ids
         */
        try {
            List<String> lines = readFile(filePath);
            //convert input into array list, 0 each contain counts of (counts R, counts, counts B)
            int red = 12;
            int green = 13;
            int blue = 14;
            int ans = 0;
            List<List<Integer>> counts = new ArrayList<>();
            for (String line : lines){
                // Define the pattern for matching games and entries
                ans += parseLine(line,red,green,blue);
            
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

    private static int parseLine(String line, int red, int green, int blue){
        int[] parsed = new int[3];
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
                if (color.equals("red") && count_cubes > red){
                    return 0;
                }

                else if (color.equals("green") && count_cubes > green){
                    return 0;
                }

                else if (color.equals("blue") && count_cubes > blue ){
                    return 0;
                }

                
            }
        }

        return Integer.parseInt(game_number);
    }
}