/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * MemoryView.java
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

package simulator.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
* Class MemoryView :  UI component to show state in RAM
* use two views : one for disassembled code, other for hex values in RAM
*/
class MemoryView extends JPanel implements ISimuUIConsts{
        ISimuUIServices services;
        String titleMemory;
        double height; //main size
        double width;
        DisassembleView disassembleView;
        MemoryDump memoryDump;
        private final String NAMEFONT = "Monospaced";
        private final int NORMSIZEFONT = 11;
        private final int MEDSIZEFONT = 14;
        private final int HIGHSIZEFONT = 18	;

        MemoryView(ISimuUIServices services, String title){
                super(new GridLayout(1,2));  //1 row, 2 columns
                this.services = services;
                height = services.getFrameDim().getHeight();
                width = services.getFrameDim().getWidth();
                height *= 0.6; // 60% of total height
                width *= 0.85; // 85% of total width
                setPreferredSize(new Dimension((int)width,(int)height));
                disassembleView = new DisassembleView();
                memoryDump = new MemoryDump();
                add(disassembleView);
                add(memoryDump);
                razAll(title);
        }

        private String setTitleMemory( String myTitle){
                String title = new String("M�moire - Fichier utilis� : ");
                if (myTitle == "")
                        return (title);
                title += myTitle;
                return (title);
        }
        public void viewAll(int curAd){
                disassembleView.updateDiss(curAd);
                memoryDump.updateDump();
        }
        public void razAll(String title){
                TitledBorder titled;
                titleMemory = setTitleMemory(title);
                titled = BorderFactory.createTitledBorder(titleMemory);
                setBorder(titled);
                disassembleView.razDisassemble();
                memoryDump.razDump();
        }

        /** Class for disassembled view : highlight instruction to be executed
        */
        private class DisassembleView extends JPanel{
                private String titleDisassemble;
                private JTextPane output;
                private JScrollPane scrollPane;
                private int nbLinesWin; // nb lines view in window
                private int pcShown; // current pc shown
                private int pageNb; // nb of pages viewed
                private Vector lineSize; // memorize size of each line VIEW in window
                //consts
                private final String newline = "\n";
                private final String SPACER="\t";
                private final String HEADER ="Adresse   Contenu   Code assembleur";
                private final int MIDLE = 1; //scroll enum
                private final int TOP = 2; //scroll enum
                private final int BOTTOM = 3; //scroll enum

