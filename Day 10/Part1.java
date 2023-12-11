import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.Deque;

public class Part1 {
    public static  List<String> grid;
    public static int rows,cols;
    public static boolean[][] visited;
    public static int[][] distances;
    public static void main(String[] args) throws IOException{
        String file_path = "input.txt";
        /*
        this is a dfs problem
        S is starting point
        basically we want the longest path starting from better to do BFS
        for each piece check the possile directions, there are really only two directions for reach
        need to think  about how i can effeicnielty move
         */
        grid = getLines(file_path);
        rows = grid.size();
        cols = grid.get(0).length();
        // Create a 2D map
        char[][] map = new char[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                map[i][j] = grid.get(i).charAt(j);
            }
        }
        distances = new int[rows][cols];
        for (int i = 0; i < rows; i++){
            Arrays.fill(distances[i], -1);
        }

        //mak adjlist
        Map<int[], List<int[]>> adj_list = new HashMap<>();
        int start_i = -1;
        int start_j = -1;
        int[][] start_adj;

        //adjlist
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                char pipe = map[i][j];
                List<int[]> neighs = new ArrayList<>();
                if (pipe == '|'){
                    int[] temp = {i-1,j};
                    int[] temp2 = {i+1,j};
                    neighs.add(temp);
                    neighs.add(temp2);
                }
                else if (pipe == '-'){
                    int[] temp = {i,j-1};
                    int[] temp2 = {i,j+1};
                    neighs.add(temp);
                    neighs.add(temp2);
                }
                else if (pipe == 'L'){
                    int[] temp = {i-1,j};
                    int[] temp2 = {i,j+1};
                    neighs.add(temp);
                    neighs.add(temp2);
                }
                else if (pipe == 'J'){
                    int[] temp = {i-1,j};
                    int[] temp2 = {i,j-1};
                    neighs.add(temp);
                    neighs.add(temp2);
                }
                else if (pipe == '7'){
                    int[] temp = {i+1,j};
                    int[] temp2 = {i,j-1};
                    neighs.add(temp);
                    neighs.add(temp2);
                }
                else if (pipe == 'F'){
                    int[] temp = {i+1,j};
                    int[] temp2 = {i,j+1};
                    neighs.add(temp);
                    neighs.add(temp2);
                }
                else if (pipe == 'S'){
                    start_i = i;
                    start_j = j;
                }
                for (int[] neigh : neighs){
                    int x = neigh[0];
                    int y = neigh[1];
                    if (x >= 0 && x < rows && y >= 0 && y < cols){
                        int[] curr = {i,j};
                        if (!adj_list.containsKey(curr))
                            adj_list.put(curr, new ArrayList<>());
                        adj_list.get(curr).add(neigh);
                    }
                }
            }
        }

        List<int[]> adj_start = new ArrayList<>();
        for (int[] vert : adj_list.keySet()){
            for (int[] vert2 : adj_list.get(vert)){
                if (Arrays.equals(vert, vert2))
                    adj_start.add(vert);
            }
        }

        int[] start_cell = {start_i,start_j};
        if (!adj_list.containsKey(start_cell))
            adj_list.put(start_cell, new ArrayList<>());
        adj_list.put(start_cell, adj_start);

        Queue<int[]> q = new ArrayDeque<>(); //list entry (x,y,dist)
        int[] entry = {start_i,start_j,0};
        q.add(entry);
        distances[start_i][start_j] = 0;
        int ans = 0;
        while (!q.isEmpty()){
            int[] curr_entry = q.poll();
            int curr_x = curr_entry[0];
            int curr_y = curr_entry[1];
            int[] key_cell = {curr_x,curr_y};
            int dist = curr_entry[2];
            for (int[] neigh : adj_list.get(key_cell)){
                int neigh_x = neigh[0];
                int neigh_y = neigh[1];
                if (distances[neigh_x][neigh_y] == -1){
                    distances[neigh_x][neigh_y] = distances[curr_x][curr_y] + 1;
                    int[] next_entry = {neigh_x,neigh_y,dist+1};
                    ans = Math.max(ans, dist+1);
                    q.add(next_entry);
                }
            }
        }

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
   

};
