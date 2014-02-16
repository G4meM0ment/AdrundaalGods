package de.G4meM0ment.Framework;

import org.bukkit.Location;

import de.G4meM0ment.Framework.God.God;

public class Shrine {
	
	private Location min, max;
	private God god;
	private String name;
	
	public Shrine(String name, God god, Location max, Location min)
	{
		this.name = name;
		this.god = god;
		this.max = max;
		this.min = min;
	}

	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}

	public Location getMin() 
	{
		return min;
	}
	public void setMin(Location min) 
	{
		this.min = min;
	}

	public Location getMax() 
	{
		return max;
	}
	public void setMax(Location max) 
	{
		this.max = max;
	}

	public God getGod() 
	{
		return god;
	}
	public void setGod(God god) 
	{
		this.god = god;
	}

}
