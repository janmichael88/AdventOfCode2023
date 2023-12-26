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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.Comparator;
import java.util.Deque;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Iterator;


public class Part2{

    public static void main(String[] args) throws IOException{
        /*
         * for part 2, its pretty much the same problem, but now the the slopes are just dots
         * so we need to check all 4 directions, problem is longest path in graph is NP hard
         * we need to do edge contraction, and prune the serach in order for naive dfs to work
         * basically we only need to dfs on a node that has two neighbords only
         * you basically dont need to explore all paths, which is kinda of a dumb huertistic
         * 
         * intuition, instead of falling each node in one step, just walk all the way down the hallway until we hit a wall
         * check out these
         * https://topaz.github.io/paste/#XQAAAQC8BwAAAAAAAAAzHIoib6pMX4ickc1Mep93pgqtuV6rGB3/t2SKtfp5xR3hNc6GRwmjRXNiUgRBiz4gFYkm4PrDS5YAxmO7eRj0qI9WyrszuxCe5V1RunBfyr8VIAwoxnksvb1DP/q8kIGgUA9gU9ht9UlqwOpKowGq1ze7AeH+5W+hzsUODbIv3ghhuAzq3Zcv19ryiQfZoInKKPON10liefoJhvQ1XATcBp8tL9BHAxONfM0+kOJf23k3cZzpIz1oJQuPJJVjyjY5SMnwz1K7ySs62dHi+2bJXvJUGNdYv32gP7JOB9hb3XT5ONoAYpkB2Juy8dhNrcwWhj6Ql3ZiSRpjtim2gSNq1jJomI7zPDsodf03LJZM4SCC1Yc3v/xi04qi2wzEwVG21UCFVv4nz0dCvyHdsrpCNyjlWtl0AwRBVwyQDZ7UHNO6YGYoiwzzW2ghlHKCgW1SM058zH79USkE2W/+I+kqQ1o5KiuOnS3EGdH6g8+mJ0Bqg1PBUaQXuOlKSn6jgmQh53OOl2W5vs4ndO532WyeWtrIaI0cGCpRH6YnYlGJirUt8hBmYWVPn7Rnn8YHcxW17GxsqIz9h3ehs7+U7ajdSs5PC8gTCrHM6uFy0cpx0srOJ68Q4nrI7Fpi4dz9fEGFLDlMMwmKeKonEfWZtSA1+1wW0B8dhZ++qq5XDhXionVoPV/GBu8v6HY1MRthRuUq8WV8Fca02Jx1YMdzJ3zVf+kC61ImSbE0QXvXIafQwIRMtykD7zyHUjFHxC978WCauNuM5SWGECAA2ZvRjJGioKX24uxK74ofkn0cbBeg3RF76DGj6Fgf3JqtbyaQ8/82DNV7mlXIhUFnIE3dECjs3iMk0pazxvj7uedD4bulIxakNaW1xCcaobo/DW+jJJNd9n1jLc7UlSyd1SSwyLpFGwyNKrvIeey80YUen8ohK4CNOgEtqYwas5SqyVcq2ylwfFnStOPpF3H6oIU1TE2/MQMdFI28qTIL0j13OBcMLN/Sv92bGMXyfIcnkG5X77OfOlyt/vrH+0I=
         * https://gist.github.com/qwewqa/00d8272766c2945f4aa965ea36dba7f5
         */
        String file = "test_input.txt";
        List<String> lines = getLines(file);
        char[][] grid = getGrid(lines);
        int rows = grid.length;
        int cols = grid[0].length;

        //make new graph and only keep track of ndoes that ahve to neighbors
        Map<Integer[], List<int[]>> graph = new HashMap<>();
        HashSet<int[]> v = new HashSet<>();

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if (grid[i][j] != '#'){
                    List<int[]> neighs = new ArrayList<>();
                    int[][] dirrs = {{1,0},{-1,0},{0,1},{0,-1}};
                    for (int[] d : dirrs){
                        int neigh_x = i + d[0];
                        int neigh_y = j + d[1];
                        if (neigh_x >= 0 && neigh_x < grid.length && neigh_y >= 0 && neigh_y < grid[0].length){
                            int[] neigh_entry = {neigh_x,neigh_y};
                            neighs.add(neigh_entry);
                        }
                    }
                    if (neighs.size() > 2){
                        int[] neigh_entry = {i,j};
                        v.add(neigh_entry);
                    }
                }
            }
        }
        int[] start_state = {0,1};
        int[] end_state = {rows-1,cols-2};
        v.add(start_state);
        v.add(end_state);

        Iterator<int[]> iterator = v.iterator();
        while (iterator.hasNext()){
            int[] entry = iterator.next();
            int x = entry[0];
            int y = entry[1];
            
            Deque<int[]> q = new ArrayDeque<>();
            int[] temp = {x,y,0};
            q.add(temp);
            HashSet<int[]> seen = new HashSet<>();
            int[] temp2 = {x,y};
            seen.add(temp2);
            while (!q.isEmpty()){
                int[] curr_entry = q.pollFirst();
                int[][] dirrs = {{1,0},{-1,0},{0,1},{0,-1}};
                for (int[] d : dirrs){
                    int neigh_x = curr_entry[0] + d[0];
                    int neigh_y = curr_entry[1] + d[1];
                    int[] key = {neigh_x,neigh_y};
                    if (!seen.contains(key)){
                        if (graph.containsKey(key)){
                            int[] graph_entry = {neigh_x,neigh_y,curr_entry[0]};
                            graph.get(key).add(graph_entry);
                            int[] temp3 = {neigh_x,neigh_y};
                            v.add(temp3);
                        }
                        else{
                            seen.add(key);
                            //incremnt distance
                            int[] abc = {neigh_x,neigh_y,curr_entry[2] + 1};
                            q.add(abc);
                        }
                    }
                }
            }
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

    public static char[][] getGrid(List<String> lines){
        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                grid[i][j] = lines.get(i).charAt(j);
            }
        }

        return grid;
    }
}
