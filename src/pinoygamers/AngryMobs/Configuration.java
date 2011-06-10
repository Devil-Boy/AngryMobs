package pinoygamers.AngryMobs;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

import org.bukkit.World;
import org.bukkit.World.Environment;

/**
 * Editable configuration class (user input)
 * @author DevilBoy
 */

public class Configuration implements java.io.Serializable {
	private File file;
	Properties p;
	/**
	 * Whether or not the configuration file has all the new options.
	 */
	public boolean upToDate = true;
	
	// List of Config Options
	/**
	 * Whether or not to output debug messages.
	 */
	boolean debug = false;
	/**
	 * The range at which a mob will begin to attempt to maul you
	 */
	int alertRange = 16;
	/**
	 * The frequency at which a monster will think about murdering you
	 */
	int alertFrequency = 16;
	/**
	 * The minimum distance a monster can spawn from a player.
	 */
	int monsterSpawnDistance = 24;
	/**
	 * The type of world.
	 */
	Environment worldtype = Environment.NORMAL;
	/**
	 * A list of monsters that can spawn in a world.
	 */
	LinkedList<String> spawnableMonsters; 
	/**
	 * The frequency to spawn monsters in milliseconds.
	 */
	int monsterSpawnFrequency = 10000;
	/**
	 * The maximum light level a monster can spawn in.
	 */
	int spawnMaxLight = 7;
	/**
	 * Whether or not normal monster spawns will occur.
	 */
	Boolean disableNormalMonsters = false;
	
	/**
	 * Fills in the configuration based on the file.
	 * @param file The file to open.
	 * @throws NoSuchElementException
	 */
	public Configuration(File file, Environment worldtype) throws NoSuchElementException {
		this.file = file;
		this.worldtype = worldtype;
		if (worldtype == World.Environment.NORMAL) {
			spawnableMonsters = new LinkedList<String>(Arrays.asList("Creeper,Skeleton,Spider,Zombie".split(",")));
		} else if (worldtype == World.Environment.NETHER) {
			spawnableMonsters = new LinkedList<String>(Arrays.asList("PigZombie,Ghast".split(",")));
		}
		
		
		//if it exists, let's read it, if it doesn't, let's create it.
		if (file.exists()) {
			try {
				p = new Properties();
				p.load(new FileInputStream(file));
		        
		        // Grab values here.
		        debug = getBoolean("debug", false);
		        alertRange = getInt("alertRange", 16);
		        alertFrequency = (int)(getDouble("monsterSpawnFrequency", 5) *1000);
		        monsterSpawnDistance = getInt("monsterSpawnDistance", 24);
		        monsterSpawnFrequency = (int)(getDouble("monsterSpawnFrequency", 10) *1000);
				spawnableMonsters = ratioizeList(getList("spawnableMonsters", linkedListToString(spawnableMonsters)));
				spawnMaxLight = getInt("spawnMaxLight", 7);
				disableNormalMonsters = getBoolean("disableNormalMonsters", false);
			}catch (Exception ex) {
		    	
		    }
		}else {
			createConfig();
		}
    }
	
	/**
	 * Returns the int value of a variable.
	 * @param label The variable name
	 * @param thedefault The default value to return if the variable isn't an int.
	 * @return Whatever int was set.
	 */
	public int getInt(String label, int thedefault) {
        try {
        	String value = getString(label);
        	return Integer.parseInt(value);
        }catch (NoSuchElementException e) {
        	return thedefault;
        }
    }
    
	/**
	 * Returns the double value of a variable.
	 * @param label The variable name
	 * @param thedefault The default value to return if the variable isn't an int.
	 * @return Whatever was set.
	 * @throws NoSuchElementException If the labeled value doesn't exist.
	 */
    public double getDouble(String label, double thedefault) throws NoSuchElementException {
        try {
            String value = getString(label);
            return Double.parseDouble(value);
        }catch (NoSuchElementException e) {
        	return thedefault;
        }
    }
    
    /**
	 * Returns the a file that was in settings.
	 * @param label The name of the key.
	 * @param thedefault The default value to return if the key doesn't exist.
	 * @return File which was configured.
	 */
    public File getFile(String label) throws NoSuchElementException {
        String value = getString(label);
        return new File(value);
    }

    /**
     * Returns the boolean value of the variable
     * @param label The variable name
     * @param thedefault Default value to return if an error occurs.
     * @return Whatever the person set it to.
     */
    public boolean getBoolean(String label, boolean thedefault) {
    	String values;
        try {
        	values = getString(label);
        	return Boolean.valueOf(values).booleanValue();
        }catch (NoSuchElementException e) {
        	return thedefault;
        }
    }
    
    public Color getColor(String label) {
        String value = getString(label);
        Color color = Color.decode(value);
        return color;
    }
    
