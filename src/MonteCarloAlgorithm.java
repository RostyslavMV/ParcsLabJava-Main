import java.math.*;
import java.util.*;

import parcs.*;

public class MonteCarloAlgorithm implements AM{
    public void run(AMInfo info)
    {
        long pointsPerProcess;

        pointsPerProcess = info.parent.readLong();


        System.out.println("The MonteCarloAlgorithm class have read data from the parent server");
        System.out.println("pointsPerProcess = " + pointsPerProcess);

        Random random = new Random();
        long pointsInCircle = 0;

        for (long i = 0; i < pointsPerProcess; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();

            if (x * x + y * y <= 1) {
                pointsInCircle++;
            }
        }


        if(pointsInCircle != 0)
        {
            System.out.println("The MonteCarloAlgorithm class return the result: pointsInCircle = " + pointsInCircle);
            info.parent.write(pointsInCircle);
        }
        else
        {
            System.out.println("The MonteCarloAlgorithm class doesn't find any solution");
            info.parent.write(0);
        }
    }
}
