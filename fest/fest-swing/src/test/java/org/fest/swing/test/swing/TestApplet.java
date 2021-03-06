/*
 * Created on Jun 5, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.test.swing;

import java.awt.FlowLayout;

import javax.swing.JApplet;
import javax.swing.JButton;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands a simple applet.
 *
 * @author Alex Ruiz
 */
public class TestApplet extends JApplet {

  private static final long serialVersionUID = 1L;
  
  private boolean initialized;
  private boolean destroyed;
  private boolean started;
  private boolean stopped;

  @RunsInEDT
  public static TestApplet createNew() {
    return execute(new GuiQuery<TestApplet>() {
      protected TestApplet executeInEDT() {
        return new TestApplet();
      }
    });
  }

  @RunsInCurrentThread
  public TestApplet() {
    setLayout(new FlowLayout());
    JButton button = new JButton("Click Me");
    button.setName("clickMe");
    add(button);
  }
  
  @Override public void init() { 
    initialized = true;
  }
  
  @Override public void start() {
    started = true;
  }

  @Override public void stop() {
    stopped = true;
  }
  
  @Override public void destroy() {
    destroyed = true;
  }

  public boolean initialized() { return initialized; }
  
  public boolean started() { return started; }
  
  public boolean stopped() { return stopped; }

  public boolean destroyed() { return destroyed; }
}
