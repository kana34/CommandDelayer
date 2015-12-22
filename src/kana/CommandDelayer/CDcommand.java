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
	private List<String> commandes;
	
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
        		//--------------
        		//---- HELP ----
        		//--------------
        		if(args[0].equalsIgnoreCase("help")){
        			sender.sendMessage(ChatColor.GOLD + "---------------------- CommandDelayer help ----------------------");
        			sender.sendMessage(ChatColor.YELLOW + "/cd help " + ChatColor.GREEN + "Affiche l'aide du plugin");
        			sender.sendMessage(ChatColor.YELLOW + "/cd recup " + ChatColor.GREEN + "Recupère les commandes en stocks");
        			sender.sendMessage(ChatColor.YELLOW + "/cd liste " + ChatColor.GREEN + "liste les commandes en stocks du joueur");
        			sender.sendMessage(ChatColor.YELLOW + "/cd command {joueur} {commande sans le '/' } " + ChatColor.GREEN + "Stocke la commande");
        			return true;
        		}
        		//---------------
        		//---- RECUP ----
        		//---------------
        		else if(args[0].equalsIgnoreCase("recup")){
        			
        			// On récupère la liste des commandes
        			//-----------------------------------
        			this.commande = this.plugin.getConfig().getStringList(player.getName().toString());
        			
        			// On execute les commandes
        			//-------------------------
        			for (int i = 0; i < commande.size(); i++) {
        				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commande.get(i));
        			}
        			
        			// On supprime le commandes du joueur
        			//-----------------------------------
        			this.plugin.getConfig().set(player.getName(), null);
        			this.plugin.saveConfig();
        			
        			// On envoie un message de confirmation
        			//-------------------------------------
        			sender.sendMessage(ChatColor.GOLD + "[CommandDelayer] " + ChatColor.GREEN + "Vos commandes ont étais transférées !");
        			return true;
        		}
        		//---------------
        		//---- LISTE ----
        		//---------------
        		else if(args[0].equalsIgnoreCase("liste")){
        			
        			// On récupère la liste des commandes
        			//-----------------------------------
        			this.commande = this.plugin.getConfig().getStringList(player.getName().toString());
        			
        			// On envoie un message avec la liste des commandes
        			//-------------------------------------------------
        			for (int i = 0; i < commande.size(); i++) {
        				sender.sendMessage(ChatColor.GOLD + "[CommandDelayer] " + ChatColor.GREEN + commande.get(i));
        			}
        			return true;
        		}
        		//--------------
        		//---- NULL ----
        		//--------------
        		else{
        			sender.sendMessage(ChatColor.GOLD + "[CommandDelayer] " + ChatColor.RED + "Commande inconnu !!!");
        			return false;
        		}
        	}
        	else if(player == null || player.hasPermission("commanddelayer.admin")){
        		//-------------------------
        		//---- ENVOIE COMMANDE ----
        		//-------------------------
        		if(args[0].equalsIgnoreCase("command")){        			
        			commandString = args[2];        			
        			for (int i = 3; i < args.length; i++) {
        				commandString = commandString + " " + args[i];
        			}
	    	      
        			this.commandes = this.plugin.getConfig().getStringList(args[1]);
	    	      
		    	    commandes.add(commandString);
		    	    this.plugin.getConfig().set(args[1], commandes);
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