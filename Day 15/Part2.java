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
import java.util.LinkedHashMap;

public class Part2{
    public static void main(String[] args) throws IOException{
        String filepath = "input.txt";
        /*
         * hasing algo is
         * codes are split into two
         * anything before a = or - is the box that we want, i.e (hash(code)) = box number
         * hashmap problem
         * - operator: gp tp relevant box and remove the lens with the given label if one is present, 
         * then move anyt of the remanin lenses as far fowards in the box as the can go without changing order
         * fillin in any space made by removing the indicated lens
         * = operation, will be followed by value for focal length of lens that needs to go into the relevant box
            two scenarios for + 
            1. if there is already a lens in the box with lable, replace old lense with new lens, remove old lends and put new lens in place, DONT move others
            2. if there is no lens, add lens to the box immediately behind any lense already in the box
        we can use hashmap, and find the box id
        entries should mapp to some orderedSet, that allows fast removal, this is easy to do in python
        order matters when adding in lens
         */
        List<String> lines = getLines(filepath);
        String[] codes = lines.get(0).split(",");
        Map<Integer, LinkedHashMap<String,Integer>> mapp = new LinkedHashMap<>();
        for (String c : codes){
            char op = getOp(c);
            int box = getBox(c);
            int lens = 0;
            String label = getLabel(c);
            if (op == '='){
                lens = getLens(c);
            }
            if (op == '-'){
                if (!mapp.containsKey(box))
                    continue;
                if (mapp.get(box).containsKey(label))
                    mapp.get(box).remove(label);
            }

            else if (op == '='){
                //add new entry if its not in there
                if (!mapp.containsKey(box)){
                    mapp.put(box, new LinkedHashMap<>());
                }
                //check label in box, and replace
                if (mapp.get(box).containsKey(label)){
                    mapp.get(box).put(label,lens);
                }
                else{
                    mapp.get(box).put(label,lens);
                }     
            }
        }
        //travese and get power of each lens
        int power = 0;
        for (Map.Entry<Integer, LinkedHashMap<String,Integer>> entry : mapp.entrySet()){
            //System.out.println(entry.getKey());
            int box_id = entry.getKey();
            int curr_pos = 1;
            LinkedHashMap<String,Integer> innermap = entry.getValue();
            for (Map.Entry<String,Integer> innerEntry : innermap.entrySet()){
                int focal_length = innerEntry.getValue();
                //System.out.println(innerEntry.getKey()+" "+innerEntry.getValue());
                power += (box_id + 1)*(curr_pos)*(focal_length);
                curr_pos++;
            }
        }

        System.out.println(power);



    }
    public static String getLabel(String l){
        if (getOp(l) == '-'){
            return l.split("-")[0];
        }
        else
            return l.split("=")[0];
    }
    public static int getLens(String l){
        return Integer.parseInt(l.split("=")[1]);
    }

    public static int getBox(String l){
        if (getOp(l) == '-'){
            return getReindeerHash(l.split("-")[0]);
        }
        else
            return getReindeerHash(l.split("=")[0]);
    }
    public static char getOp(String l){
        char equals = '=';
        char minus = '-';
        if (l.contains(String.valueOf(equals)))
            return equals;
        else
            return minus;
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
