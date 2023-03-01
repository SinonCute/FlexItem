package net.minevn.flexitem;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.DropperGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minevn.flexitem.object.FlexContainer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

import static net.minevn.flexitem.object.TypeContainer.Armor;
import static net.minevn.flexitem.object.TypeContainer.Hand;

public class Utils {

    public static void sendMessages(Player player, UUID uuid, FlexContainer container) {
        TextComponent announcement = new TextComponent("§e§l[§f§lFlexItem§e§l] §f" + player.getDisplayName() + " vừa khoe:");
        TextComponent items = new TextComponent();
        int max = 5;
        int min = Math.min(container.getItems().size(), max);
        for (int i = 0; i < min; i++) {
            items.addExtra("- " + container.getItems().get(i).getItemMeta().getDisplayName() + "\n");
        }
        if (container.getItems().size() > max) {
            items.addExtra("- Và " + (container.getItems().size() - max) + " vật phẩm khác");
        }
        items.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {
                new TextComponent("§fNhấn để xem vật phẩm của " + player.getDisplayName())
        }));
        items.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/flexitem open " + uuid));
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.spigot().sendMessage(announcement, items);
        }
    }

    public static void openContainer(FlexContainer container) {
        var player = Bukkit.getPlayer(container.getOwner());
        if (container.getType() == Armor) {
            ChestGui gui = new ChestGui(3, "Khoe vật phẩm của " + player.getDisplayName());
            StaticPane pane = new StaticPane(0, 0, 9, 3);
            int x = 1;
            for (var item : container.getItems()) {
                Bukkit.broadcastMessage(item.getType().name() + " x: " + x);
                pane.addItem(new GuiItem(item), x, 1);
                x += 2;
            }
            pane.setOnClick(event -> event.setCancelled(true));
            gui.addPane(pane);
            gui.show(player);
            return;
        }
        if (container.getType() == Hand) {
            DropperGui gui = new DropperGui("Khoe vật phẩm của " + player.getDisplayName());
            gui.setOnGlobalClick(event -> event.setCancelled(true));
            StaticPane pane = new StaticPane(1, 1, 1, 1);
            pane.addItem(new GuiItem(container.getItems().get(0)), 0, 0);
            gui.getContentsComponent().addPane(pane);
            gui.show(player);
            return;
        }
        ChestGui gui = new ChestGui(4, "Khoe vật phẩm của " + player.getDisplayName());
        StaticPane pane = new StaticPane(0, 0, 4, 9);
        int x = 0, y = 0;
        for (var item : container.getItems()) {
            pane.addItem(new GuiItem(item), x, y);
            x++;
            if (x == 9) {
                x = 0;
                y++;
            }
        }
        pane.setOnClick(event -> event.setCancelled(true));
        gui.addPane(pane);
        gui.show(player);
    }
}
