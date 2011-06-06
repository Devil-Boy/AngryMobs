package pinoygamers.AngryMobs;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * Editable configuration class (user input)
 * @author DevilBoy
 */

public class Configuration implements java.io.Serializable {
	private Properties properties;
	private final AngryMobs plugin;
	public boolean upToDate = true;
	
	// List of Config Options
	boolean debug = false;
	int alertRange = 16;
	int monsterSpawnDistance = 24;
	
	public Configuration(Properties p, final AngryMobs plugin, boolean customRecipe) throws NoSuchElementException {
        properties = p;
        this.plugin = plugin;
        
        // Grab values here.
        debug = getBoolean("debug", false);
        alertRange = getInt("alertRange", 16);
        monsterSpawnDistance = getInt("monsterSpawnDistance", 24);
        
    }
	
	public int getInt(String label, int thedefault) {
		String value;
        try {
        	value = getString(label);
        	return Integer.parseInt(value);
        }catch (NoSuchElementException e) {
        	return thedefault;
        }
    }
    
    public double getDouble(String label) throws NoSuchElementException {
        String value = getString(label);
        return Double.parseDouble(value);
    }
    
    public File getFile(String label) throws NoSuchElementException {
        String value = getString(label);
        return new File(value);
    }

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
    
    public LinkedList<String> getList(String label, String thedefault) {
    	String values;
        try {
        	values = getString(label);
        }catch (NoSuchElementException e) {
        	values = thedefault;
        }
        if(plugin.debug) {
        	System.out.println("List from file: " + values);
        }
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
    
    public String getString(String label) throws NoSuchElementException {
        String value = properties.getProperty(label);
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
	 * Creates the configuration file using either the defaults or preset values.
	 */
    public void createConfig() {
    	try{
    		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(plugin.pluginConfigLocation)));
    		out.write("#\r\n");
    		out.write("# AngryMobs Configuration\r\n");
    		out.write("#\r\n");
    		out.write("\r\n");
    		out.write("# Debug Messages\r\n");
    		out.write("#	This can activate debug output messages for if you\r\n");
    		out.write("#	understand the source code and want to find the origin\r\n");
    		out.write("#	of and issue.\r\n");
    		out.write("debug=" + debug + "\r\n");
    		out.write("\r\n");
    		out.write("# Monster Alert Range\r\n");
    		out.write("#	Here you set the distance (in blocks) at which a monster\r\n");
    		out.write("#	entity will target your player.\r\n");
    		out.write("alertRange=" + alertRange + "\r\n");
    		out.write("\r\n");
    		out.write("# Monster Spawn Proximity\r\n");
    		out.write("#	The minimum distance (in blocks) that a monster can spawn\r\n");
    		out.write("#	from a player.\r\n");
    		out.write("monsterSpawnDistance=" + monsterSpawnDistance + "\r\n");
    		out.close();
    	} catch (Exception e) {
    		// Not sure what to do? O.o
    	}
    }
    
}
