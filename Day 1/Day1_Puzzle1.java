import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day1_Puzzle1 {
    public static void main(String[] args) {
        // Specify the path to the text file
        String filePath = "Day1_Puzzle1_Input.txt";

        // Call the readFile method to read and print the file contents
        /*
         * get first and list digits in each line
         */
        try {
            List<String> lines = readFile(filePath);
            int ans = 0;
            for (String line : lines){
                //System.out.println(line);
                //read forwards first and backwards
                char first_digit = '1';
                char last_digit = '1';

                for (int i = 0; i < line.length(); i++){
                    char curr_char = line.charAt(i);
                    if (Character.isDigit(curr_char)){
                        first_digit = curr_char;
                        break;
                    }
                }

                for (int i = line.length() - 1; i >= 0; i--){
                    char curr_char = line.charAt(i);
                    if (Character.isDigit(curr_char)){
                        last_digit = curr_char;
                        break;
                    }
                }
                
                String concatenatedString = Character.toString(first_digit) + Character.toString(last_digit);
                int result = Integer.parseInt(concatenatedString);
                //System.out.println(result);
                ans += result;


            }

            System.out.println(ans);
        } catch (IOException e) {
            // Handle exceptions, e.g., file not found or unable to read
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
}