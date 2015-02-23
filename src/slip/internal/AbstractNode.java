package slip.internal; 

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNode
{
   public final static String ELN = "\r" ;
   static String[] vt ;  // name of the local variables of the current method.
   static int magic = 0; // to avoid looping problems in toString().
   
   // Adresse mémoire de la frame courante se trouve dans le Registre Rf
   public int Rf = 11;
   // Plus petite adresse mémoire libre se trouve dans Rs
   public int Rs = 12;
   // Plus grande adresse mémoire libre se trouve dans Rh
   public int Rh = 13;
   // Adresse de retour des méthodes
   public int Rr = 10;
   // Message pour les commandes IN/OUT
   public int Rmessage = 14;
   // Entier stocké pour les commandes IN/OUT
   public int Rentier = 15;
   // Adresse courante
   public static int currAddr = 0;
   // Adresse de départ des LIT
   public static int currAddrLit = 65532;
   // Liste qui retient tous les Jump définis dans les appels de méthode static
   public static List<JumpMeth> JumpMeth = new ArrayList<JumpMeth>();
   // Table d'indirection
   public static IndirectionTable IT;
   // Level de la méthode en cours de traduction
   public static int currentLevel;
   // Programme en cours de traduction
   public static Prog prog;
   // Jump qui saute vers la main
   public static LMAInstruction JumpMainInstr;
   
   public static LMAInstruction litErr = new LMAInstruction("ERR:", true);
   public static LMAInstruction litSOF = new LMAInstruction("SOF", true);
   
   static String varName(int i)
   {
    if (i < 0) return "[bad variable number : " + i + "]";
    if (i == 0) return "result#" + i ;
    if (i <= vt.length) return vt[i - 1] + "#" + i ;
    return "#" + i ;
   }

}

