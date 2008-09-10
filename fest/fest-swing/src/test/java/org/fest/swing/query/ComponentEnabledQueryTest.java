/*
 * Created on Aug 9, 2008
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
package org.fest.swing.query;

import java.awt.Component;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.testing.BooleanProvider;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.testing.TestGroups.EDT_ACTION;

/**
 * Tests for <code>{@link ComponentEnabledQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = EDT_ACTION)
public class ComponentEnabledQueryTest {

  private Component component;
  private ComponentEnabledQuery query;

  @BeforeMethod public void setUp() {
    component = createMock(Component.class);
    query = new ComponentEnabledQuery(component);
  }
  
  @Test(dataProvider = "booleans", dataProviderClass = BooleanProvider.class, groups = EDT_ACTION)
  public void shouldIndicateIfComponentIsEnabled(final boolean enabled) {
    new EasyMockTemplate(component) {
      protected void expectations() {
        expect(component.isEnabled()).andReturn(enabled);
      }

      protected void codeToTest() {
        assertThat(query.executeInEDT()).isEqualTo(enabled);
      }
    }.run();
  }
}