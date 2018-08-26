package io.github.galaipa.sr;


import static io.github.galaipa.sr.SimpleRename.getTranslation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.enchantments.Enchantment;


public class Methods {
    public static SimpleRename plugin;
    public Methods(SimpleRename instance) {
        plugin = instance;
    }
    //Rename
    public static void setName(ItemStack item, String name){
      ItemMeta itemStackMeta = item.getItemMeta();
      itemStackMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));           
      item.setItemMeta(itemStackMeta);
    }
    //Set a one line lore
    public static void setLore(ItemStack itemStack, String name){
        String lore = ChatColor.translateAlternateColorCodes('&', name);
        String[] loreArray = lore.split("/n");

        ItemMeta itemStackMeta = itemStack.getItemMeta();
        itemStackMeta.setLore(Arrays.asList(loreArray));
        itemStack.setItemMeta(itemStackMeta);
    }
    //Add a new lore line
    public static void addLore(ItemStack itemStack, String name){
        String[] splittedName = name.split("/n");    
        List<String> lore = itemStack.getItemMeta().getLore();
        if(lore == null){
            setLore(itemStack, name);
        }
        else {
            for(String s : splittedName){
                lore.add(ChatColor.translateAlternateColorCodes('&', s));
            }
            ItemMeta itemStackMeta = itemStack.getItemMeta();
            itemStackMeta.setLore(lore);
            itemStack.setItemMeta(itemStackMeta);
        }
        }
    // Set book author
    public static void setBookAuthor(ItemStack book, String name){
          BookMeta meta = (BookMeta) book.getItemMeta();
          meta.setAuthor(name);
          book.setItemMeta(meta);
    }
    // Set book title
    public static void setBookTitle(ItemStack book, String name){
          BookMeta meta = (BookMeta) book.getItemMeta();
          meta.setTitle(name);
          book.setItemMeta(meta);
    }
    // Unsign book
    public static ItemStack  unSignBook(ItemStack book){
        BookMeta oldMeta = (BookMeta) book.getItemMeta();
        ItemStack unsigned = new ItemStack(Material.WRITABLE_BOOK, 1);
        BookMeta newMeta = (BookMeta) unsigned.getItemMeta();
        newMeta.setPages(oldMeta.getPages());
        unsigned.setItemMeta(newMeta);
        return unsigned;
        
    }
    // Clear Meta
    public static void clearItem(ItemStack item){
        item.setItemMeta(null);
    }
    // Duplicate item
    public static void duplicateItem(ItemStack item, int amount){
        int amountInHand = item.getAmount();
        int result = amountInHand*amount;
        item.setAmount(result);
    }
    //Copy / paste
    public static HashMap<String, ItemMeta > copy = new HashMap<String, ItemMeta>();
        // Copy
    public static void copyMeta(Player player){
        ItemStack item = player.getItemInHand();
        ItemMeta meta = item.getItemMeta();
        copy.put(player.getName(), meta);
        player.sendMessage(ChatColor.GREEN+(getTranslation("11")));
    }
        // Paste
    public static void pasteMeta(Player player){
        ItemStack item1 = player.getItemInHand();
        int slot = player.getInventory().getHeldItemSlot();
        ItemMeta MetaData = copy.get(player.getName());  
        item1.setItemMeta(MetaData);
        player.getInventory().removeItem(item1);
        player.getInventory().setItem(slot, item1);
    }
    //Update
    public static void updatePlugin(){
       // Updater updater = new Updater(this, 75680, getFile(), Updater.UpdateType.NO_VERSION_CHECK, true);
    }
    //Help info
    public static void helpInfo(Player sender, String version1){
        sender.sendMessage(ChatColor.YELLOW + "Simple Rename Commands" + " v" + ChatColor.GREEN + version1);
        sender.sendMessage(ChatColor.BLUE + "/rename or /setname");
        sender.sendMessage(ChatColor.BLUE + "/relore or /setlore");     
        sender.sendMessage(ChatColor.BLUE + "/addlore");
        sender.sendMessage(ChatColor.BLUE + "/sr characters");
        sender.sendMessage(ChatColor.BLUE + "/sr clear");
        sender.sendMessage(ChatColor.BLUE + "/sr book setAuthor/setTitle/unSign");
        sender.sendMessage(ChatColor.BLUE + "/sr getskull");
        sender.sendMessage(ChatColor.BLUE + "/sr copy/paste");
        sender.sendMessage(ChatColor.BLUE + "/sr duplicate");
        sender.sendMessage(ChatColor.BLUE + "/sr getamount");
        sender.sendMessage(ChatColor.BLUE + "/sr mob <name>");
        sender.sendMessage(ChatColor.BLUE + "/sr reload");
        sender.sendMessage(ChatColor.BLUE + "/removelore <lineN>");
        sender.sendMessage(ChatColor.BLUE + "/sr hideflags" );
    }
    //Get Skull
    public static ItemStack getSkull(String owner){
        ItemStack skull = new ItemStack(Material.LEGACY_SKULL_ITEM, 1);
        skull.setDurability((short)3);
        SkullMeta meta = (SkullMeta)skull.getItemMeta();
       // meta.setDisplayName(name);
        meta.setOwner(owner);
        skull.setItemMeta(meta);
        return skull;      
    }
    //Rename mobs
    public static void renameMobs(Player p, String name){
        Listeners.mobs.put(p, name);
        p.sendMessage(ChatColor.GREEN+(getTranslation("17")));
    }
    public static void glowItem(ItemStack item){
      ItemMeta itemStackMeta = item.getItemMeta(); 
      itemStackMeta.addEnchant(Enchantment.LURE, 0, true);
      itemStackMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      item.setItemMeta(itemStackMeta);
    }
    
    public static void unGlowItem(ItemStack item){
      ItemMeta itemStackMeta = item.getItemMeta();
      itemStackMeta.removeEnchant(Enchantment.LURE);
      item.setItemMeta(itemStackMeta);
    }
    public static void hideFlags(ItemStack item){ 
      ItemMeta itemStackMeta = item.getItemMeta(); 
      itemStackMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      itemStackMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
      itemStackMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
      item.setItemMeta(itemStackMeta);
    }
    public static void removeLore(ItemStack item, int n){
      ItemMeta itemStackMeta = item.getItemMeta(); 
      if(itemStackMeta.hasLore() && (n != -1)){
        List<String> list =itemStackMeta.getLore();
        if(list.size() >= (n +1)){
            list.remove(n);
            itemStackMeta.setLore(list);
        } 
      }else{
          itemStackMeta.setLore(null);
      }
      item.setItemMeta(itemStackMeta);
    } 
     
}
