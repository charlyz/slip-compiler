package slip;

import java.util.ArrayList;
import java.util.List;

import fichiers.text;

import slip.internal.JumpMeth;
import slip.internal.LMAInstruction;
import slip.internal.Method;
import slip.internal.Prog;
import slip.internal.Stmt;
import slip.internal.AbstractNode;
import slip.internal.IndirectionTable;

public class ConcreteToLMA extends AbstractNode
{
	// Fichier o� l'on �crit le code lma obtenu
	public String f;
	
	public ConcreteToLMA(String f)
	{
		this.f = f;
	}
	
	/**
	 * Retourne le niveau le plus grand niveau d'une m�thode
	 * 
	 * @param name : nom de la m�thode
	 * @return maxlevel
	 */
	public static int GetMaxLevel(String name)
	{
		int i = 0;
		int maxlevel = 0;
		while(i<prog.meths.length)
		{
			if(prog.meths[i].m.equals(name) && prog.meths[i].level>maxlevel)
				maxlevel = prog.meths[i].level;
			i++;
		}
		return maxlevel;
	}
	
	/**
	 * Ecrit dans le fichier f la traduction en LMA
	 * de Prog
	 */
	public void writeInstructions()
	{
		int i = 0;
		List<LMAInstruction> l = translate();
		text file = new text(f + "_new.lma");
		file.rewrite();
		
		while(i<l.size())
		{
			file.writeln(l.get(i).toString());
			System.out.println(l.get(i).toString());
			i++;
		}
		
		file.writeln();
		file.writeln();
		file.close();
	}
	
	/**
	 * Retourne une liste d'instructions LMA
	 * correspondant � la traduction de Prog.
	 * 
	 * @return res
	 */
	public List<LMAInstruction> translate()
	{
		ConcreteToInternal translator = new ConcreteToInternal(f, true, true, true) ;
		
		try
		{ 
			this.prog = translator.translate();
			
			int i = 0;
			List<LMAInstruction> tmp = new ArrayList<LMAInstruction>();
			ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
			
			
			// On g�n�re les adresses pour la table d'indirection
			IT = new IndirectionTable(prog.meths);
			List<LMAInstruction> indirectionInstr = IT.translate();
			
			// On ajoute les entetes du programme
			res.add(new LMAInstruction("LDA", Rf, 0, true));
			LMAInstruction stackInit = new LMAInstruction("LDA", Rs, -1, false);
			stackInit.generateAddress();
			res.add(stackInit);

			LMAInstruction heapInit = new LMAInstruction("LDA", Rh, -1, false);
			heapInit.generateAddress();
			res.add(heapInit);
			
			// Premi�re instruction de la main
			JumpMainInstr = new LMAInstruction("JUMP", Rr, -1, true);
			res.add(JumpMainInstr);
			res.add(new LMAInstruction("LDM", Rmessage, litErr.getAddr(), true));
			res.add(new LMAInstruction("LDM", Rentier, litSOF.getAddr(), true));
			res.add(new LMAInstruction("HALT", 0, 0, true));
			
			// Traduction de toutes les m�thodes
			while(i<prog.meths.length)
			{
				tmp = prog.meths[i].translate();
				res.addAll(tmp);
				i++;
			}
			
			// On ajoute les litteraux habituels
			res.add(litErr);
			res.add(litSOF);
			
			stackInit.setArg2(currAddr);
			heapInit.setArg2(currAddrLit);
			
			// Maintenant que toutes les m�thodes sont g�n�r�es
			// on connait l'adresse des m�thodes statics
			// On peut d�finir tous les jumps qui y font 
			// r�f�rence.
			i = 0;
			JumpMeth jm;
			while(i<JumpMeth.size())
			{
				jm = JumpMeth.get(i);
				int j = 0;
				Method meth;
				while(j<prog.meths.length)
				{
					meth = prog.meths[j];
					
					if(meth.m.equals(jm.m))
						jm.instr.setUnknownAddr(meth.firstInstr);
					
					j++;
				}
				i++;
			}
			
			// On ajoute les litteraux de la table d'indirection
			IT.CheckTable();
			res.addAll(indirectionInstr);
			
			return res;
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
		
	}

	
	public static void main(String[] arg) throws Exception
	{
	    ConcreteToLMA ctl = new ConcreteToLMA(arg[0]);
	    ctl.writeInstructions();
	}
}
