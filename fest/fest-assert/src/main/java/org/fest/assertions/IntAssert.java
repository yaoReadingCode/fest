/*
 * Created on Jun 14, 2007
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
package org.fest.assertions;

import static org.fest.assertions.PrimitiveFail.*;

/**
 * Understands assert method for <code>int</code>s. To create a new instance of this class use the
 * method <code>{@link Assertions#assertThat(int)}</code>.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 */
public final class IntAssert extends PrimitiveAssert {

  private static final int ZERO = 0;

  private final int actual;

  IntAssert(int actual) {
    this.actual = actual;
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(value).<strong>as</strong>(&quot;Some value&quot;).isEqualTo(otherValue);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public IntAssert as(String description) {
    description(description);
    return this;
  }

  /**
   * Alternative to <code>{@link #as(String)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(value).<strong>describedAs</strong>(&quot;Some value&quot;).isEqualTo(otherValue);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public IntAssert describedAs(String description) {
    return as(description);
  }

  /**
   * Sets the description of the actual value, to be used in as message of any <code>{@link AssertionError}</code>
   * thrown when an assertion fails. This method should be called before any assertion method, otherwise any assertion
   * failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(value).<strong>as</strong>(new BasicDescription(&quot;Some value&quot;)).isEqualTo(otherValue);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public IntAssert as(Description description) {
    description(description);
    return this;
  }

  /**
   * Alternative to <code>{@link #as(Description)}</code>, since "as" is a keyword in
   * <a href="http://groovy.codehaus.org/" target="_blank">Groovy</a>. This method should be called before any assertion
   * method, otherwise any assertion failure will not show the provided description.
   * <p>
   * For example:
   * <pre>
   * assertThat(value).<strong>describedAs</strong>(new BasicDescription(&quot;Some value&quot;)).isEqualTo(otherValue);
   * </pre>
   * </p>
   * @param description the description of the actual value.
   * @return this assertion object.
   */
  public IntAssert describedAs(Description description) {
    return as(description);
  }


  /**
   * Verifies that the actual <code>int</code> value is equal to the given one.
   * @param expected the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> value is not equal to the given one.
   */
  public IntAssert isEqualTo(int expected) {
    failIfNotEqual(description(), actual, expected);
    return this;
  }

  /**
   * Verifies that the actual <code>int</code> value is not equal to the given one.
   * @param value the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> value is equal to the given one.
   */
  public IntAssert isNotEqualTo(int value) {
    failIfEqual(description(), actual, value);
    return this;
  }

  /**
   * Verifies that the actual <code>int</code> value is greater than the given one.
   * @param value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> value is not greater than the given one.
   */
  public IntAssert isGreaterThan(int value) {
    failIfNotGreaterThan(description(), actual, value);
    return this;
  }

  /**
   * Verifies that the actual <code>int</code> value is less than the given one.
   * @param value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> value is not less than the given one.
   */
  public IntAssert isLessThan(int value) {
    failIfNotLessThan(description(), actual, value);
    return this;
  }

  /**
   * Verifies that the actual <code>int</code> value is greater or equal to the given one.
   * @param value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> value is not greater than or equal to the given one.
   */
  public IntAssert isGreaterThanOrEqualTo(int value) {
    failIfNotGreaterThanOrEqualTo(description(), actual, value);
    return this;
  }

  /**
   * Verifies that the actual <code>int</code> value is less or equal to the given one.
   * @param value the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> value is not less than or equal to the given one.
   */
  public IntAssert isLessThanOrEqualTo(int value) {
    failIfNotLessThanOrEqualTo(description(), actual, value);
    return this;
  }

  /**
   * Verifies that the actual <code>int</code> value is positive.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> value is not positive.
   */
  public IntAssert isPositive() { return isGreaterThan(ZERO); }

  /**
   * Verifies that the actual <code>int</code> value is negative.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> value is not negative.
   */
  public IntAssert isNegative() { return isLessThan(ZERO); }

  /**
   * Verifies that the actual <code>int</code> value is equal to zero.
   * @return this assertion object.
   * @throws AssertionError if the actual <code>int</code> value is not equal to zero.
   */
  public IntAssert isZero() { return isEqualTo(ZERO); }
}
