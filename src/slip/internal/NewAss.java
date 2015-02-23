package slip.internal;

import java.util.ArrayList;
import java.util.List;

public class NewAss extends Ass// x = new/i ;
{
  int x ; // where to put the reference to the object
  int i ; // level of the object to be created

  public NewAss(int x, int i){ this.x = x; this.i = i ;}

  public String toString()
  { 
    return varName(x) + " := new/" + i  ; 
  }
  
  public List<LMAInstruction> translate()
  {
	  ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
	  res.add(new LMAInstruction("SUBA", Rh, 4*(i+1), true));
	  // On compare Rs et Rh
	  res.add(new LMAInstruction("COMP", Rs, Rh, true));
	  // Si Rs est inférieur, c'est bon, on saute 1 mots et demi plus loin
	  LMAInstruction instr1 = new LMAInstruction("JLE", Rs, -1, false);
	  instr1.generateAddress();
	  res.add(instr1);

	  LMAInstruction instr4 = new LMAInstruction("LDM", Rmessage, -1, false);
	  instr4.setUnknownAddr(litErr);	
	  LMAInstruction instr5 = new LMAInstruction("LDM", Rentier, -1, false);
	  instr5.setUnknownAddr(litSOF);
	  instr4.generateAddress();
	  res.add(instr4);
	  instr5.generateAddress();
	  res.add(instr5);
	  
	  res.add(new LMAInstruction("HALT", 0, 0, true));
	  
	  // On définit la première valeur de l'objet qui est son nombre de var
	  LMAInstruction a = new LMAInstruction("LDA", 0, i, true);
	  res.add(a);
	  
	  res.add(new LMAInstruction("STM", 0, 0, Rh, true));

	  // On définit dans la frame l'adresse de l'objet
	  LMAInstruction instr2 = new LMAInstruction("STM", Rh, 4*x, Rf, false);
	  instr2.generateAddress();
	  res.add(instr2);
	  
	  // On définit l'argument du jump ci dessus
	  instr1.setUnknownAddr(a);
	  
	  
	  return res;
  }

}

