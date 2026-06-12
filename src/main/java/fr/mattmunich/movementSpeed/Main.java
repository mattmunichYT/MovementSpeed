package fr.mattmunich.movementSpeed;

import fr.mattmunich.movementSpeed.commands.Flyspeed;
import fr.mattmunich.movementSpeed.commands.Walkspeed;
import fr.mattmunich.movementSpeed.commands.Speed;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.Objects;

public final class Main extends JavaPlugin {

    Messages messages;

    @Override
    public void onEnable() {
        log("Enabling plugin...");

        loadConfigs();

        loadMessages();

        log("Loading commands...");
        Objects.requireNonNull(getCommand("flyspeed")).setExecutor(new Flyspeed(this));
        Objects.requireNonNull(getCommand("walkspeed")).setExecutor(new Walkspeed(this));
        Objects.requireNonNull(getCommand("speed")).setExecutor(new Speed(this));
        log("Done loading commands.");

        log("Plugin enabled.");
    }

    public void loadConfigs() {
        log("Configuring config files");
        saveDefaultConfig();
        reloadConfig();
        saveResource("lang/en_US.yml", true);
        saveResource("lang/fr_FR.yml", true);
        log("Done configuring config files!");
    }

    public void loadMessages() {
        log("Loading Messages...");
        messages = new Messages(this);
        log("Done loading Messages!");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage("[MovementSpeed] " + message);
    }

    /**
     * Converts user input (-10 to 10) into Bukkit's internal scale (-1.0f to 1.0f)
     */
    public float parseSpeed(String input) {
        try {
            float speed = Float.parseFloat(input);
            if (speed < -10 || speed > 10) {
                return 100;
            }
            // Maps standard 0-10 scale down to Bukkit's internal 0.0-1.0 scale (Default fly speed is 1, which is 0.1f)
            return speed / 10f;
        } catch (NumberFormatException e) {
            return 100;
        }
    }
}
