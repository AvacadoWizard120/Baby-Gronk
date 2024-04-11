package io.github.avacadowizard120.babygronk;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class BabyGronk extends JavaPlugin implements Listener {

    private final List<String> deathMessages = Arrays.asList(
            "Baby Gronk sends his regards to {player}'s family and friends.",
            "{player} just got rizzed by Baby Gronk.",
            "Baby Gronk just tested out his new killer rizz on {player}.",
            "Baby Gronk cheated on Livvy Dunne with {player}."
    );

    private boolean isBabyGronk = false;

    private String playerKilledByGronk;

    private final Random random = new Random();

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getServer().getPluginManager().registerEvents(this, this);
            getLogger().info("PlaceholderAPI found!");
            getLogger().info("Baby Gronk has been enabled!");
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.ZOMBIE) {
            Zombie zombie = (Zombie) event.getEntity();
            if (!zombie.isAdult()) {
                zombie.setCustomName("Baby Gronk");
                zombie.setCustomNameVisible(true);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent damageEvent) {
                if (damageEvent.getDamager() instanceof Zombie zombie) {
                    if (!zombie.isAdult() && zombie.getCustomName() != null && zombie.getCustomName().equals("Baby Gronk")) {
                        isBabyGronk = true;
                        playerKilledByGronk = player.getName();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();

        if (isBabyGronk && player.getName().equals(playerKilledByGronk))
        {
            String victimName = player.getName();
            String randomDeathMessage = getRandomDeathMessage(victimName);
            event.setDeathMessage(randomDeathMessage);
        }
    }




    private String getRandomDeathMessage(String playerName) {
        String message = deathMessages.get(random.nextInt(deathMessages.size()));
        return message.replace("{player}", playerName);
    }

    @Override
    public void onDisable() {
        getLogger().info("Baby Gronk has been disabled!");
    }
}