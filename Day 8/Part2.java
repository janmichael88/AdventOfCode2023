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
import java.util.Queue;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.ArrayDeque;
import java.util.Deque;
import java.math.BigInteger;



public class Part2 {
    public static void main(String[] args) throws IOException{
        String file_path = "input.txt";
        /*
        multi point BFS, start at all nodes ending at A, level by level for direction
        keep going until we have nothing in the queue
        we need all the nodes in the quueu to end in Z, if they dont, we keep going
        https://git.sr.ht/%7Eawsmith/advent-of-code/tree/2023-ruby/item/lib/day08.rb
        turns out its least common multiple for all steps with start nodes A
        assumption is that its the LCM for each path

        say we have 3 nodes ending with A
        the first path takes 4 steps
        the second path takes 15 steps
        the third path takes 20 steps
        for any path, we can retraverse it full again to get to the end
        so when will all paths hit z?
            its the LCM of all path lengths!

        also we dont need to hit 'ZZZ' for part Z, just ending in 'Z'
         */
        List<String> lines = getLines(file_path);
        String directions = lines.get(0);
        HashMap<String, List<String>> graph = makeGraph(lines);

        //init starting nodes
        List<Long> q = new ArrayList<>();
        for (String node : graph.keySet()){
            if (node.charAt(2) == 'A'){
                long steps = countSteps(directions, graph, node);
                System.out.println(steps);
                q.add(steps);
            }
        }

        //get LCM
        BigInteger lcm = BigInteger.valueOf(1);
        for (long step : q){
            lcm = calculateLCM(lcm, BigInteger.valueOf(step));
        }

        System.out.println(lcm);

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

    public static long countSteps(String directions, HashMap<String,List<String>> graph, String curr){
        long ans = 0;
        int ptr = 0;

        while (curr.charAt(2) != 'Z'){
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

    public static long BFS(String directions, HashMap<String,List<String>> graph, Queue<String> q){
        long ans = 0;
        int ptr = 0;

        while (!allZs(q)){
            int size = q.size();
            System.out.println(q.toString() + " " + ans);
            for (int i = 0; i < size; i++){
                String curr = q.poll();
                if (directions.charAt(ptr) == 'L')
                    curr = graph.get(curr).get(0);
                else
                    //go right
                    curr = graph.get(curr).get(1);
                q.add(curr);
            }
            ptr = (ptr + 1) % directions.length();
            ans++;
        }

        return ans;
    }

    public static boolean allZs (Queue<String> q){
        for (String node : q){
            if (node.charAt(2) != 'Z')
                return false;
        }
        return true;
    }

    private static BigInteger calculateLCM(BigInteger a, BigInteger b) {
        // |a * b| / gcd(a, b)
        BigInteger absProduct = a.abs().multiply(b.abs());
        BigInteger gcd = a.gcd(b);
        return absProduct.divide(gcd);
    }

   
};
