public class Player {
    private String name;
    private boolean active = true;
    private int attack;
    private int defence;
    private int id;

    public Player(String name, int attack, int defence, int id) {
        this.name = name;
        this.attack = attack;
        this.defence = defence;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public int getId() { return id; }

    public int getAttack() {
        return attack;
    }

    public void setInactive() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public int getDefence() {
        return defence;
    }
}
