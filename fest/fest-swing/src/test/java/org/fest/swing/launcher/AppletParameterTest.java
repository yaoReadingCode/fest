/*
 * Created on Jul 14, 2008
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
package org.fest.swing.launcher;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link AppletParameter}</code>.
 *
 * @author Yvonne Wang
 */
public class AppletParameterTest {

  @Test public void shouldCreateAppletParameter() {
    AppletParameter parameter = AppletParameter.name("bgcolor").value("blue");
    assertThat(parameter.name).isEqualTo("bgcolor");
    assertThat(parameter.value).isEqualTo("blue");
  }
}
