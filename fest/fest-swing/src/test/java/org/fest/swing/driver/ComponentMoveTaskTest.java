/*
 * Created on Feb 23, 2008
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

import java.awt.Component;
import java.awt.Point;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createMock;

/**
 * Test for <code>{@link ComponentMoveTask}</code>.
 *
 * @author Alex Ruiz
 */
public class ComponentMoveTaskTest {

  private Component c;
  private Point location;

  @BeforeMethod public void setUp() throws Exception {
    c = createMock(Component.class);
    location = new Point(80, 60);
  }

  @Test public void shouldSetLocation() {
    new EasyMockTemplate(c) {
      protected void expectations() {
        c.setLocation(location);
        expectLastCall();
      }

      protected void codeToTest() {
        ComponentMoveTask.moveTask(c, location).executeInEDT();
      }
    }.run();
  }
}
