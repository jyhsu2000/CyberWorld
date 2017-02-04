package io.github.kuohsuanlo.cyberworld;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;
/* 0 = ground
 * 1 = road-NS
 * 2 = road-EW
 * <0= building
 * 
 * 
 * 
 */
public class CyberWorld extends JavaPlugin{
    private Logger log = Logger.getLogger("Minecraft");
    private CyberWorldChunkGenerator c;
    private CityStreetGenerator cityg;
    private final int BIOME_TYPES = 3;
    private final int BIOME_NUMBERS = (int) Math.round( Math.pow(2, BIOME_TYPES));
    public CyberWorld(){

        createBiomeFolder();
        readCityStreetGenerator();
        
        c = new CyberWorldChunkGenerator(this,3,cityg);
    } 
    public void onEnable(){
    	
        reportMessage(" enabled");
        
    }
    public void onDisable(){
    	saveCityStreetGenerator();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
        return c;
    }
    
    
    public String getPluginName() {
		return getDescription().getName();
	}
	private String getQuotedPluginName() {
		return "[" + getPluginName() + "]";
	}
	public void reportMessage(String message) {
		if (!message.startsWith("["))
			message = " " + message;
		log.info(getQuotedPluginName() + message);
	}
	public void reportMessage(String message1, String message2) {
		reportMessage(message1);
		log.info(" \\__" + message2);
	}
    private void createBiomeFolder(){
    	if(createFolder("./plugins/CyberWorld")){
        	
        }
        if(createFolder("./plugins/CyberWorld/citymap_pregen")){
        	
        }
        if(createFolder("./plugins/CyberWorld/schematics")){
        	
        }
        
        if(createFolder("./plugins/CyberWorld/schematics/"+"/citysurface")){
        	
        }
        if(createFolder("./plugins/CyberWorld/schematics/"+"/highway")){
        	
        }
        if(createFolder("./plugins/CyberWorld/schematics/"+"/underground")){
        	
        }
        if(createFolder("./plugins/CyberWorld/schematics/"+"/import")){
        	
        }
        for(int i=0;i< BIOME_NUMBERS;i++){
            if(createFolder("./plugins/CyberWorld/schematics/"+i+"/citysurface")){
            	
            }
            if(createFolder("./plugins/CyberWorld/schematics/"+i+"/highway")){
            	
            }
            if(createFolder("./plugins/CyberWorld/schematics/"+i+"/underground")){
            	
            }
            if(createFolder("./plugins/CyberWorld/schematics/"+i+"/import")){
            	
            }
        }
        
		
        
    }
    private static boolean createFolder(String path){
    	 
  	   	File file = new File(path);
  	   	if(!file.exists()){
  	   		new File(path).mkdirs();
  	   		return true;
  	   	}
  	   	return false;

    }    
    private void saveCityStreetGenerator(){
    	try {
        	FileOutputStream fos;
			fos = new FileOutputStream("./plugins/CyberWorld/citymap_pregen/"+"test.cityobj");
        	ObjectOutputStream oos = new ObjectOutputStream(fos);
        	oos.writeObject(c.getOg().getCg());
        	oos.close();
		} catch (Exception e  ) {
			// TODO Auto-generated catch block
			System.out.print("[CyberWorld] : City Map saving error. It will regenerate next time.");
			e.printStackTrace();
		} 
    }
    private void readCityStreetGenerator(){
    	//Creating
		try {
	  	   	String path = "./plugins/CyberWorld/citymap_pregen/"+"test.cityobj";
	  	   	File file = new File(path);
	  	   	
	  	   	
	  	   	if(file.exists()){
	  	   		System.out.print("[CyberWorld] : Loading City Map... Please wait.");
	  	   		System.out.println("[CyberWorld] : successfully load from "+"./plugins/CyberWorld/citymap_pregen/"+"test.cityobj");	
	  	   		
				FileInputStream fis;
				fis = new FileInputStream("./plugins/CyberWorld/citymap_pregen/"+"test.cityobj");
		    	ObjectInputStream ois = new ObjectInputStream(fis);
		    	cityg = (CityStreetGenerator) ois.readObject();
		    	ois.close();
	  	   		System.out.print("[CyberWorld] : City Map loaded.");
	  	   	}
	  	   	else{
	  	   		cityg =null;
	  	   	}
		} catch (Exception e  ) {
  	   		cityg =null;
  	   		System.out.print("[CyberWorld] : City Map loading error, regenerating...");
			e.printStackTrace();
		}
    			
    }


}