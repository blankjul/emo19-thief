import algorithms.Algorithm;
import algorithms.ExhaustiveSearch;
import algorithms.RandomSearch;
import model.Solution;
import model.TravelingThiefProblem;
import model.Util;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

class Runner {

    static final ClassLoader loader = Runner.class.getClassLoader();

    public static void main(String[] args) throws IOException {

        //String pathToFile =loader.getResource("berlin52_n51_uncorr_01.txt").getFile();
        String pathToFile =loader.getResource("example_n4_corr.txt").getFile();

        // read the problem from the file
        TravelingThiefProblem problem = Util.read(pathToFile);

        // use the algorithm to solve the problem and return a set of solutions
        //Algorithm algorithm = new RandomSearch(10000);
        Algorithm algorithm = new ExhaustiveSearch();
        List<Solution> nds = algorithm.solve(problem);

        // sort by time and print it
        nds.sort(Comparator.comparing(a -> a.time));
        Util.print(nds);


    }

}