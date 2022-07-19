/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * ExeFunc.java
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

/**
*  functions class : handle thread and all functions
**/
class ExeFunc implements Runnable, ISimuUIConsts{
        ISimuUIServices services;
        private volatile Thread exeThread;// exe simu thread
        private volatile boolean threadSuspended;

        ExeFunc(ISimuUIServices services){
                this.services = services;
                exeThread = new Thread(this, "exeSimu");
                threadSuspended = true;
        }
        void startThread(){
                if(exeThread != null)
                        exeThread.start();
        }
        synchronized void suspendThread(){
                threadSuspended = true;
                notify();
        }
        synchronized void resumeThread(){
                threadSuspended = false;
                notify();
        }
        void threadState(){
                Thread.State stateTh = exeThread.getState();
                System.out.println("Thread state");
                System.out.println(stateTh);
        }
        public void run(){ // exe thread
                if (services.getUiState() != ERROR)
                {
                        Thread thisThread = Thread.currentThread();
                        //String name = thisThread.getName();
                        //System.out.println("Thread : "+name);
                        while (exeThread==thisThread)
                        {
                                try{
                                        exeThread.sleep(50);
                                        //System.out.println("Thread try");
                                        synchronized(this) {
                                                while (threadSuspended && exeThread==thisThread)
                                                        wait();
                                        }
                                        services.getCpuComponent().Step();
                                        services.getUIMemory().viewAll(services.getCpuComponent().GetPc());
                                        services.getUICpu().showState();
                                        if (services.getUiState() == STEP)
                                                threadSuspended = true; //one shot
                                }
                                catch(InterruptedException ie){
                                        System.out.println("Thread interrupted");
                                }
                        }
                }
        }
        public synchronized void stop() {
                exeThread = null;
                notify();
        }
        // ui functions
        public void stepProg(){
                services.setUIState(STEP);
                resumeThread();
        }
        public void runProg(){
                services.setUIState(RUN);
                services.getButtonsCmdes().enableButtons(BUTTON_STOP_NUM+BUTTON_RESET_NUM);
                resumeThread();
        }
        public void stopProg(){
                suspendThread();
                services.setUIState(READY);
                services.getButtonsCmdes().enableButtons(BUTTON_STEP_NUM+BUTTON_RUN_NUM+BUTTON_RESET_NUM);
        }
        public void reset(){
                suspendThread();
                services.resetUI(services.getHexFile().getName());
        }
}

