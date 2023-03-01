package net.minevn.flexitem.command;

import net.minevn.flexitem.Config;
import net.minevn.flexitem.FlexItem;
import net.minevn.flexitem.Storage;
import net.minevn.flexitem.Utils;
import net.minevn.flexitem.object.FlexContainer;
import net.minevn.flexitem.object.TypeContainer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import remvn.xlibrary.ItemStackUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PlayerCommand implements CommandExecutor, TabCompleter {

    private final Storage storage = FlexItem.getStorage();
    private final long expiredTime = Config.getTimeout();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("flexitem.use")) {
            sender.sendMessage("§cBạn không có quyền sử dụng lệnh này!");
            return false;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cChỉ có thể sử dụng lệnh này trong game!");
            return false;
        }

        if (args.length == 0) {
            var container = new FlexContainer(UUID.randomUUID(), player.getUniqueId(),
                    TypeContainer.Hand, System.currentTimeMillis() + expiredTime);
            var item = player.getInventory().getItemInMainHand();
            var items = new ItemStack[]{item};
            return !addItem(player, container, items);
        }

        switch (args[0].toLowerCase()) {
            case "help" -> {
                sender.sendMessage("§a/flexitem help §7- §fXem trợ giúp");
                sender.sendMessage("§a/flexitem §7- §fĐể khoe vật phẩm trong tay");
                sender.sendMessage("§a/flexitem <giap, kiem,...> §7- §fĐể khoe vật phẩm");
            }

            case "giap" -> {
                var container = new FlexContainer(UUID.randomUUID(), player.getUniqueId(),
                        TypeContainer.Armor, System.currentTimeMillis() + expiredTime);
                var items = player.getInventory().getArmorContents();
                if (addItem(player, container, items)) return false;
            }

            case "kiem" -> {
                var container = new FlexContainer(UUID.randomUUID(), player.getUniqueId(),
                        TypeContainer.Item, System.currentTimeMillis() + expiredTime);
                var items = Arrays.stream(player.getInventory().getContents())
                        .filter(item -> item != null && item.getType().name().endsWith("_SWORD"))
                        .toArray(ItemStack[]::new);
                if (addItem(player, container, items)) return false;
            }

            case "riu" -> {
                var container = new FlexContainer(UUID.randomUUID(), player.getUniqueId(),
                        TypeContainer.Item, System.currentTimeMillis() + expiredTime);
                var items = Arrays.stream(player.getInventory().getContents())
                        .filter(item -> item != null && item.getType().name().endsWith("_AXE"))
                        .toArray(ItemStack[]::new);
                if (addItem(player, container, items)) return false;
            }

            case "cup" -> {
                var container = new FlexContainer(UUID.randomUUID(), player.getUniqueId(),
                        TypeContainer.Item, System.currentTimeMillis() + expiredTime);
                var items = Arrays.stream(player.getInventory().getContents())
                        .filter(item -> item != null && item.getType().name().endsWith("_PICKAXE"))
                        .toArray(ItemStack[]::new);
                if (addItem(player, container, items)) return false;
            }

            case "cuoc" -> {
                var container = new FlexContainer(UUID.randomUUID(), player.getUniqueId(),
                        TypeContainer.Item, System.currentTimeMillis() + expiredTime);
                var items = Arrays.stream(player.getInventory().getContents())
                        .filter(item -> item != null && item.getType().name().endsWith("_HOE"))
                        .toArray(ItemStack[]::new);
                if (addItem(player, container, items)) return false;
            }

            case "xeng" -> {
                var container = new FlexContainer(UUID.randomUUID(), player.getUniqueId(),
                        TypeContainer.Item, System.currentTimeMillis() + expiredTime);
                var items = Arrays.stream(player.getInventory().getContents())
                        .filter(item -> item != null && item.getType().name().endsWith("_SHOVEL"))
                        .toArray(ItemStack[]::new);
                if (addItem(player, container, items)) return false;
            }

            case "cancau" -> {
                var container = new FlexContainer(UUID.randomUUID(), player.getUniqueId(),
                        TypeContainer.Item, System.currentTimeMillis() + expiredTime);
                var items = Arrays.stream(player.getInventory().getContents())
                        .filter(item -> item != null && item.getType().name().contains("FISHING_ROD"))
                        .toArray(ItemStack[]::new);
                if (addItem(player, container, items)) return false;
            }

            case "dinhba" -> {
                var container = new FlexContainer(UUID.randomUUID(), player.getUniqueId(),
                        TypeContainer.Item, System.currentTimeMillis() + expiredTime);
                var items = Arrays.stream(player.getInventory().getContents())
                        .filter(item -> item != null && item.getType().name().contains("TRIDENT"))
                        .toArray(ItemStack[]::new);
                if (addItem(player, container, items)) return false;
            }

            case "all" -> {
                var container = new FlexContainer(UUID.randomUUID(), player.getUniqueId(),
                        TypeContainer.All, System.currentTimeMillis() + expiredTime);
                var items = player.getInventory().getContents();
                if (addItem(player, container, items)) return false;
            }

            case "open" -> {
                var container = storage.getContainer(UUID.fromString(args[1]));
                if (container == null) {
                    player.sendMessage("§cKhông tìm thấy vật phẩm!");
                    return false;
                }
                if (container.isExpired()) {
                    player.sendMessage("§cVật phẩm đã hết hạn!");
                    return false;
                }
                Utils.openContainer(container);
            }
            default -> player.sendMessage("§cSai cú pháp! /flexitem help");
        }
        return false;
    }

    private boolean addItem(Player player, FlexContainer container, ItemStack[] items) {
        if (items.length == 0) {
            player.sendMessage("§cBạn không có gì để khoe!");
            return true;
        }
        for (ItemStack item : items) {
            if (item == null || item.getType().isAir()) continue;
            if (Config.getExcludeNames().contains(ItemStackUtils.getItemDisplayName(item).replace("§", "&").substring(2))) continue;
            container.addItem(item);
        }
        storage.addContainer(container);
        Utils.sendMessages(player, container.getUUID(), container);
        return false;
    }

    //auto complete
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("help", "giap", "kiem", "riu", "cup", "cuoc", "xeng", "cancau", "dinhba", "all");
        }
        return null;
    }
}
