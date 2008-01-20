/*
 * Created on Jan 19, 2008
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
package org.fest.swing.driver;

import java.awt.Component;
import java.awt.Point;

import org.fest.swing.core.RobotFixture;

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.core.Settings.*;
import static org.fest.swing.exception.ActionFailedException.actionFailure;
import static org.fest.swing.util.Platform.*;

/**
 * Understands drag and drop actions.
 *
 * <p>
 * Adapted from <code>abbot.tester.ComponentTester</code> from 
 * <a href="http://abbot.sourceforge.net" target="_blank">Abbot</a>.
 * </p>
 * 
 * @author Alex Ruiz
 */
public final class DragAndDropDriver {

  /** Number of pixels traversed before a drag starts. */
  public static final int DRAG_THRESHOLD = IS_WINDOWS || IS_MACINTOSH ? 10 : 16;

  private final RobotFixture robot;
  private final Component target;

  /**
   * Creates a new </code>{@link DragAndDropDriver}</code>.
   * @param robot the robot to use to simulate user input.
   * @param target the target <code>Component</code>. 
   */
  public DragAndDropDriver(RobotFixture robot, Component target) {
    this.robot = robot;
    this.target = target;
  }

  /**
   * Performs a drag action at the given point.
   * @param where the point where to start the drag action.
   */
  public void drag(Point where) {
    robot.mousePress(target, where, LEFT_BUTTON);
    if (dragDelay() > delayBetweenEvents()) pause(dragDelay());
    mouseMove(where.x, where.y);
    robot.waitForIdle();
  }

  private void mouseMove(int x, int y) {
    if (IS_WINDOWS || IS_MACINTOSH) {
      mouseMoveOnWindowsAndMacintosh(x, y);
      return;
    } 
    mouseMove(
        point(x + DRAG_THRESHOLD / 2, y + DRAG_THRESHOLD / 2),
        point(x + DRAG_THRESHOLD, y + DRAG_THRESHOLD),
        point(x + DRAG_THRESHOLD / 2, y + DRAG_THRESHOLD / 2),
        point(x, y)
    );
  }

  private void mouseMoveOnWindowsAndMacintosh(int x, int y) {
    int dx = dragThreshold(x, target.getWidth());
    int dy = dragThreshold(y, target.getHeight());
    if (dx == 0 && dy == 0) dx = DRAG_THRESHOLD;
    mouseMove(
        point(x + dx / 4, y + dy / 4), 
        point(x + dx / 2, y + dy / 2),
        point(x + dx, y + dy),
        point(x + dx + 1, y + dy)
    );
  }
  
  private int dragThreshold(int coordinate, int dimension) {
    return coordinate + DRAG_THRESHOLD < dimension ? DRAG_THRESHOLD : 0;    
  }
  
  private Point point(int x, int y) { return new Point(x, y); }

  /**
   * Ends a drag operation, releasing the mouse button over the given target location.
   * <p>
   * This method is tuned for native drag/drop operations, so if you get odd behavior, you might try using a simple
   * <code>{@link RobotFixture#mouseMove(Component, int, int)}</code> and
   * <code>{@link RobotFixture#releaseMouseButtons()}</code>.
   * @param where the point where the drag operation ends.
   */
  public void drop(Point where) {
    dragOver(where);
    long start = System.currentTimeMillis();
    while (!robot.isDragging()) {
      if (System.currentTimeMillis() - start > eventPostingDelay() * 4) 
        throw actionFailure("There is no drag in effect");
      pause();
    }
    if (dropDelay() > delayBetweenEvents()) pause(dropDelay() - delayBetweenEvents());
    robot.releaseMouseButtons();
    robot.waitForIdle();
  }
  
  /**
   * Move the mouse appropriately to get from the source to the destination. Enter/exit events will be generated where
   * appropriate.
   * @param where the point to drag over.
   */
  public void dragOver(Point where) {
    dragOver(where.x, where.y);
  }
  
  private void dragOver(int x, int y) {
    robot.mouseMove(target, x - 4, y);
    robot.mouseMove(target, x, y);
  }

  private void mouseMove(Point...points) {
    for (Point p : points) robot.mouseMove(target, p.x, p.y);
  }
}