/*
 * Created on Jul 8, 2008
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
package org.fest.swing.fixture;

import java.awt.Component;

import org.testng.annotations.Test;

import org.fest.swing.core.Robot;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JTextFields.textField;

/**
 * Tests for <code>{@link ComponentFixtureValidator}</code>.
 *
 * @author Alex Ruiz
 */
@Test
public class ComponentFixtureValidatorTest {

  public void shouldReturnRobotIfItIsNotNull() {
    Robot robot = createMock(Robot.class);
    assertThat(ComponentFixtureValidator.notNullRobot(robot)).isSameAs(robot);
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfRobotIsNull() {
    ComponentFixtureValidator.notNullRobot(null);
  }

  public void shouldReturnTargetIfItIsNotNull() {
    Component target = textField().createNew();
    assertThat(ComponentFixtureValidator.notNullTarget(target)).isSameAs(target);
  }
  
  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfTargetIsNull() {
    ComponentFixtureValidator.notNullTarget(null);
  }
}
