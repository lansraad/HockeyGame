import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Team {
    private ArrayList<Player> players = new ArrayList<>();
    private String name;
    private boolean inUse = false;

    public Team(String name) throws IOException {
        this.name = name;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setInUse() {
        this.inUse = true;
    }
    public void setOutOfUse() {
        this.inUse = false;
    }
    public boolean inUse() {
        return this.inUse;
    }

    public Player getFromID(int id) {
        for (Player player : players) {
            if (id == player.getId()) {
                return player;
            }
        }
        return null;
    }

    public void listPlayers() {
        String line = "─".repeat(20 - (name.length()/2));
        System.out.printf("┌%s┥ %s ┝%s┐\n", line, name, line);
        System.out.format("    %-20s%11s%10s\n", "Name", "Attack", "Defence");

        int i=0;
        for (Player player : players) {
            String playerID = String.valueOf(player.getId());
            // Make the text appear strikethrough to show the user that the player cannot be used
            if (!player.isActive()) playerID = "X";
            i++;
            System.out.format(" %s) %-20s %5d %8d\n", playerID, player.getName(), player.getAttack(), player.getDefence());
        }
        System.out.println("└────────────────────────────────────────────┘");
    }

    public void generate(int size, int totalStats) throws IOException {
        double[] stats = Util.generateRandoms(totalStats, size*2);
        ArrayList names = new ArrayList<>();
        Gson gson = new Gson();

        URL url = new URL("https://randommer.io/api/Name?nameType=fullname&quantity=" +size);

        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("X-Api-Key", "1d94ab71e2e948c99c2505311bd1f78d");

        if (http.getResponseCode() != 200) System.out.println("HTTP error code: " + http.getResponseCode());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()))) {
            names = gson.fromJson(reader.readLine(), ArrayList.class);
        }

        http.disconnect();

        int j=0;
        for(int i=0; i < size; i++) {

            players.add(
                    new Player(
                            (String) names.get(i),
                            (int) stats[j],
                            (int) stats[j+1],
                            i+1
                    )
            );
            j=j+2;
        }
    }
}
