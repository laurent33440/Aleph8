/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * LineParser.java
 * README :
 * A)Tree of tokens is construct by multiples matchers
 * Name matcher matches all keywords!! BUT keywords matchers overwrite wrong
 * name in the tree. Thus Tree construct is correct.
 * BUT :    1) be aware do not suppress blank on the raw input. Else, name
 * matcher will matche all concatanate words.
 *          2) name matcher must be call first when finding tokens because of
 * his large spectrum.
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
 *  0.1         prog         31 déc. 2011                 Creation
 */

package pascal.parser;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pascal.tokenfactory.ITokenDef.TokenValue;
import pascal.tokenfactory.Token;

/**
 *
 */
public class LineParser {
    private String rawInput;
    private TreeMap<Integer, Token> lineToken;
    private Integer LineNumber;
    
    //----------regular expressions (http://prevert.upmf-grenoble.fr/Prog/Java/CoursJava/expressionsRegulieres.html#introduction)
    //PONCTUATION
    private final String name = "([_a-zA-Z]+[1-9]*)";
    private final String comma = "("+TokenValue.COMMA.getVal()+")";
    private final String dot = "(\\"+TokenValue.DOT.getVal()+")";//escape to prevent metacharacter mecanism
    private final String semicolon = "("+TokenValue.SEMICOLON.getVal()+")";
    private final String colon = "("+TokenValue.COLON.getVal()+"(?!=))";// colon never followed by "=" (avoid conflict with LET)
    //EXPRESSION
    private final String absNum = "(?<![_a-zA-Z])[0-9]+"; // matches num never preceded by alpha or underscore (avoid conflict with NAME)
    //FIXME : in name abc123 this reg expr matches 23 !!!! This work fine with abc1 but not abc12
    private final String equal = "(?<!:)("+TokenValue.EQUAL.getVal()+")";//equal never preceded by ":" (avoid conflict with LET)
    private final String plus = "(\\"+TokenValue.PLUS.getVal()+"){1}";
    private final String minus = "("+TokenValue.MINUS.getVal()+")";
    private final String mul = "(\\"+TokenValue.MUL.getVal()+")";
    private final String div = "("+TokenValue.DIV.getVal()+")";
    private final String lp = "(\\"+TokenValue.LP.getVal()+")";
    private final String rp = "(\\"+TokenValue.RP.getVal()+")";
    private final String great = "((?<!<)"+TokenValue.GREAT.getVal()+"(?!=))";//great never preceded by less or followed by equal !!FIXME doesn't match
    private final String less = "("+TokenValue.LESS.getVal()+"(?![>=]))";//less never followed by great or equal !!FIXME doesn't match
    private final String geq = "("+TokenValue.GEQ.getVal()+")";
    private final String leq = "("+TokenValue.LEQ.getVal()+")";
    private final String diff = "("+TokenValue.DIFF.getVal()+")";
    //TYPE
    private final String type = "("+TokenValue.TYPE.getVal()+")";
    //PASCAL KEY WORDS
    private final String program = "("+TokenValue.PROGRAM.getVal()+"(?![0-9]+))"; // keyword never followed by numericals
    private final String constw = "("+TokenValue.CONST.getVal()+"(?![0-9]+))"; //FIXME : doesn't work with const923 but with only one digit ie : const9
    private final String var = "("+TokenValue.VAR.getVal()+"(?!(\\d)+))";//same
    private final String begin = "("+TokenValue.BEGIN.getVal()+"(?![0-9]+))";
    private final String let = "("+TokenValue.LET.getVal()+")"; //assignation  := 23 is legal
    private final String ifw = "("+TokenValue.IF.getVal()+"(?![0-9]+))";
    private final String thenw = "("+TokenValue.THEN.getVal()+"(?![0-9]+))";
    private final String whilew = "("+TokenValue.WHILE.getVal()+"(?![0-9]+))";
    private final String dow = "("+TokenValue.DO.getVal()+"(?![0-9]+))";
    private final String write = "("+TokenValue.WRITE.getVal()+"(?![0-9]+))";
    private final String read = "("+TokenValue.READ.getVal()+"(?![0-9]+))";
    private final String end = "("+TokenValue.END.getVal()+"(?![0-9]+))";

