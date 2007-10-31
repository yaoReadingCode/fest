/*
 * Created on Oct 22, 2007
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
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.util.Collection;

import static org.fest.swing.util.ComponentCollections.EMPTY;
import static org.fest.swing.util.ComponentCollections.empty;

import static org.fest.util.Arrays.isEmpty;

/**
 * Understands how to find children components in a <code>{@link Window}</code>.
 *
 * @author Yvonne Wang
 */
final class WindowChildrenFinder implements ChildrenFinderStrategy {

  public Collection<Component> nonExplicitChildrenOf(Container c) {
    if (!(c instanceof Window)) return EMPTY;
    return ownedWindows((Window)c);
  }
  
  private Collection<Component> ownedWindows(Window w) {
    return windows(w.getOwnedWindows());
  }
  
  private Collection<Component> windows(Window[] windows) {
    if (isEmpty(windows)) return EMPTY;
    Collection<Component> components = empty();
    for (Window w : windows) components.add(w);
    return components;
  }
}