                DisassembleView(){
                        double lheight = height * 0.8; // % of total height
                        double lwidth = width * 0.45; // % of total width
                        //setSize(new Dimension((int)width,(int)height));
                        output = createTextPane();
                        output.setEditable(false);
                        scrollPane = new JScrollPane(output);
                        scrollPane.setPreferredSize(new Dimension((int)lwidth,(int)lheight));
                        JLabel header = new JLabel(HEADER);
                        scrollPane.setColumnHeaderView(header);
                        add(scrollPane);
                        TitledBorder titled;
                        titleDisassemble = new String("Code d�sassembl�");
                        titled = BorderFactory.createTitledBorder(titleDisassemble);
                        setBorder(titled);
                        //compute nb of lines
                        nbLinesWin = computeLineWin((int)lwidth, (int)lheight);
                        lineSize = new Vector();
                        razDisassemble();
                }
                private int computeLineWin(int w, int h) { //return even value
                        //get font size
                        Font font = new Font(NAMEFONT, Font.PLAIN, NORMSIZEFONT);
                        FontMetrics fontMetrics = getFontMetrics(font);
                        int heightNorm = fontMetrics.getHeight();
                        //debug("========font norm size height : "+ heightNorm);
                        scrollPane.getVerticalScrollBar().setUnitIncrement(heightNorm);
                        font = new Font(NAMEFONT, Font.BOLD, HIGHSIZEFONT);
                        fontMetrics = getFontMetrics(font);
                        int heightHigh = fontMetrics.getHeight();
                        //debug("========font high size height : "+ heightHigh);
                        //get header size
                        Dimension dim = scrollPane.getViewport().getViewSize();
                        int headerHeight = (int)(dim.getHeight());
                        //remainder
                        int heightText = h - headerHeight - heightHigh;
                        scrollPane.getViewport().setExtentSize(new Dimension(w,heightText));
                        //set nb of lines
                        Dimension dimext = scrollPane.getViewport().getExtentSize();
                        //debug("1)dim ext view : h, l : "+dimext.getHeight()+" "+dimext.getWidth());
                        dim = scrollPane.getViewport().getViewSize();
                        //debug("1)dim view : h, l : "+dim.getHeight()+" "+dim.getWidth());
                        //Dimension d3 = scrollPane.getViewport().toViewCoordinates(dim);
                        //debug("1)dim view coord : h, l : "+d3.getHeight()+" "+d3.getWidth());
                        int size = (int)(heightText/heightNorm);
                        //debug("nb lines win : "+size);
                        //check if odd value
                        if((size % 2) == 1)
                                size -=1; //get even value
                        return (size);
                }
                // fill text area & scroll
                private void showDiss(int curAd, boolean centered){
                        int	newAd=curAd;
                        int	nbLine = 0;
                        String	line="";
                        String	style="regular";

                        if(centered)
                                nbLine = nbLinesWin/2;
                        else
                                nbLine = nbLinesWin;
                        //adding necessary lines to text
                        for (int nb=0; nb < nbLine; nb++){
                                //adress
                                line += line.format("%02X",newAd);
                                line += SPACER;
                                // * adress
                                line += line.format("%02X",services.getRamComponent().read(newAd));
                                line += SPACER;
                                //disassemble
                                line+=services.getCpuComponent().Disassemble(newAd);
                                line+= newline;
                                Integer intObj = new Integer(line.length());
                                lineSize.addElement(intObj); //update size
                                appendText(output, line, style);
                                newAd=services.getCpuComponent().NextAdress(newAd);
                                line="";
                        }
                        Dimension dim = scrollPane.getViewport().getExtentSize();
                        //debug("dim view_ext : h, l : "+dim.getHeight()+" "+dim.getWidth());
                        dim = scrollPane.getViewport().getViewSize();
                        //debug("dim view : h, l : "+dim.getHeight()+" "+dim.getWidth());
                        if(centered)
                                scrollView(scrollPane, BOTTOM);
                        else
                                scrollView(scrollPane, TOP);
                }
                // highlight curent pc & handle page text
                public void updateDiss(int curAd){
                        int adInPage;
                        //check if curAd in this page (use int divider)
                        if(pageNb==0) //first page is nbLines Height
                                adInPage=((curAd-(curAd % nbLinesWin))/nbLinesWin);
                        else // further pages are nbLines/2 Height
                                adInPage=((curAd-(curAd % (nbLinesWin/2)))/(nbLinesWin/2))-1;
                        //debug("curAd : "+curAd);debug("adInPage : "+adInPage);debug("page: "+pageNb);
                        if(adInPage > pageNb){
                                //debug("rafraichi ecran ");
                                showDiss(curAd, true); //centered PC view
                                pageNb++;
                        }
                        StyledDocument doc = output.getStyledDocument();
                        //debug("pcShown:" +pcShown);
                        Integer size = (Integer)lineSize.elementAt(pcShown);
                        /*debug("dump vector");
                        for(int i = 0; i<lineSize.size();i++){
                                debug("element at: "+i);
                                Integer val1 = (Integer)lineSize.elementAt(i);
                                debug("contenu: " +val1.intValue());
                        }*/
                        // regular style for old pc
                        int offset = offset(pcShown);
                        //debug("offset pcshown: "+offset);
                        doc.setCharacterAttributes(offset,size.intValue(),doc.getStyle("regular"),true);
                        //highlight curAd (newer PC)
                        size = (Integer)lineSize.elementAt(curAd);
                        offset=0;
                        offset = offset(curAd);
                        //debug("offset curAd: "+offset);
                        doc.setCharacterAttributes(offset, size.intValue(),doc.getStyle("largeBoldGreen"),true);
                        pcShown = curAd;
                }
                //compute offset to find line where changes have to be made
                private int offset(int end){
                        int offs = 0;
                        for(int i = 0; i<end;i++){
                                Integer val1 = (Integer)lineSize.elementAt(i);
                                offs +=val1.intValue();
                        }
                        return offs;
                }
                public void razDisassemble(){
                        pageNb = 0;
                        pcShown = 0;
                        lineSize.removeAllElements();
                        removeAllText(output);
                        showDiss(0,false); //initial view
                        updateDiss(0);
                }
                private void scrollView(JScrollPane scrollPane, int type){
                        switch (type) {
                                case TOP : {
                                        scrollPane.getViewport().setViewPosition( new Point(0, 0) );
                                        break;
                                }
                                case MIDLE : {
                                        Dimension dim = scrollPane.getViewport().getExtentSize();
                                        int posY = (int)(dim.getHeight()/2);
                                        //debug("scroll : pos, l : "+posY+" "+dim.getWidth());
                                        scrollPane.getViewport().setViewPosition( new Point(0, posY) );
                                        break;
                                }
                                case BOTTOM : {
                                        Dimension dim = scrollPane.getViewport().getExtentSize();
                                        Dimension dimV = scrollPane.getViewport().getViewSize();
                                        int posY = (int)(dimV.getHeight());
                                        //debug("scroll dim ext: pos, l : "+dim.getHeight()+" "+dim.getWidth());
                                        //debug("scroll dim view: pos, l : "+posY+" "+dimV.getWidth());
                                        scrollPane.getViewport().setViewPosition( new Point(0, posY) );
                                        //Rectangle rect = new Rectangle(0,posY,1,1);
                                        //output.scrollRectToVisible(rect);
                                        //output.revalidate();
                                        break;
                                }
                        }
                }
        }

