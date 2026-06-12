package fr.mattmunich.movementSpeed.commands;

import fr.mattmunich.movementSpeed.Main;
import fr.mattmunich.movementSpeed.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Flyspeed implements CommandExecutor {
    private final Main main;

    public Flyspeed(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender s, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        // Case 1: /flyspeed (No arguments)
        if (args.length == 0) {
            return false;
        }

        // Case 2: /flyspeed [speed] -> Changing own speed
        if (args.length == 1) {
            if (!(s instanceof Player p)) {
                s.sendMessage(Messages.getMessage("error.consoleSpecifyPlayer"));
                return true;
            }

            if(args[0].equalsIgnoreCase("reset")) {
                p.setFlySpeed(0.1f);
                p.sendMessage(Messages.getMessage("flyspeed.self.resetSuccess"));
                return true;
            }

            float speed = main.parseSpeed(args[0]);
            if (speed == 100) {
                s.sendMessage(Messages.getMessage("error.invalidSpeed"));
                return true;
            }

            p.setFlySpeed(speed);
            p.sendMessage(Messages.getMessage("flyspeed.self.changeSuccess", Messages.formatArguments("speed", speed*10 + "")));
            return true;
        }

        // Case 3: /flyspeed [player] [speed] -> Changing someone else's speed
        if (args.length == 2) {
            if (!s.hasPermission("movementspeed.command.flyspeed.others")) {
                s.sendMessage(Messages.getMessage("flyspeed.others.noPermission", Messages.formatArguments(
                        "speedType", " fly" //Space required before fly (see language file)
                )));
                return true;
            }

            Player t = Bukkit.getPlayer(args[0]);
            if (t == null) {
                s.sendMessage(Messages.getMessage("error.playerNotFound"));
                return true;
            }

            if(args[1].equalsIgnoreCase("reset")) {
                t.setFlySpeed(0.1f);
                t.sendMessage(Messages.getMessage("flyspeed.others.success.target.reset", Messages.formatArguments(
                        "sender", s.getName()
                )));
                s.sendMessage(Messages.getMessage("flyspeed.others.success.sender.reset", Messages.formatArguments(
                        "target", t.getName()
                )));
                return true;
            }

            float speed = main.parseSpeed(args[1]);
            if (speed == 100) {
                s.sendMessage(Messages.getMessage("error.invalidSpeed"));
                return true;
            }

            t.setFlySpeed(speed);
            t.sendMessage(Messages.getMessage("flyspeed.others.success.target.change", Messages.formatArguments(
                    "speed", speed*10 + "",
                    "sender", s.getName()
            )));
            s.sendMessage(Messages.getMessage("flyspeed.others.success.sender.change", Messages.formatArguments(
                    "target", t.getName(),
                    "speed", speed*10 + ""
            )));
            return true;
        }

        return false;
    }
}