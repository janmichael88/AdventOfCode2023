import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Iterator;

import java.util.Collections;

public class Part1{

    public static void main(String[] args) throws IOException{
        /*
         * 3d grid space, blocks have positions (x,y,z) first cooridnate is each end of the brick
         * its always the case that the first brick's z cooridnate is > second brick's z cooridnate
         * to simulate falling process bricks in increasing order of Z axis, and then drop them
         * modeled afer this 
         * https://topaz.github.io/paste/#XQAAAQADBgAAAAAAAAAFGovjf455e4alKESHRGN/kMkO4XUEY/B294mDtTc1ce7nyfovoxyuhweNrb4vl2nh16QTmZmHOLhovez4pyTUgjaGMm0G9EU3m8KcL3GytkJAGqEtRKuUPtrRIcU9dhkDFU/WZ7UyAafjVpIFvPH7rdDNZ7AwTJ3o6wuqW8vRFvKFdVXgYz19SMoGfc6lSaxYQyC1pgiyhcwsDmyiFQc1HIKlBEolavr84EoeNnNG3RKYDLjYcTe7ZYxy3yLfgWFiVz2lH/6R8gJQb1uJ4Hrp9ypjELrN5skogksUJ3I4bU/XltzaOhRKR7lKDRfCs+VqAgh/iy/kB8z98ZKf8rn25ZlhPxkdHtWpG9LLafi80R7bjaIn5tK8E2KFOjn/7DaejyQupi3PJMSGGtLSehObSIZwo4tB2Ojsofle5Q6z/IVm13k6KZMyonPZv3E7EOxseyPX0teWJHJMC31TrB+bQt2u2u5uDI84k6w5RhPTG/FzFZSbL8RE0Hi0/ROenqN+uq5Utdqak2zICVYL+XXrJoV1MS0//fKldirZYyBZXtRaHvQ83faKfe4X+iyJBR41uXyw3hy8pRAtvy8luZqQIBUT6T8cERx8OQoTk22Weh+w3Vv41Da/mmF6YS04BQPyd87VqdSGtH6XHZscG279bM76aXqSVyFMz4xoGF4mJbgPWzEPKkDIO1ojtyiESK8zY4STOfqbMnnbhPt/x4k6Rqk5K31n9CV+Enm3wCAj86mZwTejxw4z8uNdUOdpZuqh/t9nNq9NXBPQcFTcoBvCJPzw/+Sgqog=
         */
        String file = "test_input.txt";
        List<String> lines = getLines(file);
        List<List<int[]>> bricks = new ArrayList<>();


        for (String l : lines){
            String a = l.split("~")[0];
            String b = l.split("~")[1];
            int[] start = new int[3];
            int[] end = new int[3];
            int i = 0;
            for (String ch : a.split(",")){
                start[i] = Integer.parseInt(ch);
                i++;
            }
            i = 0;
            for (String ch : b.split(",")){
                end[i] = Integer.parseInt(ch);
                i++;
            }
            List<int[]> entry = new ArrayList<>();
            entry.add(start);
            entry.add(end);
            bricks.add(entry);
        }

        //sort on increaisng z
        Collections.sort(bricks, Comparator.comparingInt(x -> x.get(0)[2]));
        int N = bricks.size();
        HashMap<int[],Pair> highest = new HashMap<>();
        HashSet<Integer> bad = new HashSet<>();
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        

        for (int i = 0; i < N; i++){
            int max_height = -1;
            HashSet<Integer> support = new LinkedHashSet<>();
            List<int[]> b = bricks.get(i);
            for (int x = b.get(0)[0]; x <= b.get(1)[0]; x++){
                for (int y = b.get(0)[1]; y <= b.get(1)[1]; y++){
                    int[] key = {x,y};
                    Pair accessed = highest.getOrDefault(key, new Pair(0, -1));
                    if (accessed.first + 1 > max_height){
                        max_height = accessed.first + 1;
                        support.clear();
                        support.add(accessed.second);
                    }
                    else if (accessed.first + 1 == max_height){
                        support.add(accessed.second);
                    }
                }
            }
            
            if (support.size() == 1){
                Iterator<Integer> iter = support.iterator();
                int first = iter.next();
                support.remove(first);
                bad.add(first);
            }
            

            //drop it
            int fall = b.get(0)[2] - max_height;
            if (fall > 0){
                b.get(0)[2] -= fall;
                b.get(1)[2] -= fall;
            }

            //update
            for (int x = b.get(0)[0]; x <= b.get(1)[0]; x++){
                for (int y = b.get(0)[1]; y <= b.get(1)[1]; y++){
                    int[] key = {x,y};
                    highest.put(key, new Pair(b.get(1)[2], i));
                }
            }


        }

        System.out.println(bricks.size() - (bad.size() - 1));

    }
    static class Pair{
        public static int first, second;

        public Pair(int first, int second){
            this.first = first;
            this.second = second;
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