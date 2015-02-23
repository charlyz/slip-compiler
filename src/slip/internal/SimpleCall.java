package slip.internal;

import java.util.ArrayList;
import java.util.List;

public class SimpleCall extends Call // x = m(x, ..., x);
{
  String m;   // Name of the method 

  public SimpleCall(int x,  String m, int[] lfp)
  { super(x, lfp); this.m = m; }

  String target(){ return m ; }
  
  public List<LMAInstruction> translate()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  
	  /**
	   * ***********************************
	   *                                   *
	   * Etape a) Passage des param�tres   *
	   *                                   *
	   * ***********************************
	   */
	  
	  // On v�rifie qu'il y a assez de place pour copier l'adresse
	  // des param�tres.
	  res.add(new LMAInstruction("LDA", 0, 4*(ap.length), Rs, true));
	  res.add(new LMAInstruction("COMP", 0, Rh, true));
	  
	  LMAInstruction jlInstr = new LMAInstruction("JLE", 0, -1, true);
	  jlInstr.setArg2(jlInstr.getAddr()+14); // Affectation du 2�me arg originale pour �viterle chipotage dans la boucle + bas.
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
	   * Affectation des param�tres effectifs aux param�tres formels
	   */
	  
	  int i = 0;
	  while(i<ap.length)
	  {
		  // On stocke l'adresse de la variable dans R0
		  res.add(new LMAInstruction("LDM", 0, 4*(ap[i]), Rf, true));
		  // On place l'adresse de la a variable � un endroit o� 
		  // la futur m�thode appel�e pourra les r�cup�rer.
		  res.add(new LMAInstruction("STM", 0, 4*(i+1), Rs, true));
		  i++;
	  }
	  
	  /**
	   * *****************************************
	   *                                         *
	   * Etape b) Aller au d�but de la m�thode   *
	   *                                         *
	   * *****************************************
	   */
	  
	  LMAInstruction jumpInstr = new LMAInstruction("JUMP", Rr, -1, true);
	  res.add(jumpInstr);
	  // On stock le jump car on ne connait pas l'adresse de la m�thode
	  JumpMeth.add(new JumpMeth(m, jumpInstr));
	  
	  
	  /**
	   * *****************************************
	   *                                         *
	   * Etape c) Stockage du r�sultat           *
	   *                                         *
	   * *****************************************
	   */
	  
	  res.add(new LMAInstruction("STM", 0, 4*x, Rf, true));
	  
	  return res;
  }
}

