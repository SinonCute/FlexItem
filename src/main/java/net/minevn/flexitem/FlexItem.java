package net.minevn.flexitem;

import net.minevn.flexitem.command.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (var container : storage.getContainers().values()) {
                if (container.isExpired()) {
                    storage.removeContainer(container);
                }
            }
        }, 0, 20 * 60);
    }
}
