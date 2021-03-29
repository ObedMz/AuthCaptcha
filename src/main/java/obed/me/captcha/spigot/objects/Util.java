package obed.me.captcha.spigot.objects;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import obed.me.captcha.spigot.SpigotMain;
import obed.me.captcha.spigot.controller.CapController;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Util {

    public static void sendRunnableMessage(){
        new BukkitRunnable(){

            @Override
            public void run() {
                for(Player p : CapController.getVerify().keySet()){
                    if(!CapController.getVerify().get(p)){
                        p.playSound(p.getLocation(), Sound.ANVIL_LAND, 5.0F, 5.0F);
                        sendTitle(p, ChatColor.translateAlternateColorCodes('&', "&cCaptcha"),5,5,5);
                        sendSubTitle(p, ChatColor.translateAlternateColorCodes('&', "&7Click al NPC para continuar."),5,5,5);
                        sendActionBar(ChatColor.translateAlternateColorCodes('&', "&7Click al NPC para continuar."),p);
                    }
                }
            }
        }.runTaskTimer(SpigotMain.getInstance(), 0L,40L);
    }
    public static void sendTitle(Player player, String mensaje, int fadein, int stay, int fadeout) {
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + mensaje + "\"}"), fadein, stay, fadeout);
        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(title);
    }

    public static void sendSubTitle(Player player, String mensaje, int fadein, int stay, int fadeout) {
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + mensaje + "\"}"), fadein, stay, fadeout);
        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(title);
    }

    public static void sendActionBar(String mensaje, Player player){
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + mensaje + "\"}"), (byte) 2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }
}
