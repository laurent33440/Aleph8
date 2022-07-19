/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration.simulate;

import java.util.Stack;
import pascal.icodegeneration.AIcode;
import pascal.icodegeneration.BraIcode;
import pascal.icodegeneration.BzeIcode;
import pascal.icodegeneration.LdaIcode;
import pascal.icodegeneration.LdiIcode;

/**
 *
 * @author laurent
 */
public class StackProcessor {
    private Stack<Byte> stack = new Stack<Byte>();
    private Memory mem;
    
    public void attachMem(Memory mem){
        this.mem = mem;
    }
    
    public void execIcode(AIcode icode){
        String icodeName;
        if(icode!=null){
            icodeName = icode.getClass().getSimpleName();
            if(icodeName.equals("AddIcode")){
                stack.push((byte)(stack.pop()+stack.pop()));
            }else{
                if(icodeName.equals("BraIcode")){
                    BraIcode ic = (BraIcode)icode;
                    mem.jumpIteratorToLabel(ic.getBranchName());
                }else{
                    if(icodeName.equals("BzeIcode")){
                        if(stack.pop()==0){//branch
                            BzeIcode ic = (BzeIcode)icode;
                            mem.jumpIteratorToLabel(ic.getBranchName());
                        }else{
                            this.mem.getNextICode();//no branch
                        }
                    }else{
                       if(icodeName.equals("EqlIcode")){
                           byte b0 = stack.pop(); byte b1 = stack.peek();stack.push(b0);  
                           byte i = (b0-b1==0)?stack.push((byte)1):stack.push((byte)0);
                        }else{
                            if(icodeName.equals("GeqIcode")){
                                byte b0 = stack.pop(); byte b1 = stack.peek();stack.push(b0);  
                                byte i = (b0 >= b1)?stack.push((byte)1):stack.push((byte)0);
                            }else{
                                if(icodeName.equals("GtrIcode")){
                                    byte b0 = stack.pop(); byte b1 = stack.peek();stack.push(b0);  
                                    byte i = (b0 > b1)?stack.push((byte)1):stack.push((byte)0);
                                }else{
                                    if(icodeName.equals("HltIcode")){
                                            //nothing to do
                                    }else{
                                        if(icodeName.equals("LdaIcode")){
                                            LdaIcode ic = (LdaIcode)icode;
                                            this.stack.push(mem.getDataAdressFromName(ic.getSymbolicAdress()));
                                        }else{
                                            if(icodeName.equals("LdiIcode")){
                                                LdiIcode ic =(LdiIcode)icode;
                                                stack.push(ic.getValue());
                                            }else{
                                                if(icodeName.equals("LdrIcode")){
                                                    this.stack.push(mem.getDataValueFromAddress(this.stack.pop()));
                                                }else{
                                                    if(icodeName.equals("LeqIcode")){
                                                        byte b0 = stack.pop(); byte b1 = stack.peek();stack.push(b0);  
                                                        byte i = (b0<= b1)?stack.push((byte)1):stack.push((byte)0);
                                                    }else{
                                                        if(icodeName.equals("LssIcode")){
                                                            byte b0 = stack.pop(); byte b1 = stack.peek();stack.push(b0);  
                                                            byte i = (b0< b1)?stack.push((byte)1):stack.push((byte)0);
                                                        }else{
                                                            if(icodeName.equals("NeqIcode")){
                                                                byte b0 = stack.pop(); byte b1 = stack.peek();stack.push(b0);  
                                                                byte i = (b0!=b1)?stack.push((byte)1):stack.push((byte)0);
                                                            }else{
                                                                if(icodeName.equals("StoIcode")){
                                                                    this.mem.setDataValueFromAddress(this.stack.pop(), this.stack.pop());
                                                                }else{
                                                                    if(icodeName.equals("SubIcode")){
                                                                          stack.push((byte)(stack.pop()-stack.pop()));
                                                                    }else{
                                                                        //unknown!!
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(!icodeName.equals("BraIcode")&&!icodeName.equals("BzeIcode")){//specific branch
                this.mem.getNextICode();
            }
        }    
    }
    
    public void execProg(boolean trace){
        AIcode icode;
        icode = mem.getCurrentIcode();
        String icodeName = "";
        while(icode!=null&&!icodeName.equals("HltIcode")){
            icodeName = icode.getClass().getSimpleName();
            if(trace){
                this.simuSnapShot();
            }
            this.execIcode(icode);
            icode = mem.getCurrentIcode();
        }
        if(!trace){
            this.simuSnapShot();
        }
    }
    
    private void simuSnapShot(){
        //prog list
        this.mem.printCode();
        //processor's stack
        this.print();
        //data list
        this.mem.printData();
        
    }
    
    public void print(){
        System.out.println("Processor stack :");
        if(!stack.empty()){
            Stack<Byte> stackbis = (Stack<Byte>)this.stack.clone();
            while( !stackbis.empty()){
                Byte b = stackbis.pop();
                System.out.println(b);
            }
        }else{
            System.out.println("empty");
        }
//        for (Byte b : stack){ //reverse order
//            System.out.println(b);
//        }
    }
    
}
