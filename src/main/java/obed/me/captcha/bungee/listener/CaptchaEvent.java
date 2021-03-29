package obed.me.captcha.bungee.listener;

import com.lahuca.botsentry.api.BotSentryAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import obed.me.captcha.bungee.BungeeMain;
import obed.me.captcha.bungee.connector.SpigotConnector;
import obed.me.captcha.bungee.controller.CapController;
import obed.me.captcha.bungee.objects.Status;

public class CaptchaEvent implements Listener {



    @EventHandler
    public void onJoin(PostLoginEvent e){
        ProxiedPlayer pp = e.getPlayer();
        boolean val = BotSentryAPI.getAPI().getAntiBotMode().isAntiBotMode();
        ServerInfo sv = BungeeMain.getInstance().getProxy().getServerInfo(BungeeMain.getInstance().getConfig().getConfig().getString("bungee.authserver"));
        CapController.setStatus(val ? Status.ENABLE : Status.DISABLE);
        SpigotConnector.enableCaptcha(sv, val);
        CapController.getVerify().put(pp, false);

    }
    @EventHandler(priority = 64)
    public void command(ChatEvent e){

        if(CapController.getStatus() == Status.DISABLE)
            return;

        ProxiedPlayer pp = (ProxiedPlayer) e.getSender();
        if(!CapController.getVerify().get(pp)){
            pp.sendMessage(ChatColor.RED + "Primero debes completar el captcha.");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void leave(PlayerDisconnectEvent e){
        CapController.getVerify().remove(e.getPlayer());
    }



}
