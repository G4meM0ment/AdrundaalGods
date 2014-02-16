package de.G4meM0ment.Framework.God;

import java.util.List;

public class God {

	private String name, description, displayname;
	private List<Praying> prayings;
	
	public God(String name, List<Praying> prayings, String displayname, String description)
	{
		this.name = name;
		this.prayings = prayings;
		this.displayname = displayname;
		this.description = description;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Praying> getPrayings() {
		return prayings;
	}
	public void setPrayings(List<Praying> prayings) {
		this.prayings = prayings;
	}
	public Praying getPraying(String name) {
		if(prayings.contains(name)) return null;
		for(Praying p : prayings)
			if(p.getName().equalsIgnoreCase(name))
				return p;
		return null;
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
	
	public int getMenuSize()
	{
		if(prayings.size() <= 9)
			return 9;
		if(prayings.size() <= 18)
			return 18;
		if(prayings.size() <= 27)
			return 27;
		if(prayings.size() <= 36)
			return 36;
		if(prayings.size() <= 45)
			return 45;
		if(prayings.size() <= 54)
			return 54;
		return 0;
	}
}
