/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * Aleph8Code.java
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

import java.io.ByteArrayOutputStream;

/**
** Class Aleph8Code : generate hex for aleph8.
** The format is : adress1, data1, adress2, data2, ...
** All datas are byte long
**/
class Aleph8Code implements IAssDef{
        private final int OP_NOR=0, OP_ADD=0x40, OP_STA=0x80, OP_JCC=0xC0;
        private ByteArrayOutputStream hexData; //

        Aleph8Code(){
                hexData = new ByteArrayOutputStream();
        }
        // generate opcode
        void gen(int ad, int inst, int operand){
                int adr=0, opcode=0;
                switch (inst){
                        case NOR:
                                opcode =(int)(( OP_NOR|operand)& 0xFF);
                        break;
                        case ADD:
                                opcode =(int)(( OP_ADD|operand)& 0xFF);
                        break;
                        case STA:
                                opcode =(int)(( OP_STA|operand)& 0xFF);
                        break;
                        case JCC:
                                opcode =(int)(( OP_JCC|operand)& 0xFF);
                        break;
                }
                adr = (int)(ad & 0xFF);
                hexData.write(adr);//debug("hex adr : ");debug(adr);
                hexData.write(opcode);//debug("hex opcode : ");debug(opcode);
        }
        void genData(int ad, int val)  {
                int adr = (int)(ad & 0xFF);
                int value = (int)(val & 0xFF);
                hexData.write(adr);//debug("hex data adr : ");debug(adr);
                hexData.write(value);//debug("hex data val : ");debug(value);
        }
        byte[] getByteArray(){
                return hexData.toByteArray();
        }
}
