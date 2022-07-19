/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration.simulate;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import pascal.icodegeneration.AIcode;
import pascal.symboltable.ASymbol;
import pascal.symboltable.ConstanteSymbol;

/**
 * Memory pseudo HARVARD architecture
 * @author laurent
 */
public class Memory {
    //lower memory
    private Map<Byte, AtomicCode> code = new  TreeMap<Byte, AtomicCode>(); // address, <label, icode>
    private Iterator<Byte> itCode;
    private AIcode currentIcode = null;
    //higher memory
    private Map<Byte, AtomicData> data = new  TreeMap<Byte, AtomicData>(); // address, <name, value>
    private Iterator<Byte> itData;
    //global address
    private Byte address=0;

    public void loadProg(Vector<AIcode> v){
        address =0;
        for(AIcode c : v){
            this.code.put(address++, new AtomicCode(c.getAssociatedLabel(),c));
        }
        resetCodeIterator();
        this.getNextICode();
    }
    
    public void loadData(Vector<ASymbol> v){
        for(ASymbol d : v){
            if(d.getSymbolClass().equals(ASymbol.SymbolClass.CONSTANTE)){
                ConstanteSymbol cst = (ConstanteSymbol)d;
                this.data.put(address++, new AtomicData(d.getName(), Byte.valueOf(cst.getConstValue())));
            }else{
                 if(d.getSymbolClass().equals(ASymbol.SymbolClass.VARIABLE)){
                    this.data.put(address++, new AtomicData(d.getName(), (byte)0));
                 }
            }
        }
        resetDataIterator();
    }

    public AIcode getCurrentIcode() {
        return currentIcode;
    }

    public AIcode getNextICode(){
        if(this.itCode.hasNext()){
            this.currentIcode = this.code.get(this.itCode.next()).getIcode();
            return this.currentIcode;
        }
        return null;
    }
    
    public Iterator<Byte> jumpIteratorToLabel(String label){
        this.resetCodeIterator();
        while(this.itCode.hasNext()){
            AtomicCode ac = this.code.get(this.itCode.next());
            if(ac.getLabel().equals(label)){
                this.currentIcode = ac.getIcode();
                return this.itCode;
            }
        }
        return null;
    }
    
    public Byte getDataAdressFromName(String name){
        Byte adr;
        this.resetDataIterator();
        while(this.itData.hasNext()){
            adr=this.itData.next();
            AtomicData d= this.data.get(adr);
            if(d.getName().equals(name)){
                return adr;
            }
        }
        return null;
    }
    
    public Byte getDataValueFromName(String name){
        this.resetDataIterator();
        while(this.itData.hasNext()){
            AtomicData d = this.data.get(this.itData.next());
            if(d.getName().equals(name)){
                return d.getValue();
            }
        }
        return null;  
    }
    
    public Byte getDataValueFromAddress(Byte adr){
        if(this.data.containsKey(adr)){
            AtomicData d = this.data.get(adr);
            return d.getValue();
        }
        return null;  
    }
    
    public void setDataValueFromAddress(Byte val, Byte adr){
        if(this.data.containsKey(adr)){
            AtomicData d = this.data.get(adr);
            d.setValue(val);
        }
    }
    
    public void printCode(){
        Set<Byte> ka = this.code.keySet();
        System.out.println("ICODE :  \tCount : "+ka.size());
        System.out.println("Adresses : \tLabels : \t-- icode :");
        for(Byte a: ka){
            AtomicCode c = this.code.get(a);
            if(c.icode.equals(this.currentIcode)){
                System.out.println(a+"\t\t"+c.getIcode().toString()+"\t\t\t<===");
            }else{
                System.out.println(a+"\t\t"+c.getIcode().toString());
            }
        }
    }
    
    public void printData(){
        Set<Byte> ka = this.data.keySet();
        System.out.println("DATA : \tCount : "+ka.size());
        System.out.println("Adresses : \tName:\t\t\tValue:\t" );
        for(Byte a: ka){
            AtomicData d = this.data.get(a);
            System.out.println(a+"\t\t-- "+d.name+"\t\t-- "+d.value.toString());
        }
    }
    
    private Iterator resetCodeIterator() {
        Set<Byte> k = this.code.keySet();
        this.itCode = k.iterator();
        return itCode;
    }
    
    private Iterator resetDataIterator() {
        Set<Byte> k = this.data.keySet();
        this.itData = k.iterator();
        return itCode;
    }
    
    private class AtomicCode{
        private AIcode icode;
        private String label;

        public AtomicCode(String label, AIcode icode) {
            this.icode = icode;
            this.label = label;
        }

        public AIcode getIcode() {
            return icode;
        }

        public String getLabel() {
            return label;
        }
    }
    
    private class AtomicData{
        private String name;
        private Byte value;

        public AtomicData(String name, Byte value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Byte getValue() {
            return value;
        }
        
        public void setValue(Byte value) {
            this.value = value;
        }
       
    }
    
}
