/*
 * Created on Sep 21, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import static org.fest.swing.MouseButtons.BUTTON1;
import static org.fest.swing.MouseButtons.BUTTON2;
import static org.fest.swing.MouseButtons.BUTTON3;

/**
 * Understands a mouse listener that records mouse events.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang 
 */
public class ClickRecorder extends MouseAdapter {

  public static ClickRecorder attachTo(Component target) {
    return new ClickRecorder(target);
  }
  
  private static final Map<Integer, MouseButtons> MOUSE_BUTTON_MAP = new HashMap<Integer, MouseButtons>(); 
  
  static {
      MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON1, BUTTON1);
      MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON2, BUTTON2);
      MOUSE_BUTTON_MAP.put(MouseEvent.BUTTON3, BUTTON3);
  }
  
  private MouseButtons clickedButton;
  private int clickCount;
  private Point pointClicked;

  private ClickRecorder(Component target) {
    attach(this, target);
  }
  
  private static void attach(ClickRecorder mouseListener, Component target) {
    target.addMouseListener(mouseListener);
    if (!(target instanceof Container)) return;
    for (Component c : ((Container)target).getComponents()) attach(mouseListener, c);
  }

  @Override public void mousePressed(MouseEvent e) {
    clickedButton = MOUSE_BUTTON_MAP.get(e.getButton());
    clickCount = e.getClickCount();
    pointClicked = e.getPoint();
  }
  
  public boolean clicked() { 
    return BUTTON1.equals(clickedButton()) && clickCount == 1; 
  }

  public boolean doubleClicked() { 
    return BUTTON1.equals(clickedButton()) && clickCount == 2; 
  }

  public boolean rightClicked() { 
    return BUTTON3.equals(clickedButton()) && clickCount == 1; 
  }

  public MouseButtons clickedButton() { return clickedButton; }
  public int clickCount() { return clickCount; }
  public Point pointClicked() { return pointClicked; }
}