import java.util.*;

public class Project implements Comparable<Project> {

  public String name;
  public int duration;
  public int score;
  public int bestBefore;
  public List<Skill> skills;

  public Project(String name, int duration, int score, int bestBefore, List<Skill> skills) {
    this.name = name;
    this.duration = duration;
    this.score = score;
    this.bestBefore = bestBefore;
    this.skills = skills;
  }

  public List<Person> whoCanDoIt(List<Person> persons) {
    List<Person> canDoIt = new ArrayList<Person>();
    Set<Integer> usedPersons = new HashSet<Integer>();
    for (Skill skill : skills) {
      boolean done = false;
      for (int i = 0; i < persons.size(); i++) {
        if (!usedPersons.contains(i)) {
          if (persons.get(i).canDo(skill)) {
            usedPersons.add(i);
            canDoIt.add(persons.get(i));
            i = persons.size();
            done = true;
          }
          else if (persons.get(i).canAlmostDo(skill) && skill.hasMentor(canDoIt)) {
            usedPersons.add(i);
            canDoIt.add(persons.get(i));
            i = persons.size();
            done = true;
          }
        }
      }
      if (!done)
        return null;
    }
    return canDoIt;
  }

  public double getRank() {
    return (double) score / duration;
  }

  @Override
  public int compareTo(Project p) {
    if (getRank() < p.getRank()) return -1;
    if (getRank() > p.getRank()) return 1;
    return 0;
  }
}
