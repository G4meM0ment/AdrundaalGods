package de.G4meM0ment.Handler;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import de.G4meM0ment.Framework.Shrine;
import de.G4meM0ment.Framework.God.God;

public class ShrineHandler {

	private GodHandler gH;
	
	private static List<Shrine> shrines = new ArrayList<Shrine>();
	
	public ShrineHandler()
	{
		gH = new GodHandler();
	}
	
	public List<Shrine> getShrines()
	{
		return shrines;
	}
	public void setShrines(List<Shrine> list)
	{
		shrines = list;
	}
	
	/**
	 * Add a new shrine to a worlds list, the boolean to check if it has worked
	 * @param name
	 * @param p1
	 * @param p2
	 * @param spawn
	 * @param binding
	 * @return
	 */
	public boolean addShrine(String name, String godName, Location p1, Location p2)
	{
		if(name == null || p1 == null || p2 == null) return false;
		God god = gH.getGod(godName);
		if(god == null) return false;
		
		//sort the points after there max and minimal coord value
		int maxX, maxY, maxZ, minX, minY, minZ, p1X, p1Y, p1Z, p2X, p2Y, p2Z;
		p1X = p1.getBlockX(); p1Y = p1.getBlockY(); p1Z = p1.getBlockZ();
		p2X = p2.getBlockX(); p2Y = p2.getBlockY(); p2Z = p2.getBlockZ();
		
/*		if(p1X > p2X) {maxX = p1X; minX = p2X;} else {maxX = p2X; minX = p1X;}
		if(p1Y > p2Y) {maxY = p1Y; minY = p2Y;} else {maxY = p2Y; minY = p1Y;}
		if(p1Z > p2Z) {maxZ = p1Z; minZ = p2Z;} else {maxZ = p2Z; minZ = p1Z;} */
		
		if(p1X<p2X) { minX = p1X; maxX = p2X; } else { minX = p2X; maxX = p1X;	}
		if(p1Y<p2Y) { minY = p1Y; maxY = p2Y; } else { minY = p2Y; maxY = p1Y;	}
		if(p1Z<p2Z) { minZ = p1Z; maxZ = p2Z; } else { minZ = p2Z; maxZ = p1Z;	}
		
		//create new locations of these sorted values (it's a cuboid this will work everytime ;))
		Location max = new Location(p1.getWorld(), maxX, maxY, maxZ);
		Location min = new Location(p1.getWorld(), minX, minY, minZ);
		
		//add a new shrine to the worlds list (world defined by one of the locations)
		getShrines().add(new Shrine(name, god, max, min));
		return true;
	}
	
	/**
	 * Checks if location (plus the radius around) is the given shrine
	 * @param s
	 * @param loc
	 * @param radius
	 * @return
	 */
	public boolean isShrine(Shrine s, Location loc, int radius)
	{
		if(s == null) return false;
		if(loc == null) return false;
		
		if(getShrine(loc, radius) != null)
			return true;
		return false;
	}
	/**
	 * Checks if the location (plus the radius around) is a shrine
	 * @param loc
	 * @param radius
	 * @return
	 */
	public boolean isShrine(Location loc, int radius)
	{
		if(loc == null) return false;
		
		for(Shrine s : getShrines())
			if(isShrine(s, loc, radius))
				return true;
		return false;
	}
	
	/**
	 * Gets a shrine by its name and world
	 * @param name
	 * @param world
	 * @return
	 */
	public Shrine getShrine(String name)
	{
		for(Shrine s : getShrines())
			if(s.getName().equals(name))
				return s;
		return null;
	}
	/**
	 * Get the Shrine which exists at this location
	 * @param loc
	 * @return
	 */
	public Shrine getShrine(Location loc, int radius)
	{		
		for(Shrine s : getShrines())
		{
			if(!s.getMax().getWorld().equals(loc.getWorld())) continue;
			
			int locX = loc.getBlockX(),
				locY = loc.getBlockY(),
				locZ = loc.getBlockZ(),
				maxX = s.getMax().getBlockX(),
				maxY = s.getMax().getBlockY(),
				maxZ = s.getMax().getBlockZ(),
				minX = s.getMin().getBlockX(),
				minY = s.getMin().getBlockY(),
				minZ = s.getMin().getBlockZ();
				    										
			if(     locX <= maxX+radius
				&&	locX >= minX-radius
				&&	locY <= maxY+radius
				&&	locY >= minY-radius
				&&	locZ <= maxZ+radius
				&&	locZ >= minZ-radius)
				return s;
		}
		return null;
	}

	public void removeShrine(Shrine s) {
		if(s == null) return;
		
		getShrines().remove(s);
	}
}
