

import org.chocosolver.solver.*;
import org.chocosolver.util.*;

import java.util.*;
import java.io.File;

public class Main {

  public static void main(String[] args) {
    long timeLimit = 300L*1000000000L;
    try {
      Scanner scanner = new Scanner(new File(args[0]));
      int C=scanner.nextInt();
      int P = scanner.nextInt();
      // persons
      for (int i = 0; i < C; i++) {
        String name = scanner.next();
        int N = scanner.nextInt();
        for (int j= 0; j < N; j++) {
          String talent = scanner.next();
          int level = scanner.nextInt();
        }
      }
      //projects
      for (int i = 0; i < P; i++) {
        String name = scanner.next();
        int D = scanner.nextInt();
        int S = scanner.nextInt();
        int B = scanner.nextInt();
        int R = scanner.nextInt();
        for (int j= 0; j < R; j++) {
          String talent = scanner.next();
          int level = scanner.nextInt();
        }
      }
      //int intValue = scanner.nextInt();
      //String stringValue = scanner.next();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Model makeModel(int C, List<String> intToAliment, int[][] allAccepted, int[][] allRefused) {
    Model model = new Model("Problem");
    model.sum(clients, "=", objective).post();
    //model.setObjective(true, objective);
    model.getSolver().plugMonitor(new IMonitorSolution() {
        @Override
        public void onSolution() {
          System.out.println(objective.getValue());
        }
      });
    return model;
  }
}
  