        /** Class for memory dump : highlight value changed in memory
        */
        private class MemoryDump extends JPanel{
                private String titleDump;
                private JTextPane output;
                private JScrollPane scrollPane;
                private Vector lineSize; //store size of each line of text
                private Vector memCopy; // copy of memory (originals values)
                private int oldOffset; // highlighted index of text
                //consts
                private final String newline = "\n";
                private final String SPACE=" ";
                private final String SPACER="\t";
                private final String HEADER ="Adresse"+SPACE+SPACE+SPACE+SPACE+SPACE+SPACE+SPACE+"Contenu";
                private final int SIZENUMBER = 2; // all numbers are 2 digits
                private final int NBBYLINE = 4; // numbers by line
                private final int SPCBTWNNUM = 2; // space between numbers

                MemoryDump(){
                        double lheight = height * 0.8; // % of total height
                        double lwidth = width * 0.45; // % of total width
                        //setSize(new Dimension((int)width,(int)height));
                        output = createTextPane();
                        output.setEditable(false);
                        scrollPane = new JScrollPane(output);
                        scrollPane.setPreferredSize(new Dimension((int)lwidth,(int)lheight));
                        //scrollPane.setSize(new Dimension((int)width,(int)height));
                        JLabel header = new JLabel(HEADER);
                        scrollPane.setColumnHeaderView(header);
                        add(scrollPane);
                        TitledBorder titled;
                        titleDump = new String("Valeurs en m�moire");
                        titled = BorderFactory.createTitledBorder(titleDump);
                        setBorder(titled);
                        lineSize = new Vector();
                        memCopy = new Vector();
                        razDump();
                }

