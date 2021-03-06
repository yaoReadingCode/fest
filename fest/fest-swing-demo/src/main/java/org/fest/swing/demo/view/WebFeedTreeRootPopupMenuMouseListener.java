/*
 * Created on Apr 23, 2008
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
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.demo.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import static org.fest.swing.demo.view.WebFeedTreeListeners.*;

/**
 * Understands a mouse listener that shows a pop-up menu on the root node of a <code>{@link WebFeedTree}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class WebFeedTreeRootPopupMenuMouseListener {

  static void attachTo(WebFeedTree tree) {
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem item = popupMenu.add(new AddWebFeedOrFolderAction(tree.mainFrame()));
    item.setName("add");
    tree.addMouseListener(new Listener(popupMenu));
  }

  private WebFeedTreeRootPopupMenuMouseListener() {}

  private static class Listener extends MouseAdapter {
    private final JPopupMenu popupMenu;

    Listener(JPopupMenu popupMenu) {
      this.popupMenu = popupMenu;
    }

    @Override public void mouseReleased(MouseEvent e) {
      if (!e.isPopupTrigger()) return;
      if (!isSelectionRootNode(e)) return;
      popupMenu.show(webFeedTreeFrom(e), e.getX(), e.getY());
    }
  }
}
