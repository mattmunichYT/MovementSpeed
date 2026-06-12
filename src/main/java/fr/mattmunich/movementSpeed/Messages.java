package fr.mattmunich.movementSpeed;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Messages {

    private static YamlConfiguration langConfig;
    private static Main main;

    private static String currentLang;

    public Messages(Main main) {
        Messages.main = main;

        loadLanguageFile();
    }

    /**
     * Loads the selected language file from /lang/
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void loadLanguageFile() {

        // Get language from config.yml
        currentLang = main.getConfig().getString("language", "en_US");

        File pluginFolder = main.getDataFolder();
        File langFolder = new File(pluginFolder, "lang");

        if (!langFolder.exists()) {
            main.log("Creating lang folder...");
            langFolder.mkdirs();
        }

        File langFile = new File(langFolder, currentLang + ".yml");

        // If language file doesn't exist, create it
        if (!langFile.exists()) {
            main.log("Creating default language file: " + langFile.getName());

            try {
                langFile.createNewFile();
            } catch (IOException e) {
                main.getLogger().severe("Could not create language file!");
                Bukkit.getPluginManager().disablePlugin(main);
                return;
            }
        }

        langConfig = YamlConfiguration.loadConfiguration(langFile);

        main.log("Loaded language: " + currentLang);
    }

    public static String getMessage(String identifier) {

        if (!langConfig.contains(identifier)) {
            main.getLogger().severe("Missing message: " + identifier +
                    " in lang/" + currentLang + ".yml");
            return "§cMESSAGE NOT FOUND";
        }

        // Prefix special case
        if (identifier.equalsIgnoreCase("prefix")) {
            return langConfig.getString("prefix");
        }

        String message = "§r" + langConfig.getString(identifier);

        // Add prefix automatically
        if (addPrefix(identifier)) {
            message = getMessage("prefix") + "§r " + message;
        }

        return message;
    }

    /**
     * Formats placeholders like %player%
     */
    public static Map<String, String> formatArguments(String... args) {

        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < args.length; i += 2) {
            map.put(args[i], args[i + 1]);
        }

        return map;
    }

    public static String getMessage(String identifier, Map<String, String> arguments) {

        if (!langConfig.contains(identifier)) {
            main.getLogger().severe("Missing message: " + identifier);
            return "§cMESSAGE NOT FOUND";
        }

        String message = langConfig.getString(identifier);

        if (message == null) return "§cMESSAGE NOT FOUND";

        // Replace placeholders
        for (Map.Entry<String, String> entry : arguments.entrySet()) {
            //Ignore unset arguments
            if(!message.contains("%" + entry.getKey() + "%")) continue;
            //Actually replace
            message = message.replace("%" + entry.getKey() + "%", entry.getValue());
        }

        //Add the prefix if required
        if (addPrefix(identifier)) {
            message = getMessage("prefix") + "§r " + message;
        }

        return message;
    }

    private static boolean addPrefix(String identifier) {
        return !identifier.contains("noPrefix")
                && !identifier.equals("prefix")
                && !identifier.contains("title")
                && !identifier.contains("check");
    }
}
