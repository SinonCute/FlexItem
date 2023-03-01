package net.minevn.flexitem;

import net.minevn.flexitem.command.PlayerCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class FlexItem extends JavaPlugin {
    private static FlexItem instance;
    private static Storage storage;

    @Override
    public void onEnable() {
        instance = this;
        storage = new Storage();
        Config.loadConfig();
        getCommand("flexitem").setExecutor(new PlayerCommand());
        getCommand("flexitem").setTabCompleter(new PlayerCommand());
        RemoveExpired();
    }

    public static Storage getStorage() {
        return storage;
    }

    private void RemoveExpired() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (var container : storage.getContainers().values()) {
                if (container.isExpired()) storage.removeContainer(container);
            }
        }, 0, 24000);
    }

    public static FlexItem getInstance() {
        return instance;
    }
}
