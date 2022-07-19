/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * Malloc.java
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
** Class Malloc : handle memory
 * TODO : add checks for memory's limits
**/
class Malloc implements IAssDef{
        private int dataSeg;
        private int codeSeg;
        private int offData;
        private int offCode;

        Malloc(){
                dataSeg = 0;
                codeSeg = 0;
                offData = 0;
                offCode = 0;
        }
        void setDataSeg(int seg){
                dataSeg = seg;
        }
        void setCodeSeg(int seg){
                codeSeg = seg;
        }
        int getAdData(){
                return dataSeg+offData++;
        }
        int getAdCode(){
                return codeSeg+offCode++;
        }
        int curCodeAd(){
                return codeSeg+offCode;
        }
}
