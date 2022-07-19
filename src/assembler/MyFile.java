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
 *  0.1         prog         28 oct. 2010                 Creation
 */

package assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
** class MyFile : file use in assembler we assume that file is in current directory (*.aph8)
** binary file as the same name with (*.o8) extension.
*/
class MyFile {
        private IAssemblerServices services;
        private File file;

        MyFile(IAssemblerServices services, File asmFile) throws MyException{
                this.services = services;
                if (asmFile == null)
                        throw new MyException("Pas de fichier � assembler !");
                else{
                        file = asmFile;
                }
        }
        String readFile() {
                String ligne;
                StringBuffer buf = new StringBuffer();
                try {
                        BufferedReader in = new BufferedReader(
                        new FileReader(file) );
                        while ((ligne = in.readLine()) != null)
                                buf.append(ligne + (char) '\n');
                        String code = buf.toString();
                        return(code);
                }
                catch(FileNotFoundException e) {
                         MyException me = new MyException("Fichier assembleur introuvable ! ");
                }
                catch(IOException e) {
                        MyException me = new MyException("Erreur de lecture du fichier assembleur !");
                }
                return"";
        }
        void writeFile() {
                try {
                        String title = file.getAbsolutePath();
                        int idx = title.indexOf(".");
                        String name = title.substring(0,idx)+".o8";
                        file = new File(name);
                        FileOutputStream out = new FileOutputStream(file) ;
                        byte [] hexa = services.getAleph8Code().getByteArray();
                        out.write(hexa);
                        out.close();
                }
                catch(FileNotFoundException e) {
                        MyException me = new MyException("Fichier binaire � ecrire introuvable !");
                }
                catch(IOException e) {
                        MyException me = new MyException("impossible de cr�er le fichier binaire !");
                }
        }
}
