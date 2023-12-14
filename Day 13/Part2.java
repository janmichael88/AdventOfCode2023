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
import java.util.Queue;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.ArrayDeque;
import java.util.Deque;

public class Part2 {
    public static void main(String[] args) {
        String fileName = "input.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            String[] patterns = sb.toString().trim().split("\n\n");
            int result = part2(patterns);

            System.out.println("Result (Part 2): " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int calcDiff(String l1, String l2) {
        int diff = 0;

        for (int i = 0; i < l1.length(); i++) {
            if (l1.charAt(i) != l2.charAt(i)) {
                diff++;
            }
        }

        return diff;
    }

    public static String findMirrorWithSmudge(String[] lines) {
        // Horizontal
        for (int i = 0; i < lines.length - 1; i++) {
            int diff = calcDiff(lines[i], lines[i + 1]);
            boolean smudgeDone = false;

            if (diff == 1) {
                smudgeDone = true;
            }

            if (diff <= 1) {
                int l1 = i + 2;
                int l2 = i + i + 1 - l1;

                while (0 <= l2 && l1 < lines.length) {
                    diff = calcDiff(lines[l1], lines[l2]);

                    if (diff > 1 || (diff == 1 && smudgeDone)) {
                        break;
                    } else if (diff == 1) {
                        smudgeDone = true;
                    }

                    l1++;
                    l2--;
                }

                if (smudgeDone && l1 > l2) {
                    return "row, " + (i + 1);
                }
            }
        }

        // Vertical
        for (int i = 0; i < lines[0].length() - 1; i++) {
            StringBuilder col = new StringBuilder();
            StringBuilder col2 = new StringBuilder();

            for (String line : lines) {
                col.append(line.charAt(i));
                col2.append(line.charAt(i + 1));
            }

            int diff = calcDiff(col.toString(), col2.toString());
            boolean smudgeDone = false;

            if (diff == 1) {
                smudgeDone = true;
            }

            if (diff <= 1) {
                int c1 = i + 2;
                int c2 = i + i + 1 - c1;

                while (0 <= c2 && c1 < lines[0].length()) {
                    col = new StringBuilder();
                    col2 = new StringBuilder();

                    for (String line : lines) {
                        col.append(line.charAt(c1));
                        col2.append(line.charAt(c2));
                    }

                    diff = calcDiff(col.toString(), col2.toString());

                    if (diff > 1 || (diff == 1 && smudgeDone)) {
                        break;
                    } else if (diff == 1) {
                        smudgeDone = true;
                    }

                    c1++;
                    c2--;
                }

                if (smudgeDone && c1 > c2) {
                    return "col, " + (i + 1);
                }
            }
        }

        return "No mirror found";
    }

    public static int part2(String[] patterns) {
        int sum = 0;

        for (String p : patterns) {
            String[] lines = p.split("\n");
            String[] result = findMirrorWithSmudge(lines).split(", ");
            String type = result[0];
            int n = Integer.parseInt(result[1]);

            if (type.equals("row")) {
                sum += n * 100;
            } else if (type.equals("col")) {
                sum += n;
            }
        }

        return sum;
    }
}