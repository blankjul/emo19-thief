package algorithms;

import model.Solution;
import model.TravelingThiefProblem;

import java.util.List;

public interface Algorithm {

    List<Solution> solve(TravelingThiefProblem problem);

}
