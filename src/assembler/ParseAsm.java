/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * ParseAsm.java
 *
 *
 ******************************************************************************
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 ******************************************************************************
 *  Ver         Who             When                    What
 *  0.1         prog         28 oct. 2010                 Creation
 */

package assembler;


/**
** Class ParseAsm : parse & logic handler
**/
class ParseAsm implements IAssDef{
        private IAssemblerServices services;
        private int lengthAsm;
        private int genIndex; // index in asm src
        private boolean endOfText;
        private int curToken;
        private String tokenName;  //
        private int valOfToken; //
        private String idName;
        private int numLine=0; //current line number in src code

        ParseAsm(IAssemblerServices services){
                this.services = services;
                endOfText = false;
                tokenName=new String("");
                valOfToken=0;
                numLine = 1;
                lengthAsm = services.getAsmSrc().length();
                genIndex=0;
                try{
                        //debug("asm()\n");
                        asm();
                }
                catch(MyException e){
                        System.out.println(e.getMessage());
                }
        }
        private void asm() throws MyException{
                if(!checkToken(BEGVAR,true))
                        throw new MyException("Directive VAR attendue !!", tokenName, numLine);
                if(!checkToken(NUM,true))
                        throw new MyException("Valeur non num�rique !!", tokenName, numLine);
                services.getMalloc().setDataSeg(valOfToken);
                //debug("goto block()\n");
                block();
                if(!checkToken(END,false))
                        throw new MyException("Directive END attendue !!", tokenName, numLine);
                //resolve label jump if necessary
                if(services.getIdConflict().exist()){
                        //debug(" conflict ");
                        services.getIdConflict().resolveConflicts();
                }
                //symbolsTable.print();
        }

        private void block() throws MyException{
                dcb();
                insts();
        }
        
        private void dcb() throws MyException{
                //debug("In dcb()\n");
                String myIdName; //use because value (hexa & bin) can be seen as id (grammar violation)
                checkToken(ID,true);
                do{
                        //debug("In loop dcb()\n");
                        if(!checkToken(ID,false))
                                throw new MyException("D�claration de variable attendue ou incorrect !!", tokenName, numLine);
                        myIdName = tokenName;
                        if(!checkToken(DCB,true))
                                throw new MyException("Directive <= attendue !!", tokenName, numLine);
                        if(!checkToken(NUM,true))
                                throw new MyException("Valeur ou format non num�rique !!", tokenName, numLine);
                        services.getSymbolsTable().addSymbol(myIdName, valOfToken, VAR, this.numLine);
                        services.getAleph8Code().genData(services.getSymbolsTable().getAd(myIdName), services.getSymbolsTable().getVal(myIdName));
                }while (checkToken(ID,true));
        }
        
        private void insts() throws MyException{
                //debug("In Insts()\n");
                if(!checkToken(BEGIN,false))
                        throw new MyException("Directive BEGIN attendue !!", tokenName, numLine);
                if(!checkToken(NUM,true))
                        throw new MyException("Valeur non num�rique !!", tokenName, numLine);
                services.getMalloc().setCodeSeg(valOfToken);
                inst();
        }
        
        private void inst() throws MyException{
                //debug("In inst()\n");
                while ((!checkToken(END,true)) && !endOfText){
                    //debug("In loop inst()\n");
                    switch (curToken){
                        case ID: // jump label : get cur ad code & set it to the ID
                            int ad = services.getMalloc().curCodeAd();
                            services.getSymbolsTable().addSymbol(idName, ad, JUMP, this.numLine);
                        break;
                        case NOR:
                            nor();
                        break;
                        case ADD:
                            add();
                        break;
                        case STA:
                            sta();
                        break;
                        case JCC:
                            jcc();
                        break;
                        default:
                            throw new MyException("Instruction non reconnue !!", tokenName, numLine);
                    }
                }
                if(endOfText)
                    throw new MyException("Directive END attendue !!", tokenName, numLine);
        }
        
