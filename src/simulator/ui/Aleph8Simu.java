/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * Aleph8Simu.java
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import simulator.file.HexFile;
import simulator.hard.cpualeph8.Aleph8;
import simulator.hard.ram.Ram;

 /**
     * Main class simulating.
     * Create GUI and handle simulated components
  *     TODO : use MVC structure (big job...)     */
public class Aleph8Simu implements ISimuUIServices, ISimuUIConsts{
	// ui components
	private MyMenuBar simuMenu;
	private MemoryView simuMemory;
	private ShowAleph8 simuCpu;
	private ButtonsPanel simuButtons;
	private JFrame frame; // main frame
	//ui dimensions
	private Dimension frameDim;
	// ui logic
	private ExeFunc exeFunc;
	private short uiState;
	// simu components
	private Aleph8 newCpu8;
	private Ram newRam;
	private HexFile hexFile;
	private File fileToSimulate;

	/** setup file to test and go
	 */
	public Aleph8Simu(File fileToTest){
		fileToSimulate = fileToTest;
		createAndShowGUI();
	}

        public  void resetUi(String nameFile){
		uiState = READY;
		newCpu8.Reset();
		newRam.loadRam(hexFile);
		simuButtons.enableButtons(simuButtons.stepNum+simuButtons.runNum+simuButtons.resetNum);
		simuMemory.razAll(nameFile);
		simuCpu.showState();
	}

	public void upDate(File fTosim){
		hexFile.readFile(fTosim);
		resetUi(fTosim.getName());
	}

	//Create the content-pane-to-be.
	private void createContentPane(JFrame frame) {
                // create ui components
                String param  = hexFile.getName();
                simuMemory = new MemoryView(this,param) ;
                simuCpu = new ShowAleph8(this);
                simuButtons = new ButtonsPanel(this);
                //Add ui components to the content pane.
                frame.getContentPane().add(simuMemory, BorderLayout.NORTH);
                frame.getContentPane().add(simuCpu, BorderLayout.CENTER);
                frame.getContentPane().add(simuButtons, BorderLayout.SOUTH);
    	}

	private void createAndShowGUI() {
		//Create simu components
		newRam = new Ram((short)64);
		newCpu8 = new Aleph8(newRam);
		hexFile = new HexFile(fileToSimulate) ;
		newRam.loadRam(hexFile);
		// create functions class
		exeFunc = new ExeFunc(this);
        	//Create and set up the window.
        	frame = new JFrame("Aleph8 simulateur");
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setSize(800, 600);
		frameDim = frame.getSize();
		// resize if necessary
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if(frameDim.height > screenSize.height){
			frameDim.height = screenSize.height;
		}
		if(frameDim.width > screenSize.width){
			frameDim.width = screenSize.width;
		}
		frame.setSize(frameDim);
		frame.setLocation((screenSize.width-frameDim.width)/2, (screenSize.height-frameDim.height)/2);
		ImageIcon icon = new ImageIcon("images/icon2_40.png");
		frame.setIconImage(icon.getImage());
		frame.setResizable(false); // user can't resize
        	//Create and set up the content pane.
		simuMenu = new MyMenuBar();
        	frame.setJMenuBar(simuMenu.CreateMenuBar(this));
		createContentPane(frame);
		frame.pack();
		frame.validate();
		//run thread
		exeFunc.startThread();
		//setup ui logic
		resetUi(fileToSimulate.getName());
        	//Display the window.
        	frame.setVisible(true);
    	}

    public ExeFunc cmdeSimu() {
        return this.exeFunc;
    }

    public ButtonsPanel getButtonsCmdes() {
        return this.simuButtons;
    }

    public Aleph8 getCpuComponent() {
        return this.newCpu8;
    }

    public Dimension getFrameDim() {
        return this.frameDim;
    }

    public Frame getMainFrame() {
        return this.frame;
    }


    public HexFile getHexFile() {
        return this.hexFile;
    }

    public Ram getRamComponent() {
        return this.newRam;
    }

    public short getUiState() {
        return this.uiState;
    }

    public void setUIState(short newState) {
        this.uiState = newState;
    }


    public void resetUI(String fileName) {
        this.resetUi(fileName);
    }

    public ShowAleph8 getUICpu() {
        return this.simuCpu;
    }

    public MemoryView getUIMemory() {
        return this.simuMemory;
    }


	void debug(String s){
		System.out.println(s);
	}
	void debug(int n){
		System.out.println(n);
	}
	void debug(double d){
		System.out.println(d);
	}




}
