/******************************************************************************
 * By SILIBRE - www.silibre.com - 2009
 * contact@silibre.com
 ******************************************************************************
 * ISimuUIConsts.java
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
 *  0.1         prog         29 oct. 2010                 Creation
 */

package simulator.ui;

/**
 *
 */
public interface ISimuUIConsts {

    // ui logic
    short READY = 1;
    short RUN = 2;
    short STEP = 3;
    short ERROR = 4;
    //buttons number
    short BUTTON_STEP_NUM = 1;
    short BUTTON_RUN_NUM = 2;
    short BUTTON_STOP_NUM = 4;
    short BUTTON_RESET_NUM = 8;
}
