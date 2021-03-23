import java.util.Scanner;
import java.util.LinkedList;
import java.io.File;

class Point { // holds x and y coords
	int i, j;
	public Point(int i, int j) {
		this.i = i;
		this.j = j;
	}
}

public class Islands { 
	
	private static LinkedList<Point> queue = new LinkedList<Point>();
	private static boolean keepGoing = false;
	private static int N;
	private static char[][] grid; // originally used integers but I realized that im dumb and char's take up less space in memory
	private static int flags = 0;
	
	public static int numIslands() {
		int count = 0;
		for (int i= 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (grid[i][j] == '*') { // loop through and visit anything that is land
					visit(i, j);
					count++;
				}
			}
		}
		return count;
	}
	
	private static void visit(int i, int j) { // use DFS to visit all land connected to origin
		visit(i, j, 0);
		if (keepGoing) {
			keepGoing = false;
			while (!queue.isEmpty()) {
				Point p = queue.poll();
				visit(p.i, p.j, 0);
			}
		}
	}
	
	private static void visit(int i, int j, int x) {
		grid[i][j] = '.'; // mark land as visited by turning it into water
		if (x == 4000) { // use counter to stop recursion at depth of 4000 to prevent stackoverflow error
			keepGoing = true;
			queue.offer(new Point(i, j));
			return;
		}
		if (i > 0 && grid[i - 1][j] == '*') { // North
			visit(i - 1, j, x + 1);
		}
		if (i < N - 1 && grid[i + 1][j] == '*') { // South
			visit(i + 1, j, x + 1);
		}
		if (j > 0 && grid[i][j - 1] == '*') { // West
			visit(i, j - 1, x + 1);
		}
		if (j < N - 1 && grid[i][j + 1] == '*') { // East
			visit(i, j + 1, x + 1);
		}
		if (i > 0 && j > 0 && grid[i - 1][j - 1] == '*') { // Northwest
			visit(i - 1, j - 1, x + 1);
		}
		if (i > 0 && j < N - 1 && grid[i - 1][j + 1] == '*') { // Northeast
			visit(i - 1, j + 1, x + 1);
		}
		if (i < N - 1 && j > 0 && grid[i + 1][j - 1] == '*') { // Southwest
			visit(i + 1, j - 1, x + 1);
		}
		if (i < N - 1 && j < N - 1 && grid[i + 1][j + 1] == '*') { // Southeast
			visit(i + 1, j + 1, x + 1);
		}
	}
	
	public static void main(String [] args) {
		try {
			File file = new File("islands_large.in");
			Scanner input = new Scanner(file);
			N = Integer.parseInt(input.nextLine());
			grid = new char[N][N];
			for (int i= 0; i < N; i++) { // load char array of land = '*', water = '.'
				String line = input.nextLine();
				for (int j = 0; j < N; j++) {
					char block = line.charAt(j);
					if (block == '*') grid[i][j] = '*';
					else grid[i][j] = '.';
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("The total number of islands is " + numIslands());
	}
}
