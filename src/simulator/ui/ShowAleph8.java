/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * ShowAleph8.java
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
* Class ShowAleph8 : display internal states of cpu
*/
class ShowAleph8 extends JPanel implements ISimuUIConsts{
        ISimuUIServices services;
        String strPC, strACC, strCy;
        Font fontVal;
        //drawing
        int wUGA, wUAL, hAll;
        int orgwUGA, orgwUAL, orghAll;
        final double WRECT = 0.35; //% of total width
        final double HRECT = 0.6; //% of total height

        ShowAleph8(ISimuUIServices services){
                this.services = services;
                double height = services.getFrameDim().getHeight();
                double width = services.getFrameDim().getWidth();
                height *= 0.15; // % of total height
                width *= 0.85; // % of total width
                setPreferredSize(new Dimension((int)width,(int)height));
                double lwUGA = (WRECT*width);
                double lwUAL = lwUGA;
                double lhAll = (HRECT*height);
                double lorgwUGA = (width - lwUGA - lwUAL)/3;
                double lorgwUAL = 2*lorgwUGA+lwUGA;
                double lorghAll = (height-lhAll)/2;
                wUGA = (int)lwUGA;
                wUAL = (int)lwUAL;
                hAll = (int)lhAll;
                orgwUGA = (int)lorgwUGA;
                orgwUAL = (int)lorgwUAL;
                orghAll = (int)lorghAll;
                fontVal = new Font("TimesRoman",Font.BOLD,20);
                TitledBorder titled;
                titled = BorderFactory.createTitledBorder("CPU Aleph8");
                setBorder(titled);
                repaint();
        }

        public void showState(){
                int pc = services.getCpuComponent().GetPc();
                int acc = services.getCpuComponent().GetAcc();
                boolean carry = services.getCpuComponent().GetCarry();
                int nCy;
                if(carry)
                        nCy=1;
                else nCy=0;
                strPC = strPC.format("%2X",pc);
                strACC = strACC.format("%2X",acc);
                strCy = strCy.format("%1X",nCy);
                repaint();
        }
        public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int width = getSize().width;
                int height = getSize().height;
                g2.setColor(Color.green);
                g2.fillRect(orgwUGA,orghAll,wUGA,hAll);
                g2.setColor(Color.black);
                g2.drawRect(orgwUGA,orghAll,wUGA,hAll);
                g2.drawString("UGA",(orgwUGA+wUGA-30),(orghAll+hAll-5));
                g2.drawString("PC",(orgwUGA+(wUGA/2)-10),(orghAll+15));
                g2.setColor(Color.white);
                g2.fillRect((orgwUGA+(wUGA/2)-20),(orghAll+18),35, 20);
                g2.setColor(Color.black);
                g2.drawRect((orgwUGA+(wUGA/2)-20),(orghAll+18),35, 20);
                g2.setColor(Color.cyan);
                g2.fillRect(orgwUAL,orghAll,wUAL,hAll);
                g2.setColor(Color.black);
                g2.drawRect(orgwUAL,orghAll,wUAL,hAll);
                g2.drawString("UAL",(orgwUAL+wUAL-30),(orghAll+hAll-5));
                g2.drawString("ACC",(orgwUAL+(wUAL/2)-10),(orghAll+15));
                g2.setColor(Color.white);
                g2.fillRect((orgwUAL+(wUAL/2)-18),(orghAll+18),35,20 );
                g2.setColor(Color.black);
                g2.drawRect((orgwUAL+(wUAL/2)-18),(orghAll+18),35,20 );
                g2.drawString("Cy",(orgwUAL+(wUAL/3)-10),(orghAll+15));
                g2.setColor(Color.white);
                g2.fillRect((orgwUAL+(wUAL/3)-10),(orghAll+18),15,20);
                g2.setColor(Color.black);
                g2.drawRect((orgwUAL+(wUAL/3)-10),(orghAll+18),15,20);
                //draw arrow from UAL to UGA
                int [] xp={orgwUGA+wUGA, orgwUGA+wUGA+(orgwUAL-(orgwUGA+wUGA))/2, orgwUGA+wUGA+(orgwUAL-(orgwUGA+wUGA))/2, orgwUAL, orgwUAL, orgwUGA+wUGA+(orgwUAL-(orgwUGA+wUGA))/2, orgwUGA+wUGA+(orgwUAL-(orgwUGA+wUGA))/2, orgwUGA+wUGA};
                int [] yp={(orghAll+hAll/2),(orghAll+hAll/2)-20, (orghAll+hAll/2)-10, (orghAll+hAll/2)-10,(orghAll+hAll/2)+10, (orghAll+hAll/2)+10,(orghAll+hAll/2)+20,(orghAll+hAll/2)};
                Polygon arrow = new Polygon (xp,yp,8);
                g2.setColor(Color.orange);
                g2.fillPolygon(arrow);
                g2.setColor(Color.black);
                g2.drawPolygon(arrow);
                // fill with values
                g2.setColor(Color.red);
                g2.setFont(fontVal);
                g2.drawString(strPC,(orgwUGA+(wUGA/2)-10),(orghAll+35));
                g2.drawString(strACC,(orgwUAL+(wUAL/2)-10),(orghAll+35));
                g2.drawString(strCy,(orgwUAL+(wUAL/3)-8),(orghAll+35));
        }
}

