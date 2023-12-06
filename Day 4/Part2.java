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
        for part 2, for every match of winnin number, we also win an in stance of that card
        not only do we get the card, but we also get the matched
        only up to the limit of number of cards
        keep count array of cards, everytime we win increment count and also the matched ones
        ans is the sum of the count array

         */
        int ans = 0;
        List<String> lines = getLines(file_path);
        int num_cards = lines.size();
        int[] count_card_wins = new int[num_cards+1];
        for (String l : lines){
            getPoints(l,count_card_wins, num_cards);
        }
        //System.out.println(Arrays.toString(count_card_wins));
        for (int num : count_card_wins){
            ans += num;
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
    public static int getPoints(String line, int[] counts, int num_cards){
        //get card number
        int card_number = Integer.parseInt(line.split(":")[0].split("Card")[1].trim());
        System.out.println(card_number);
        String[] split_line = line.split(":")[1].split("\\|");
        String[] winning_numbers = split_line[0].split(" ");
        String[] scratched_numbers = split_line[1].split(" ");
        HashSet<Integer> winning = new HashSet<>();
        HashSet<Integer> scratched = new HashSet<>();
        for (String num : winning_numbers){
            if (!num.trim().isEmpty()){
                winning.add(Integer.parseInt(num));
            }
        }
        for (String num : scratched_numbers){
            if (!num.trim().isEmpty()){
                scratched.add(Integer.parseInt(num));
            }
        }

        HashSet<Integer> intersection = new HashSet<>(winning);
        intersection.retainAll(scratched);

        int size = intersection.size();

        //always incremnt the count for the current number
        counts[card_number]++;
        //add card wins to count arrays
        if (size != 0){
            for (int c = card_number+1; c <= Math.min(card_number + size, num_cards); c++){
                counts[c]++;
            }
            //if we have copies for this card
            int copies = counts[card_number] - 1;
            for (int c = card_number + 1; c<= Math.min(card_number + size, num_cards); c++){
                counts[c] += copies;
        }
    }




        return (int) Math.pow(2, size-1);
    }

};
