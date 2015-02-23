package slip ;
import  slip.lexer.LexerException ;
import  slip.parser.CParser ;
import  slip.parser.ParserException ;
import  slip.parser.Cprog ;
import  slip.internal.Prog ;
import  fichiers.text ;


public class ConcreteToInternal
{
  private final boolean errSlip ;
		private final boolean csyn ;
		private final boolean internal ;
		private final String  fileName ;
		
  public ConcreteToInternal(String fileName, boolean errSlip, boolean csyn, boolean internal) 
  {
			 this.fileName = fileName ;
			 this.errSlip  = errSlip ;
			 this.csyn = csyn;
			 this.internal = internal;
		}
		
		public ConcreteToInternal(String fileName) 
  {
			 this(fileName, true, false, true);
		}
		
		public Prog translate() throws Exception
		{
			
			 text slipFile = new text(fileName + ".slip") ;
				CParser toto ;
    Cprog lulu ;
						
			 try
				{
      toto = new CParser(slipFile) ;
      lulu = toto.parse() ;
				}
				catch(Exception e)
				{
					  slipFile.close() ;
							
					  if (errSlip &(e instanceof LexerException | e instanceof ParserException))
								{
								  int ln = 1 ; int pos = 1 ; String msg = "" ;
										
										if (e instanceof LexerException) 
											{ LexerException ee = (LexerException) e ; 
											  ln = ee.ln() ; pos = ee.pos() ; msg = ee.msg(); }
										
										if (e instanceof ParserException) 
											{ ParserException ee = (ParserException) e ; ln = ee.ln() 
											  ; pos = ee.pos() ; msg = ee.msg(); }
										
										text errFile = new text(fileName + ".err") ;
										errFile.rewrite();
										if (errFile.ioError() != 0)
											{ System.out.println("Cannot create file " + fileName + ".err.") ;
											  throw e ;
											}
											
										slipFile.reset();
										if (slipFile.ioError() != 0)
											{ System.out.println("Cannot reread file " + fileName + ".slip.") ;
											  throw e ;
											}
											
											while (!slipFile.eof())
												{
												  errFile.writeln(slipFile.readlnString()); ln-- ;
														if (ln==0)
														{ 
															 if (pos > 0) pos-- ; else pos = 0 ;
															 char[] c = new char[pos] ;
														  int i = 0 ;
														  while (i != pos) { c[i] = '-' ; i++ ; }
														  errFile.writeln(String.valueOf(c) 
																                + "|  [" + msg + "]") ;
														}
												}
												
												slipFile.close() ; errFile.close() ;
										
								}
								
								throw e ;
				}
				
				slipFile.close();
				
				if (csyn) 
    { 
					 text  csyn  = new text(fileName + ".csyn") ;
      csyn.rewrite(); csyn.writeln("" + lulu) ;
      csyn.close() ;
				}

				Prog  titi = lulu.translate() ;
    if (internal)
    { 
      text  internal  = new text(fileName + ".internal") ;
      internal.rewrite(); internal.writeln("" + titi) ;
      internal.close() ;
				}
    return titi ;
  }
		
		public static void main(String[] arg) throws Exception
  {
    ConcreteToInternal translator = new ConcreteToInternal(arg[0], true, true, true) ;
				
				try
				{ translator.translate() ;
				  System.out.println("File " + arg[0] + ".slip is syntactically correct.");
				}catch(Exception e)
				{
				  System.out.println(e);
				}
  }

}

