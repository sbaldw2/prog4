package Semant;

public class VarEntry extends Entry {
  Translate.Access access;
  public Types.Type ty;
// VARENTRY AND LOOPVARENTRY NEED AN ADDITIONAL ACCESS PARAM ADDED TO CONSTRUCTORS 
  VarEntry(Types.Type t, Translate.Access a) {
    ty = t;
    access = a;
  }
}
