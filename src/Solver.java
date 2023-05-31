import java.io.*;
import java.math.*;
import java.util.*;

import parcs.*;

public class Solver implements AM {

    public static void main(String[] args) {
        System.out.println("The Solver class start method main");

        task curtask = new task();
        curtask.addJarFile("Solver.jar");
        curtask.addJarFile("MonteCarloAlgorithm.jar");

        System.out.println("The Solver class method main adder jar files");

        (new Solver()).run(new AMInfo(curtask, (channel) null));

        System.out.println("The Solver class method main finish work");
        curtask.end();
    }

    public void run(AMInfo info) {
        long totalPoints;
        int processes;

        try {
            BufferedReader in = new BufferedReader(new FileReader(info.curtask.findFile("input_0_2.txt")));

            processes = Integer.parseInt(in.readLine());
            totalPoints = Long.parseLong(in.readLine());
        } catch (IOException e) {
            System.out.print("Reading input data error\n");
            e.printStackTrace();
            return;
        }

        System.out.println("The Solver class have read data from the parent server");
        System.out.println("totalPoints = " + totalPoints);
        System.out.println("processes = " + processes);
        // Time counting
        long tStart = System.nanoTime();

        double res = solve(info, processes, totalPoints);

        long tEnd = System.nanoTime();
        // Printing results
        System.out.println("Pi = " + res);
        System.out.println("Working time on " + processes + " processes: " + ((tEnd - tStart) / 1000000) + "ms");
    }

    static public double solve(AMInfo info, int nThreads, long totalPoints) {
        List<point> points = new ArrayList<point>();
        List<channel> channels = new ArrayList<channel>();
        // Connection to points
        for (int i = 0; i < nThreads; i++) {
            long pointsPerProcess = totalPoints / nThreads;
            if (i < totalPoints % nThreads) {
                pointsPerProcess++;
            }
            points.add(info.createPoint());
            channels.add(points.get(i).createChannel());
            points.get(i).execute("MonteCarloAlgorithm");
            channels.get(i).write(pointsPerProcess);
        }

        // Mapping results
        long totalPointsInCircle = 0;
        for (int i = 0; i < nThreads; i++) {
            totalPointsInCircle +=(channels.get(i).readLong());
        }

        // Calculate Pi approximation
        return  4.0 * totalPointsInCircle / totalPoints;
    }
}