                private void showMem(){
                        String	line="";
                        String	style="regular";

                        for (int Ad=0; Ad < services.getRamComponent().size();){
                                //adress
                                line += line.format("%02X",Ad);
                                line += SPACER;
                                // * adress
                                for (int n=0 ; n<NBBYLINE ; n++ ){
                                        line += line.format("%02X",services.getRamComponent().read(Ad));
                                        line = line + SPACE + SPACE;
                                        Ad++;
                                }
                                line+= newline;
                                Integer intObj = new Integer(line.length());
                                lineSize.addElement(intObj); //update size
                                appendText(output, line, style);
                                line="";
                        }
                }
                public void razDump(){
                        lineSize.removeAllElements();
                        removeAllText(output);
                        makeMemCopy();
                        oldOffset = -1; // none highlight
                        showMem(); //initial view
                }
                private void makeMemCopy(){
                        Integer intObj;
                        memCopy.removeAllElements();
                        //copy memory
                        for (int Ad=0; Ad < services.getRamComponent().size();Ad++){
                                intObj =  (Integer)(services.getRamComponent().read(Ad));
                                memCopy.addElement(intObj);
                        }
                }
                private void updateDump(){
                        int chgAtAd;
                        StyledDocument doc = output.getStyledDocument();
                        if(oldOffset >= 0)//regular format for old higlight
                                doc.setCharacterAttributes(oldOffset, 2,doc.getStyle("regular"),true);
                        if ((chgAtAd = doCompare())>=0){//find change(if any)
                                //find offset in text area
                                int offset = getOffset(chgAtAd);
                                //remove old val
                                try {
                                        doc.remove(offset, SIZENUMBER);
                                }
                                catch (BadLocationException ble) {}
                                //get new val & highlight
                                try {
                                        Integer val = (Integer)(services.getRamComponent().read(chgAtAd));
                                        String strVal = "";
                                        strVal = strVal.format("%02X",val.intValue());
                                        doc.insertString(offset,strVal, doc.getStyle("normalBoldCyan"));
                                }
                                catch (BadLocationException ble) {}
                                oldOffset = offset;
                                //make new image of memory
                                makeMemCopy();
                        }else
                                oldOffset = -1; //no change in memory
                }
                private int doCompare(){
                        Integer inRam, inCopy;
                        for (int Ad=0; Ad < services.getRamComponent().size();Ad++){
                                inRam = (Integer)(services.getRamComponent().read(Ad));
                                inCopy = (Integer)(memCopy.elementAt(Ad));
                                if (!inCopy.equals(inRam))
                                        return Ad;
                        }
                        return -1;
                }
                private int getOffset(int Ad){
                        Integer val1;
                        int lineNb = (Ad-(Ad % NBBYLINE))/NBBYLINE; //get line nb of txt where to highlight
                        int offs = 0;
                        for(int i = 0; i<lineNb;i++){ //get all lines before
                                val1 = (Integer)lineSize.elementAt(i);
                                offs +=val1.intValue();
                        }
                        offs =offs + SIZENUMBER + SPACER.length(); // goto values
                        offs = offs+((Ad%NBBYLINE)*(SIZENUMBER+SPCBTWNNUM));//goto the value in line
                        return offs;
                }
        }
        // tools for text
        private JTextPane createTextPane() {
                JTextPane textPane = new JTextPane();
                StyledDocument doc = textPane.getStyledDocument();
                addStylesToDocument(doc);
                return textPane;
        }
        //Initialize some styles.
        protected void addStylesToDocument(StyledDocument doc) {
                Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

                Style regular = doc.addStyle("regular", def);
                StyleConstants.setFontFamily(def, NAMEFONT);
                StyleConstants.setFontSize(regular, NORMSIZEFONT);

                Style highlightDiss = doc.addStyle("largeBoldGreen", regular);
                StyleConstants.setFontSize(highlightDiss, HIGHSIZEFONT);
                StyleConstants.setBold(highlightDiss, true);
                StyleConstants.setBackground(highlightDiss, Color.green);

                Style highlightMem = doc.addStyle("normalBoldCyan", regular);
                StyleConstants.setFontSize(highlightMem, NORMSIZEFONT);
                StyleConstants.setBold(highlightMem, true);
                StyleConstants.setBackground(highlightMem, Color.cyan);
        }

        private void appendText(JTextPane text,String line, String style){
                StyledDocument doc = text.getStyledDocument();
                try {
                        doc.insertString(doc.getLength(), line, doc.getStyle(style));
                }
                catch (BadLocationException ble) {
                        System.err.println("Couldn't insert initial text into text pane.");
                }
        }

        private void removeAllText(JTextPane text){
                text.setText("");
        }
}

