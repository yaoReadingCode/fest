/*
 * Created on Dec 23, 2007
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import java.io.File;
import java.io.IOException;

import org.fest.assertions.FileContentComparator.LineDiff;

import static org.fest.assertions.Formatting.inBrackets;
import static org.fest.util.Arrays.isEmpty;
import static org.fest.util.Strings.concat;
import static org.fest.util.Systems.LINE_SEPARATOR;

/**
 * Understands assertion methods for <code>File</code>. To create a new instance of this class use the method <code>
 * {@link Assertions#assertThat(File)}</code>.
 *
 * @author David DIDIER
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class FileAssert extends GenericAssert<File> {

  private final FileContentComparator comparator;

  /**
   * Creates a new <code>FileAssert</code>.
   * @param actual the actual <code>File</code> to test.
   */
  FileAssert(File actual) {
    this(actual, new FileContentComparator());
  }

  FileAssert(File actual, FileContentComparator comparator) {
    super(actual);
    this.comparator = comparator;
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>.
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(folder).<strong>as</strong>(&quot;Temp Folder&quot;).exists();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  @Override public FileAssert as(String description) {
    description(description);
    return this;
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in <a
   * href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(folder).<strong>describedAs</strong>(&quot;Temp Folder&quot;).exists();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  @Override public FileAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>.
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(folder).<strong>as</strong>(new BasicDescription(&quot;Temp Folder&quot;)).exists();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  @Override public FileAssert as(Description description) {
    description(description);
    return this;
  }

  /**
   * Alternative to <code>{@link #as(Description)}</code>, since "as" is a keyword in <a
   * href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(folder).<strong>describedAs</strong>(new BasicDescription(&quot;Temp Folder&quot;)).exists();
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  @Override public FileAssert describedAs(Description description) {
    return as(description);
  }

  /**
   * Verifies that the actual <code>File</code> does not exist.
   * @return this assertion object.
   * @throws AssertionError if the the actual <code>File</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>File</code> exists.
   */
  public FileAssert doesNotExist() {
    isNotNull();
    if (actual.exists()) fail(concat("file:", inBrackets(actual), " should not exist"));
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> does exist.
   * @return this assertion object.
   * @throws AssertionError if the the actual <code>File</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>File</code> does not exist.
   */
  public FileAssert exists() {
    isNotNull();
    assertExists(actual);
    return this;
  }

  /**
   * Verifies that the size of the actual <code>File</code> is equal to the given one.
   * @param expected the expected size of the actual <code>File</code>.
   * @return this assertion object.
   * @throws AssertionError if the the actual <code>File</code> is <code>null</code>.
   * @throws AssertionError if the size of the actual <code>File</code> is not equal to the given one.
   */
  public FileAssert hasSize(long expected) {
    isNotNull();
    long size = actual.length();
    if (size != expected)
      fail(concat(
          "size of file:", inBrackets(actual), " expected:", inBrackets(expected), " but was:", inBrackets(size)));
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> is a directory.
   * @return this assertion object.
   * @throws AssertionError if the the actual <code>File</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>File</code> is not a directory.
   */
  public FileAssert isDirectory() {
    isNotNull();
    if (!actual.isDirectory())
      fail(concat("file:", inBrackets(actual), " should be a directory"));
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> is equal to the given one. To verify that the actual <code>File</code>
   * has the same content as another <code>File</code>, use <code>{@link #hasSameContentAs(File)}</code>.
   * @param expected the given <code>File</code> to compare the actual <code>File</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is not equal to the given one.
   */
  public FileAssert isEqualTo(File expected) {
    assertEqualTo(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> is a regular file.
   * @return this assertion object.
   * @throws AssertionError if the the actual <code>File</code> is <code>null</code>.
   * @throws AssertionError if the actual <code>File</code> is not a regular file.
   */
  public FileAssert isFile() {
    isNotNull();
    if (!actual.isFile())
      fail(concat("file:", inBrackets(actual), " should be a file"));
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> is not equal to the given one.
   * @param other the given <code>File</code> to compare the actual <code>File</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is equal to the given one.
   */
  public FileAssert isNotEqualTo(File other) {
    assertNotEqualTo(other);
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> is not <code>null</code>.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is <code>null</code>.
   */
  @Override public FileAssert isNotNull() {
    assertNotNull();
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> is not the same as the given one.
   * @param other the given <code>File</code> to compare the actual <code>File</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is the same as the given one.
   */
  public FileAssert isNotSameAs(File other) {
    assertNotSameAs(other);
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> is the same as the given one.
   * @param expected the given <code>File</code> to compare the actual <code>File</code> to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is not the same as the given one.
   */
  public FileAssert isSameAs(File expected) {
    assertSameAs(expected);
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> satisfies the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> does not satisfy the given condition.
   * @throws IllegalArgumentException if the given condition is null.
   */
  public FileAssert satisfies(Condition<File> condition) {
    assertSatisfies(condition);
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> does not satisfy the given condition.
   * @param condition the given condition.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> satisfies the given condition.
   * @throws IllegalArgumentException if the given condition is null.
   */
  public FileAssert doesNotSatisfy(Condition<File> condition) {
    assertDoesNotSatisfy(condition);
    return this;
  }

  /**
   * Verifies that the content of the actual <code>File</code> is equal to the content of the given one. Adapted from
   * <a href="http://junit-addons.sourceforge.net/junitx/framework/FileAssert.html" target="_blank">FileAssert</a> (from
   * <a href="http://sourceforge.net/projects/junit-addons">JUnit-addons</a>.)
   * @param expected the given <code>File</code> to compare the actual <code>File</code> to.
   * @return this assertion object.
   * @throws AssertionError if the the actual <code>File</code> is <code>null</code>.
   * @throws AssertionError if the content of the actual <code>File</code> is not equal to the content of the given
   *          one.
   * @throws IllegalArgumentException if the file to compare to is <code>null</code>.
   */
  public FileAssert hasSameContentAs(File expected) {
    isNotNull();
    if (expected == null) throw new IllegalArgumentException("File to compare to should not be null");
    assertExists(actual).assertExists(expected);
    try {
      LineDiff[] diffs = comparator.compareContents(actual, expected);
      if (!isEmpty(diffs)) fail(expected, diffs);
    } catch (IOException e) {
      cannotCompareToExpectedFile(expected, e);
    }
    return this;
  }

  private void fail(File expected, LineDiff[] diffs) {
    StringBuilder b = new StringBuilder();
    b.append("file:").append(inBrackets(actual)).append(" and file:").append(inBrackets(expected))
      .append(" do not have same contents:");
    for (LineDiff diff : diffs) {
      b.append(LINE_SEPARATOR).append("line:").append(inBrackets(diff.lineNumber))
        .append(", expected:").append(inBrackets(diff.expected)).append(" but was:").append(inBrackets(diff.actual));
    }
    fail(b.toString());
  }

  private void cannotCompareToExpectedFile(File expected, Exception e) {
    String message = concat(
        "unable to compare contents of files:", inBrackets(actual), " and ", inBrackets(expected));
    fail(message, e);
  }

  private FileAssert assertExists(File file) {
    if (!file.exists()) fail(concat("file:", inBrackets(file), " should exist"));
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> is a relative path.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is not a relative path.
   */
  public FileAssert isRelative() {
    isNotNull();
    if (actual.isAbsolute()) fail(concat("file:", inBrackets(actual), " should be a relative path"));
    return this;
  }

  /**
   * Verifies that the actual <code>File</code> is an absolute path.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>File</code> is not an absolute path.
   */
  public FileAssert isAbsolute() {
    isNotNull();
    if (!actual.isAbsolute()) fail(concat("file:", inBrackets(actual), " should be an absolute path"));
    return this;
  }
}
