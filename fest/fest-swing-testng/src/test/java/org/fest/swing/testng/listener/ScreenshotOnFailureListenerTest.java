/*
 * Created on May 16, 2007
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
 * Copyright @2007-2009  the original author or authors.
 */
package org.fest.swing.testng.listener;

import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.fest.swing.annotation.GUITest;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.ImageAssert.read;
import static org.fest.util.Files.temporaryFolderPath;
import static org.fest.util.Strings.*;

/**
 * Tests for <code>{@link ScreenshotOnFailureListener}</code>.
 *
 * @author Yvonne Wang
 */
public class ScreenshotOnFailureListenerTest {

  @GUITest public static class SomeGUITestClass {
    @GUITest public void someGUITestMethod() {}
    public void someNonGUITestMethod() {}
  }

  private TestContextStub testContext;
  private TestResultStub testResult;

  private ScreenshotOnFailureListener listener;

  @BeforeClass public void setUp() {
    testContext = new TestContextStub();
    testResult = new TestResultStub();
    listener = new ScreenshotOnFailureListener();
  }

  @Test public void shouldGetOutputFolderOnStart() {
    String outputFolder = temporaryFolderPath();
    testContext.setOutputDirectory(outputFolder);
    listener.onStart(testContext);
    assertThat(listener.output()).isEqualTo(outputFolder);
  }

  @Test(dependsOnMethods = "shouldGetOutputFolderOnStart")
  public void shouldTakeScreenshotOfDesktopOnTestFailure() throws Exception {
    setUpStubsForScreenshot();
    listener.onTestFailure(testResult);
    String imageFileName = screenshotFileName();
    String screenshotPath = concat(testContext.getOutputDirectory(), imageFileName);
    assertThat(read(screenshotPath)).hasSize(Toolkit.getDefaultToolkit().getScreenSize());
    assertScreenshotHyperlinkAddedToReport(imageFileName);
  }

  private void setUpStubsForScreenshot() {
    Date now = new GregorianCalendar().getTime();
    ClassStub testClass = testResult.getTestClass();
    testClass.setName(new SimpleDateFormat("yyMMdd").format(now));
    testClass.setRealClass(SomeGUITestClass.class);
    testResult.getMethod().setMethodName(new SimpleDateFormat("hhmmss").format(now));
  }

  private String screenshotFileName() {
    String className = testResult.getTestClass().getName();
    String methodName = testResult.getMethod().getMethodName();
    return join(className, methodName, "png").with(".");
  }

  private void assertScreenshotHyperlinkAddedToReport(String imageFileName) {
    List<String> reporterOutput = Reporter.getOutput();
    assertThat(reporterOutput).hasSize(1);
    assertThat(reporterOutput.get(0)).isEqualTo(concat("<a href=\"", imageFileName, "\">Screenshot</a>"));
  }
}
