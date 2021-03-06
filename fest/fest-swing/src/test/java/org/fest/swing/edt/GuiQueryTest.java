/*
 * Created on Jul 29, 2008
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
package org.fest.swing.edt;

import org.testng.annotations.Test;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.ActionFailedException;

import static javax.swing.SwingUtilities.isEventDispatchThread;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Tests for <code>{@link GuiQuery}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
@Test public class GuiQueryTest {

  @Test(expectedExceptions = ActionFailedException.class)
  public void shouldExecuteInEDTWhenNotCalledInEDT() {
    GuiQueryInEDT task = new GuiQueryInEDT();
    assertThat(isEventDispatchThread()).isFalse();
    task.run();
  }

  public void shouldExecuteInEDTWhenCalledInEDT() {
    final GuiQueryInEDT task = new GuiQueryInEDT();
    boolean executedFromEDT = execute(task);
    assertThat(executedFromEDT).isTrue();
  }

  private static class GuiQueryInEDT extends GuiQuery<Boolean> {
    GuiQueryInEDT() {}

    protected Boolean executeInEDT() {
      return isEventDispatchThread();
    }
  }
}
