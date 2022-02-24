

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.search.loop.lns.neighbors.RandomNeighborhood;
import org.chocosolver.solver.search.limits.FailCounter;
import org.chocosolver.util.tools.ArrayUtils;
import org.chocosolver.solver.ParallelPortfolio;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;

import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) {
    long timeLimit = 300L*1000000000L;
    try {
      Scanner scanner = new Scanner(new File(args[0]));
      int C=scanner.nextInt();
      int[][] allAccepted = new int[C][];
      int[][] allRefused = new int[C][];
      List<String> intToAliment = new ArrayList<String>();
      Map<String,Integer> alimentToInt = new HashMap<String,Integer>();
      for (int i = 0; i < C; i++) {
        int nbAccepted = scanner.nextInt();
        int[] accepted = new int[nbAccepted];
        for (int accI = 0; accI < nbAccepted; accI++) {
          String acc = scanner.next();
          if (!alimentToInt.containsKey(acc)) {
            alimentToInt.put(acc, intToAliment.size());
            intToAliment.add(acc);
          }
          accepted[accI] = alimentToInt.get(acc);
        }
        allAccepted[i] = accepted;
        int nbRefused = scanner.nextInt();
        int[] refused = new int[nbRefused];
        for (int refI = 0; refI < nbRefused; refI++) {
          String ref = scanner.next();
          if (!alimentToInt.containsKey(ref)) {
            alimentToInt.put(ref, intToAliment.size());
            intToAliment.add(ref);
          }
          refused[refI] = alimentToInt.get(ref);
        }
        allRefused[i] = refused;
      }
      
      /*Solver solver = model.getSolver();
      solver.setLNS(new RandomNeighborhood(ArrayUtils.concat(clients, aliments), 100, System.nanoTime()), new FailCounter(model, 100));
      solver.limitTime(600*1000);
      Solution bestSol = new Solution(model, aliments);
      while (solver.solve()) {
        bestSol.record();
        System.out.println(objective.getValue());
      }
      System.out.print(Arrays.stream(aliments).mapToInt(al -> bestSol.getIntVal(al)).sum() + " ");
      for (int i = 0; i < aliments.length; i++) {
        if (bestSol.getIntVal(aliments[i]) == 1)
        System.out.print(intToAliment.get(i) + " ");
        }
        System.out.println();*/
      ParallelPortfolio portfolio = new ParallelPortfolio();
      int nbModels = 5;
      for(int s=0;s<nbModels;s++){
        portfolio.addModel(makeModel(C, intToAliment, allAccepted, allRefused));
      }
      portfolio.solve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Model makeModel(int C, List<String> intToAliment, int[][] allAccepted, int[][] allRefused) {
    Model model = new Model("Pizza");
    BoolVar[] clients = model.boolVarArray(C);
    BoolVar[] aliments = model.boolVarArray(intToAliment.size());
    for (int i = 0; i < C; i++) {
      for (int accI = 0; accI < allAccepted[i].length; accI++) {
        clients[i].imp(aliments[allAccepted[i][accI]]).post();
      }
      for (int refI = 0; refI < allRefused[i].length; refI++) {
        clients[i].and(aliments[allRefused[i][refI]]).not().post();
      }
    }
    IntVar objective = model.intVar(0, C);
    model.sum(clients, "=", objective).post();
    model.setObjective(true, objective);
    model.getSolver().plugMonitor(new IMonitorSolution() {
        @Override
        public void onSolution() {
          System.out.println(objective.getValue());
        }
      });
    return model;
  }
}
  
