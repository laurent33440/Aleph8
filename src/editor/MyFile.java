/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * MyFile.java
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
** class MyFile : file use in editor
*/
public class MyFile {
    private IEditorServices services;
        private File file;
        private boolean asName;
        MyFile(IEditorServices services){
            this.services = services;
            newFile("Nouveau fichier");
        }
        void readFile(File fileToRead){
            file = fileToRead;
            asName = true;
            String ligne;
            StringBuffer buf = new StringBuffer();
            try {
                services.editViewClearAll();
                BufferedReader in = new BufferedReader(
                new FileReader(file) );
                while ((ligne = in.readLine()) != null)
                        buf.append(ligne + (char) '\n');
                String code = buf.toString();
                services.editViewShowText(code);
            }
            catch(FileNotFoundException e) {}
            catch(IOException e) {}
        }
        void writeFile(File fileToWrite){
                if (fileToWrite != null)
                {
                        file = fileToWrite;
                        asName = true;
                }
                try {
                        BufferedWriter out = new BufferedWriter(new FileWriter(file) );
                        out.write(services.editViewGetText());
                        out.close();
                }
                catch(FileNotFoundException e) {
                        System.out.println("file not found exception");
                }
                catch(IOException e) {
                        System.out.println("file IO exception");
                }
        }
        void newFile(String name){
                file = new File(name);
                asName = false;
        }
        boolean fileAsName(){
                return asName;
        }
        File getFile(){
                return(file);
        }
        
        String getFileName(){
                return file.getName();
        }
}


