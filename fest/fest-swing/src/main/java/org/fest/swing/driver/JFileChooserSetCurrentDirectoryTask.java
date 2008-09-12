/*
 * Created on Aug 8, 2008
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

import java.io.File;

import javax.swing.JFileChooser;

import org.fest.swing.core.GuiTask;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands a task that sets the current directory in a <code>{@link JFileChooser}</code>. This task is executed in
 * the event dispatch thread.
 * 
 * @author Yvonne Wang
 */
final class JFileChooserSetCurrentDirectoryTask {

  static void setCurrentDir(final JFileChooser fileChooser, final File dir) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        fileChooser.setCurrentDirectory(dir);
      }
    });
  }
  
  private JFileChooserSetCurrentDirectoryTask() {}
}