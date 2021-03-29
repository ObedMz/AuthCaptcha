package obed.me.captcha.spigot.listener;

import obed.me.captcha.spigot.SpigotMain;
import obed.me.captcha.spigot.connector.BungeeConnector;
import obed.me.captcha.spigot.controller.CapController;
import obed.me.captcha.spigot.objects.Status;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class CaptchaEvent implements Listener {

    @EventHandler
    public void Join(PlayerJoinEvent e){
        Player p = e.getPlayer();
        CapController.getVerify().put(p, false);

        new BukkitRunnable(){

            @Override
            public void run() {

                CapController.getVerify().put(p, CapController.getStatus() == Status.DISABLE);
                if(CapController.getVerify().get(p)){
                    BungeeConnector.sendJSON(p, "User");
                    p.sendMessage(ChatColor.GREEN + "Captcha completado automaticamente, inicia sesión!");
                    p.playSound(p.getLocation(), Sound.NOTE_PLING, 5.0F, 5.0F);
                }
            }
        }.runTaskLater(SpigotMain.getInstance(), 40L);
        new BukkitRunnable(){

            @Override
            public void run() {
                assert p != null;
                if(!CapController.getVerify().get(p))
                    p.kickPlayer(ChatColor.RED + "Has tardado demasiado en completar el captcha.");


            }
        }.runTaskLater(SpigotMain.getInstance(), 20L*20);
    }
    @EventHandler
    public void CommandEvent(PlayerCommandPreprocessEvent e){
        if(!CapController.getVerify().get(e.getPlayer())){
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Primero debes completar el captcha.");
        }

    }

    public static void openMenu(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&6Dale Click a la Estrella"));
        ItemStack stack = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Captcha");
        stack.setItemMeta(meta);
        int rand = new Random().nextInt(54);
        inv.setItem(rand, stack);

        p.openInventory(inv);
    }

    @EventHandler
    public void onPlayerInteractWithEntity(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof LivingEntity)
            openMenu(e.getPlayer());

    }

    @EventHandler
    public void openListMenu(InventoryClickEvent e) {

        String title = ChatColor.translateAlternateColorCodes('&', "&6Dale Click a la Estrella");
        String stripTitle = ChatColor.stripColor(title);
        Player p = (Player)e.getWhoClicked();
        if (ChatColor.stripColor(e.getInventory().getName()).equals(stripTitle)) {
            if (e.getCurrentItem() == null || e.getSlotType() == null || e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }
            CapController.getVerify().put(p, true);
            BungeeConnector.sendJSON(p, "User");
            p.sendMessage(ChatColor.GREEN + "Has completado el captcha correctamente, ahora inicia sesión!");
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 5.0F, 5.0F);
            p.closeInventory();

            e.setCancelled(true);
        }
    }
}
