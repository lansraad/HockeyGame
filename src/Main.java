import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        load();
        Game game = new Game();
        //save();
    }

    public static void save() {
        try {
            // Use {Gson} to convert the list of teams into JSON and write it to disk
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = new FileWriter("teams.json");

            for (Team team : Game.teamRoster) { //
                team.setOutOfUse();
            }

            gson.toJson(Game.teamRoster, writer);
            writer.close();

        } catch (Exception e) {
            // Catch any exception, though saving isn't vital to continue playing
            System.out.println("> ERROR SAVING TEAMS, DO YOU WANT TO CONTINUE?");
            if (!Util.getConfirmation()) {
                System.out.println("User exit");
                System.exit(0);
            }
        }
    }

    public static void load() throws IOException {
        try {
            System.out.println("> ATTEMPTING TO LOAD TEAMS");
            // Open the JSON using {Gson} and save it as an array of elements
            Team[] importedTeams = new Gson().fromJson(Files.readString(Paths.get("teams.json")), Team[].class);
            // Convert the generic array into a list of {Teams} objects
            Game.teamRoster.addAll(Arrays.asList(importedTeams));
            System.out.println("> TEAMS LOADED");

        } catch (Exception e) {
            // Catch any exception and prompt the user to generate a new team
            System.out.println("> CANNOT FIND PREVIOUS TEAMS, WOULD YOU LIKE TO GENERATE NEW ONES?");
            if (Util.getConfirmation()) {
                Game.createTeams();
            } else {
                System.out.println("Cannot start without teams!");
                System.exit(0);
            }
        }
    }
}
