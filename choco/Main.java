

import org.chocosolver.solver.*;
import org.chocosolver.util.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class Main {

  public static void main(String[] args) {
    String filename = args[0];
    long timeLimit = 300L*1000000000L;
    try {
      Scanner scanner = new Scanner(new File(filename));
      int C=scanner.nextInt();
      int P = scanner.nextInt();
      List<Person> persons = new ArrayList<Person>();
      // persons
      for (int i = 0; i < C; i++) {
        String name = scanner.next();
        int N = scanner.nextInt();
        List<Skill> skills = new ArrayList<Skill>();
        for (int j= 0; j < N; j++) {
          String talent = scanner.next();
          int level = scanner.nextInt();
          skills.add(new Skill(talent, level));
        }
        persons.add(new Person(name, skills));
      }
      //projects
      List<Project> projects = new ArrayList<Project>();
      for (int i = 0; i < P; i++) {
        String name = scanner.next();
        int D = scanner.nextInt();
        int S = scanner.nextInt();
        int B = scanner.nextInt();
        int R = scanner.nextInt();
        List<Skill> skills = new ArrayList<Skill>();
        for (int j= 0; j < R; j++) {
          String talent = scanner.next();
          int level = scanner.nextInt();
          skills.add(new Skill(talent, level));
        }
        projects.add(new Project(name, D, S, B, skills));
      }
      Collections.sort(projects, Collections.reverseOrder());
      saveResults(greedyLevelUpRerun(persons, projects), filename);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static List<ProjectPeople> greedySimple(List<Person> persons, List<Project> projects) {
    List<ProjectPeople> pp = new ArrayList<ProjectPeople>();
    for (Project project : projects) {
      List<Person> talented = project.whoCanDoIt(persons);
      if (talented != null) {
        pp.add(new ProjectPeople(project, talented));
      }
    }
    return pp;
  }

  public static List<ProjectPeople> greedyLevelUpRerun(List<Person> people, List<Project> projects) {
    Random random = new Random(97);
    Set<Project> usedProjects = new HashSet<Project>();
    List<ProjectPeople> pp = new ArrayList<ProjectPeople>();
    boolean modified = true;
    while (modified) {
      modified = false;
      ProjectPeople bestProjectP = null;
      double bestScore = Double.MIN_VALUE;
      for (Project project : projects) {
        if (!usedProjects.contains(project)) {
          List<Person> talented = project.whoCanDoIt(people);
          if (talented != null) {
            ProjectPeople smallPP = new ProjectPeople(project, talented);
            double scorePP = smallPP.weightedScore();
            if (scorePP > bestScore && random.nextDouble() < 0.1) {
              bestProjectP = smallPP;
              bestScore = scorePP;
            }
          }
        }
      }
      if (bestProjectP != null) {
        bestProjectP.teach();
        pp.add(bestProjectP);
        usedProjects.add(bestProjectP.project);
        modified = true;
      }
    }
    return pp;
  }

  public static void saveResults(List<ProjectPeople> projects, String filename) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".out"));
      writer.write(projects.size() + "\n");
      for (ProjectPeople p : projects) {
        writer.write(p.project.name + "\n");
        for (Person pers : p.people) {
          writer.write(pers.name + " ");
        }
        writer.write("\n");
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /*private static Model makeModel(int C, List<String> intToAliment, int[][] allAccepted, int[][] allRefused) {
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
    }*/
}
  
