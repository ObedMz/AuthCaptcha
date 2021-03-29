package obed.me.captcha.spigot.connector;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import obed.me.captcha.spigot.SpigotMain;
import obed.me.captcha.spigot.controller.CapController;
import obed.me.captcha.spigot.objects.Status;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;

public class BungeeConnector implements PluginMessageListener {
    private final Gson GSON = new Gson();

    @Override
    public void onPluginMessageReceived(String paramString, Player player, byte[] paramArray) {

        if (!paramString.equals("cap:auth"))
            return;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArray);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        try {
            JsonObject jbo = this.GSON.fromJson(dataInputStream.readUTF(), JsonObject.class);
            String task = jbo.get("type").getAsString();

            if(task.equalsIgnoreCase("server")) CapController.setStatus(Status.valueOf(jbo.get("value").getAsString()));
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public static void sendJSON(Player paramPlayer, String task) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            JsonObject paramJsonObject = new JsonObject();
            paramJsonObject.addProperty("value", paramPlayer.getName());
            paramJsonObject.addProperty("type", task);
            dataOutputStream.writeUTF(paramJsonObject.toString());

        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        paramPlayer.sendPluginMessage(SpigotMain.getInstance(), "cap:auth", byteArrayOutputStream.toByteArray());
    }
}
