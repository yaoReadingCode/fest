/*
 * Created on Jul 12, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.applet;

import java.applet.Applet;
import java.awt.event.WindowEvent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.*;

/**
 * Tests for <code>{@link StopAppletWindowListener}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test
public class StopAppletWindowListenerTest {

  private Applet applet;
  private StopAppletWindowListener listener;

  @BeforeMethod public void setUp() {
    applet = createStrictMock(Applet.class);
    listener = new StopAppletWindowListener(applet);
  }
  
  public void shouldStopAndDestroyAppletWhenWindowIsClosing() {
    new EasyMockTemplate(applet) {
      protected void expectations() {
        applet.stop();
        expectLastCall().once();
        applet.destroy();
        expectLastCall().once();
      }

      protected void codeToTest() {
        listener.windowClosing(windowEvent());
      }
    }.run();
  }

  public void shouldNotDoAnythingWhenWindowOpened() {
    new EasyMockTemplate(applet) {
      protected void expectations() {}

      protected void codeToTest() {
        listener.windowOpened(windowEvent());
      }
    }.run();
  }

  public void shouldNotDoAnythingWhenWindowClosed() {
    new EasyMockTemplate(applet) {
      protected void expectations() {}

      protected void codeToTest() {
        listener.windowClosed(windowEvent());
      }
    }.run();
  }

  public void shouldNotDoAnythingWhenWindowIconified() {
    new EasyMockTemplate(applet) {
      protected void expectations() {}

      protected void codeToTest() {
        listener.windowIconified(windowEvent());
      }
    }.run();
  }

  public void shouldNotDoAnythingWhenWindowDeiconified() {
    new EasyMockTemplate(applet) {
      protected void expectations() {}

      protected void codeToTest() {
        listener.windowDeiconified(windowEvent());
      }
    }.run();
  }

  public void shouldNotDoAnythingWhenWindowActivated() {
    new EasyMockTemplate(applet) {
      protected void expectations() {}

      protected void codeToTest() {
        listener.windowActivated(windowEvent());
      }
    }.run();
  }

  public void shouldNotDoAnythingWhenWindowDeactivated() {
    new EasyMockTemplate(applet) {
      protected void expectations() {}

      protected void codeToTest() {
        listener.windowDeactivated(windowEvent());
      }
    }.run();
  }

  private WindowEvent windowEvent() {
    return createMock(WindowEvent.class);
  }
}
