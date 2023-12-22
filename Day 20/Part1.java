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
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;

public class Part1 {
    public static void main(String[] args) throws IOException{
        /*
         * modules can send high or low pulses
         * can be sent fomr soure to destination modules
         * flip flop (%) are either on or off, they are initally off
         * high pulse they are ignored
         * low pulse it flips
         * 
         * conjunction (&) remember most recent pulse from received module
         * initally default to remembering a low pulse
         * when received it updates its last remember pulse
         * if it remembers high pulse it sends low pulse, else it sends high pulse
         * 
         * looks like bits
         * broadcast module is start and sends out pulses, 
         * single low pulse sent from broadcaster, cannot push button again until finishing broadcast - queue
         * low pulse will be 0, high pulse should be 1
         * this is just BFS but run 1000 times and get prdocut of (count low pulses)*high pulses
         */
        HashMap<String, String[]> adj = new HashMap<>();
        HashMap<String, HashMap<String,Integer>> conjs = new HashMap<>();
        HashMap<String,Boolean> ffs = new HashMap<>();
        String file = "input.txt";
        List<String> lines = getLines(file);
        for (String l : lines){
            String module = l.split(" -> ")[0];
            String[] dests = l.split(" -> ")[1].split(", ");
            char t = module.charAt(0);
            String label = module.substring(1);
            if (module.equals("broadcaster")){
                adj.put("broadcaster", dests);
            }
            else{
                adj.put(label, dests);
            }
            if (t == '&'){
                conjs.put(label, new HashMap<>());
            }
            if (t == '%'){
                ffs.put(label, false);
            }


        }
        for (Map.Entry<String,String[]> entry : adj.entrySet()){
            String label = entry.getKey();
            String[] dests = entry.getValue();
            for (String dest : dests){
                if (conjs.containsKey(dest)){
                    conjs.get(dest).put(label, 0);
                }
            }
        }

        long[] low_pulses = new long[1];
        long[] high_pulses = new long[1];
        long[] presses = new long[1];
        for (int i = 0; i < 1000; i++){
            press(adj, conjs, ffs, low_pulses, high_pulses, presses);
        }
        
        System.out.println(low_pulses[0]*high_pulses[0]);

        
    }
    public static void press(HashMap<String, String[]> adj, HashMap<String, HashMap<String,Integer>> conjs , HashMap<String,Boolean> ffs, 
    long[] low_pulses, long[] high_pulses, long[] presses){
        presses[0]++;

        low_pulses[0] += 1 + adj.get("broadcaster").length;
        Queue<Q_Entry> q = new ArrayDeque<>();
        for (String dest: adj.get("broadcaster")){
            Q_Entry entry = new Q_Entry(0, "broadcaster", dest);
            q.add(entry);
        }
        while (!q.isEmpty()){
            Q_Entry entry = q.poll();
            int pulse = entry.first;
            String src = entry.second;
            String label = entry.third;

            //conjunction
            //mark because it remmebers
            int to_send = 0;
            if (conjs.containsKey(label)){
                conjs.get(label).put(src, pulse);
                //check previous values
                for (int n : conjs.get(label).values()){
                    if (n == 0){
                        to_send = 1;
                        break;
                    }
                }
            }

            //flip flop
            if (ffs.containsKey(label)){
                if (pulse == 1)
                    continue;
                ffs.put(label, !ffs.get(label));
                if (ffs.get(label))
                    to_send = 1;
            }

            //increment low or high pulses
            if (to_send == 1)
                high_pulses[0] += adj.get(label).length;
            else{
                low_pulses[0] += adj.get(label).length;
            }

            //send pulse to destination ondes
            for (String dest : adj.get(label)){
                Q_Entry next_entry = new Q_Entry(to_send, label, dest);
                q.add(next_entry);
            }
        }



    }
    static class Q_Entry{
        int first;
        String second; 
        String third;

        public Q_Entry(int first, String second, String third){
            this.first = first; 
            this.second = second;
            this.third = third;
        }
        @Override
        public String toString(){
            return "(" + first + ", " + second + ", " + third + ")";
        }
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
