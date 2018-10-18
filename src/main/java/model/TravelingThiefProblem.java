package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents the main problem to be solved. It stores all variables which are necessary
 * to evaluate the objective function.
 *
 * Either define the problem by yourself or load it from a file.
 *
 */
public class TravelingThiefProblem {

    //! name of the problem - set if read from a file
    public String name = "unknown";

    //! number of cities
    public int numOfCities = -1;

    //! number of items
    public int numOfItems = -1;

    // ! minimal speed of the salesman
    public double minSpeed = 0.1;

    // ! maximal speed of the salesman
    public double maxSpeed = 1.0;

    // ! maximal weight of the knapsack
    public int maxWeight;

    //! Renting Rate (not needed for multi-objective version)
    public double R = -1.0;

    // ! coordinate where the salesman could visit cities
    public double[][] coordinates;

    //! corresponding city of each item
    public int[] cityOfItem;

    // ! the weight of each item
    public double[] weight;

    // ! the profit of each item
    public double[] profit;

    //! used for faster evaluation
    private List<LinkedList<Integer>> itemsAtCity = null;


    public void initialize() {

        // initialize the itemsAtCity data structure
        this.itemsAtCity = new ArrayList<>(this.numOfCities);
        for (int i = 0; i < this.numOfCities; i++) {
            this.itemsAtCity.add(new LinkedList<>());
        }
        for (int i = 0; i < this.cityOfItem.length; i++) {
            this.itemsAtCity.get(this.cityOfItem[i]).add(i);
        }

    }



    public List<Double> evaluate(List<Integer> pi, List<Boolean> z) {

        if (pi.size() != this.numOfCities || z.size() != this.numOfItems) {
            throw new RuntimeException("Wrong input for traveling thief evaluation!");
        } else if(pi.get(0) != 0) {
            throw new RuntimeException("Thief must start at city 0!");
        }

        // the values that are evaluated in this function
        double time = 0;
        double profit = 0;

        // attributes in the beginning of the tour
        double weight = 0;

        // iterate over all possible cities
        for (int i = 0; i < this.numOfCities; i++) {

            // the city where the thief currently is
            int city = pi.get(i);

            // for each item index this city
            for (int j : this.itemsAtCity.get(city)) {

                // if the thief picks that item
                if (z.get(j)) {
                    // update the current weight and profit
                    weight += this.weight[j];
                    profit += this.profit[j];
                }

            }

            // if the maximum capacity constraint is reached
            if (weight > maxWeight) {
                time = Double.MAX_VALUE;
                profit = - Double.MAX_VALUE;
                break;
            }

            // update the speed accordingly
            double speed = this.maxSpeed - (weight / this.maxWeight) * (this.maxSpeed - this.minSpeed);

            // increase time by considering the speed - do not forget the way from the last city to the first!
            int next = pi.get((i + 1) % this.numOfCities);
            double distance = Math.ceil(euclideanDistance(city, next));

            time += distance / speed;

        }


        // return the objective values
        return Arrays.asList(time, -profit);

    }

    public double euclideanDistance(int a, int b) {
        return Math.sqrt(Math.pow(this.coordinates[a][0] - this.coordinates[b][0], 2)
                + Math.pow(this.coordinates[a][1] - this.coordinates[b][1], 2));
    }


}
