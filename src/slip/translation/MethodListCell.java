package slip.translation ;
import  slip.parser.Cmethod ;
import  slip.internal.Method ;

class MethodListCell
{
  Cmethod m ;
  Method  im ;
  MethodListCell next ;

  MethodListCell(Cmethod c){ m = c ; }

  void putMethod(Method m){ im = m ; }

}

