package slip.internal;

import java.util.ArrayList;
import java.util.List;

import slip.ConcreteToLMA;

public class SuperCall extends Call // x = super.m(x,..., x)
{
  String m; // Method name

  public SuperCall(int x,  String m, int[] lfp)
  { super(x, lfp); this.m = m; }

  String target(){ return "super." + m ; }
  
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
	  // +3 ? this + ? + ?
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
	  res.add(new LMAInstruction("LDM", 1, 0, Rf, true));
	  res.add(new LMAInstruction("STM", 1, 0, Rs, true));
	  
	  /**
	   * *****************************************
	   *                                         *
	   * Etape b) Aller au début de la méthode   *
	   *                                         *
	   * *****************************************
	   */

	  // Jump vers la méthode
	  LMAInstruction jump = new LMAInstruction("JUMP", Rr, -1, true);
	  try {
		  jump.setUnknownArg2(IT.getSuperMethod(m, currentLevel));
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	  res.add(jump);
	  
	  
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

