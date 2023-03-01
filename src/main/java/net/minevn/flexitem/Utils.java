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
import remvn.xlibrary.ItemStackUtils;

import java.util.UUID;

import static net.minevn.flexitem.object.TypeContainer.*;

public class Utils {

    public static void sendMessages(Player player, UUID uuid, FlexContainer container) {
        TextComponent announcement = new TextComponent("\n§e§l" + player.getName() + " §6vừa khoe:\n");
        int max = Config.getAmountShow();
        int min = Math.min(container.getItems().size(), max);
        for (int i = 0; i < min; i++) {
            announcement.addExtra("- " + ItemStackUtils.getItemDisplayName(container.getItems().get(i)) + "\n");
        }
        if (container.getItems().size() > max) {
            announcement.addExtra("- Và " + (container.getItems().size() - max) + " vật phẩm khác\n");
        }
        announcement.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {
                new TextComponent("§fNhấn để xem vật phẩm của " + player.getName())
        }));
        announcement.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/flexitem open " + uuid));
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.spigot().sendMessage(announcement);
        }
    }

    public static void openContainer(FlexContainer container) {
        var player = Bukkit.getPlayer(container.getOwner());
        if (player == null) return;
        if (container.getType() == Armor) {
            ChestGui gui = new ChestGui(3, "Vật phẩm của " + player.getName());
            StaticPane pane = new StaticPane(0, 0, 9, 3);
            int x = 1;
            for (var item : container.getItems()) {
                pane.addItem(new GuiItem(item), x, 1);
                x += 2;
            }
            pane.setOnClick(event -> event.setCancelled(true));
            gui.addPane(pane);
            gui.show(player);
            return;
        }
        if (container.getType() == Hand) {
            DropperGui gui = new DropperGui("Vật phẩm của " + player.getName());
            gui.setOnGlobalClick(event -> event.setCancelled(true));
            StaticPane pane = new StaticPane(1, 1, 1, 1);
            pane.addItem(new GuiItem(container.getItems().get(0)), 0, 0);
            gui.getContentsComponent().addPane(pane);
            gui.show(player);
            return;
        }
        if (container.getType() == All) {
            ChestGui gui = new ChestGui(6, "Kho đồ của " + player.getName());
            StaticPane pane = new StaticPane(0, 0, 9, 6);
            addItemPane(container, player, gui, pane);
            return;
        }
        ChestGui gui = new ChestGui(4, "Vật phẩm của " + player.getName());
        StaticPane pane = new StaticPane(0, 0, 4, 9);
        addItemPane(container, player, gui, pane);
    }

    private static void addItemPane(FlexContainer container, Player player, ChestGui gui, StaticPane pane) {
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
