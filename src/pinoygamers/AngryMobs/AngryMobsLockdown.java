package pinoygamers.AngryMobs;

/**
 * This is a thread that runs in each world and runs through 
 * all the players alerting all nearby monsters that don't
 * already have a target.
 * @author joshua
 *
 */
public class AngryMobsLockdown implements Runnable {

	private AngryMobs plugin;
	private boolean running = true;
	int waittime = 10000;
	Configuration config;
	String world;
	
	public AngryMobsLockdown(AngryMobs plugin, Configuration config, String world) {
		this.plugin = plugin;
		this.config = config;
		this.world = world;
	}
	
	public synchronized void stopIt() {
		running = false;
	}
	
	public synchronized void setWaitTime(int wait) {
		waittime = wait;
	}
	
	@Override
	public void run() {
		while (running) {
			try {
				wait(waittime);
			} catch (InterruptedException e) {
				
			}
		}
	}

}
