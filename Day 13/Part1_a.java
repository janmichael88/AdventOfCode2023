import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part1_a {
    public static void main(String[] args) {
        String fileName = "input.txt";
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            StringBuilder sb = new StringBuilder();
            
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            String[] patterns = sb.toString().trim().split("\n\n");
            int result = part1(patterns);

            System.out.println("Result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String findMirror(String[] lines) {
        // Horizontal
        for (int i = 0; i < lines.length - 1; i++) {
            if (lines[i].equals(lines[i + 1])) {
                int l1 = i + 2;
                int l2 = 2 * i + 1 - l1;
                while (0 <= l2 && l1 < lines.length) {
                    if (!lines[l1].equals(lines[l2])) {
                        break;
                    }
                    l1++;
                    l2--;
                }
                if (l1 > l2) {
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

            if (col.toString().equals(col2.toString())) {
                int c1 = i + 2;
                int c2 = 2 * i + 1 - c1;
                while (0 <= c2 && c1 < lines[0].length()) {
                    StringBuilder currentCol = new StringBuilder();
                    StringBuilder currentCol2 = new StringBuilder();

                    for (String line : lines) {
                        currentCol.append(line.charAt(c1));
                        currentCol2.append(line.charAt(c2));
                    }

                    if (!currentCol.toString().equals(currentCol2.toString())) {
                        break;
                    }

                    c1++;
                    c2--;
                }

                if (c1 > c2) {
                    return "col, " + (i + 1);
                }
            }
        }

        return "No mirror found";
    }

    public static int part1(String[] patterns) {
        int sum = 0;

        for (String p : patterns) {
            String[] lines = p.split("\n");
            String[] result = findMirror(lines).split(", ");
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
