/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.itjesse.focuson;

import java.util.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

    private Map<String, Long> sendList = new HashMap<String, Long>();

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
        EntityType type = event.getEntity().getType();
        if (((target instanceof Player)) && (!"EXPERIENCE_ORB".equals(type.toString())) && (!target.isDead())) {
            Long now = System.currentTimeMillis() / 1000;

            Player p = (Player)target;
            String playName = p.getDisplayName();
            Long lastSend = sendList.get(playName);
            if (lastSend != null) {
                if (now - lastSend > 1) {
                    sendList.remove(playName);
                    sendList.put(playName, now);
                } else {
                    return;
                }
            } else {
                sendList.put(playName, now);
            }
            Location monsterLocation = event.getEntity().getLocation();
            Location playerLocation = p.getLocation();
            int distance = (int)Math.floor(monsterLocation.distance(playerLocation));
            String reason = event.getReason().toString();
            // getLogger().info(playName);
            // getLogger().info(reason);
            // getLogger().info("Send time: " + now);
            String name = getConfig().getString("entity." + type, "未知生物");
            String reason_cn = getConfig().getString("reason." + reason, "No why!");
            p.sendMessage(ChatColor.YELLOW + "你被" + ChatColor.RED + name + ChatColor.YELLOW + "盯上了！因为" + ChatColor.RED + reason_cn + ChatColor.YELLOW + "还有" + ChatColor.RED + distance + "m" + ChatColor.YELLOW + "被爆菊！");
        }
    }
 
}
