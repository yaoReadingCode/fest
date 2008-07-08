/*
 * Created on Dec 10, 2007
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
package org.fest.swing.remote.core;

import static org.fest.assertions.Assertions.assertThat;

import java.util.UUID;

import javax.swing.JTextField;

import org.fest.swing.remote.testing.SerializableAssert;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link FindComponentRequest}</code>.
 *
 * @author Alex Ruiz
 */
public class FindComponentRequestTest {

  @Test public void shouldBeSerializable() throws Exception {
    SerializableAssert request = new SerializableAssert(
        FindComponentRequest.findByName(UUID.randomUUID(), "aName", JTextField.class));
    assertThat(request).isSerializable();
  }

  @Test public void shouldBeSerializableWithoutUUID() throws Exception {
    SerializableAssert request = new SerializableAssert(FindComponentRequest.findByName("aName", JTextField.class));
    assertThat(request).isSerializable();
  }

}