package codes.carl.AnimalLimiter;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        Main.instance = this;
        getServer().getPluginManager().registerEvents(new BreedLimiter(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled.");
    }

}
