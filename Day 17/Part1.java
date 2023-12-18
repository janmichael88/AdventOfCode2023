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
         * need to move from top left to bottom right wit minimum heat loss
         * no heat loss at beginning (i can just subtract that ouf at the end)
         * we can only move at most three blocks in a single direction before truning 90 de left or right
         * cant reverse direction can only go left, right or straight
         * we can only go down and right
         * states are cell (i,j), and steps going remaing down and right
         * need to use dijsktras, care ful with state spaces
         * the thing is the all the numbers are positive, so taking more steps always gives a bigger answer
         * during djisktras, the first time we hit the end we are done, we dont need to keeo track of states by cell
         * cell state is enough
         */
        String file = "test_input.txt";
        List<String> lines = getLines(file);
        int[][] grid = convert(lines);
        int ans = dji2(grid, 1, 3);
        System.out.println(ans);
  
    }
    public static int dji2(int[][] grid, int mindist, int maxdist){
        int rows = grid.length;
        int cols = grid[0].length;
        heap_entry start = new heap_entry(0, 0, 0, -1); //entry of the form # cost, x, y, disallowedDirection
        HashSet<int[]> seen = new HashSet<>();
        Map<int[], Integer> costs = new HashMap<>();
        PriorityQueue<heap_entry> min_heap = new PriorityQueue<>();
        int[][] dirrs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        min_heap.add(start);
        
        while (!min_heap.isEmpty()){
            heap_entry curr_entry = min_heap.poll();
            int cost = curr_entry.cost;
            int x = curr_entry.x;
            int y = curr_entry.y;
            int dd = curr_entry.dir;
            if (x == rows - 1 && y == cols - 1){
                return cost;
            }
            int[] temp = {x,y,dd};
            if (seen.contains(temp))
                continue;
            seen.add(temp);
            for (int direction = 0; direction < 4; direction++){
                int costincrease = 0;
                //cool way of checking turns
                if (direction == dd || (direction + 2) % 4 == dd)
                    continue;
                for (int distance = 1; distance <= maxdist; distance++){
                    int xx = x + dirrs[direction][0]*distance;
                    int yy = y + dirrs[direction][1]*distance;
                    //bounds
                    if (xx >= 0 && xx < rows && yy >= 0 && yy < cols){
                        costincrease += grid[xx][yy];
                        if (distance < mindist)
                            continue;
                        int nc = cost + costincrease;
                        int[] temp2 = {xx,yy,direction};
                        if (costs.getOrDefault(temp2, Integer.MAX_VALUE) <= nc)
                            continue;
                        costs.put(temp2,nc);
                        min_heap.add(new heap_entry(nc, xx, yy, direction));
                        
                    }
                }
                
            }
        }

        return 0;

    }
    static class heap_entry implements Comparable<heap_entry> {
        int cost,x,y,dir;
    
        public heap_entry(int cost, int x, int y, int dir) {
            this.cost = cost;
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
        @Override //overload
        public int compareTo(heap_entry other) {
            // Compare based on the first element for max heap
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static int dikstras(int[][] grid, State start_state){
        int rows = grid.length;
        int cols = grid[0].length;
        HashMap<State,Integer> dists = new HashMap<>();
        dists.put(start_state, 0);
        HashSet<State> seen = new HashSet<>();
        // states are (heat,i,j,right_remaining,down_remaining,curr direction)
        // use hashap for diststs
        PriorityQueue<State> min_heap = new PriorityQueue<>();
        min_heap.add(start_state);

        while (!min_heap.isEmpty()){
            State curr_state = min_heap.poll();
            System.out.println(curr_state.heat);
            //for cached statue only keep (i,j) and dirtions
            int[] temp = {curr_state.x, curr_state.y, curr_state.dx,curr_state.dy};
            State cached_state = new State(temp);
            //correct for bounds here

            if (curr_state.x < 0 || curr_state.x >= rows || curr_state.y < 0 || curr_state.y >= cols)
                continue;
            //reach end
            if (curr_state.x == rows-1 || curr_state.y == cols - 1){
                return curr_state.heat + grid[curr_state.x][curr_state.y];
            }
            if (seen.contains(cached_state)){
                continue;
            }
            //if we cant minmize heat
            if (dists.getOrDefault(cached_state, Integer.MAX_VALUE) < curr_state.heat){
                continue;
            }
            //mark as visited
            seen.add(cached_state);

            //check turns and extend out in the loop
            //populate directions in loop, and also keep , if curr (dx,dy) turns are (dy,dx) (-dy,dx)
            int[][] dirrs = {{curr_state.dx,curr_state.dy}, {-curr_state.dy, curr_state.dx}, {curr_state.dy,-curr_state.dx}};
            for (int[] d : dirrs){
                //move at most three in the directions
                int neigh_heat = dists.get(curr_state);
                for (int step = 1; step <= 3; step++){
                    int neigh_x = curr_state.x + d[0]*step;
                    int neigh_y = curr_state.y + d[1]*step;
                    //bounds
                    if (neigh_x < 0 || neigh_x >= rows || neigh_y < 0 || neigh_y >= cols){
                        continue;
                    }
                    neigh_heat += grid[neigh_x][neigh_y];
                    int[] next_state = {neigh_heat, neigh_x, neigh_y, d[0],d[1],step};
                    State next_state_converted = new State(next_state);
                    int[] temp2 = {neigh_x, neigh_y, d[0],d[1]};
                    State next_cached_state = new State(temp2);
                    if (seen.contains(next_cached_state))
                        continue;
                    //if we can minimize
                    if (neigh_heat < dists.getOrDefault(next_cached_state, Integer.MAX_VALUE)){
                        //releax it
                        dists.put(next_cached_state, neigh_heat);
                        min_heap.add(next_state_converted);
                    }
                }
            }
        }

        return 0;

    }
    static class State implements Comparable<State> {
        int heat,x,y,dx,dy,steps;
    
        public State(int[] entry) {
            this.heat= entry[0];
            this.x = entry[1];
            this.y = entry[2];
            this.dx = entry[3];
            this.dy = entry[4];
            this.steps = entry[5];
        }
    
        @Override //overload
        public int compareTo(State other) {
            // Compare based on the first element for max heap
            return Integer.compare(this.heat, other.heat);
        }
    }

    public static int dp(int[][] grid, Map<int[],Integer> memo, int i, int j, int steps_right, int steps_down){
        int rows = grid.length;
        int cols = grid[0].length;
        int[] key = {i,j,steps_right,steps_down};
        //retreive
        if (memo.containsKey(key))
            return memo.get(key);
        //ending, need the cell
        if (i == grid.length - 1 && j == grid[0].length - 1){
            return grid[i][j];
        }
        if (steps_right == 0){
            return dp(grid, memo, i, j+1, 3, steps_down);
        }
        
        if (steps_down == 0){
            return dp(grid, memo, i+1, j, steps_right, 3);
        }
        //out of bounds, return large value
        if (i < 0 || i >= rows || j < 0 || j >= cols){
            return 0;
        }
        //transition
        int ans = Integer.MAX_VALUE;

        //right step
        for (int right = 1; right <= steps_right; right++){
            ans = Math.min(ans, dp(grid, memo, i+right, j, steps_right-right, steps_down));
        }

        //down step
        for (int down = 1; down <= steps_down; down++){
            ans = Math.min(ans, dp(grid, memo, i, j+down, steps_right, steps_down-down));
        }

        memo.put(key, ans);
        return ans;
    }

    public static int[][] convert(List<String> lines){
        int rows = lines.size();
        int cols = lines.get(0).length();
        int[][] grid = new int[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                grid[i][j] = (int) lines.get(i).charAt(j) - '0';
            }
        }
        return grid;
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
