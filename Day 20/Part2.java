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
         * part 2, same but add rxs
         * final machine responsible for moving the sand down to island is module named rx, turns on when single low pulse is sent to rx
         * find fewest number of button presses required to deliver a single low pulse to the modnule names rx
         * just the lcm for all paths
         * https://topaz.github.io/paste/#XQAAAQBxCAAAAAAAAAAzHIoib6pXbueH4X9F244lVRDcOZab5q1+VXn364pL+Sfnforeu8ifbPbDweKGkWmr+SrjWj2k6KP4yG50PuAp5MvrH2ikyKniid3mZ3oaPgkBf4w3pRd5w+2I6+AT5hKqG+tjO7Sp8RIL/0HykRrQFlmODOL/57NXgO5127FDtyqjmudO83DE6hiiGVZe+Fdcd0S/mUHfYsjYu/sSigrs6i+NmQFA6ZrbyaIyvv9HTBLdl4WVRl/cAiPBva17qC3V5jzhNXLfCqaiMuomF81HtZwGWvc2Nj07IS3SBuYedrJidrCM4HgVVxL1LB2oo8RBg4k0eGEEh0Ys4ra3QWb39Q/ZfsVZRcJGCBjTVboOeYKoQUF04MmDvOYq35Bp09zOZO+OSA0E8EC6cZCL32ES5gWTSzsa+J4eI+OGEhhglML4REskidMEykGDArD9i5jVu35cUDPa3gq7YGScZ203RMoJTKHe1f9BfP0BtjxfkgqqlkhXoTl4FgiyU85gPoIwptLo/hqYrjzHXHhz+xDfe7rdwYdnbLxxXswry7bkmnSVs5p3HprcrBxSrOFVYsL//6l2baZSfsWYiURrI+rVWnk1c8khS5OALTXV71sMLTK2t6wXAIqze54rs8WyuL0IGbceXxASVgNi3wLAl3T71/2tl3DntLxV5ZIZYKn34ASKxc5q25Dc/7GUmA8M7GKbNjQ/EQg6PN6LclKcNrmmRrKDyM7NQb8ItNo/qsNvajnwHBwN2PHXLQjggliF1PbYtlMOrrm8TdtOPWYWxdy8v0fqIYHiXvRpw/ntAIUPVq8jzjEjiub/VOGJcc0ph3icXmRksN8LmwaeE27k1XmW5qZQ+nIf+oh8X8Z4J4XjCFIQMFm7NKEEuVHLiiIFpQ155f9bdGSl0d6ehz4eX+sHVnFO1N2/x5Xirqz/BR0db48Rzc0307oybzgURPXVetS54udufOlvLNiYLdERsdjTFSXwbpjhfH2LDwO+NB46+44FPQpaozDnPP/pauVd
         * details here
         */
        HashMap<String, String[]> adj = new HashMap<>();
        HashMap<String, HashMap<String,Integer>> conjs = new HashMap<>();
        HashMap<String,Boolean> ffs = new HashMap<>();
        String file = "input.txt";
        String rx_conj = "";
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
            for (String d : dests){
                if (d.equals("rx")){
                    rx_conj = label;
                    break;
                }
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
        HashMap<String,Integer> rx_conj_presses = new HashMap<>();
        while (rx_conj_presses.size() < 4){
            press(adj, conjs, ffs, rx_conj_presses, low_pulses, high_pulses, presses,rx_conj);
        }
        int lcm = 1;

        for (int num : rx_conj_presses.values()){
            lcm = findLCM(lcm, num);
        }

        System.out.println(lcm);
        


        
    }
    public static void press(HashMap<String, String[]> adj, HashMap<String, HashMap<String,Integer>> conjs , HashMap<String,Boolean> ffs, HashMap<String,Integer> rx_conj_presses ,
    long[] low_pulses, long[] high_pulses, long[] presses, String rx_conj){
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

            if (label.equals("rx"))
                continue;

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

            //check if any of the inputs connected to the conjunction connected to rd are one and record number of presees
            for (Map.Entry<String,Integer> entry_1 : conjs.get(rx_conj).entrySet()){
                String label_1 = entry_1.getKey();
                int val_1 = entry_1.getValue();
                if (val_1 == 1 && (!rx_conj_presses.containsKey(label_1))){
                    rx_conj_presses.put(label, (int) presses[0]);
                }
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
    // Function to find the GCD (Greatest Common Divisor)
    public static int findGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Function to find the LCM (Least Common Multiple)
    public static int findLCM(int a, int b) {
        // LCM * GCD = |a * b|
        // LCM = |a * b| / GCD(a, b)
        return Math.abs(a * b) / findGCD(a, b);
    }

}
