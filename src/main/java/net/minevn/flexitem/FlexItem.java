package net.minevn.flexitem;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minevn.flexitem.command.PlayerCommand;
import net.minevn.flexitem.object.FlexContainer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public final class FlexItem extends JavaPlugin {

    private static Storage storage;

    @Override
    public void onEnable() {
        storage = new Storage();
        getCommand("flexitem").setExecutor(new PlayerCommand());
        RemoveExpired();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Storage getStorage() {
        return storage;
    }

    private void RemoveExpired() {
        //create task 20m
        //loop storage
        //if expired remove
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (var container : storage.getContainers().values()) {
                if (container.isExpired()) {
                    storage.removeContainer(container);
                }
            }
        }, 0, 20 * 60);
    }
}
