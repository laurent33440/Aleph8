/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pascal.icodegeneration.label;

import pascal.parser.SourceParser;

/**
 *
 * @author laurent
 */
public class GenerateLabel {
    private static int count=0;
    
    public static String getIfLabel(){
        return "l"+count+++"_if_"+SourceParser.getStaticCurrentLineNumber();
    }
    
    public static String getWhileLabel(){
        return "l"+count+++"_while_"+SourceParser.getStaticCurrentLineNumber();
    }
    
}
