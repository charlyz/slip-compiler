package slip.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class IndirectionTable 
{
	// Clé: Nom de méthode, Valeur: Indice dans le tableau des adresses
	private HashMap<String, Integer> nameToIdx;
	
	// Clé: indice, Valeur: nom de la méthode 
	private String[] indexToName;
	
	// X = indice correspondant au nom de la variable, Y = Niveau de la méthode, Valeur: Adresse de la meth
	private LMAInstruction[][] table;
	
	public IndirectionTable(Method[] meths)
	{
		Method m;
		nameToIdx = new HashMap();
		Integer j = 0;
		int i = 0;
		
		// Recherche de toutes les méthodes dynamiques
		// + stockage du + haut level
		HashMap<String, Integer> dynamicMeth = new HashMap<String, Integer>();
		while (i<meths.length)
		{
			m = meths[i];
			if (!m.isStatic)
			{
				j = dynamicMeth.get(m.m);
				
				if(j == null || j < m.level)
					dynamicMeth.put(m.m, m.level);
			}
			i++;
		}
		
		// Création de la table pour chaque méthode dynamique
		String name;
		int maxLevel;
		int currentIdx = 0;
		table = new LMAInstruction[dynamicMeth.size()][];
		indexToName = new String[dynamicMeth.size()];
		
		Iterator<String> it = dynamicMeth.keySet().iterator();
		while(it.hasNext())
		{
			// nom de la méthode
			name = it.next();
			// Ajout du couple (Nom de méthode, Indice dans table)
			nameToIdx.put(name, currentIdx);
			// Ajout du couple (Indice dans la table, Nom de méthode)
			indexToName[currentIdx] = name;
			// Recuperation du niveau max de la méthode
			maxLevel = dynamicMeth.get(name);
			// Création de la table d'adresse pour cette méthode
			table[currentIdx] = new LMAInstruction[maxLevel+1];

			currentIdx++;
		}
		
		// On crée un littéral non défini pour chaque champs
		LMAInstruction instr;

		for(i=0; i < table.length; i++)
		{
			for(j=0; j < table[i].length; j++)
			{
				table[i][table[i].length-j-1] = new LMAInstruction("empty", true);
				table[i][table[i].length-j-1].setTypeLit("I");
			}
		}
	}
	
	public void setAdress(String m, int level, LMAInstruction firstInstr)
	{
		int id = nameToIdx.get(m);
		table[id][level].setLit(String.valueOf(firstInstr.getAddr()));
	}
	
	public LMAInstruction getTableAddress(String name) throws Exception
	{
		Integer i = nameToIdx.get(name);
		
		if(i == null)
			throw new Exception("Méthode dynamique '"+name+"' indéfinie");
		
		return table[i][0];
	}
	
	public LMAInstruction getSuperMethod(String name, int level) throws Exception
	{
		Integer i = nameToIdx.get(name);
		
		if(level == 0)
			throw new Exception("Pas de méthode super pour une méthode de niveau 0");
		if(i == null)
			throw new Exception("Méthode '"+name+"' indéfinie");

		return table[i][level-1];
	}
	
	public List<LMAInstruction> translate()
	{
		LMAInstruction currentVal;
		ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
		
		for(int i=0; i < table.length; i++)
		{
			currentVal = table[i][0];
			
			res.add(new LMAInstruction("indirection table for "+indexToName[i]));
			res.add(currentVal);
			for(int j=1; j < table[i].length; j++)
			{
				res.add(table[i][j]);
			}
		}
		return res;
	}
	
	public void CheckTable()
	{
		// On remplit les "trous"
		LMAInstruction currentVal;
		
		for(int i=0; i < table.length; i++)
		{
			currentVal = table[i][0];
			for(int j=0; j < table[i].length; j++)
			{
				// On remplit si c'est non définit
				if(table[i][j].getLit().equals("empty"))
				{	
					if(currentVal.getLit().equals("empty"))
						// Arrive dans le cas où meth/0 n'est pas définie mais 
						// bien meth/i où i>0
						table[i][j].setLit(String.valueOf(-1));
					else
						table[i][j].setLit(currentVal.getLit());
				}
			}
		}
	}
}
