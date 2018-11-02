import algorithms.Algorithm;
import algorithms.ExhaustiveSearch;
import model.Solution;
import model.TravelingThiefProblem;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Runner {


    static final ClassLoader LOADER = Runner.class.getClassLoader();

    public static void main(String[] args) throws IOException {

        List<String> instanceToRun = Arrays.asList("test-example-n4");
        //List<String> instanceToRun = Competition.INSTANCES;

        for (String instance : instanceToRun) {

            // readProblem the problem from the file
            String fname = String.format("%s.txt", instance);
            String pathToFile = LOADER.getResource(fname).getFile();
            TravelingThiefProblem problem = Util.readProblem(pathToFile);
            problem.name = instance;

            // number of solutions that will be finally necessary for submission - not used here
            int numOfSolutions = Competition.numberOfSolutions(problem);

            // initialize your algorithm
            //Algorithm algorithm = new RandomLocalSearch(500);
            Algorithm algorithm = new ExhaustiveSearch();

            // use it to to solve the problem and return the non-dominated set
            List<Solution> nds = algorithm.solve(problem);

            // sort by time and printSolutions it
            nds.sort(Comparator.comparing(a -> a.time));

            System.out.println(nds.size());
            for(Solution s : nds) {
                System.out.println(s.time + " " + s.profit);
            }

            //Util.printSolutions(nds, false);
            System.out.println(problem.name + " " + nds.size());
            Util.writeSolutions("results", Competition.TEAM_NAME, problem, nds);


        }



    }

}