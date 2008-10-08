/*
 * Created on Aug 21, 2008
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
package org.fest.swing.driver;

import javax.swing.JScrollBar;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.testing.TestGroups.*;

/**
 * Tests for <code>{@link JScrollBarValueRangeQuery}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = { GUI, EDT_ACTION })
public class JScrollBarValueRangeQueryTest {

  private Robot robot;
  private MyScrollBar scrollBar;

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    MyWindow window = MyWindow.createNew();
    scrollBar = window.scrollBar;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldReturnMinimumAndMaximumValuesOfJScrollBar() {
    ValueRange valueRange = JScrollBarValueRangeQuery.valueRangeOf(scrollBar);
    assertThat(valueRange.minimum).isEqualTo(6);
    assertThat(valueRange.maximum).isEqualTo(10);
    assertThat(scrollBar.methodGetMaximumWasInvoked()).isTrue();
    assertThat(scrollBar.methodGetMinimumWasInvoked()).isTrue();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final MyScrollBar scrollBar = new MyScrollBar(8, 2, 6, 10);

    static MyWindow createNew() {
      return new MyWindow();
    }

    private MyWindow() {
      super(JScrollBarValueRangeQueryTest.class);
      addComponents(scrollBar);
    }
  }

  private static class MyScrollBar extends JScrollBar {
    private static final long serialVersionUID = 1L;

    private boolean methodGetMaximumInvoked;
    private boolean methodGetMinimumInvoked;

    public MyScrollBar(int value, int extent, int min, int max) {
      super(HORIZONTAL, value, extent, min, max);
    }

    @Override public int getMaximum() {
      methodGetMaximumInvoked = true;
      return super.getMaximum();
    }

    @Override public int getMinimum() {
      methodGetMinimumInvoked = true;
      return super.getMinimum();
    }

    boolean methodGetMaximumWasInvoked() { return methodGetMaximumInvoked; }
    boolean methodGetMinimumWasInvoked() { return methodGetMinimumInvoked; }
  }
}