        private void nor() throws MyException{
                //debug("In nor : ");
                int r, operand;
                if(!checkToken(ID,true))
                        throw new MyException("Identificateur attendu ou incorrect !!", tokenName, numLine);
                if ((r = services.getSymbolsTable().findSymbol(idName)) <0 ){
                        throw new MyException("Identificateur non d�clar� !!", tokenName, numLine);
                }
                else{
                        operand = services.getSymbolsTable().getAd(r);
                        services.getAleph8Code().gen(services.getMalloc().getAdCode(),NOR,operand);
                }
        }
        
        private void add() throws MyException{
                //debug("In add : ");
                int r, operand;
                if(!checkToken(ID,true))
                        throw new MyException("Identificateur attendu ou incorrect !!", tokenName, numLine);
                if ((r = services.getSymbolsTable().findSymbol(idName)) <0 ){
                        throw new MyException("Identificateur non d�clar� !!", tokenName, numLine);
                }
                else{
                operand = services.getSymbolsTable().getAd(r);
                services.getAleph8Code().gen(services.getMalloc().getAdCode(),ADD,operand);
                }
        }
        
        private void sta() throws MyException{
                //debug("In sta : ");
                int r, operand;
                if(!checkToken(ID,true))
                        throw new MyException("Identificateur attendu ou incorrect !!", tokenName, numLine);
                if ((r = services.getSymbolsTable().findSymbol(idName)) <0 ){
                        throw new MyException("Identificateur non d�clar� !!", tokenName, numLine);
                }
                else{
                        operand = services.getSymbolsTable().getAd(r);
                        services.getAleph8Code().gen(services.getMalloc().getAdCode(),STA,operand);
                }
        }
        
        private void jcc() throws MyException{
                //debug("In jcc : ");
                int r, operand;
                if(!checkToken(ID,true))
                        throw new MyException("Identificateur attendu ou incorrect !!", tokenName, numLine);
                if ((r = services.getSymbolsTable().findSymbol(idName)) >=0 ){
                        //debug("symbol trouv� idx : ");debug(r);debug("nom de l'id : ");debug(idName);
                        operand = services.getSymbolsTable().getVal(r);
                        services.getAleph8Code().gen(services.getMalloc().getAdCode(),JCC,operand);
                }
                else {
                //	record conflict : adress, inst, id not found
                        //debug("symbol non trouv� idx : ");debug(r);debug("nom de l'id : ");debug(idName);
                        services.getIdConflict().newConflict(JCC, services.getMalloc().getAdCode(), numLine, idName );
                }
        }
        
        //tools
        private int min(int a ,int b){
                if (a<b)
                        return a;
                else
                        return b;
        }
        
        private int min3Int(int a,int b, int c){
                int m1 = min(a,b);
                return min(m1,c);
        }
        
        // return the index in the src that isn't a separator. Search start at index
        private int noSeparator(int index) {
                String aString =  services.getAsmSrc().substring(index,index+1);
                while (aString.matches(" ") || aString.matches("\n") || aString.matches("\t")) {
                        index++;
                        if(aString.matches("\n"))
                                numLine++;
                        if (index>=lengthAsm) // no separator found
                                return lengthAsm;
                        else
                                aString =  services.getAsmSrc().substring(index,index+1);
                }
                return index;
        }
        
        // return the index in the src that matches with char. search start at index
        private int whereIs(String thisChar, int index) {
                if(index>=lengthAsm) // avoid search out of bound
                        return lengthAsm;
                int l = thisChar.length();
                String aString =  services.getAsmSrc().substring(index,index+l);
                while ((index<lengthAsm)&& (!aString.matches(thisChar))) {
                        index++;
                        if (index>=lengthAsm) // not found
                                return lengthAsm;
                        else
                                aString =  services.getAsmSrc().substring(index,index+l);
                }
                return index;
        }
        
