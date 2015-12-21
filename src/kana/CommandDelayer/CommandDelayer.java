package kana.CommandDelayer;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandDelayer extends JavaPlugin implements Listener{
	
	private Logger logger = Logger.getLogger("Minecraft");
	private File file;
	public CDcommand commandL;
	public PluginCommand batchcommand;
	
	public void onEnable(){
		
		this.commandL = new CDcommand(this);
        this.batchcommand = getCommand("CD");
        batchcommand.setExecutor(commandL);
    	
		Vault.load(this);
    	Vault.setupChat();
    	Vault.setupPermissions();
    	if (!Vault.setupEconomy() ) {
            logger.info(String.format("[%s] - CommandDelayer necessite Vault pour fonctionner!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
    	getServer().getPluginManager().registerEvents(this, this);
    	
		this.loadConfig();
		logger.info("[CommandDelayer] Plugin charger parfaitement!");
    }
	
	public void onDisable(){
            logger.info("[CommandDelayer] Plugin stopper...");
    }
    
	public void loadConfig(){
		file = new File(getDataFolder(), "config.yml");
	    if(!file.exists()){
	        getConfig().options().copyDefaults(true);
	        saveConfig();
	    }
    }
}