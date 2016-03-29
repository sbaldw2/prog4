package Absyn;
import Symbol.Symbol;
public class FunctionDec extends Dec {
	//You’ll need to initialize the escape fields to
	//false instead of true and the leaf field to true instead of false.
  public Symbol name;
  public FieldList params;
  public NameType result;		/* optional */
  public Exp body;
  public FunctionDec next;
  public FunctionDec(int p, Symbol n, FieldList a, NameType r, Exp b,
		     FunctionDec x) {
    pos=p; name=n; params=a; result=r; body=b; next=x;
  }
  public boolean leaf = true;
  public Semant.FunEntry entry;
}
