/*
 * Created on Apr 3, 2007
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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.fixture;

import javax.swing.JTabbedPane;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JTabbedPaneDriver;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.factory.JTabbedPanes.tabbedPane;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JTabbedPaneFixture}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JTabbedPaneFixtureTest extends CommonComponentFixtureTestCase<JTabbedPane> {

  private JTabbedPaneDriver driver;
  private JTabbedPane target;
  private JTabbedPaneFixture fixture;
  
  void onSetUp() {
    driver = createMock(JTabbedPaneDriver.class);
    target = tabbedPane().createInEDT();
    fixture = new JTabbedPaneFixture(robot(), target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldCreateFixtureWithGivenComponentName() {
    String name = "tabbedPane";
    expectLookupByName(name, JTabbedPane.class);
    verifyLookup(new JTabbedPaneFixture(robot(), name));
  }

  @Test public void shouldSelectTabWithIndex() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectTab(target, 8);
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectTab(8));
      }
    }.run();
  }
  
  @Test public void shouldSelectTabWithText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectTab(target, "A Tab");
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectTab("A Tab"));
      }
    }.run();
  }

  @Test public void shouldReturnTabTitles() {
    final String[] titles = array("One", "Two");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.tabTitles(target)).andReturn(titles);
      }
      
      protected void codeToTest() {
        String[] result = fixture.tabTitles();
        assertThat(result).isSameAs(titles);
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JTabbedPane target() { return target; }
  JTabbedPaneFixture fixture() { return fixture; }
}
