/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * EditView.java
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
 *  0.1         prog         26 oct. 2010                 Creation
 */

package editor;


import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
* Class EditView :  UI component to edit code
*/
public class EditView extends JPanel implements IEditorConsts{
    private IEditorServices services;
        //private String titleProg;
        private JTextPane editZone;
        private JScrollPane scrollPane;
        private AbstractDocument doc;
        //consts
        //private String newline = "\n";

        EditView(IEditorServices services, String title, int rows, int columns){
            this.services = services;
            double height = services.getFrameDim().getHeight();
            double width = services.getFrameDim().getWidth();
            height *= 0.75; // 75% of total height
            width *= 0.95; // 95% of total width
            editZone = createTextPane();
            editZone.setEditable(true);
            scrollPane = new JScrollPane(editZone);
            scrollPane.setPreferredSize(new Dimension((int)width,(int)height));
            add(scrollPane);
            setTitle("");
            //starting watching changes
            StyledDocument styledDoc = editZone.getStyledDocument();
            if (styledDoc instanceof AbstractDocument) {
                    doc = (AbstractDocument)styledDoc;
            } else {
                    System.err.println("Text pane's document isn't an AbstractDocument!");
                    System.exit(-1);
            }
            doc.addDocumentListener(new MyDocumentListener());
            editZone.addCaretListener(new MyCaretListener());
        }

        //This one listens for any changes to the document.
        protected class MyDocumentListener implements DocumentListener{
            public void insertUpdate(DocumentEvent e) {
                    if(services.getUIState() != NEW)// used when changed made by user not file load
                            services.setUIState(UNSAFE);
                    services.upDateStatusLine();
                    //displayEditInfo(e);
                    //check end word (last wasn't an alpha) and build word
                    //call check of word : insts, directives, val (bin, dec, hex)
                    //remove word and replace by styled one
            }
            public void removeUpdate(DocumentEvent e) {
                    //displayEditInfo(e);
                    services.setUIState(UNSAFE);
                    services.upDateStatusLine();
            }
            public void changedUpdate(DocumentEvent e) {
                    //displayEditInfo(e);
                    services.setUIState(UNSAFE);
                    services.upDateStatusLine();
            }
            private void displayEditInfo(DocumentEvent e) {
                    Document document = (Document)e.getDocument();
                    int changeLength = e.getLength();
                    String txt = e.getType().toString() + ": " +changeLength + " character" +
                    ((changeLength == 1) ? ". " : "s. ") +" Text length = " + document.getLength() +
                    "." + NEWLINE;
                    System.out.println("doc event : ");
                    System.out.println(txt);
            }
        }

         //This listens for and reports caret movements.
        protected class MyCaretListener implements CaretListener {
                // platform dependent parameters
                private boolean notUnix = false;
                private String nlf; // NL
                private int lenNlf;
                // line parameters
                private int pos; // real pos of NL
                private int mpos; // modified pos of NL
                private int lineNum;
                private boolean stop;
                //text area
                private String txt;

                public MyCaretListener() {
                        nlf = System.getProperty("line.separator");
                        lenNlf = nlf.length();
                        if (lenNlf > 1)
                                notUnix = true;
                }
                public void caretUpdate(CaretEvent e) {
                        int lineNum = getLineNum(e.getDot());
                        services.setLineNumStatusLine(lineNum);
                        services.upDateStatusLine();
                }
                // adapt if necessary pos of NL found in respect of position of caret listener (user view)
                protected int getLineNum(final int dot) {
                        pos=0; // real pos of NL
                        mpos = 0; // modified pos of NL
                        lineNum = 0;
                        stop = false;
                        txt = editZone.getText();
                        do{
                                pos = txt.indexOf(nlf,pos);
                                mpos = pos;
                                if (notUnix)
                                        mpos -=lineNum;	// particular case of Win NL's length of 2 Chars
                                if (mpos <= -1){ // NL not found we are at EOT
                                        stop =true;
                                }
                                else 	if (mpos < dot){ // cursor is on the next lines
                                                pos++; // next NL
                                        }
                                        else 	if (mpos > dot){ // we are in the line
                                                        stop=true;
                                                }
                                                else 	if (mpos == dot){ // cursor at EOL
                                                                stop = true;
                                                }
                                lineNum++;
                        }
                        while (!stop);
                        return lineNum;
                }
        }

        protected void setTitle( String myTitle){
                String title = new String("Edition - Fichier utilis� : ");
                if (!myTitle.equals(""))
                        title += myTitle;
                TitledBorder titled;
                titled = BorderFactory.createTitledBorder(title);
                setBorder(titled);
        }

        private JTextPane createTextPane() {
                JTextPane textPane = new JTextPane();
                StyledDocument doc = textPane.getStyledDocument();
                addStylesToDocument(doc);
                return textPane;
        }

        protected void addStylesToDocument(StyledDocument doc) {
        //Initialize some styles.
                Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

                Style regular = doc.addStyle("regular", def);
                StyleConstants.setFontFamily(def, "SansSerif");

                Style s = doc.addStyle("italic", regular);
                StyleConstants.setItalic(s, true);

                s = doc.addStyle("bold", regular);
                StyleConstants.setBold(s, true);

                s = doc.addStyle("small", regular);
                StyleConstants.setFontSize(s, 10);

                s = doc.addStyle("large", regular);
                StyleConstants.setFontSize(s, 16);

                s = doc.addStyle("largeBold", regular);
                StyleConstants.setFontSize(s, 16);
                StyleConstants.setBold(s, true);

                s = doc.addStyle("icon", regular);
                StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
                ImageIcon pigIcon = services.createImageIcon("images/open_i.png");
                if (pigIcon != null) {
                        StyleConstants.setIcon(s, pigIcon);
                }
        }

        private void appendText(String line, String style){
                StyledDocument doc = editZone.getStyledDocument();
                addStylesToDocument(doc);
                try {
                        doc.insertString(doc.getLength(), line, doc.getStyle(style));
                }
                catch (BadLocationException ble) {
                        System.err.println("Couldn't insert initial text into text pane.");
                }
        }

        protected void cut(){
                editZone.cut();
        }
        protected void copy(){
                editZone.copy();
        }
        protected void paste(){
                editZone.paste();
        }
        protected void slctAll(){
                editZone.selectAll();
        }
        protected void clearAll(){
                editZone.setText("");
        }
        protected boolean isSelectedText(){
                if (editZone.getSelectedText() == null)
                        return false;
                else
                        return true;
        }
        protected String getCode(){
                return editZone.getText();
        }
        protected void viewLoadCode(String code){
                appendText(code, "normal");
        }
}


