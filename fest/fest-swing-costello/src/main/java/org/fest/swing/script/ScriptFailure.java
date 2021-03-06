/*
 * Created on Sep 28, 2006
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
 * Copyright @2006 the original author or authors.
 */
package org.fest.swing.script;

import static org.fest.util.Strings.*;

/**
 * Understands a wrapper for <code>{@link Throwable}</code>s thrown while running an
 * <a href="http://abbot.sourceforge.net" target="_blank">Abbot<a/> script.
 * 
 * @author Alex Ruiz
 */
public class ScriptFailure extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Creates a new <code>{@link ScriptFailure}</code>.
   * @param scriptName the name of the failed script.
   * @param cause the cause of the failure.
   */
  public ScriptFailure(String scriptName, Throwable cause) {
    super(concat("The script ", quote(scriptName), " failed. Cause: ", cause));

  }

}
