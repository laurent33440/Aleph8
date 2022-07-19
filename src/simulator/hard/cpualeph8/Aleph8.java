/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * Aleph8.java
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

package simulator.hard.cpualeph8;

import simulator.hard.ram.Ram;

/*
** CPU class : use RAM class
*/
public class Aleph8{
    //instructions
    private static final short NOR = 0;
    private static final short ADD = 1;
    private static final short STA = 2;
    private static final short JCC = 3;
    // CPU State : we use int because each signed type use 32 bits format in JVM
    private int Accu;
    private int PC;
    private boolean CFlag;
    private Ram useRam;
    // Statistics
    private short NumInstructions;
    private short NumCycles;
    //consts
    private static final short RAMSIZE = 64;
    private static final short NoBranch=0;
    private static final short BranchTaken=1;
    private static final short BranchNotTaken=2;
    private static final short BranchAlways=3;

    // new CPU
    public Aleph8(Ram memory)
    {
            useRam = memory;
            Reset();
    }

    public void Reset()
    {
            PC=0;
            CFlag=false;
            Accu=0;
            NumInstructions=0;
            NumCycles=0;
    }

    public void Step()
    {
            int IR1,ADR1;

            IR1=(int)(useRam.read(PC++));
            ADR1 = (int) (IR1 & (RAMSIZE-1));
            NumInstructions++;
            NumCycles++;

            switch (IR1>>6)
            {
                    case NOR:
                    {
                            int res=(int)(((useRam.read(ADR1)|Accu)^0xff) & 0xFF);
                            Accu=res;
                            NumCycles++;
                    }
                    break;
                    case ADD:
                    {
                            int res=(int)((useRam.read(ADR1)+Accu) & 0x1FF); // take care of carry 8'th bit
                            if (res>>8 == 1)
                                    CFlag=true;	// carry from the add!
                            Accu=res & 0xFF; // get only value not carry
                            NumCycles++;
                    }
                    break;
                    case STA:
                    {
                            useRam.write(ADR1, Accu);
                            NumCycles++;
                    }
                    break;
                    case JCC:
                    {
                            if (!CFlag)
                            {
                                    PC=ADR1;
                            }
                            else
                            {
                                    NumCycles++;	// branch not taken
                            }
                            CFlag=false;
                    }
                    break;
            }

            PC&=(RAMSIZE-1);
    }

    public String Disassemble(int adress)
    {
            int IR1,ADR1;
            String str="";

            adress&=(RAMSIZE-1);
            IR1=(int)useRam.read(adress);
            ADR1=(int)(IR1&(RAMSIZE-1));
            switch (IR1>>6)
            {
                    case NOR:
                    {
                            //str=str.format("NOR %2X \t\t[%2X]",ADR1,useRam.read(ADR1));
                            str=str.format("NOR %2X",ADR1);
                    }
                    break;
                    case ADD:
                    {
                            //str=str.format("ADD %2X \t\t[%2X]",ADR1,useRam.read(ADR1));
                            str=str.format("ADD %2X",ADR1);
                    }
                    break;
                    case STA:
                    {
                            str=str.format("STA %2X",ADR1);
                    }
                    break;
                    case JCC:
                    {
                            str=str.format("JCC %2X",ADR1);
                    }
                    break;
            }
            return(str);
    }

    public int NextAdress(int adress)
    {
            return ((int)((adress+1)&(RAMSIZE-1)) & 0xFF);
    }

    public int PreviousAdress(int adress)
    {
            return ((int)((adress-1)&(RAMSIZE-1)) & 0xFF);
    }

    // returns branch target, if instruction is branch. Otherwhise 0xffffffff
    public int BranchTarget(int adress)
    {
            int IR;
            IR=(int)useRam.read(adress);
            if ((IR&0xc0)!=0xc0)
                    return 0xffffffff;
            return ((int)(IR&(RAMSIZE-1)) & 0xFF);
    }

    public int EvaluateBranch(int adress)
    {
            int IR;
            IR=(int)useRam.read(adress);
            if ((IR&0xC0)!=0xc0)
            {
                    return NoBranch;
            }
            else
            {
                    if (CFlag)
                            return BranchNotTaken;
                    else
                            return BranchTaken;
            }
    }

    public int GetPc()
    {
            return ((PC) & 0xFF);
    }

    public int GetAcc()
    {
            return((Accu & 0xFF));
    }

    public boolean GetCarry()
    {
            return(CFlag);
    }
}

