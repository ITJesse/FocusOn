/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.itjesse.focuson;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Jesse
 */
public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        Entity target = event.getTarget();
        //EntityTargetEvent.TargetReason reason = event.getReason();
        if(target instanceof Player) {
            if(!target.isDead()){
                Player p = (Player) target;
                EntityType type = event.getEntity().getType();
                String name = this.getConfig().getString("entity." + type, "空气");
                p.sendMessage(ChatColor.YELLOW + "你被" + ChatColor.RED + name 
                        + ChatColor.YELLOW + "盯上了！");
            }
        }
    }
 
}
