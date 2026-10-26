
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Maze {

    static class State implements Comparable<State> {
        int distance;
        int row;
        int col;

        public State(int distance, int row, int col) {
            this.distance = distance;
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        int m = maze.length;
        int n = maze[0].length;

        int[][] dist = new int[m][n];

        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        PriorityQueue<State> pq = new PriorityQueue<>();
        dist[start[0]][start[1]] = 0;
        pq.offer(new State(0, start[0], start[1]));

        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        while (!pq.isEmpty()) {
            State curr = pq.poll();

            if (curr.distance > dist[curr.row][curr.col]) {
                continue;
            }

            if (curr.row == destination[0] &&
                curr.col == destination[1]) {
                return curr.distance;
            }

            for (int[] dir : directions) {
                int nr = curr.row + dir[0];
                int nc = curr.col + dir[1];

                if (nr < 0 || nr >= m || nc < 0 || nc >= n ||
                    maze[nr][nc] == 1) {
                    continue;
                }

                int newDist = curr.distance + 1;

                if (newDist < dist[nr][nc]) {
                    dist[nr][nc] = newDist;
                    pq.offer(new State(newDist, nr, nc));
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("maze matrix=");
        String mazeLine = input.nextLine();

        System.out.println("start=");
        String startLine = input.nextLine();

        System.out.println("goal=");
        String goalLine = input.nextLine();

        String mazeText = mazeLine.substring(
            mazeLine.indexOf("[[") + 2,
            mazeLine.indexOf("]]")
        );

        String[] rows = mazeText.split("\\],\\[");
        int[][] maze = new int[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            String[] values = rows[i].split(",");
            maze[i] = new int[values.length];

            for (int j = 0; j < values.length; j++) {
                maze[i][j] = Integer.parseInt(values[j].trim());
            }
        }

        String[] startValues = startLine
            .substring(startLine.indexOf("[") + 1,
                       startLine.indexOf("]"))
            .split(",");

        int[] start = {
            Integer.parseInt(startValues[0].trim()),
            Integer.parseInt(startValues[1].trim())
        };

        // Read goal
        String[] goalValues = goalLine
            .substring(goalLine.indexOf("[") + 1,
                       goalLine.indexOf("]"))
            .split(",");

        int[] destination = {
            Integer.parseInt(goalValues[0].trim()),
            Integer.parseInt(goalValues[1].trim())
        };

        Maze solver = new Maze();

        System.out.println("Shortest distance: " +
            solver.shortestDistance(maze, start, destination));

        input.close();
    }
}
