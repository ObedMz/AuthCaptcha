package obed.me.captcha.bungee.controller;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import obed.me.captcha.bungee.objects.Status;

import java.util.HashMap;
import java.util.Map;

public class CapController {
    private static Status status;
    private static final Map<ProxiedPlayer, Boolean> verify = new HashMap<>();

    public static Status getStatus() {
        return status;
    }

    public static void setStatus(Status val) {
        status = val;
    }

    public static Map<ProxiedPlayer, Boolean> getVerify() {
        return verify;
    }
}
