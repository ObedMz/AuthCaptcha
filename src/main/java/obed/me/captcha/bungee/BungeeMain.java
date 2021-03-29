package obed.me.captcha.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import obed.me.captcha.bungee.connector.SpigotConnector;
import obed.me.captcha.bungee.controller.CapController;
import obed.me.captcha.bungee.controller.ConfigController;
import obed.me.captcha.bungee.listener.CaptchaEvent;
import obed.me.captcha.bungee.objects.Status;

public class BungeeMain extends Plugin {
    private ConfigController config;
    private static BungeeMain instance;
    @Override
    public void onEnable() {
        instance = this;
        config = new ConfigController();
        config.registerConfig();
        getProxy().registerChannel( "cap:auth");
        ProxyServer.getInstance().getPluginManager().registerListener(this, new SpigotConnector());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new CaptchaEvent());

        CapController.setStatus(Status.DISABLE);
        getProxy().getLogger().info(ChatColor.GREEN + "[Auth-Captcha] Enabled.");

    }


    public static BungeeMain getInstance(){
        return instance;
    }

    public ConfigController getConfig() {
        return config;
    }
}