    private Pattern pName = Pattern.compile(this.name);
    private Pattern pComma = Pattern.compile(this.comma);
    private Pattern pDot = Pattern.compile(this.dot);
    private Pattern pSemicolon = Pattern.compile(this.semicolon);
    private Pattern pColon = Pattern.compile(this.colon);
    private Pattern pAbsNum = Pattern.compile(absNum);
    private Pattern pEqual = Pattern.compile(equal);
    private Pattern pPlus = Pattern.compile(plus);
    private Pattern pMinus = Pattern.compile(minus);
    private Pattern pMul = Pattern.compile(mul);
    private Pattern pDiv = Pattern.compile(div);
    private Pattern pLp = Pattern.compile(lp);
    private Pattern pRp = Pattern.compile(rp);
    private Pattern pGreat = Pattern.compile(this.great);
    private Pattern pLess = Pattern.compile(this.less);
    private Pattern pGeq = Pattern.compile(this.geq);
    private Pattern pLeq = Pattern.compile(this.leq);
    private Pattern pDiff = Pattern.compile(this.diff);
    private Pattern pType = Pattern.compile(this.type);
    private Pattern pProgram = Pattern.compile(this.program);
    private Pattern pConst = Pattern.compile(this.constw);
    private Pattern pVar = Pattern.compile(this.var);
    private Pattern pBegin = Pattern.compile(this.begin);
    private Pattern pLet = Pattern.compile(this.let);
    private Pattern pIf = Pattern.compile(this.ifw);
    private Pattern pThen = Pattern.compile(this.thenw);
    private Pattern pWhile = Pattern.compile(this.whilew);
    private Pattern pDo = Pattern.compile(this.dow);
    private Pattern pWrite = Pattern.compile(this.write);
    private Pattern pRead = Pattern.compile(this.read);
    private Pattern pEnd = Pattern.compile(this.end);

    private Matcher mName ;
    private Matcher mComma ;
    private Matcher mDot ;
    private Matcher mSemicolon ;
    private Matcher mColon ;
    private Matcher mAbsNum ;
    private Matcher mEqual ;
    private Matcher mPlus ;
    private Matcher mMinus ;
    private Matcher mMul ;
    private Matcher mDiv ;
    private Matcher mLp ;
    private Matcher mRp ;
    private Matcher mGreat ;
    private Matcher mLess ;
    private Matcher mGeq ;
    private Matcher mLeq ;
    private Matcher mDiff ;
    private Matcher mType ;
    private Matcher mProgram ;
    private Matcher mConst ;
    private Matcher mVar ;
    private Matcher mBegin ;
    private Matcher mLet ;
    private Matcher mIf ;
    private Matcher mThen ;
    private Matcher mWhile ;
    private Matcher mDo ;
    private Matcher mWrite ;
    private Matcher mRead ;
    private Matcher mEnd ;

    //public API

    /**
     * Construct a Pascal line parser
     * @param Currrent line token (TreeMap)
     */
    public LineParser(TreeMap<Integer, Token> currLineToken )  {
        this.lineToken= currLineToken;
    }

    /**
     * Parse a Line of Pascal source code
     * Fill in the current line token (TreeMap)
     * @param   line : string representation to parse
     *          lineNumber : line number in Pascal source
     * @return  true if all tokens were found false else
     */
    public boolean parseLine(String line, Integer lineNumber) {
        this.LineNumber = lineNumber;
        this.rawInput = line;
        mName = pName.matcher(this.rawInput);
        mComma = pComma.matcher(this.rawInput);
        mDot = pDot.matcher(this.rawInput);
        mSemicolon = pSemicolon.matcher(this.rawInput);
        mColon = pColon.matcher(this.rawInput);
        mAbsNum = pAbsNum.matcher(this.rawInput);
        mEqual = pEqual.matcher(this.rawInput);
        mPlus = pPlus.matcher(this.rawInput);
        mMinus = pMinus.matcher(this.rawInput);
        mMul = pMul.matcher(this.rawInput);
        mDiv = pDiv.matcher(this.rawInput);
        mLp = pLp.matcher(this.rawInput);
        mRp = pRp.matcher(this.rawInput);
        mGreat = pGreat.matcher(this.rawInput);
        mLess = pLess.matcher(this.rawInput);
        mGeq = pGeq.matcher(this.rawInput);
        mLeq = pLeq.matcher(this.rawInput);
        mDiff = pDiff.matcher(this.rawInput);
        mType = pType.matcher(this.rawInput);
        mProgram = pProgram.matcher(this.rawInput);
        mConst = pConst.matcher(this.rawInput);
        mVar = pVar.matcher(this.rawInput);
        mBegin = pBegin.matcher(this.rawInput);
        mLet = pLet.matcher(this.rawInput);
        mIf = pIf.matcher(this.rawInput);
        mThen = pThen.matcher(this.rawInput);
        mWhile = pWhile.matcher(this.rawInput);
        mDo = pDo.matcher(this.rawInput);
        mWrite = pWrite.matcher(this.rawInput);
        mRead = pRead.matcher(this.rawInput);
        mEnd = pEnd.matcher(this.rawInput);
        if(!findToken()){
            this.lineToken.put(0,new Token(TokenValue.NOTOKEN));
            return false;
        }else
            return true;
    }

