package org.kasimantonyo.kessentials;

import commands.*;
import events.General;
import events.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitScheduler;
import utils.HologramManager;

import java.io.File;

// Welcome to KEssentials, this plugin offers you
// a high-quality functions in your minecraft server

// The code of this plugin will be temporal because
// i was testing some things and i know that is a
// disaster, the code, so i will change the code
// in a future and then make it public in SpigotMC
// for now you can take pieces of code to test it
// in ur plugins and stuff like that

public final class KEssentials extends JavaPlugin {
    // Ponemos pa que sea c.sendmessage
    ConsoleCommandSender c = getServer().getConsoleSender();

    // Consas de config, msgs.yml, sounds.yml y más
    private FileConfiguration messagesConfig;
    private FileConfiguration config;

    private FileConfiguration soundsConfig;


    // Hologram Manager
    private HologramManager hologramManager;
    private File soundsFile;

    // Booleano planeado para usar despues nose para que
    public boolean hasPapi;

    public static KEssentials plugin;

    //
    public BukkitScheduler sh = Bukkit.getScheduler();
    //



    // Metodo onEnable
    @Override
    public void onEnable() {
        // Registrar comandos
        registerCommands();

        // Cargar y administrar config.yml
        loadConfig();

        // Cargar y administrar messages.yml
        loadMessagesConfig();

        // Cargar y administrar sounds.yml
        loadSoundsConfig();

        // Registramos cosas extras xd
        registerStuff();

        // Si placeholderapi es null se apagará el plugin
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            c.sendMessage("PlaceholderAPI is not installed, the plugin needs PlaceholderAPI to work.");
            c.sendMessage("Disabling KEssentials...");
            // Aquí se apaga
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        plugin = this;
        // Si placeholderapi no es null
        // Servirá
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            c.sendMessage("PlaceholderAPI has been detected! Hooking...");
        saveDefaultConfig();
        // Registrar cosas extras xd
        // Cargar y administrar schedule tab
    }

    // Metedo onDisable
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // Metodos de configuraciones manager
    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getSoundsConfig() {
        return soundsConfig;
    }

    public HologramManager getHologramManager() {
        return hologramManager;
    }


    public void loadMessagesConfig() {
        File messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }

        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void loadConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void loadSoundsConfig() {
        soundsFile = new File(getDataFolder(), "sounds.yml");

        if (!soundsFile.exists()) {
            saveResource("sounds.yml", false);
        }

        soundsConfig = YamlConfiguration.loadConfiguration(soundsFile); // Cargar la configuración desde el archivo
    }

// Registramos Comandos
    public void registerCommands() {
        getCommand("gm").setExecutor(new GamemodeCommand(this));
        getCommand("download").setExecutor(new Plugins(this));
        getCommand("staffchat").setExecutor(new StaffChat(this));
        getCommand("rules").setExecutor(new RulesCommand(this));
        getCommand("tp").setExecutor(new TPCommand(this));
        getCommand("kessentials").setExecutor(new KEssentialsInfo(this));
        getCommand("ping").setExecutor(new PingCommand(this));
        getCommand("clearchat").setExecutor(new ClearChat(this));
        getCommand("invsee").setExecutor(new Invsee(this));
        getCommand("heal").setExecutor(new FHCommands(this));
        getCommand("feed").setExecutor(new FeedCommand(this));
        getCommand("hologram").setExecutor(new HologramCommand(this));
        getCommand("workbench").setExecutor(new WorkbenchCommand(this));
        getCommand("fly").setExecutor(new FlyCommand(this));
        getCommand("tpa").setExecutor(new TPACommand(this));
        getCommand("tpaccept").setExecutor(new TPACommand(this));
    }

    // Registramos cositas extras claro
    public void registerStuff() {
        hologramManager = new HologramManager(this);
        getServer().getPluginManager().registerEvents(new General(this), this);
        getServer().getPluginManager().registerEvents(new Sounds(this), this);

    }



    }

