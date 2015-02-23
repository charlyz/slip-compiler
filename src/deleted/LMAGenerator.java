package slip.translation;

import java.util.ArrayList;

import slip.internal.Aop;
import slip.internal.Cexpr;
import slip.internal.Des;
import slip.internal.Divide;
import slip.internal.Expr;
import slip.internal.FieldDes;
import slip.internal.I;
import slip.internal.Minus;
import slip.internal.Null;
import slip.internal.Plus;
import slip.internal.Sexpr;
import slip.internal.This;
import slip.internal.ThisFieldDes;
import slip.internal.Times;
import slip.internal.VarDes;

public class LMAGenerator 
{	
	/*
	 * Registre de la frame actuelle. Pour l'instant on y touche pas,
	 * on pourra plus tard la modifier selon le besoin.
	 */
	private static int Rf = 10;
	
	private static int m = 10;

	private static int h = 10;
	
	/*
	 * Traduit un désignateur de type x, x.i ou this.i en LMA. 
	 * L'adresse du désignateur est placée dans R1.
	 */
	public static ArrayList<LMAInstruction> translateDes(Des des)
	{
		if(des instanceof VarDes) return translateDes((VarDes) des);
		else if(des instanceof FieldDes) return translateDes((FieldDes) des);
		else if(des instanceof ThisFieldDes) return translateDes((ThisFieldDes) des);
		else return null;
	}
	
	/*
	 * Traduit une expression de type x, x.i, this.i, this, null ou expr1 aop expr2
	 * en LMA. La valeur de l'expression est placée en R0.
	 */
	public static ArrayList<LMAInstruction> translateExpr(Expr expr)
	{
		if(expr instanceof Sexpr) return translatExpr((Sexpr)expr);
		else if(expr instanceof Cexpr) return translatExpr((Cexpr)expr);
		else return null;
	}
	
	
	//
	// Identificateurs
	//
	
	/*
	 * Traduit un désignateur de type x en LMA. L'adresse du désignateur
	 * est placée dans R1.
	 */
	public static ArrayList<LMAInstruction> translateDes(VarDes des)
	{
		LMAInstruction instr = new LMAInstruction(2);
		instr.setInstr("LDA");
		instr.setArg1(1);
		instr.setArg2(4*des.getNumber());
		instr.setOptionArg(Rf);
		
		ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
		res.add(instr);
		
		return res;
	}
	
	/*
	 * Traduit un désignateur de type x.i en LMA. L'adresse du désignateur
	 * est placée dans R1.
	 */
	public static ArrayList<LMAInstruction> translateDes(FieldDes des)
	{
		LMAInstruction instr1 = new LMAInstruction(2);
		instr1.setInstr("LDM");
		instr1.setArg1(1);
		instr1.setArg2(4*des.getNumber());
		instr1.setOptionArg(Rf);
		
		LMAInstruction instr2 = new LMAInstruction(2);
		instr2.setInstr("LDA");
		instr2.setArg1(1);
		instr2.setArg2(4*des.getFieldNumber());
		instr2.setOptionArg(1);
		
		ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
		res.add(instr1);
		res.add(instr2);
		
		return res;
	}
	
	/*
	 * Traduit un désignateur de type this.i en LMA. L'adresse du désignateur
	 * est placée dans R1.
	 */
	public static ArrayList<LMAInstruction> translatDes(ThisFieldDes des)
	{
		LMAInstruction instr = new LMAInstruction(2);
		instr.setInstr("LDA");
		instr.setArg1(1);
		instr.setArg2(4*des.getFieldNumber());
		instr.setOptionArg(Rf);
		
		ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
		res.add(instr);
		
		return res;
	}
	
	//
	// Expr
	//
	
	/*
	 * Traduit une expression de type x en LMA. La valeur de l'expression
	 * est placée dans R0.
	 */
	public static ArrayList<LMAInstruction> translateExpr(VarDes des)
	{
		LMAInstruction instr = new LMAInstruction(2);
		instr.setInstr("LDM");
		instr.setArg1(0);
		instr.setArg2(4*des.getNumber());
		instr.setOptionArg(Rf);
		
		ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
		res.add(instr);
		
		return res;
	}
	
