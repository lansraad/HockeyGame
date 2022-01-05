public class User {
    private String nickName;
    private int score;
    private Team team;

    public User(String nickName) {
        this.nickName = nickName;
    }

    public void increaseScore() {
        this.score++;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    public Team getTeam() {
        return this.team;
    }
    public String getName() {
        return this.nickName;
    }
}
