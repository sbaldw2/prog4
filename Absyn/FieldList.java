package Absyn;
import Symbol.Symbol;
public class FieldList extends Absyn {
	//You’ll need to initialize the escape fields to
	//false instead of true and the leaf field to true instead of false.
   public Symbol name;
   public Symbol type;
   public FieldList tail;
   public boolean escape = false;
   public FieldList(int p, Symbol n, Symbol t, FieldList x) {pos=p; name=n; type=t; tail=x;}
}