        // get token. return it in minus. separators and comments are ignored. Check EOT
        private String getToken() throws MyException{      
                String curToken;
                boolean comment = false;
                int index2, pos1, pos2, pos3;
                do {
                        comment = false;
                        //get position of first occurence of no separator
                        genIndex = noSeparator(genIndex);
                        //search next separator
                        pos1 = whereIs(" ", genIndex);
                        pos2 = whereIs("\n", genIndex); // FIXME : very bad code
                        pos3 = whereIs("\t", genIndex);
                        index2 = min3Int(pos1,pos2,pos3);
                        //get sub string between separators
                        curToken = services.getAsmSrc().substring(genIndex, index2);
                        genIndex = index2;
                        //debug("Current token : " + curToken+ "\n");
                        if ((curToken.startsWith(";") )) { //comment.
                                //search index of NL that ends comment
                                genIndex = whereIs("\n", genIndex);
                                comment = true;
                        }
                }while (comment);
                // check EOT
                if (genIndex >= lengthAsm){
                        endOfText = true;
                        curToken ="";
                }
                else
                        curToken = curToken.toLowerCase();
                return curToken;
        }
        
        // Check curent token with reference and update type of token read. Read next token if wanted
        private boolean checkToken(int tokenToBe, boolean readToken) throws MyException{
                String tokenRead = tokenName;
                boolean ok=false;
                if (readToken){
                        tokenRead = getToken();
                        tokenName = tokenRead;
                }
                switch(tokenToBe){
                        case NUM:
                                if ((valOfToken = checkNum(tokenRead)) >= 0){
                                        curToken = NUM;
                                        ok=true;
                                }
                        break;
                        case ID:
                                if (checkId(tokenRead)){
                                        curToken = ID;
                                        ok=true;
                                }
                        break;
                        case BEGVAR:
                                if (tokenRead.equals(KEYWORDS[BEGVAR])) {
                                        curToken = BEGVAR;
                                        ok=true;
                                }
                        break;
                        case DCB:
                                if (tokenRead.equals(KEYWORDS[DCB])) {
                                        curToken = DCB;
                                        ok=true;
                                }
                        break;
                        case BEGIN:
                                if (tokenRead.equals(KEYWORDS[BEGIN])) {
                                        curToken = BEGIN;
                                        ok=true;
                                }
                        case END:
                                if (tokenRead.equals(KEYWORDS[END])) {
                                        curToken = END;
                                        ok=true;
                                }
                        break;
                }
                if (tokenRead.equals(KEYWORDS[NOR])) {
                        curToken = NOR;
                }
                if (tokenRead.equals(KEYWORDS[ADD])) {
                        curToken = ADD;
                }
                if (tokenRead.equals(KEYWORDS[STA])) {
                        curToken = STA;
                }
                if (tokenRead.equals(KEYWORDS[JCC])) {
                        curToken = JCC;
                }
                if (checkId(tokenRead)){
                        idName = tokenRead;
                        curToken = ID;
                }
                return(ok);
        }
        
        // get num value, return -1 if wrong
        private int checkNum(String numStr) {
                String tstBeg = numStr.substring(0,1);
                tstBeg.toLowerCase();
                if (tstBeg.matches("h") && (numStr.length()<= MAXHEXALENGTH)) { //hexa
                        try{
                                String st = numStr.substring(1,numStr.length() ); //avoid prefix
                                Integer myVal = new Integer(0);
                                return myVal.parseInt(st, 16);
                        }
                        catch(NumberFormatException nfe){
                                return -1;
                        }
                }
                if (tstBeg.matches("b") && (numStr.length()<= MAXBINLENGTH)) { //binary
                        try{
                                String st = numStr.substring(1,numStr.length() ); //avoid prefix
                                Integer myVal = new Integer(0);
                                return myVal.parseInt(st, 2);
                        }
                        catch(NumberFormatException nfe){
                                return -1;
                        }
                }
                try{ //decimal
                        Integer myVal = new Integer(numStr);
                        return myVal.intValue();
                }
                catch(NumberFormatException nfe){
                        return -1;
                }
        }
        
        //check Id format. return false if wrong
        private boolean checkId(String Id){
                if(Id.length() == 0) // avoid out of bound exception
                        return false;
                String begId = new String("[a-zA-Z]"); //reg exp
                String tstBeg = Id.substring(0,1);
                if((tstBeg.matches(begId)) && (Id.length()<= MAXIDLENGTH)){
                        //check if no keywords
                        int size = KEYWORDS.length;
                        while ((size--)>0){
                                if(Id.equalsIgnoreCase(KEYWORDS[size]))
                                        return false;
                        }
                        return true;
                }
                else
                        return false;
        }
}




