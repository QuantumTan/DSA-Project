import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int G = scanner.nextInt();
        int T = scanner.nextInt();
        int R = scanner.nextInt();

        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            int g = scanner.nextInt();
            items.add(new Item(v, w, g));
        }
        scanner.close();

        KnapsackSolver solver = new KnapsackSolver(G, T, R, items);
        SolverResult result = solver.solve();
        System.out.println(result.getMaxValue());
    }
}
