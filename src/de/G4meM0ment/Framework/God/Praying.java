package de.G4meM0ment.Framework.God;

import org.bukkit.configuration.ConfigurationSection;

public class Praying {
	
	private String name, displayname, description;
	private double chance;
	private int praytime;
	private ConfigurationSection benefit;
	
	public Praying(String name, String displayname, String description,  double chance, int praytime, ConfigurationSection benefit)
	{
		this.name = name;
		this.displayname = displayname;
		this.description = description;
		this.chance = chance;
		this.praytime = praytime;
		this.benefit = benefit;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public double getChance() {
		return chance;
	}
	public void setChance(double chance) {
		this.chance = chance;
	}

	public int getPraytime() {
		return praytime;
	}
	public void setPraytime(int praytime) {
		this.praytime = praytime;
	}

	public ConfigurationSection getBenefit() {
		return benefit;
	}
	public void setBenefit(ConfigurationSection benefit) {
		this.benefit = benefit;
	}
}
