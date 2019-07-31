package io.github.animeavi.notntfactions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.Conf;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;

public class Main extends JavaPlugin implements Listener {
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();

        // Check for primed TNT
        if (entity instanceof TNTPrimed) {
            boolean canBlowUp = false;
            FLocation fLocation = new FLocation(event.getLocation());
            Faction faction = Board.getInstance().getFactionAt(fLocation);
            boolean fWilderness = faction.isWilderness() && !Conf.wildernessDenyBuild;
            boolean fWarzone = faction.isWarZone() && !Conf.warZoneDenyBuild;
            boolean fSafezone = faction.isSafeZone() && !Conf.safeZoneDenyBuild;

            if (fWilderness || fWarzone || fSafezone) {
                canBlowUp = true;
            }

            if (!canBlowUp) {
                event.setCancelled(true);
            }
        }
    }
}
