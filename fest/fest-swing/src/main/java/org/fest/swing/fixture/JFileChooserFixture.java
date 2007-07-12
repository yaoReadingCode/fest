/*
 * Created on Jul 9, 2007
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
package org.fest.swing.fixture;

import java.awt.Component;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.UIManager;

import static javax.swing.JFileChooser.DIRECTORIES_ONLY;
import static javax.swing.JFileChooser.FILES_ONLY;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.isEmpty;
import static org.fest.util.Strings.quote;

import org.fest.swing.ComponentLookupException;
import org.fest.swing.ComponentMatcher;
import org.fest.swing.RobotFixture;

/**
 * Understands simulation of user events on a <code>{@link JFileChooser}</code> and verification of the state of such
 * <code>{@link JFileChooser}</code>.
 *
 *
 * @author Yvonne Wang 
 */
public class JFileChooserFixture extends ComponentFixture<JFileChooser> {

  /**
   * Creates a new </code>{@link JFileChooserFixture}</code>.
   * @param robot performs simulation of user events on a <code>JFileChooser</code>.
   * @throws ComponentLookupException if a matching <code>JFileChooser</code> could not be found.
   */
  public JFileChooserFixture(RobotFixture robot) {
    super(robot, JFileChooser.class);
  }
  
  /**
   * Creates a new </code>{@link JFileChooserFixture}</code>.
   * @param robot performs simulation of user events on a <code>JFileChooser</code>.
   * @param labelName the name of the <code>JFileChooser</code> to find using the given <code>RobotFixture</code>.
   * @throws ComponentLookupException if a matching <code>JFileChooser</code> could not be found.
   */
  public JFileChooserFixture(RobotFixture robot, String labelName) {
    super(robot, labelName, JFileChooser.class);
  }
  
  /**
   * Creates a new </code>{@link JFileChooserFixture}</code>.
   * @param robot performs simulation of user events on the given <code>JFileChooser</code>.
   * @param target the <code>JFileChooser</code> to be managed by this fixture.
   */
  public JFileChooserFixture(RobotFixture robot, JFileChooser target) {
    super(robot, target);
  }

  /**
   * Selects the given file in the <code>{@link JFileChooser}</code> managed by this fixture.
   * @param file the file to select.
   * @return this fixture.
   * @throws AssertionError if the managed <code>JFileChooser</code> can select directories only and the file to
   *           select is not a directory.
   * @throws AssertionError if the managed <code>JFileChooser</code> cannot select directories and the file to select
   *           is a directory.
   */
  public final JFileChooserFixture selectFile(final File file) {
    int mode = target.getFileSelectionMode();
    boolean isFolder = file.isDirectory();
    if (mode == FILES_ONLY && isFolder) throw new AssertionError("The file chooser cannot open directories");
    if (mode == DIRECTORIES_ONLY && !isFolder) throw new AssertionError("The file chooser can only open directories");
    robot.invokeAndWait(null, new Runnable() {
      public void run() {
        target.setSelectedFile(file);
      }
    });
    return this;
  }

  /**
   * Sets the current diretory of the <code>{@link JFileChooser}</code> managed by this fixture to the given one.
   * @param dir the directory to set as current.
   * @return this fixture.
   */
  public final JFileChooserFixture setCurrentDirectory(final File dir) {
    robot.invokeAndWait(null, new Runnable() {
      public void run() {
        target.setCurrentDirectory(dir);
      }
    });
    return this;
  }

  /**
   * Returns a fixture that manages the text field where the user can enter the name of the file to select in the 
   * <code>{@link JFileChooser}</code> managed by this fixture.
   * @return the created fixture.
   * @throws ComponentLookupException if a matching text field could not be found.
   */
  public final JTextComponentFixture fileNameTextBox() {
    return new JTextComponentFixture(robot, robot.finder().findByType(target, JTextField.class));
  }

  /**
   * Simulates a user pressing the "Cancel" button in the <code>{@link JFileChooser}</code> managed by this fixture.
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   * @throws AssertionError if the "Cancel" button is disabled.
   */
  public final void cancel() {
    cancelButton().requireEnabled().click();
  }
  
  /**
   * Finds the "Cancel" button in the <code>{@link JFileChooser}</code> managed by this fixture.
   * @return the found "Cancel" button.
   * @throws ComponentLookupException if the "Cancel" button cannot be found.
   */
  public final JButtonFixture cancelButton() {
    String buttonText = UIManager.getString("FileChooser.cancelButtonText");
    JButton cancelButton = findButton(buttonText);
    if (cancelButton == null) throw cannotFindButton("Cancel", buttonText);
    return new JButtonFixture(robot, cancelButton);
  }

  /**
   * Simulates a user pressing the "Approve" button in the <code>{@link JFileChooser}</code> managed by this fixture.
   * @throws ComponentLookupException if the "Approve" button cannot be found.
   * @throws AssertionError if the "Approve" button is disabled.
   */
  public final void approve() {
    approveButton().requireEnabled().click();
  }
  
  /**
   * Finds the "Approve" button in the <code>{@link JFileChooser}</code> managed by this fixture.
   * @return the found "Approve" button.
   * @throws ComponentLookupException if the "Approve" button cannot be found.
   */
  public final JButtonFixture approveButton() {
    String buttonText = approveButtonText();
    JButton approveButton = findButton(buttonText);
    if (approveButton == null) throw cannotFindButton("Approve", buttonText);
    return new JButtonFixture(robot, approveButton);
  }

  private String approveButtonText() {
    String text = target.getApproveButtonText();
    if (isEmpty(text)) text = target.getUI().getApproveButtonText(target);
    return text;
  }

  private JButton findButton(final String text) {
    JButton button = (JButton) robot.finder().find(target, new ComponentMatcher() {
      public boolean matches(Component c) {
        return (c instanceof JButton && text.equals(((JButton) c).getText()));
      }
    });
    return button;
  }
  
  private ComponentLookupException cannotFindButton(String name, String text) {
    throw new ComponentLookupException(concat("Unable to find ", quote(name), " button with text ", quote(text)));
  }
}