package obed.me.captcha.bungee.controller;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import obed.me.captcha.bungee.BungeeMain;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigController {
    private static final ConfigurationProvider cp = ConfigurationProvider.getProvider(YamlConfiguration.class);
    Configuration config;

    public Configuration getConfig(){
        if(config == null){
            reloadConfig();
        }
        return this.config;
    }


    public void reloadConfig() {
        saveConfig();
        try{
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(BungeeMain.getInstance().getDataFolder(), "config.yml"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void saveConfig(){
        File file = new File(BungeeMain.getInstance().getDataFolder(), "config.yml");
        try {
            Configuration cgf = cp.load(file);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cgf, new File(BungeeMain.getInstance().getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerConfig() {
        if(!BungeeMain.getInstance().getDataFolder().exists())
            BungeeMain.getInstance().getDataFolder().mkdir();

        File file = new File(BungeeMain.getInstance().getDataFolder(), "config.yml");
        if(!file.exists()){
            try {
                InputStream in = BungeeMain.getInstance().getResourceAsStream("config.yml");
                Files.copy(in, file.toPath());
                this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(BungeeMain.getInstance().getDataFolder(), "config.yml"));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}
