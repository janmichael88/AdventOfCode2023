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
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Collections;



public class Part1 {
    public static void main(String[] args) throws IOException{
        String file_path = "input.txt";
        /*
        this is an ordering problem and we need to use custom comparator
        five of kind > four of kind > full house > three of kind > two pair > one pair > high card
        cast types as numbers 7
        7 > 6 > 5 .... > 1
        tie breakers is largest lexographical string
        order is A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2
         */
        List<String> lines = getLines(file_path);
        List<Card> all_cards = new ArrayList<>();
        for (String l : lines){
            String[] l_split = l.split(" ");
            String hand = l_split[0];
            int bid = Integer.parseInt(l_split[1]);
            int type = getCardType(hand);
            all_cards.add(new Card(type,hand,bid));
        }
        // Sort the list using the custom comparator
        Collections.sort(all_cards, (card1, card2) -> {
            // Compare based on type
            if (card1.type != card2.type) {
                return Integer.compare(card1.type, card2.type);
            }

            // If types are the same, compare based on hand
            int handComparison = compareEntireHands(card1.hand, card2.hand);
            if (handComparison != 0) {
                return -handComparison;
            }

            // If types and hands are the same, you can add additional comparisons if needed
            // For example, compare based on bid

            return 0; // Cards are considered equal
        });

        int ans = 0;
        for (int i = 0; i < all_cards.size(); i++){
            Card card = all_cards.get(i);
            //System.out.println(card.hand + " " + card.bid + " " + (i+1));
            ans += (i+1)*card.bid;
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
    static class Card {
        int type;
        String hand;
        int bid;

        public  Card(int type, String hand, int bid) {
            this.type = type;
            this.hand = hand;
            this.bid = bid;
        }
    }

    public static boolean isGreater(Card card1, Card card2) {
        // Compare based on type
        if (card1.type != card2.type) {
            return card1.type > card2.type;
        }

        // If types are the same, compare based on hand
        int handComparison = compareEntireHands(card1.hand, card2.hand);
        if (handComparison != 0) {
            return handComparison > 0;
        }

        // If types and hands are the same, you can add additional comparisons if needed
        // For example, compare based on bid

        return false; // Cards are considered equal
    }

    public static int compareHands(String hand1, String hand2) {
        // Define the order of hands
        String order = "AKQJT98765432";

        // Compare based on the index in the order string
        int result = Integer.compare(order.indexOf(hand1.charAt(0)), order.indexOf(hand2.charAt(0)));

        if (result == 0) {
            // First cards have the same label, compare the entire hands
            result = compareEntireHands(hand1, hand2);
        }

        return result;
    }

    private static int compareEntireHands(String hand1, String hand2) {
        // Compare each card in the hands
        String order = "AKQJT98765432";
        for (int i = 0; i < hand1.length(); i++) {
            int cardComparison = Integer.compare(order.indexOf(hand1.charAt(i)), order.indexOf(hand2.charAt(i)));
            if (cardComparison != 0) {
                return cardComparison;
            }
        }

        // All cards are equal
        return 0;
    }


    

    public static int getCardType(String l){
        HashMap<Character,Integer> counts = new HashMap<>();

        for (char c : l.toCharArray()){
            counts.put(c,counts.getOrDefault(c, 0) + 1);
        }
        //count of counts!
        HashMap<Integer,Integer> count_of_counts = new HashMap<>();
        for (int count : counts.values()){
            count_of_counts.put(count, count_of_counts.getOrDefault(count, 0) + 1);
        }
        //five of kind
        if (count_of_counts.getOrDefault(5, 0) == 1)
            return 7;
        //4 of kind
        else if (count_of_counts.getOrDefault(4, 0) == 1)
            return 6;
        //full house
        else if (count_of_counts.getOrDefault(3, 0) == 1 && count_of_counts.getOrDefault(2, 0) == 1)
            return 5;
        //three of a kind
        else if (count_of_counts.getOrDefault(3, 0) == 1)
            return 4;
        //two pair
        else if (count_of_counts.getOrDefault(2, 0) == 2)
            return 3;
        //one pair
        else if (count_of_counts.getOrDefault(2, 0) == 1)
            return 2;
        else
            return 1;

    }





};
