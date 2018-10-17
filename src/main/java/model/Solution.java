package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a solution objective which stores the tour, packing plan and the objective values.
 */
public class Solution {

    //! the tour of the thief
    public List<Integer> pi;

    //! the packing plan
    public List<Boolean> z;

    //! the time the thief needed for traveling
    public double time;

    //! the profit the thief made on that tour
    public double profit;

    //! the objective values of the function
    public List<Double> objectives;


    public Solution(List<Integer> pi, List<Boolean> z, List<Double> objectives, boolean copy) {
        if (!copy) {
            this.pi = pi;
            this.z = z;
            this.objectives = objectives;
        } else {
            this.pi = new ArrayList<>(pi);
            this.z = new ArrayList<>(z);
            this.objectives = new ArrayList<>(objectives);
        }
        this.time = objectives.get(0);
        this.profit = -objectives.get(1);

    }

    public int getRelation(Solution other) {
        int val = 0;
        for (int i = 0; i < objectives.size(); i++) {

            if (objectives.get(i) < other.objectives.get(i)) {
                if (val == -1) return 0;
                val = 1;
            } else if (objectives.get(i) > other.objectives.get(i)) {
                if (val == 1) return 0;
                val = -1;
            }

        }

        return val;

    }

    public boolean equalsInDesignSpace(Solution other) {
        return pi.equals(other.pi) && z.equals(other.z);
    }

    @Override
    public int hashCode() {
        return objectives != null ? objectives.hashCode() : 0;
    }
}
