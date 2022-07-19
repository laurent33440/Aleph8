/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * HexFile.java
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

package simulator.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
** classe HexFile
*/
public class HexFile{
	private
	String nameFile;
	int [] hexData;
	int length;

	public HexFile (File file) {
		length = 64; // 64 bytes long
		readFile(file);
	}
	public String getName(){
		return nameFile;
	}
	public int fileLength(){
		return (length & 0xFF);
	}
	public void readFile(File file){
		if(file == null){
			nameFile = "test_simulateur.o8";
			fileTest();
		}
		else{
			nameFile = file.getName();
			try {
				FileInputStream in = new FileInputStream(file) ;
				hexData = new int[length];
				int nByte = in.available();
				while ((nByte) >0){
					int ad = in.read(); //first ad
					int op = in.read();
					hexData[ad] = op; //second opcode
					nByte-=2;
				}
        			in.close();
      			}
			catch(FileNotFoundException e) {
				System.out.println("Fichier binaire � lire introuvable !");
			}
      			catch(IOException e) {
				System.out.println("impossible d'acc�der au fichier binaire !");
			}
		}
	}
	public int getData(int index){
		if (index <= length)
			return (int)(hexData[index] & 0xFF);
		else
			return 0;
	}
	private void fileTest(){
		length = 64;
		hexData = new int[length];
		hexData[0x3B] = (byte)1; //data
		hexData[0x3D] = (byte)5;
		hexData[0x3E] = (byte)0;
		hexData[0x3F] = (byte)0xFF;
		hexData[0] = (byte)0x3F; // 0		NOR 3F
		hexData[1] = (byte)0x7D; // 1		ADD 3D
		hexData[2] = (byte)0x7D; // 2		ADD 3D
		hexData[3] = (byte)0xBC; // 3		STA 3C
		hexData[4] = (byte)0x3F; // 4		NOR 3F
		hexData[5] = (byte)0x7D; // 5		ADD 3D
		hexData[6] = (byte)0x3E; // 6		NOR 3E
		hexData[7] = (byte)0x7B; // 7		ADD 3B
		hexData[8] = (byte)0x7C; // 8 add	ADD 3C
		hexData[9] = (byte)0xC8; // 9		JCC add
		hexData[10]= (byte)0xC8; // A		JCC add
		hexData[11]= (byte)0x3F; // B		NOR 3F
		hexData[12]= (byte)0xCC; // C hlt	JCC hlt
	}
}
