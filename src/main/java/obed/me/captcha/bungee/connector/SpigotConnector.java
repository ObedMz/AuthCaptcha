package obed.me.captcha.bungee.connector;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import obed.me.captcha.bungee.BungeeMain;
import obed.me.captcha.bungee.controller.CapController;
import obed.me.captcha.bungee.objects.Status;


import java.io.*;

public class SpigotConnector implements Listener {
    private static final Gson GSON = new Gson();
    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) {
        if(!e.getTag().equalsIgnoreCase("cap:auth"))
            return;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(e.getData());
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        try {
            JsonObject jbo = GSON.fromJson(dataInputStream.readUTF(), JsonObject.class);
            String nick =jbo.get("value").getAsString();
            String task = jbo.get("type").getAsString();

            if(task.equalsIgnoreCase("User"))
                CapController.getVerify().put(BungeeMain.getInstance().getProxy().getPlayer(nick), true);


        } catch (IOException iOException) {
            iOException.printStackTrace();
        }


    }

    public static void enableCaptcha(ServerInfo paramServerInfo , Boolean value){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            JsonObject paramJsonObject = new JsonObject();
            paramJsonObject.addProperty("type", "server");
            paramJsonObject.addProperty("value", value ? Status.ENABLE.toString() : Status.DISABLE.toString());

            dataOutputStream.writeUTF(paramJsonObject.toString());

        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        if (paramServerInfo == null)
            return;
        paramServerInfo.sendData("cap:auth", byteArrayOutputStream.toByteArray());

    }

}
