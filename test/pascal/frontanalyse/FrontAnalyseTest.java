/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pascal.frontanalyse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import pascal.PascalException;
import pascal.parser.ISourceParser;
import pascal.parser.SourceParser;
import pascal.srcInstructions.SrcInstructionsList;

/**
 *
 * @author prog
 */
public class FrontAnalyseTest {
    final private String sourceProgram = "program srcPrg ; \n" +
            "                             begin \n" +
            "                               \t; \n" +
            "                             end.";
    final private String sourceProgramEmpty = "program srcPrg ; \n" +
            "                             begin \n" +
            "                                \n" +
            "                             end.";
    final private String sourceConstInst = "program srcInst; \n" +
            "                               const\n" +
            "                                   \t myConst =10 ; \n" +
            "                               begin \n" +
            "                                    ; \n" +
            "                               end.";
    final private String sourceMultipleConstsVarsInst = "program srcInst; " +
            "                               \nconst\n" +
            "                                   \t myConst =10 ; \n" +
            "                                   \t myconst2 = 20 ; \n" +
            "                                   \t myconst3 = 30;\n" +
            "                               var\n" +
            "                                   \t myVar : byte;\n" +
            "                                   \t myvar2 : byte; \n" +
            "                                   \t myvar3 : byte ;\n" +
            "                               begin \n " +
            "                                   myVar := 1967; \n" +
            "                               end.";
    final private String sourceConstsInstVoid = "program srcInst; \n" +
            "                                const\n" +
            "                                   \t myConst =10 ; \n" +
            "                                   \t myconst2 = 20 ; \n" +
            "                                   \t myconst3 = 30;\n  " +
            "                                begin \n " +
            "                                       ; ; ; ; ;;; \n" +
            "                                end.";
    final private String sourceConstVarInst = "program srcInst; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n ; \nend.";
    final private String sourceInst = "program srcInst; \n  begin\n ; \nend.";
    final private String srcLet = "program srcLet; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t myVar := myConst ; \nend.";
    final private String srcLetKo1 = "program srcLetKo1; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t unKnownVar := myConst ; \nend.";
    final private String srcLetKo2 = "program srcLetKo2; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t unKnownVar :  myConst ; \nend.";
    final private String srcLetKo3 = "program srcLetKo3; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t myConst :=  myVar ; \nend.";
    final private String srcIf = "program srcIf; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t if myVar = myConst then myVar := 1 ; \nend.";
    final private String srcIf2 = "program srcIf2; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t if myVar = myConst then myVar := 1; \n  ; ; ; ; \nend.";
    final private String srcIf3 = "program srcIf3; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t if myVar = myConst then myVar := 1 ;\n  myVar := 10 ; \nend.";
    final private String cascadeIf1 = "program cascadeIf1; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t if myVar = myConst then if myVar <> myConst then myVar := 1 ; \nend.";
    final private String cascadeIf2 = "program cascadeIf2; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t if myVar = myConst then \n if myVar <> 2 then myVar := 1 ; \n myVar := 2; \nend.";
    final private String srcIfKo1 = "program srcIfKo1; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t if myVar !! myConst then myVar := 1 ; \nend.";
    final private String srcIfKo2 = "program srcIfKo2; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t if myVar <> myConst  myVar := 1 ; \nend.";
    final private String srcWhile = "program srcIf; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t while myVar = myConst do myVar := myVar + 1 ; \nend.";
    final private String srcWhileKo1 = "program srcWhileKo1; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t while myVar !! myConst do myVar := 1 ; \nend.";
    final private String srcWhileKo2 = "program srcIfKo2; \nconst\n\t myConst =10 ; \nvar\n\t myVar : byte;\n  begin \n \t while myVar <> myConst  myVar := 1 ; \nend.";
    final private String sourceProgKo = "program srcFail \n ";
    
    
    private ISourceParser sp;

    public FrontAnalyseTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFrontAnalyseProgram() throws PascalException{
        System.out.println("testFrontAnalyse_program");
            this.sp = new SourceParser(sourceProgram);
            FrontAnalyse fa = new FrontAnalyse(sp);
    }
    
    @Ignore @Test(expected=PascalException.class)
    public void testFrontAnalyseProgramEmpty() throws PascalException{
        System.out.println("testFrontAnalyse_program_empty");
            this.sp = new SourceParser(sourceProgramEmpty);
            FrontAnalyse fa = new FrontAnalyse(sp);
    }

    @Test 
    public void testFrontAnalyseConst() throws PascalException{
        System.out.println("testFrontAnalyse_const_inst_void");
            this.sp = new SourceParser(sourceConstInst);
            FrontAnalyse fa = new FrontAnalyse(sp);
    }
    
    @Test 
    public void testFrontAnalyseConsts() throws PascalException{
        System.out.println("testFrontAnalyse_multiple_const_inst_void");
       
            this.sp = new SourceParser(sourceConstsInstVoid);
            FrontAnalyse fa = new FrontAnalyse(sp);
            
    }

    @Test 
    public void testFrontAnalyseConstVarInstVoid() throws PascalException{
        System.out.println("testFrontAnalyse_const_var_inst_void");
            this.sp = new SourceParser(sourceConstVarInst);
            FrontAnalyse fa = new FrontAnalyse(sp);
            

    }