	/*
	 * Traduit une expression de type x.i en LMA. La valeur de l'expression
	 * est placée dans R0.
	 */
	public static ArrayList<LMAInstruction> translateExpr(FieldDes des)
	{
		LMAInstruction instr1 = new LMAInstruction(2);
		instr1.setInstr("LDM");
		instr1.setArg1(1);
		instr1.setArg2(4*des.getNumber());
		instr1.setOptionArg(Rf);
		
		LMAInstruction instr2 = new LMAInstruction(2);
		instr2.setInstr("LDM");
		instr2.setArg1(0);
		instr2.setArg2(4*des.getFieldNumber());
		instr2.setOptionArg(1);
		
		ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
		res.add(instr1);
		res.add(instr2);
		
		return res;
	}
	
	/*
	 * Traduit une expression de type this.i en LMA. La valeur de l'expression
	 * est placée dans R0.
	 */
	public static ArrayList<LMAInstruction> translatExpr(ThisFieldDes des)
	{
		LMAInstruction instr = new LMAInstruction(2);
		instr.setInstr("LDM");
		instr.setArg1(0);
		instr.setArg2(4*des.getFieldNumber());
		instr.setOptionArg(Rf);
		
		ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
		res.add(instr);
		
		return res;
	}
	
	/*
	 * Traduit une expression de type i en LMA. La valeur de l'expression
	 * est placée dans R0.
	 */
	public static ArrayList<LMAInstruction> translatExpr(I expr)
	{
		// ?
		ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
		return res;
	}
	
	/*
	 * Traduit une expression de type this en LMA. La valeur de l'expression
	 * est placée dans R0.
	 */
	public static ArrayList<LMAInstruction> translatExpr(This expr)
	{
		LMAInstruction instr = new LMAInstruction(2);
		instr.setInstr("LDM");
		instr.setArg1(0);
		instr.setArg2(0);
		instr.setOptionArg(Rf);
		
		ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
		res.add(instr);
		
		return res;
	}
	
	/*
	 * Traduit une expression de type null en LMA. La valeur de l'expression
	 * est placée dans R0.
	 */
	public static ArrayList<LMAInstruction> translatExpr(Null expr)
	{
		LMAInstruction instr = new LMAInstruction(1);
		instr.setInstr("LDA");
		instr.setArg1(0);
		instr.setArg2(0);
		
		ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
		res.add(instr);
		
		return res;
	}
	
	/*
	 * Traduit une expression de type i, x, x.i, this, this.i, null ou 
	 * en LMA. La valeur de l'expression est placée dans R0.
	 */
	public static ArrayList<LMAInstruction> translatExpr(Sexpr expr)
	{
		if(expr instanceof VarDes) return translateExpr((VarDes)expr);
		else if(expr instanceof FieldDes) return translateExpr((FieldDes)expr);
		else if(expr instanceof ThisFieldDes) return translatExpr((ThisFieldDes)expr);
		else if(expr instanceof I) return translatExpr((I)expr);
		else if(expr instanceof Null) return translatExpr((Null)expr);
		else return null;
	}
	
	/*
	 * Traduit une expression de type sexpr1 aop sexpr2 en LMA. La valeur 
	 * de l'expression est placée dans R0.
	 */
	public static ArrayList<LMAInstruction> translatExpr(Cexpr expr)
	{
		ArrayList<LMAInstruction> res = new ArrayList<LMAInstruction>();
		// On execute expr2
		res.addAll(LMAGenerator.translatExpr(expr.getExpr2()));
		
		// On place la valeur de expr2 dans R1
		LMAInstruction instr1 = new LMAInstruction(1);
		instr1.setInstr("STM");
		instr1.setArg1(0);
		instr1.setArg2(1);
		
		res.add(instr1);
		
		// On execute expr1
		res.addAll(LMAGenerator.translatExpr(expr.getExpr1()));
		
		// On execute sexpr1 aop sexpr2, R0 aop R1
		LMAInstruction instr2 = new LMAInstruction(1);
		instr2.setInstr(LMAGenerator.getLMAStringAop(expr.getAop()));
		instr2.setArg1(0);
		instr2.setArg2(1);
		
		res.add(instr2);
		return res;
	}
	
	/*
	 * Renvoie la commande LMA d'un aop ou null s'il n'existe pas.
	 */
	public static String getLMAStringAop(Aop aop)
	{
		if(aop instanceof Divide) return "DIV";
		else if(aop instanceof Minus) return "SUB";
		else if(aop instanceof Plus) return "ADD";
		else if(aop instanceof Times) return "MUL";
		//else if(aop instanceof Remainder) return "SUB";
		else return null;
	}
}
