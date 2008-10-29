/*
 * Created on Nov 1, 2007
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
package org.fest.swing.hierarchy;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;

import javax.swing.JInternalFrame;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

import org.fest.swing.annotation.RunsInCurrentThread;
import org.fest.swing.query.ComponentParentQuery;
import org.fest.swing.query.JPopupMenuInvokerQuery;

import static org.fest.swing.hierarchy.JInternalFrameDesktopPaneQuery.desktopPaneOf;


/**
 * Understands how to find the parent of a <code>{@link Component}</code>. Unlike
 * <code>{@link ComponentParentQuery}</code> and <code>{@link JPopupMenuInvokerQuery}</code>, this class is not
 * limited to simply call <code>{@link Component#getParent()}</code> and <code>{@link JPopupMenu#getInvoker()}</code>.
 * This class returns the most likely parent based on the type of a given <code>Component</code>.
 *
 * @author Alex Ruiz
 */
class ParentFinder {

  /**
   * Return the parent for the given component.
   * @param c the given component.
   * @return the parent for the given component.
   */
  @RunsInCurrentThread
  Container parentOf(Component c) {
    Container p = c.getParent();
    if (p == null && c instanceof JInternalFrame) p = parentOf((JInternalFrame)c);
    return p;
  }

  @RunsInCurrentThread
  private Container parentOf(JInternalFrame internalFrame) {
    // From Abbot: workaround for bug in JInternalFrame: COMPONENT_HIDDEN is sent before the desktop icon is set, so
    // JInternalFrame.getDesktopPane will throw a NPE if called while dispatching that event. Reported against 1.4.x.
    return desktopPaneOf(internalFrame);
  }

  /**
   * Similar to
   * <code>{@link javax.swing.SwingUtilities#windowForComponent(Component) SwingUtilities.windowForComponent}</code>),
   * but returns the component itself if it is a <code>{@link Window}</code>, or the invoker's window if on a pop-up.
   * @param c the component whose window ancestor we are looking for.
   * @return the window ancestor of the given component, or given component itself it is a window.
   */
  Window windowFor(Component c) {
    if (c == null) return null;
    if (c instanceof Window) return (Window)c;
    if (c instanceof MenuElement) {
      Component invoker = invokerFor(c);
      if (invoker != null) return windowFor(invoker);
    }
    return windowFor(parentOf(c));
  }

  /**
   * Returns the invoker, if any, of the given component. Returns <code>null</code> if the component is not on a pop-up
   * of any sort.
   * @param c the given component.
   * @return the invoker of the given component if found. Otherwise, <code>null</code>.
   */
  @RunsInCurrentThread
  Component invokerFor(Component c) {
    if (c instanceof JPopupMenu) return ((JPopupMenu)c).getInvoker();
    Component parent = c.getParent();
    if (parent == null) return null;
    return invokerFor(parent);
  }
}
