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
            "Baby Gronk sends his regards to {player}'s family and friends",
            "{player} just got rizzed by Baby Gronk",
            "Baby Gronk just tested out his new killer rizz on {player}",
            "Baby Gronk cheated on Livvy Dunne with {player}",
            "Soooo {player}'s getting rizzed up by Baby Gronk...",
            "Soooo Baby Gronk did not ask {player} for consent...",
            "Soooo Baby Gronk just put it in {player}...",
            "Livvy Dunne wasn't enough for Baby Gronk... {player} was...",
            "{player} got caught lacking by Baby Gronk"
    );

    private final List<String> diegoDeathMessages = Arrays.asList(
            "{player}'s fat ass couldn't out run Baby Gronk!",
            "Baby Gronk couldn't resist motor-boating {player}'s fat ass!",
            "{player} fell for Baby Gronk's food offering",
            "Soooo we're overweight...",
            "{player} are too much and couldn't escape Baby Gronk",
            "Goodwill wifi was no match for Baby Gronk",
            "Goodwill wifi wasn't as fast as Baby Gronk",
            "Baby Gronk couldn't resist {player}'s level 1000 gyat",
            "Baby Gronk beats {player}",
            "Diego no pudo subirse a su bicicleta lo suficientemente rápido como para escapar de Baby Gronk"
    );

    private final List<String> edwinDeathMessages = Arrays.asList(
            "Baby Gronk couldn't handle {player}'s yapping",
            "{player} was within yapping distance of Baby Gronk",
            "{player} tried to tell Baby Gronk he was yapping...",
            "Edwin fue violado por Baby Gronk"
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
            if (player.getName().equals("ButterMines"))
            {
                String victimName = player.getName();
                String randomDeathMessage = getDiegoDeathMessage(victimName);
                event.setDeathMessage(randomDeathMessage);
            } else if (player.getName().equals(".Edwin90923point")) {
                String victimName = player.getName();
                String randomDeathMessage = getEdwinDeathMessage(victimName);
                event.setDeathMessage(randomDeathMessage);
            }else {
                String victimName = player.getName();
                String randomDeathMessage = getRandomDeathMessage(victimName);
                event.setDeathMessage(randomDeathMessage);
            }
        }
    }




    private String getRandomDeathMessage(String playerName) {
        String message = deathMessages.get(random.nextInt(deathMessages.size()));
        return message.replace("{player}", playerName);
    }

    private String getDiegoDeathMessage(String playerName)
    {
        String message = diegoDeathMessages.get(random.nextInt(diegoDeathMessages.size()));
        return message.replace("{player}", playerName);
    }
    private String getEdwinDeathMessage(String playerName)
    {
        String message = edwinDeathMessages.get(random.nextInt(edwinDeathMessages.size()));
        return message.replace("{player}", playerName);
    }

    @Override
    public void onDisable() {
        getLogger().info("Baby Gronk has been disabled!");
    }
}