    @Test
    public void testFrontAnalyseMultipleConstVarInstVoid() throws PascalException{
        System.out.println("testFrontAnalyse_multiple_const_var_inst_void");
            this.sp = new SourceParser(sourceMultipleConstsVarsInst);
            FrontAnalyse fa = new FrontAnalyse(sp);
            

    }

    @Test
    public void testFrontAnalyseOnlyInstVoid() throws PascalException{
        System.out.println("testFrontAnalyse_only_inst_void");
            this.sp = new SourceParser(sourceInst);
            FrontAnalyse fa = new FrontAnalyse(sp);

    }

    @Test
    public void testInstLet() throws PascalException{
        System.out.println("test_inst_let");
            this.sp = new SourceParser(srcLet);
            FrontAnalyse fa = new FrontAnalyse(sp);

    }
    //@Ignore
    @Test//(expected=PascalException.class)
    public void testInstLetKo1() throws PascalException{
        System.out.println("test_inst_letKo1");
            this.sp = new SourceParser(srcLetKo1);
            FrontAnalyse fa = new FrontAnalyse(sp);

    }
    //@Ignore
    @Test//(expected=PascalException.class)
    public void testInstLetKo2() throws PascalException{
        System.out.println("test_inst_letKo2");
            this.sp = new SourceParser(srcLetKo2);
            FrontAnalyse fa = new FrontAnalyse(sp);

    }
    //@Ignore
    @Test//(expected=PascalException.class)
    public void testInstLetKo3() throws PascalException{
        System.out.println("test_inst_letKo3");
            this.sp = new SourceParser(srcLetKo3);
            FrontAnalyse fa = new FrontAnalyse(sp);
    }
    
    @Test
    public void testInstIf() throws PascalException{
        System.out.println("test_inst_if");
            this.sp = new SourceParser(srcIf);
            FrontAnalyse fa = new FrontAnalyse(sp);

    }
    
    @Test
    public void testInstIf2() throws PascalException{
        System.out.println("test_inst_if2");
            this.sp = new SourceParser(srcIf2);
            FrontAnalyse fa = new FrontAnalyse(sp);

    }
    
    @Test
    public void testInstIf3() throws PascalException{
        System.out.println("test_inst_if3");
            this.sp = new SourceParser(srcIf3);
            FrontAnalyse fa = new FrontAnalyse(sp);

    }
    
    @Test
    public void testCascadeInstIf1() throws PascalException{
        System.out.println("test_cascade_inst_if1");
            this.sp = new SourceParser(cascadeIf1);
            FrontAnalyse fa = new FrontAnalyse(sp);

    }
    
     @Test
    public void testCascadeInstIf2() throws PascalException{
        System.out.println("test_cascade_inst_if2");
            this.sp = new SourceParser(cascadeIf2);
            FrontAnalyse fa = new FrontAnalyse(sp);

    }
    
    
    @Test//(expected=PascalException.class)
    public void testInstIfKo1() throws PascalException{
        System.out.println("test_inst_IfKo1");
            this.sp = new SourceParser(srcIfKo1);
            FrontAnalyse fa = new FrontAnalyse(sp);

    }
    
    @Test//(expected=PascalException.class)
    public void testInstIfKo2() throws PascalException{
        System.out.println("test_inst_IfKo2");
            this.sp = new SourceParser(srcIfKo2);
            FrontAnalyse fa = new FrontAnalyse(sp);

    }
    
    @Test
    public void testInstWhile() throws PascalException{
        System.out.println("test_inst_While");
            this.sp = new SourceParser(srcWhile);
            FrontAnalyse fa = new FrontAnalyse(sp);

    }
    
    @Test//(expected=PascalException.class)
    public void testInstWhileKo1() throws PascalException{
        System.out.println("test_inst_WhileKo1");
            this.sp = new SourceParser(srcWhileKo1);
            FrontAnalyse fa = new FrontAnalyse(sp);

    }
    
    @Test//(expected=PascalException.class)
    public void testInstWhileKo2() throws PascalException{
        System.out.println("test_inst_WhileKo2");
            this.sp = new SourceParser(srcWhileKo2);
            FrontAnalyse fa = new FrontAnalyse(sp);
    }

    @Ignore @Test(expected=PascalException.class)
    public void testFrontAnalyseProgramKo() throws PascalException{
        System.out.println("testFrontAnalyse_programKo");
            this.sp = new SourceParser(sourceProgKo);
            FrontAnalyse fa = new FrontAnalyse(sp);
    }
    
    @Test
    public void testFrontAnalyseGetSourceInstructionsListsMethod() throws PascalException{
        System.out.println("testFrontAnalyse_SourceInstructionList_one_instruction");
        this.sp = new SourceParser(srcLet);
        FrontAnalyse fa = new FrontAnalyse(sp);
        SrcInstructionsList sil = fa.getSourceInstructionsList();
        assertNotNull(sil);
    }
    
    

//    @Test
//    public void testFrontAnalyseBlock1() {
//        System.out.println("testFrontAnalyse_block_inst");
//        try{
//            this.sp = new SourceParser(sourceInst);
//            FrontAnalyse fa = new FrontAnalyse(sp);
//            assertTrue(true);
//
//        }catch(PascalException pe){
//            assertTrue(false);
//        }
//    }

}