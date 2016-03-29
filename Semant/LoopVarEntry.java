package Semant;

class LoopVarEntry extends VarEntry {
	// VARENTRY AND LOOPVARENTRY NEED AN ADDITIONAL ACCESS PARAM ADDED TO CONSTRUCTORS
  LoopVarEntry(Types.Type t, Translate.Access a) {
    super(t, a);
  }
}
