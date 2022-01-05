import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static ArrayList<Team> teamRoster = new ArrayList<>();

    public Game() throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.println("""
                \n╒═══════════════╡ HOCKEY GAME╞═══════════════╕
                    Please enter your nicknames below:
                ╘════════════════════════════════════════════╛
                """);
        System.out.print("Player 1 > ");
        User user1 = new User(scan.nextLine());
        System.out.print("Player 2 > ");
        User user2 = new User(scan.nextLine());

        selectTeam(user1);
        selectTeam(user2);

        Main.save(); // Save the possibly altered teams

        System.out.printf("%s \uD835\uDE51\uD835\uDE4E %s!\n", user1.getTeam().getName(), user2.getTeam().getName());

        Player defender1 = getPlayer(user1, "Select your defender!\n");
        System.out.printf("\nYou've selected %s to defend!\n", defender1.getName());

        Player defender2 = getPlayer(user2, "Select your defender!\n");
        System.out.printf("\nYou've selected %s to defend!\n", defender2.getName());

        int flip = 0;
        while (true) {
            flip = (flip + 1) % 2;

            if (flip == 0) {
                Player attacker = getPlayer(user1, "Select your attacker!\n");
                penalty(attacker, defender2);
            } else {
                Player attacker = getPlayer(user2, "Select your attacker!\n");
                penalty(attacker, defender1);
            }
        }
    }

    public void penalty (Player attacker, Player defender) throws InterruptedException {
        Random rand = new Random();
        int difference = defender.getDefence() - attacker.getAttack();
        int result = difference + rand.nextInt(5) + 1;
        System.out.println();

        for (int i=0; i<10; i++) {
            System.out.print("* ");
            Thread.sleep(250);
        }

        System.out.println((result < 0) ? "\nYou scored!\n" : "\nSaved - Try harder next time 🔫\n");
        Thread.sleep(3000);
    }

    public Player getPlayer(User user, String message) {
        Team team = user.getTeam();
        String username = user.getName();
        Scanner scan = new Scanner(System.in);
        Player player;
        while (true) {
            try {
                team.listPlayers();
                System.out.println(message);
                System.out.printf("%s > ", username);
                player = team.getFromID(Integer.parseInt(scan.nextLine()));
                if (!Objects.isNull(player)) {
                    if (player.isActive()) {
                        player.setInactive();
                        return player;
                    } else {
                        System.out.println("You cannot use the same attacker again!");
                    }
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    public void selectTeam(User user) {
        Scanner scan = new Scanner(System.in);
        do {
            for (Team team : teamRoster) {
                if (!team.inUse()) {
                    team.listPlayers();
                }
            }

            System.out.println("""
                \n╒═══════════════╡ HOCKEY GAME╞═══════════════╕
                    Please Select your team:
                ╘════════════════════════════════════════════╛
                """);

            System.out.printf("%s > ", user.getName());
            String search = scan.nextLine();

            for (Team team : teamRoster) {
                if (search.equalsIgnoreCase(team.getName()) && !team.inUse()) {
                    user.setTeam(team);
                    System.out.printf("You have selected %s, would you like to rename it?\n", team.getName());
                    team.setInUse();

                    if (Util.getConfirmation()) {
                        System.out.print("Enter a new name: ");
                        team.setName(scan.nextLine());
                        System.out.println("Complete!");
                    }
                    return;
                }
            }
        } while (true);
    }

    public static void createTeams() throws IOException {
        teamRoster.clear();
        for(int i=0; i<4; i++) {
            Team team = new Team("Team " + (i+1));
            team.generate(6, 35);
            teamRoster.add(team);
        }
        System.out.println("Generation successful!");
        Main.save();
    }
}
