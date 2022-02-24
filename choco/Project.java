import java.util.List;

public class Project {

    public String name;
    public String duration;
    public int score;
    public int bestBefore;
    public List<Skill> roles;

    public Project(String name, String duration, int score, int bestBefore, List<Skill> roles) {
        this.name = name;
        this.duration = duration;
        this.score = score;
        this.bestBefore = bestBefore;
        this.roles = roles;
    }
}
