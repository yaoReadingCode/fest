/*
 * Created on Nov 12, 2008
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
package org.fest.swing.driver;

import javax.swing.JComboBox;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.cell.JComboBoxCellReader;
import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.driver.JComboBoxSelectedItemQuery.selectedItemOf;
import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the selected value of a
 * <code>{@link JComboBox}</code> as plain text.
 * 
 * @author Alex Ruiz
 */
final class JComboBoxSelectionValueQuery {

  static final Object NO_SELECTION_VALUE = new Object();

  @RunsInEDT
  static Object selection(final JComboBox comboBox, final JComboBoxCellReader cellReader) {
    return execute(new GuiQuery<Object>() {
      protected Object executeInEDT() {
        if (comboBox.isEditable()) return selectionInEditableComboBox(comboBox);
        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex == -1) return NO_SELECTION_VALUE;
        return cellReader.valueAt(comboBox, selectedIndex);
      }
    });
  }

  @RunsInCurrentThread
  private static Object selectionInEditableComboBox(JComboBox comboBox) {
    Object selectedItem = selectedItemOf(comboBox);
    if (selectedItem == null) return NO_SELECTION_VALUE;
    return selectedItem.toString();
  }

  private JComboBoxSelectionValueQuery() {}
}
