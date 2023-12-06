import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Day1_Puzzle2 {
    public static void main(String[] args) {
        // Specify the path to the text file
        //String filePath = "Day1_Puzzle1_Input.txt";
        String filePath = "additional_test.txt";

        // Call the readFile method to read and print the file contents
        /*
         * now they can be words
         * for each line search for all starting indices for 'one', 'two', 'three' ...'nine'
         * then sort the indices
         * actual numbers count two
         */
        try {
            List<String> lines = readFile(filePath);
            Map<String, Integer> numberMap = new HashMap<>();

            numberMap.put("one", 1);
            numberMap.put("two", 2);
            numberMap.put("three", 3);
            numberMap.put("four", 4);
            numberMap.put("five", 5);
            numberMap.put("six", 6);
            numberMap.put("seven", 7);
            numberMap.put("eight", 8);
            numberMap.put("nine", 9);

            List<String> stringList = new ArrayList<>();

            stringList.add("one");
            stringList.add("two");
            stringList.add("three");
            stringList.add("four");
            stringList.add("five");
            stringList.add("six");
            stringList.add("seven");
            stringList.add("eight");
            stringList.add("nine");
            int ans = 0;
            for (String line : lines){
                //System.out.println(line);
                //read forwards first and backwards

                //check all word numbers
                List<Object[]> entries = new ArrayList<>();
                for (String number : stringList){
                    int index = line.indexOf(number);
                    if (index != -1){
                        Object[] entry = {index,number};
                        //System.out.println(entry);
                        entries.add(entry);

                    }
                    //look for the actual number
                    index = line.indexOf(String.valueOf(numberMap.get(number)));
                    if (index != -1){
                        Object[] entry = {index,number};
                        //System.out.println(entry);
                        entries.add(entry);

                    }
                
                }

                //Sort
                Collections.sort(entries, Comparator.comparingInt(o -> (int) o[0]));
                //get first and list 
                /* 
                for (Object[] tuple : entries) {
                    System.out.println("(" + tuple[0] + ", " + tuple[1] + ")");
                }
                System.out.println("______________-");
                */

                int size = entries.size();
                Object[] first = entries.get(0);
                Object[] last = entries.get(size-1);
                String a = (String) first[1];
                String b = (String) last[1];
                
                //get mapping
                String a_mapp = Integer.toString(numberMap.get(a));
                String b_mapp = Integer.toString(numberMap.get(b));
                String concatenatedString = a_mapp + b_mapp;
                int result = Integer.parseInt(concatenatedString);
                //System.out.println(result);
                ans += result;

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
}