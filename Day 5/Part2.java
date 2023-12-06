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
        part 2 is the same, but insteaf of seeds, we have sead pairs
         */
        List<String> lines = getLines(file_path);
        List<Long> seeds = getSeeds(lines.get(0));
        //System.out.println(mapper(lines, 3, 4, 13)); //indexing into lines must be 0 indexed
     
        //for test input 
        /* 
        int[][] mapp_idxs = {
            {4,5},{8,10},{13,16},{19,20},{23,25},{28,29},{32,33}
        };
        */
    
         
        //need them for  actual input 
        int[][] mapp_idxs = {
            {4,36},{39,79},{82,123},{126,152},{155,192},{195,204},{210,235}
        };
        List<long[]> seed_ranges = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i += 2){
            long seed = seeds.get(i);
            long range = seeds.get(i+1);
            long[]temp = {seed,range};
            seed_ranges.add(temp);
        }

        long location = 0;
        while (true){
            long curr_location = location;
            //need to go backwards from locataion
            //and you need to reverse the direction of the mapp
            for (int i = mapp_idxs.length - 1; i >= 0; i--){
                int[] pair = mapp_idxs[i];
                curr_location = reverse_mapper(lines, pair[0] - 1, pair[1] - 1, curr_location);
            }

            for (long[] temp : seed_ranges){
                long s = temp[0];
                long e = temp[1];
                if (curr_location >= s && curr_location < e + s){
                    System.out.println(location);
                    return;
                }
            }

            location++;
        }

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

    public static List<Long> getSeeds(String l){
        String[] seeds = l.split(":")[1].split(" ");
        int size = seeds.length;
        List<Long> seeds_array = new ArrayList<>();
        for (String s : seeds){
            if (s != ""){
                seeds_array.add(Long.parseLong(s.trim()));
            }
            
        }

        return seeds_array;
    }

    public static long mapper(List<String> lines, int start, int end, long source ){
        long ans = 0;
        int size = end - start + 1;
        long[] sources = new long[size];
        long[] dests = new long[size];
        long[] ranges = new long[size];

        for (int i = 0; i < size; i++){
            String[] line_split = lines.get(i+start).split("\\s+");
            sources[i] = Long.parseLong(line_split[1]);
            dests[i] = Long.parseLong(line_split[0]);
            ranges[i] = Long.parseLong(line_split[2]);
            
        }

        //generate hashmap
        //this part takes two long
        //find the ranges and check of sourse is in there

        for (int i = 0; i < size; i ++){
            if (sources[i] <= source &&  source < sources[i] + ranges[i]){
                //in range and need steps from dest
                return source - sources[i] + dests[i];
            }
        }

        return source;
        /* 
        HashMap<Long,Long> mapp = new HashMap<>();
        for (int i = 0; i < size; i++){
            long s = sources[i];
            long e = dests[i];
            for (int j = 0; j < ranges[i]; j++){
                mapp.put(s + j, e + j);
            }
        }

        return mapp.getOrDefault(source, source);
        */
    }
    //never reverse mapper
    public static long reverse_mapper(List<String> lines, int start, int end, long source ){
        long ans = 0;
        int size = end - start + 1;
        long[] sources = new long[size];
        long[] dests = new long[size];
        long[] ranges = new long[size];

        for (int i = 0; i < size; i++){
            String[] line_split = lines.get(i+start).split("\\s+");
            sources[i] = Long.parseLong(line_split[1]);
            dests[i] = Long.parseLong(line_split[0]);
            ranges[i] = Long.parseLong(line_split[2]);
            
        }

        //generate hashmap
        //this part takes two long
        //find the ranges and check of sourse is in there

        for (int i = 0; i < size; i ++){
            if (dests[i] <= source &&  source < dests[i] + ranges[i]){
                //in range and need steps from dest
                return source - dests[i] + sources[i];
            }
        }

        return source;




}
}
