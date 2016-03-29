package FindEscape;

import Absyn.Exp;
import Absyn.VarDec;
import Absyn.FunctionDec;

public class FindEscape {
  Symbol.Table escEnv = new Symbol.Table(); // escEnv maps Symbol to Escape
  
  boolean isLeaf = true;
  
  public FindEscape(Absyn.Exp e) { traverseExp(0, e);  }

  void traverseExp(int depth, Absyn.Exp e) {
	  if (e == null)
		  return;
	  else if (e instanceof Absyn.VarExp){
		  Absyn.VarExp temp = (Absyn.VarExp) e;
		  traverseVar(depth, temp.var);
	  }
	  else if (e instanceof Absyn.RecordExp){
		  Absyn.RecordExp temp = (Absyn.RecordExp) e;
		  for (Absyn.FieldExpList fl = temp.fields; fl != null; fl = fl.tail){
			  traverseExp(depth, fl.init);
		  }
	  }
	  else if (e instanceof Absyn.CallExp){
		  isLeaf = false;
		  Absyn.CallExp temp = (Absyn.CallExp) e;
		  for (Absyn.ExpList el = temp.args; el != null; el = el.tail){
			  traverseExp(depth, el.head);
		  }
	  }
	  else if (e instanceof Absyn.AssignExp){
		  Absyn.AssignExp temp = (Absyn.AssignExp) e;
		  traverseExp(depth, temp.exp);
		  traverseVar(depth, temp.var);
	  }
	  else if (e instanceof Absyn.SeqExp){
		  Absyn.SeqExp temp = (Absyn.SeqExp) e;
		  for(Absyn.ExpList el = temp.list; el != null; el = el.tail){
			  traverseExp(depth, el.head);
		  }
	  }
	  else if (e instanceof Absyn.WhileExp){
		  Absyn.WhileExp temp = (Absyn.WhileExp) e;
		  traverseExp(depth, temp.body);
		  traverseExp(depth, temp.test);
	  }
	  else if (e instanceof Absyn.ForExp){
		  Absyn.ForExp temp = (Absyn.ForExp) e;
		  escEnv.beginScope();
		  traverseExp(depth, temp.body);
		  traverseExp(depth, temp.hi);
		  traverseDec(depth, temp.var);
		  escEnv.endScope();
	  }
	  else if (e instanceof Absyn.ArrayExp){
		  Absyn.ArrayExp temp = (Absyn.ArrayExp) e;
		  traverseExp(depth, temp.init);
		  traverseExp(depth, temp.size);
	  }
	  else if (e instanceof Absyn.IfExp){
		  Absyn.IfExp temp = (Absyn.IfExp) e;
		  if (temp.elseclause != null)
			  traverseExp(depth, temp.elseclause);
		  traverseExp(depth, temp.test);
		  traverseExp(depth, temp.thenclause);
	  }
	  else if (e instanceof Absyn.LetExp){
		  Absyn.LetExp temp = (Absyn.LetExp) e;
		  traverseExp(depth, temp.body);
		  escEnv.beginScope();
		  for (Absyn.DecList dl = temp.decs; dl != null; dl = dl.tail){
			  traverseDec(depth, dl.head);
		  }
		  escEnv.endScope();
	  }
	  else if (e instanceof Absyn.OpExp){
		  Absyn.OpExp temp = (Absyn.OpExp) e;
		  traverseExp(depth, temp.left);
		  traverseExp(depth, temp.right);
	  }
  }

  void traverseVar(int depth, Absyn.Var v) {
	  if (v == null)
		  return;
	  else if (v instanceof Absyn.FieldVar){
		  Absyn.FieldVar temp = (Absyn.FieldVar) v;
		  traverseVar(depth, (temp.var));
	  }
	  else if (v instanceof Absyn.SubscriptVar){
		  Absyn.SubscriptVar temp = (Absyn.SubscriptVar) v;
		  traverseVar(depth, temp.var);
		  traverseExp(depth, temp.index);
	  }
	  else if (v instanceof Absyn.SimpleVar){
		  Absyn.SimpleVar temp = (Absyn.SimpleVar) v;
		  Escape esc = (Escape) escEnv.get(temp.name);
		  if (depth > 0){
			  int escapeDepth = esc.depth;
			  if (escapeDepth < depth){
				  esc.setEscape();
			  }
		  }
	  }
  }

  void traverseDec(int depth, Absyn.Dec d) {
	  if (d == null)
		  return;
	  else if (d instanceof Absyn.FunctionDec){
		  Absyn.FunctionDec temp = (Absyn.FunctionDec) d;
		  for (Absyn.FunctionDec fd = temp; fd != null; fd = fd.next){
			  isLeaf = true;
			  escEnv.beginScope();
			  if (!isLeaf){
				  fd.leaf = false;
				  isLeaf = true;
			  }
			  
			  for (Absyn.FieldList fl = fd.params; fl != null; fl = fl.tail){
				  escEnv.put(fl.name, new FormalEscape(depth + 1, fl));
			  }
			  traverseExp(depth + 1, fd.body);
			  escEnv.endScope();
		  }		
	  }
	  else if (d instanceof Absyn.VarDec){
		  Absyn.VarDec temp = (Absyn.VarDec) d;
		  escEnv.beginScope();
		  escEnv.put(temp.name, new VarEscape(depth, temp));
		  traverseExp(depth, temp.init);
		  escEnv.endScope();
	  }
  }
}
