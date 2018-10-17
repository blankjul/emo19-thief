package algorithms;

import model.NonDominatedSet;
import model.Solution;
import model.TravelingThiefProblem;
import model.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * This algorithm is a naive random search, where on random tours items are added in random order.
 * Every time an item is added, it is evaluated and the non-dominated set is updated.
 *
 * If the attribute pi is set the tour is kept fixed and only the order if adding items to it are
 * modified.
 *
 */
public class RandomSearch implements Algorithm {

    //! number of randomly created tours
    private int maxNumOfTrials = 1000;

    //! only do search on this tour
    private List<Integer> pi = null;

    //! default constructor for this very naive algorithm
    public RandomSearch(int numberOfTrials) {
        this.maxNumOfTrials = numberOfTrials;
    }


    public List<Solution> solve(TravelingThiefProblem problem) {

        // set the evaluation counter to 0 and initialize the non-dominated set
        int counter = 0;
        NonDominatedSet nds = new NonDominatedSet();

        // loop while the function evaluation limit is reached
        while (true) {

            // either sample a random tour or use the tour provided to the algorithm
            List<Integer> pi;
            if (this.pi == null) {
                // Create a random permutation
                pi = Util.getIndex(1, problem.numOfCities);
                java.util.Collections.shuffle(pi);
                pi.add(0,0);
            } else {
                pi = this.pi;
            }

            // Create an empty packing plan
            List<Boolean> z = new ArrayList<>(problem.numOfItems);
            for (int j = 0; j < problem.numOfItems; j++) {
                z.add(false);
            }

            // evaluate for this random tour
            List<Double> objectives = problem.evaluate(pi,z);
            nds.add(new Solution(pi,z,objectives, true));

            // this is the order of items we will try to add to generated a new non-dominated point
            List<Integer> rnd = Util.getIndex(0, problem.numOfItems);
            java.util.Collections.shuffle(rnd);

            // no iteratively evaluate when an item is added and add to non-dominated set
            double weight = 0.0;
            for (int j = 0; j < rnd.size(); j++) {

                // item to be considered for adding
                int item = rnd.get(j);

                // if picking up this item will not violate this maximum constraint
                if (weight + problem.weight[item] < problem.maxWeight) {

                    // pick the item
                    z.set(item, true);
                    weight += problem.weight[item];

                    // evaluate and update the non-dominated solutions
                    objectives = problem.evaluate(pi,z);
                    nds.add(new Solution(pi,z,objectives, true));

                    // increase the function evaluation counter
                    ++counter;

                }

            }

            if (counter > this.maxNumOfTrials) break;

        }

        return nds.entries;

    }



}
