package obed.me.captcha.spigot.controller;

import obed.me.captcha.spigot.objects.Status;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CapController {
    private static Status status;
    private static final Map<Player, Boolean> verify = new HashMap<>();

    public static Status getStatus() {
        return status;
    }

    public static void setStatus(Status status) {
        CapController.status = status;
    }

    public static Map<Player, Boolean> getVerify() {
        return verify;
    }
}
