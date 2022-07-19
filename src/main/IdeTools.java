/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * IdeTools.java
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
 *  0.1         prog         27 oct. 2010                 Creation
 */

package main;

import java.io.File;

import assembler.Assemble;
import simulator.ui.Aleph8Simu;

/**
 *
 */
public class IdeTools implements IActionsIde {
    private Assemble assemble;
    private Aleph8Simu aleph8Simu;
    private boolean simuAlive;

    IdeTools(){
            simuAlive=false;
    }
    
    @Override
    public boolean doAssemble(File asmFile){
            assemble = new Assemble(asmFile);
            return !assemble.getAnyError().existError();
    }
    
    @Override
    public void doSimule(File objFile){
            aleph8Simu = new Aleph8Simu(objFile);
            simuAlive = true;
    }
    
    @Override
    public void updateSim(File newSim){
            aleph8Simu.upDate(newSim);
    }

    @Override
    public boolean isSimuAlive() {
        return simuAlive;
    }

}
