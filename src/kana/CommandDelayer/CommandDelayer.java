package kana.CommandDelayer;

import java.util.logging.Logger;

import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandDelayer extends JavaPlugin implements Listener{
	
	private Logger logger = Logger.getLogger("Minecraft");
	public CDcommand commandL;
	public PluginCommand batchcommand;
	
	public void onEnable(){
		
		this.commandL = new CDcommand(this);
        this.batchcommand = getCommand("CD");
        batchcommand.setExecutor(commandL);
        
    	getServer().getPluginManager().registerEvents(this, this);
    	
		this.loadConfig();
		logger.info("[CommandDelayer] Plugin charger parfaitement!");
    }
	
	public void onDisable(){
            logger.info("[CommandDelayer] Plugin stopper...");
    }
    
	public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}