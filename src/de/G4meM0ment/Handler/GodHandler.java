package de.G4meM0ment.Handler;

import java.util.ArrayList;
import java.util.List;

import de.G4meM0ment.Framework.God.God;

public class GodHandler {
	
	private static List<God> gods = new ArrayList<God>();
	
	public List<God> getGodList()
	{
		return gods;
	}
	public void setGodList(List<God> list)
	{
		gods = list;
	}
	
	public God getGod(String name)
	{
		God god = null;
		for(God g : gods)
			if(g.getName().equalsIgnoreCase(name))
				god = g;
		return god;
	}
}
