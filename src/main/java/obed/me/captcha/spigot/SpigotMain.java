package obed.me.captcha.spigot;

import obed.me.captcha.spigot.connector.BungeeConnector;
import obed.me.captcha.spigot.controller.CapController;
import obed.me.captcha.spigot.listener.CaptchaEvent;
import obed.me.captcha.spigot.objects.Status;
import obed.me.captcha.spigot.objects.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotMain extends JavaPlugin {
    private static Plugin instance;

    @Override
    public void onEnable(){
        instance = this;
        CapController.setStatus(Status.DISABLE);
        Util.sendRunnableMessage();
        Bukkit.getPluginManager().registerEvents(new CaptchaEvent(), this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "cap:auth");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "cap:auth", new BungeeConnector());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[Auth-Captcha] Enabled.");
    }

    public static Plugin getInstance(){
        return instance;
    }

}
