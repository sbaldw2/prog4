package Mips;
import Temp.Temp;
import Temp.Label;
import Symbol.Symbol;
import Frame.Frame;
import Frame.Access;
import Frame.AccessList;
import java.util.Hashtable;

public class MipsFrame extends Frame {

  private int count = 0;
  private static final int wordSize = 4;
  private int offset = 0; 
  
  public Frame newFrame(Symbol name, Util.BoolList formals) {
    Label label;
    if (name == null)
      label = new Label();
    else if (this.name != null)
      label = new Label(this.name + "." + name + "." + count++);
    else
      label = new Label(name);
    return new MipsFrame(label, formals);
  }

  public MipsFrame() {}
  private MipsFrame(Label n, Util.BoolList bl) {
    name = n;
    // ALLOCATE FORMALS (FROM FRAME.FRAME) 
    formals = allocFormals(0, bl);
  }
  
  public int wordSize() { 
	  return wordSize; 
  }

  public Access allocLocal(boolean escape) { 
	  if(escape){
		  offset = offset - wordSize; 
		  return new InFrame(offset);
	  }
	  else
		  return new InReg(new Temp());
  }
  
  public AccessList allocFormals(int off, Util.BoolList bl) {
	  if(bl == null){
		  return null;
	  }
	  else if(bl.head){
		  return new AccessList(new InFrame(off), allocFormals(offset + wordSize, bl.tail));
	  }
	  else
		  return new AccessList(new InReg(new Temp()), allocFormals(offset + wordSize, bl.tail));
  }

}