package io.github.avacadowizard120.babygronk;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BabyGronk extends JavaPlugin implements Listener {

    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Baby Gronk has been enabled!");
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event)
    {
        if (event.getEntityType() == EntityType.ZOMBIE)
        {
            Zombie zombie = (Zombie) event.getEntity();
            if (!zombie.isAdult())
            {
                zombie.setCustomName("Baby Gronk");
                zombie.setCustomNameVisible(true);
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Baby Gronk has been disabled!");
    }
}
