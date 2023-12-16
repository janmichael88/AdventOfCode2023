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


public class Part1 {
    public static void main(String[] args) throws IOException{
        String filepath = "input.txt";
        /*
         * hasing algo is
         * inrease the current value of the acii code you just determines
         * set curent value to itself multiplie by 17
         * set curent vale to reaminder 256
         */
        List<String> lines = getLines(filepath);
        String[] codes = lines.get(0).split(",");
        int ans = 0;
        for (String c : codes){
            ans += getReindeerHash(c);
        }
        System.out.println(ans);

    }

    public static int getReindeerHash(String code){
        int ans = 0;
        for (char ch : code.toCharArray()){
            int curr_val = (int) ch;
            ans += curr_val;
            ans *= 17;
            ans %= 256;
        }
        return ans;
    }

    public static List<String> getLines(String filepath) throws FileNotFoundException, IOException{
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            //process each
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
