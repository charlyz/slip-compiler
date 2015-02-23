package slip.internal ;

import java.util.ArrayList;
import java.util.List;

import slip.ConcreteToLMA;

public class VariableCall extends Call
{
  int target; // target of the call (a variable)
  String m;   // Method name

  public VariableCall(int x, int target, String m, int[] lfp)
  { super(x, lfp); this.target = target; this.m = m; }

  String target(){ return varName(target) + "." + m ; }
  
  public List<LMAInstruction> translate()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  
	  /**
	   * ***********************************
	   *                                   *
	   * Etape a) Passage des paramètres   *
	   *                                   *
	   * ***********************************
	   */
	  
	  // On vérifie qu'il y a assez de place pour copier l'adresse
	  // des paramètres.
	  res.add(new LMAInstruction("LDA", 0, 4*(ap.length+1), Rs, true));
	  res.add(new LMAInstruction("COMP", 0, Rh, true));
	  
	  LMAInstruction jlInstr = new LMAInstruction("JLE", 0, -1, true);
	  jlInstr.setArg2(jlInstr.getAddr()+14); // Affectation du 2ème arg originale pour éviterle chipotage dans la boucle + bas.
	  res.add(jlInstr);
	  
	  // Erreur
	  LMAInstruction instr1 = new LMAInstruction("LDM", Rmessage, -1, false);
	  instr1.generateAddress();
	  instr1.setUnknownAddr(litErr);
	  LMAInstruction instr2 = new LMAInstruction("LDM", Rentier, -1, false);
	  instr2.generateAddress();
	  instr2.setUnknownAddr(litSOF);
	  res.add(instr1);
	  res.add(instr2);
	  res.add(new LMAInstruction("HALT", 0, 0, true));
	  
	  /*
	   * Affectation des paramètres effectifs aux paramètres formels
	   * + ajout de la variable this
	   */
	  
	  int i = 0;
	  while(i<ap.length)
	  {
		  // On stocke l'adresse de la variable dans R0
		  res.add(new LMAInstruction("LDM", 0, 4*(ap[i]), Rf, true));
		  // On place l'adresse de la a variable à un endroit où 
		  // la futur méthode appelée pourra les récupérer.
		  res.add(new LMAInstruction("STM", 0, 4*(i+1), Rs, true));
		  i++;
	  }
	  
	  // On rajoute le this sur la stack
	  // ? Pourquoi dans les exemples, pour le this on utilise R1 ?
	  res.add(new LMAInstruction("LDM", 1, 4*target, Rf, true));
	  res.add(new LMAInstruction("STM", 1, 0, Rs, true));
	  
	  /**
	   * *****************************************
	   *                                         *
	   * Etape b) Aller au début de la méthode   *
	   *                                         *
	   * *****************************************
	   */
	  
	  // On stocke le level max de la méthode
	  res.add(new LMAInstruction("LDM", 1, 0, 1, true));
	  res.add(new LMAInstruction("LDA", 2, ConcreteToLMA.GetMaxLevel(m), true));
	  res.add(new LMAInstruction("COMP", 1, 2, true));
	  
	  LMAInstruction jle = new LMAInstruction("JLE", 0, -1, true);
	  jle.setArg2(jle.getAddr()+8);
	  res.add(jle);
	  
	  res.add(new LMAInstruction("LDA", 1, 0, 2, true));
	  // Récupération de l'adresse de la méthode
	  res.add(new LMAInstruction("MULA", 1, 4, true));
	  LMAInstruction instr3 = new LMAInstruction("ADDA", 1, -1, true);
	  try {
		  instr3.setUnknownAddr(IT.getTableAddress(m));
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	  res.add(instr3);

	  res.add(new LMAInstruction("LDM", 1, 0, 1, true));
	  
	  // Jump (enfin!!) vers la méthode
	  res.add(new LMAInstruction("JUMP", Rr, 0, 1, true));
	  
	  
	  /**
	   * *****************************************
	   *                                         *
	   * Etape c) Stockage du résultat           *
	   *                                         *
	   * *****************************************
	   */
	  
	  res.add(new LMAInstruction("STM", 0, 4*x, Rf, true));
	  
	  return res;
  }
}

