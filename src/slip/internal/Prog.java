package slip.internal; 
import  slip.translation.*;

public class Prog extends AbstractNode // Program
{  
   public Method[] meths; // Declaration of all methods
   
   
   public Prog(Method[] meths)
   { this.meths = meths ; 
   }

   public String toString()
   {
     String res = "" ;
     
     int i = 0 ;
     while (i!=meths.length)
     { res += meths[i] ; i++ ; }

     return res ;
   }
}

