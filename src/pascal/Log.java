/******************************************************************************
 * Copyright (c) 2008. by LAURENT AUTHIER TABANON
 * authier.tabanon@free.fr
 ******************************************************************************
 * Log :
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
 *  Ver         Who             When                What
 *  0.1       laurent         15 juin 2008             Creation
 */

package pascal;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.Vector;

public class Log {
    private final Vector messages;
    private Thread t;
    private boolean running = false;
    private int logged = 0;
    private PrintStream logStream;
    // The private reference to the one and only instance.
    // Let’s eagerly instantiate it here.
    private static Log uniqueInstance = new Log(System.out);

    //private constructor:no client can instantiate a Log object!
    private Log(PrintStream stream){
        this.messages = new Vector();
        this.logStream = stream;
        this.running = true;
        this.t = new Thread(
          new Runnable(){
            public void run(){
                while(running){
                    if(getMessagesNb()>0){
                        String msg;
                        synchronized (messages){
                            msg = (String) messages.remove(0);
                            logStream.println(msg);
                            logged++;
                        }
                    }
                }
            }
        });
        t.start();
        addDayTimeStamp();
    }

    public static Log getUniqueInstance() {
        return uniqueInstance;
    }

    public void setLogStream(PrintStream logStream) {
        this.logStream = logStream;
    }
    
    public void logMessage(String msg){
        synchronized (messages){
              messages.add(msg);
        }
    }

    public void logMessageList(Vector<String> msgLst){
        synchronized (messages){
              messages.addAll(msgLst);
        }
    }
    
    public int getMessagesNb(){
        synchronized (messages){
            return messages.size();
        }
    }

    public PrintStream getLogStream() {
        synchronized (logStream){
            return logStream;
        }
    }
    
    public int logged(){
        return logged;
    }
    
    public void stop() throws InterruptedException{
        running = false;
        t.join();
    }

    private void addDayTimeStamp(){
        Calendar today  = Calendar.getInstance();
        Integer nDay = today.get(Calendar.DAY_OF_MONTH);
        Integer nMonth = today.get(Calendar.MONTH)+1;//0 is junary
        Integer nYear = today.get(Calendar.YEAR);
        Integer nH = today.get(Calendar.HOUR_OF_DAY);
        Integer nM = today.get(Calendar.MINUTE);
        Integer nS = today.get(Calendar.SECOND);
        String day = nDay.toString()+"/"+nMonth.toString()+"/"+nYear.toString();
        String time = nH.toString()+":"+nM.toString()+":"+nS.toString();
        this.logMessage("Log - date : "+day+ " at "+time);
    }

}
