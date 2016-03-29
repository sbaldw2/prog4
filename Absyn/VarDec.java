package Absyn;
import Symbol.Symbol;
public class VarDec extends Dec {
	//You’ll need to initialize the escape fields to
	//false instead of true and the leaf field to true instead of false.
   public Symbol name;
   public boolean escape = false;
   public NameTy type; /* optional */
   public Exp init;
   public VarDec(int p, Symbol n, NameTy t, Exp i) {pos=p; name=n; type=t; init=i;}
   public Semant.VarEntry entry;
}
