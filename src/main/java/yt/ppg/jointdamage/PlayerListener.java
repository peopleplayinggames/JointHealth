package yt.ppg.jointdamage;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerListener implements Listener {

    private boolean cooldown = false;

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all != e.getEntity()) {
                    all.playEffect(EntityEffect.HURT);
                    all.setHealth(all.getHealth() - e.getDamage());
                }
            }
        }
    }

    @EventHandler
    public void onChange(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all != e.getEntity()) {
                    all.setHealth(all.getHealth() + e.getAmount());
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (cooldown) return;
        cooldown = true;
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all != e.getEntity()) {
                all.setHealth(0);
            }
        }
        Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> {
            cooldown = false;
        }, 5);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all != e.getEntity()) {
                all.setFoodLevel(e.getFoodLevel());
            }
        }
    }

}
