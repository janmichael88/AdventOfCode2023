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
         * OOP question need part class and workflow class, then store the workflows as hashmap and send them
         * make class for part
         * need hashmap of workflow id to workflo steps
         * parts will always have xmas
         */
        String file = "input.txt";
        List<String> lines = getLines(file);
        int line_break = 0;
        for (int i = 0; i < lines.size(); i++){
            if (lines.get(i).isEmpty()){
                line_break = i;
                break;
            }
        }
        //split into wfs and parts
        HashMap<String,String> wfs = new HashMap<>();
        for (int i = 0; i < line_break; i++){
            String l = lines.get(i);
            String wf_id = l.split("\\{")[0];
            String steps = l.split("\\{")[1].split("\\}")[0];
            wfs.put(wf_id, steps);
        }
        //process parts
        int ans = 0;
        for (int i = line_break + 1; i < lines.size(); i++){
            Part part = make_part(lines.get(i));
            String start_wf = wfs.get("in");
            //keep doing until A or R
            //String next_wf = eval_workflow(start_wf, part);
            //System.out.println(next_wf);
            String next_wf = "*";
            while (!next_wf.equals("A") || !next_wf.equals("R")){
                next_wf = eval_workflow(start_wf, part);
                if (!wfs.containsKey(next_wf))
                    break;
                next_wf = wfs.get(next_wf);
                start_wf = next_wf;
            }

            if (next_wf.equals("A")){
                ans += part.sum();
            }
        }

        System.out.println(ans);

    }
    public static Part make_part(String l){
        l = l.substring(1, l.length()-1);
        String[] part_entries = l.split(",");
        Part part = new Part(0, 0, 0, 0);
        for (String p : part_entries){
            String part_id = p.split("=")[0];
            String part_val = p.split("=")[1];
            if (part_id.equals("x"))
                part.x = Integer.parseInt(part_val);
            else if (part_id.equals("m"))
                part.m = Integer.parseInt(part_val);
            else if (part_id.equals("a"))
                part.a = Integer.parseInt(part_val);
            else if (part_id.equals("s"))
                part.s = Integer.parseInt(part_val);     
        }
        return part;
    }
    public static String eval_workflow(String wf, Part curr_part){
        String[] steps = wf.split(",");
        //need to keep track of stesp
        int last_step = steps.length;
        for (int i = 0; i < last_step; i++){
            String curr_step = steps[i];
            //last step
            if (i == last_step - 1)
                return curr_step;
            else{
                String eval_expression = curr_step.split(":")[0];
                String next_wf = curr_step.split(":")[1];
                String lhs = eval_expression.split("[<>]")[0];
                String rhs = eval_expression.split("[<>]")[1];
                char op = eval_expression.charAt(1);
                //now do the conditions....ughhh
                if (lhs.equals("x") && op == '>'){
                    if (curr_part.x > Integer.parseInt(rhs))
                        return next_wf;
                }
                else if (lhs.equals("x") && op == '<'){
                    if (curr_part.x < Integer.parseInt(rhs))
                        return next_wf;
                }
                else if (lhs.equals("m") && op == '>'){
                    if (curr_part.m > Integer.parseInt(rhs))
                        return next_wf;
                }
                else if (lhs.equals("m") && op == '<'){
                    if (curr_part.m < Integer.parseInt(rhs))
                        return next_wf;
                }
                else if (lhs.equals("a") && op == '>'){
                    if (curr_part.a > Integer.parseInt(rhs))
                        return next_wf;
                }
                else if (lhs.equals("a") && op == '<'){
                    if (curr_part.a < Integer.parseInt(rhs))
                        return next_wf;
                }
                else if (lhs.equals("s") && op == '>'){
                    if (curr_part.s > Integer.parseInt(rhs))
                        return next_wf;
                }
                else if (lhs.equals("s") && op == '<'){
                    if (curr_part.s < Integer.parseInt(rhs))
                        return next_wf;
                }
                else{
                    continue;
                }
                
            }
        }
        return "";
    }
    public static class Part {
        public int x,m,a,s;
        
        public Part(int x, int m, int a, int s){
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
        }
        public void display(){
            System.out.println(this.x+" "+this.m+" "+this.a+" "+this.s);
        }
        public int sum(){
            return this.x + this.m + this.a + this.s;
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
