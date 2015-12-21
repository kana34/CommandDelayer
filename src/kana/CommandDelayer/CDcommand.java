package kana.CommandDelayer;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CDcommand implements CommandExecutor{
	
	private Player player;
	private String commandString;
	private List<String> commande;
	
	CommandDelayer plugin;
	
	public CDcommand(CommandDelayer plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
    	this.player = null;
    	if(sender instanceof Player){
    		player = (Player) sender;
    	}
        if(commandLabel.equalsIgnoreCase("CD")){
        	if(args.length == 0){
        		sender.sendMessage(ChatColor.GOLD + "[CommandDelayer] " + ChatColor.GREEN + "Tapez /CD help !");
        		return true;
        	}
        	if(args.length == 1){
        		if(args[0].equalsIgnoreCase("help")){
        			sender.sendMessage(ChatColor.GOLD + "---------------------- CommandDelayer help ----------------------");
        			sender.sendMessage(ChatColor.YELLOW + "/cd help " + ChatColor.GREEN + "Affiche l'aide du plugin");
        			sender.sendMessage(ChatColor.YELLOW + "/cd recup " + ChatColor.GREEN + "Recupère les commandes en stocks");
        			sender.sendMessage(ChatColor.YELLOW + "/cd liste " + ChatColor.GREEN + "liste les commandes en stocks du joueur");
        			sender.sendMessage(ChatColor.YELLOW + "/cd command {joueur} {commande sans le '/' } " + ChatColor.GREEN + "Stocke la commande");
        			return true;
        		}
        		if(args[0].equalsIgnoreCase("recup")){
        			List<String> commande = this.plugin.getConfig().getStringList(player.getName().toString());
        			
        			for (int i = 0; i < commande.size(); i++) {
        				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commande.get(i));
        			}
        			
        			this.plugin.getConfig().set(player.getName(), null);
        			this.plugin.saveConfig();
        			
        			sender.sendMessage(ChatColor.GOLD + "[CommandDelayer] " + ChatColor.GREEN + "Vos commandes ont étais transférées !");
        			return true;
        		}
        		if(args[0].equalsIgnoreCase("liste")){
        			List<String> commande = this.plugin.getConfig().getStringList(player.getName().toString());
        			
        			for (int i = 0; i < commande.size(); i++) {
        				sender.sendMessage(ChatColor.GOLD + "[CommandDelayer] " + ChatColor.GREEN + commande.get(i));
        			}
        			return true;
        		}
        	}
        	if(player == null || Vault.permission.playerHas(player,"CommandDelayer.admin")){
        		if(args[0].equalsIgnoreCase("command")){
	        		this.commandString = "";
	        	      for (int i = 1; i < args.length; i++) {
	        	        commandString = commandString + " " + args[i];
	        	      }
	        	      this.commande = this.plugin.getConfig().getStringList(args[0]);
	        	      
	        	      commande.add(commandString.substring(1));
	        	      this.plugin.getConfig().set(args[0], commande);
	        	      this.plugin.saveConfig();
	        	      return true;
        		}
        		else{
        			sender.sendMessage(ChatColor.GOLD + "[CommandDelayer] " + ChatColor.RED + "Commande inconnu !!!");
        			return false;
        		}       		
        	}       	
        }
        return true;
	}
}