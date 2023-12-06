import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Objects;


public class Part2 {

    public static void main(String[] args) {
        Map<Point, Set<Integer>> parts = new HashMap<>();
        char[][] board;
        try {
            board = readBoard("input.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Set<Point> chars = new HashSet<>();
        for (int r = 0; r < 140; r++) {
            for (int c = 0; c < 140; c++) {
                if (board[r][c] != '0' && board[r][c] != '1' && board[r][c] != '2' &&
                    board[r][c] != '3' && board[r][c] != '4' && board[r][c] != '5' &&
                    board[r][c] != '6' && board[r][c] != '7' && board[r][c] != '8' &&
                    board[r][c] != '9' && board[r][c] != '.') {
                    chars.add(new Point(r, c));
                }
            }
        }

        for (int r = 0; r < 140; r++) {
            String row = new String(board[r]);
            Matcher matcher = Pattern.compile("\\d+").matcher(row);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                Set<Point> nexts = new HashSet<>();
                for (int s = -1; s <= 1; s++) {
                    for (int d = -1; d <= 1; d++) {
                        for (int c = start; c < end; c++) {
                            nexts.add(new Point(r + s, c + d));
                        }
                    }
                }
                nexts.retainAll(chars);
                for (Point p : nexts) {
                    parts.computeIfAbsent(p, k -> new HashSet<>()).add(Integer.parseInt(matcher.group()));
                }
            }
        }

        int sum = parts.values().stream().flatMapToInt(set -> set.stream().mapToInt(Integer::intValue)).sum();
        int prodSum = parts.values().stream().filter(set -> set.size() == 2).mapToInt(set -> set.stream().reduce(1, (a, b) -> a * b)).sum();

        System.out.println(sum);
        System.out.println(prodSum);
    }

    private static char[][] readBoard(String fileName) throws IOException {
        char[][] board = new char[140][140];
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for (int i = 0; i < 140; i++) {
                String line = br.readLine();
                if (line == null) {
                    throw new IOException("Incomplete board in file");
                }
                board[i] = line.toCharArray();
            }
        }
        return board;
    }

    static class Point {
        int row;
        int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row && col == point.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
}
