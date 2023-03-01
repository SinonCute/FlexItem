package net.minevn.flexitem;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.DropperGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minevn.flexitem.object.FlexContainer;
import net.minevn.flexitem.object.TypeContainer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Utils {

    public static void sendMessages(Player player, UUID uuid) {
        TextComponent message = new TextComponent(player.getDisplayName() + " vừa khoe 1 cái gì đó");
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/flexitem open " + uuid));
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.spigot().sendMessage(message);
        }
    }

    public static void openContainer(FlexContainer container) {
        var player = Bukkit.getPlayer(container.getOwner());
        if (container.getType() == TypeContainer.Armor) {
            ChestGui gui = new ChestGui(4, "Khoe vật phẩm của " + player.getDisplayName());
            StaticPane pane = new StaticPane(0, 0, 9, 6);
            int x = 4, y = 0;
            for (var item : container.getItems()) {
                Bukkit.broadcastMessage(item.getType().name() + " x: " + x + " y: " + y + "");
                pane.addItem(new GuiItem(item), x, y);
                x += 9;
                y++;
            }
            pane.setOnClick(event -> event.setCancelled(true));
            gui.addPane(pane);
            gui.show(player);
            return;
        }
        if (container.getType() == TypeContainer.Hand) {
            DropperGui gui = new DropperGui("Khoe vật phẩm của " + player.getDisplayName());
            gui.setOnGlobalClick(event -> event.setCancelled(true));
            StaticPane pane = new StaticPane(0, 0, 3, 9);
            pane.addItem(new GuiItem(container.getItems().get(0)), 5, 1);
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
