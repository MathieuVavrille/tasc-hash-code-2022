import java.util.List;

public class ProjectPeople {

    public Project project;
  public int minStartTime;
  public int terminationTime;
    public List<Person> people;

    public ProjectPeople(Project project, List<Person> people) {
        this.project = project;
        this.people = people;
        if (project.skills.size() != people.size())
          throw new IllegalStateException("bug");
        minStartTime = people.stream().mapToInt(p -> p.nextTimeAvailable).max().getAsInt();
        terminationTime = minStartTime+project.duration;
    }

  public void teach() {
    for (int i = 0; i < people.size(); i++) {
      people.get(i).doImproveSkillOrNot(project.skills.get(i));
    }
  }

  public int score() {
    return Math.min(project.score, project.score-(terminationTime-project.bestBefore));
  }

  public void updateEndTimes() {
    people.stream().forEach(p -> p.nextTimeAvailable = terminationTime);
  }
}
