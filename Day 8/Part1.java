import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
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
import java.util.ArrayDeque;
import java.util.Deque;


public class Part1 {
    public static void main(String[] args) throws IOException{
        String file_path = "input.txt";
        /*
        given char[] of directions, find min steps to get from AAA to ZZZ
         */
        List<String> lines = getLines(file_path);
        String directions = lines.get(0);
        HashMap<String, List<String>> graph = makeGraph(lines);
        long ans = countSteps(directions, graph);
        System.out.println(ans);

    }
    public static List<String> getLines(String filepath) throws FileNotFoundException, IOException{
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                lines.add(line);
            }
        }
        return lines;
    }

    public static HashMap<String,List<String>> makeGraph(List<String> lines){
        HashMap<String, List<String>> graph = new HashMap<>();
        String regex = "\\b\\w{3}\\b";
        for (int i = 2; i < lines.size(); i++){
            String line = lines.get(i);
            List<String> nodes = new ArrayList<>();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);
    
            while (matcher.find()) {
                String match = matcher.group();
                nodes.add(match);
            }
            String source = nodes.get(0);
            List<String> neighs = nodes.subList(1, 3);
            graph.put(source, neighs);
        }
        return graph;
    }

    public static long countSteps(String directions, HashMap<String,List<String>> graph){
        long ans = 0;
        String curr = "AAA";
        int ptr = 0;

        while (!curr.equals("ZZZ")){
            System.out.println(curr + " " + ptr);
            //go left
            if (directions.charAt(ptr) == 'L'){
                curr = graph.get(curr).get(0);
            }
            else{
                //go right
                curr = graph.get(curr).get(1);
            }
            ptr = (ptr + 1) % directions.length();
            ans += 1;
        }
        return ans;
    }
   
};