    //private
    private boolean findToken(){
        while(mName.find()){//found all NAME 
            this.lineToken.put(this.leafNumber(mName.start()), new Token(TokenValue.NAME,mName.group(), this.LineNumber));
        }
        while(mComma.find()){//found all comma
            this.lineToken.put(this.leafNumber(mComma.start()), new Token(TokenValue.COMMA));
        }
        while(mDot.find()){//found all dots
            this.lineToken.put(this.leafNumber(mDot.start()), new Token(TokenValue.DOT));
        }
        while(mSemicolon.find()){//found all semi colon
            this.lineToken.put(this.leafNumber(mSemicolon.start()), new Token(TokenValue.SEMICOLON));
        }
        while(mColon.find()){//found all colon
            this.lineToken.put(this.leafNumber(mColon.start()), new Token(TokenValue.COLON));
        }
        while(mAbsNum.find()){//found all NUMBER 
            this.lineToken.put(this.leafNumber(mAbsNum.start()), new Token(TokenValue.NUMBER, mAbsNum.group(), this.LineNumber));
        }
        while(mEqual.find()){//found all EQUAL
            this.lineToken.put(this.leafNumber(mEqual.start()), new Token(TokenValue.EQUAL));
        }
        while(mPlus.find()){//found all PLUS
            this.lineToken.put(this.leafNumber(mPlus.start()), new Token(TokenValue.PLUS));
        }
        while(mMinus.find()){//found all MINUS
            this.lineToken.put(this.leafNumber(mMinus.start()), new Token(TokenValue.MINUS));
        }
        while(mMul.find()){//found all MUL
            this.lineToken.put(this.leafNumber(mMul.start()), new Token(TokenValue.MUL));
        }
        while(mDiv.find()){//found all DIV
            this.lineToken.put(this.leafNumber(mDiv.start()), new Token(TokenValue.DIV));
        }
        while(mLp.find()){//found all LP
            this.lineToken.put(this.leafNumber(mLp.start()), new Token(TokenValue.LP));
        }
        while(mRp.find()){//found all RP
            this.lineToken.put(this.leafNumber(mRp.start()), new Token(TokenValue.RP));
        }
        while(mGreat.find()){//found all greater
            this.lineToken.put(this.leafNumber(mGreat.start()), new Token(TokenValue.GREAT));
        }
        while(mLess.find()){//found all lesser
            this.lineToken.put(this.leafNumber(mLess.start()), new Token(TokenValue.LESS));
        }
        while(mGeq.find()){//found all great EQUAL
            this.lineToken.put(this.leafNumber(mGeq.start()), new Token(TokenValue.GEQ));
        }
        while(mLeq.find()){//found all  less EQUAL
            this.lineToken.put(this.leafNumber(mLeq.start()), new Token(TokenValue.LEQ));
        }
        while(mDiff.find()){//found all diff
            this.lineToken.put(this.leafNumber(mDiff.start()), new Token(TokenValue.DIFF));
        }
        while(mType.find()){//found all TYPE
            this.lineToken.put(this.leafNumber(mType.start()), new Token(TokenValue.TYPE, mType.group(), this.LineNumber));
        }
        while(mProgram.find()){//found all EQUAL
            this.lineToken.put(this.leafNumber(mProgram.start()), new Token(TokenValue.PROGRAM));
        }
        while(mConst.find()){//found all EQUAL
            this.lineToken.put(this.leafNumber(mConst.start()), new Token(TokenValue.CONST));
        }
        while(mVar.find()){//found all EQUAL
            this.lineToken.put(this.leafNumber(mVar.start()), new Token(TokenValue.VAR));
        }
        while(mBegin.find()){//found all EQUAL
            this.lineToken.put(this.leafNumber(mBegin.start()), new Token(TokenValue.BEGIN));
        }
        while(mLet.find()){//found all EQUAL
            this.lineToken.put(this.leafNumber(mLet.start()), new Token(TokenValue.LET));
        }
        while(mIf.find()){//found all EQUAL
            this.lineToken.put(this.leafNumber(mIf.start()), new Token(TokenValue.IF));
        }
        while(mThen.find()){//found all EQUAL
            this.lineToken.put(this.leafNumber(mThen.start()), new Token(TokenValue.THEN));
        }
        while(mWhile.find()){//found all EQUAL
            this.lineToken.put(this.leafNumber(mWhile.start()), new Token(TokenValue.WHILE));
        }
        while(mDo.find()){//found all EQUAL
            this.lineToken.put(this.leafNumber(mDo.start()), new Token(TokenValue.DO));
        }
        while(mWrite.find()){//found all EQUAL
            this.lineToken.put(this.leafNumber(mWrite.start()), new Token(TokenValue.WRITE));
        }
        while(mRead.find()){//found all EQUAL
            this.lineToken.put(this.leafNumber(mRead.start()), new Token(TokenValue.READ));
        }
        while(mEnd.find()){//found all EQUAL
            this.lineToken.put(this.leafNumber(mEnd.start()), new Token(TokenValue.END));
        }
        return !this.lineToken.isEmpty();
    }

    // not really usefull
    int leafNumber(int indexInLine){
        return indexInLine;
    }





}
