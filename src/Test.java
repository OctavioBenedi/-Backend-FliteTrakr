import java.util.List;
import java.util.Scanner;
import java.lang.*;
import java.util.*;

import static java.lang.String.*;
import static java.util.Objects.requireNonNull;

public class Test{

    public static void test1() {
        //    bcn - brx
        //   /   \ /   \
        // zgz   ber - mad
        //   \   / \   /
        //    par   lnd


        // The edges above the line a - g have weight of 1.0.
        // The edges below the line a - g have weight of 2.0

        Graph<String> graph = new Graph<>(
                new Edge<>("zgz", "bcn", 1.0),
                new Edge<>("zgz", "par", 2.0),
                new Edge<>("bcn", "ber", 2.5),
                new Edge<>("bcn", "brx", 2.0),
                new Edge<>("par", "ber", 1.5),
                new Edge<>("ber", "brx", 1.0),
                new Edge<>("ber", "lnd", 2.0),
                new Edge<>("brx", "mad", 3.0),
                new Edge<>("brx", "ber", 1.0),
                new Edge<>("mad", "lnd", 2.0)
        );

        List<Path<String>> paths = new DefaultKShortestPathFinder<String>()
                .findShortestPaths("zgz", "mad", graph, 20);
        
        int it = 0;
        for (Path<String> path : paths) {
            System.out.println(path.getNodeList() + " " + path.pathCost());
            //~ it++;
            //~ if (it >=6){
                //~ break;
            //~ }
        }
    }
}