    public HashSet<String> getSet(String label, String thedefault) {
        String values;
        try {
        	values = getString(label);
        }catch (NoSuchElementException e) {
        	values = thedefault;
        }
        String[] tokens = values.split(",");
        HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < tokens.length; i++) {
            set.add(tokens[i].trim().toLowerCase());
        }
        return set;
    }
    
    /**
	 * Obtains a list from the properties file.
	 * @param label A string containing the name of the key that contains the value you want from the properties file.
	 * @param thedefault The default value to use, just incase the label does not exist.
	 * @return A LinkedList of the property values that were separated by commas.
	 */
    public LinkedList<String> getList(String label, String thedefault) {
    	String values;
        try {
        	values = getString(label);
        }catch (NoSuchElementException e) {
        	values = thedefault;
        }/*
        if(plugin.debug) {
        	System.out.println("List from file: " + values);
        }*/
        if(!values.equals("")) {
            String[] tokens = values.split(",");
            LinkedList<String> set = new LinkedList<String>();
            for (int i = 0; i < tokens.length; i++) {
                set.add(tokens[i].trim().toLowerCase());
            }
            return set;
        }else {
        	return new LinkedList<String>();
        }
    }
    
    /**
	 * Obtains a string value from the properties file.
	 * @param label A string containing the name of the key that contains the value you want from the properties file.
	 * @return A string with value you wanted.
	 * @throws NoSuchElementException If the config file does not contain the label.
	 */
    public String getString(String label) throws NoSuchElementException {
        String value = p.getProperty(label);
        if (value == null) {
        	upToDate = false;
            throw new NoSuchElementException("Config did not contain: " + label);
        }
        return value;
    }
    
    /**
	 * Converts a LinkedList into a String separated by commas.
	 * @param list The LinkedList to convert.
	 * @return A string with the values of the LinkedList separate by commas.
	 */
    public String linkedListToString(LinkedList<String> list) {
    	if(list.size() > 0) {
    		String compounded = "";
    		boolean first = true;
        	for(String value : list) {
        		if(first) {
        			compounded = value;
        			first = false;
        		}else {
        			compounded = compounded + "," + value;
        		}
        	}
        	return compounded;
    	}
    	return "";
    }
    
    /**
	 * Parses ratio values in lists.
	 * @param list The LinkedList to parse.
	 * @return A parsed LinkedList.
	 */
    public LinkedList<String> ratioizeList(LinkedList<String> list) {
    	LinkedList<String> outputList = new LinkedList<String>();
    	for (String currentValue : list) {
    		if (currentValue.contains(":")) {
    			String[] theValues = currentValue.split(":");
    			if (theValues.length > 1) {
    				try {
    					int theMultiple = Integer.parseInt(theValues[1]);
        				for (int u = 0; u < theMultiple; u++) {
        					outputList.add(theValues[0]);
        				}
        			} catch (NumberFormatException nfe) {
        				System.out.println(theValues[1] + " is not a number...");
        				outputList.add(theValues[0]);
        			}
    			}else {
    				outputList.add(theValues[0]);
    			}
    		}else {
    			outputList.add(currentValue);
    		}
    		if (debug) {
    			System.out.println("Current spawnableMonsters: " + linkedListToString(outputList));
    		}
    	}
    	return outputList;
    }
    
    /**
	 * Creates the configuration file using either the defaults or preset values.
	 */
    public void createConfig() {
    	try{
    		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
    		out.write("# AngryMobs Configuration\r\n");
    		out.write("#\r\n");
    		out.write("# Each file only affects the world named in the filename.\r\n");
    		out.write("\r\n");
    		out.write("# Debug Messages\r\n");
    		out.write("#	This can activate debug output messages for if you\r\n");
    		out.write("#	understand the source code and want to find the origin\r\n");
    		out.write("#	of an issue.\r\n");
    		out.write("debug=" + debug + "\r\n");
    		out.write("\r\n");
    		out.write("# Monster Alert Range\r\n");
    		out.write("#	Here you set the distance (in blocks) at which a monster\r\n");
    		out.write("#	entity will target your player.\r\n");
    		out.write("alertRange=" + alertRange + "\r\n");
    		out.write("\r\n");
    		out.write("# Monster Alert Frequency\r\n");
    		out.write("#	Here you set the frequency (in seconds) at which a\r\n");
    		out.write("#	monster within the alert range will decide that it\r\n");
    		out.write("#	wants to kill you.\r\n");
    		out.write("alertFrequency=" + alertFrequency + "\r\n");
    		out.write("\r\n");
    		out.write("# Monster Spawn Proximity\r\n");
    		out.write("#	The minimum distance (in blocks) that a monster can spawn\r\n");
    		out.write("#	from a player.\r\n");
    		out.write("monsterSpawnDistance=" + monsterSpawnDistance + "\r\n");
    		out.write("\r\n");
    		out.write("# Monster Spawn Timing\r\n");
    		out.write("#	How many seconds to wait between each monster spawn.\r\n");
    		out.write("monsterSpawnFrequency=" + ((double)monsterSpawnFrequency)/(double)1000 + "\r\n");
    		out.write("\r\n");
    		out.write("# Spawnable Monsters\r\n");
    		out.write("#	Here you put a list of monsters that can be spawned in\r\n");
    		out.write("#	the world (separated by commas), and the ratios at which\r\n");
    		out.write("#	you wish them to be spawned using. For example:\r\n");
    		out.write("#	'Giant:5,Ghast:4' will spawn giants and ghasts in\r\n");
    		out.write("#	a 5:4 ratio.\r\n");
    		out.write("spawnableMonsters=" + linkedListToString(spawnableMonsters) + "\r\n");
    		out.write("\r\n");
    		out.write("# Spawnable Light Level\r\n");
    		out.write("#	This option lets you choose the maximum light level in\r\n");
    		out.write("#	which a monster will be able to spawn in. (0 - 15)\r\n");
    		out.write("spawnMaxLight=" + spawnMaxLight + "\r\n");
    		out.write("\r\n");
    		out.write("# Disable Normal Monster Spawn\r\n");
    		out.write("#	Here you can turn off the normal monsters spawning of a\r\n");
    		out.write("#	world. Thus making all monsters spawned only from this\r\n");
    		out.write("#	plugin.\r\n");
    		out.write("disableNormalMonsters=" + disableNormalMonsters + "\r\n");
    		out.close();
    	} catch (Exception e) {
    		System.out.println("Couldn't generate the config! Are you sure you have permissions to write in this folder?");
    	}
    }
    
}
