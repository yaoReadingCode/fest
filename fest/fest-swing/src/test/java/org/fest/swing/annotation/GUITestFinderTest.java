/*
 * Created on May 27, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.annotation;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.method;

/**
 * Understands <code>{@link GUITestFinder}</code>.
 *
 * @author Alex Ruiz
 */
public class GUITestFinderTest {

  @GUITest
  public static class GUITestClass {
    @GUITest
    public void guiTestMethodWithAnnotation() {}
    public void guiTestMethodWithoutAnnotation() {}
  }

  public static class NonGUITestClass {
    @GUITest public void guiTestMethod() {}
  }

  public static class GUITestSubclass extends GUITestClass {}
  
  public static class NonGUITestSubclass extends NonGUITestClass {
    @Override public void guiTestMethod() {
      super.guiTestMethod();
    }
  }
  
  private GUITestClass guiTest;
  private NonGUITestClass nonGUITest;
  private GUITestSubclass guiTestSubclass;
  private NonGUITestSubclass nonGUITestSubclass;
  
  @BeforeClass public void setUp() throws Exception {
    guiTest = new GUITestClass();
    nonGUITest = new NonGUITestClass();
    guiTestSubclass = new GUITestSubclass();
    nonGUITestSubclass = new NonGUITestSubclass();
  }
  
  @Test public void shouldReturnIsGUITestIfClassHasGUITestAnnotation() {
    Class<? extends GUITestClass> guiTestType = guiTest.getClass();
    Method guiTestMethod = method("guiTestMethodWithoutAnnotation").in(guiTest).info();
    boolean isGUITest = GUITestFinder.isGUITest(guiTestType, guiTestMethod);
    assertThat(isGUITest).isTrue();
  }
  
  @Test public void shouldReturnIsGUITestIfOnlyMethodHasGUITestAnnotation() {
    Class<? extends NonGUITestClass> nonGUITestType = nonGUITest.getClass();
    Method guiTestMethod = method("guiTestMethod").in(nonGUITest).info();
    boolean isGUITest = GUITestFinder.isGUITest(nonGUITestType, guiTestMethod);
    assertThat(isGUITest).isTrue();
  }
  
  @Test public void shouldReturnIsGUITestIfSuperclassIsGUITest() {
    Class<? extends GUITestSubclass> guiTestSubtype = guiTestSubclass.getClass();
    Method guiTestMethod = method("guiTestMethodWithoutAnnotation").in(guiTest).info();
    boolean isGUITest = GUITestFinder.isGUITest(guiTestSubtype, guiTestMethod);
    assertThat(isGUITest).isTrue();
  }
  
  @Test public void shouldReturnIsGUITestIfOverridenMethodIsGUITest() {
    Class<? extends NonGUITestSubclass> nonGUITestSubtype = nonGUITestSubclass.getClass();
    Method guiTestMethod = method("guiTestMethod").in(nonGUITestSubclass).info();
    boolean isGUITest = GUITestFinder.isGUITest(nonGUITestSubtype, guiTestMethod);
    assertThat(isGUITest).isTrue();
  }
  
  @Test public void shouldReturnIsNotGUITestIfNotContainingGUITestAnnotation() {
    String s = "Yoda";
    Method concat = method("concat").withReturnType(String.class).withParameterTypes(String.class).in(s).info();
    assertThat(GUITestFinder.isGUITest(s.getClass(), concat)).isFalse();
  }
}
