package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This class reads a problem into the object class.
 */
public abstract class Util {


    public static void write(String path, TravelingThiefProblem problem, List<Solution> solutions) {





    }



    public static void print(List<Solution> solutions) {

        System.out.println(String.format("Number of non-dominated solutions: %s", solutions.size()));

        for (Solution solution : solutions) {

            for (int i = 0; i < solution.pi.size(); i++) {
                System.out.print(solution.pi.get(i));
                System.out.print(" ");
            }

            for (int i = 0; i < solution.z.size(); i++) {
                System.out.print(solution.z.get(i));
                System.out.print(" ");
            }

            System.out.println(String.format("%.2f %.2f", solution.time, solution.profit));

        }
    }


	public static TravelingThiefProblem read(String pathToFile) throws IOException {

        TravelingThiefProblem problem = new TravelingThiefProblem();

        FileReader in = new FileReader(pathToFile);
        BufferedReader br = new BufferedReader(in);

        String line = br.readLine();
        while(line != null){

            if (line.contains("PROBLEM NAME")) {
            } else if (line.contains("KNAPSACK DATA TYPE")) {
            } else if (line.contains("DIMENSION")) {
                problem.numOfCities = Integer.valueOf(line.split(":")[1].trim());
                problem.coordinates = new double[problem.numOfCities][2];
            } else if (line.contains("NUMBER OF ITEMS")) {
                problem.numOfItems = Integer.valueOf(line.split(":")[1].trim());
                problem.cityOfItem = new int[problem.numOfItems];
                problem.weight = new double[problem.numOfItems];
                problem.profit = new double[problem.numOfItems];
            } else if (line.contains("CAPACITY OF KNAPSACK")) {
                problem.maxWeight = Integer.valueOf(line.split(":")[1].trim());
            } else if (line.contains("MIN SPEED")) {
                problem.minSpeed = Double.valueOf(line.split(":")[1].trim());
            } else if (line.contains("MAX SPEED")) {
                problem.maxSpeed = Double.valueOf(line.split(":")[1].trim());
            } else if (line.contains("EDGE_WEIGHT_TYPE")) {
                String edgeWeightType = line.split(":")[1].trim();
                if (!edgeWeightType.equals("CEIL_2D")) {
                    throw new RuntimeException("Only edge weight type of CEIL_2D supported.");
                }
            } else if (line.contains("NODE_COORD_SECTION")) {
                for (int i = 0; i < problem.numOfCities; i++) {
                    line = br.readLine();
                    String[] a = line.split("\\s+");
                    problem.coordinates[i][0] = Double.valueOf(a[1].trim());
                    problem.coordinates[i][1] = Double.valueOf(a[2].trim());
                }

            } else if (line.contains("ITEMS SECTION")) {

                for (int i = 0; i < problem.numOfItems; i++) {
                    line = br.readLine();
                    String[] a = line.split("\\s+");
                    problem.profit[i] = Double.valueOf(a[1].trim());
                    problem.weight[i] = Double.valueOf(a[2].trim());
                    problem.cityOfItem[i] = Integer.valueOf(a[3].trim()) - 1;
                }

            }
            line = br.readLine();
        }

        br.close();

        problem.initialize();
        return problem;
	}



    public static List<Integer> getIndex(int low, int high) {
        List<Integer> l = new ArrayList<>();
        for (int j = low; j < high; j++) {
            l.add(j);
        }
        return l;
    }




}
