/*
 * Created on Feb 15, 2008
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
package org.fest.assertions;

import static java.awt.Color.RED;
import static org.fest.assertions.Resources.file;
import static org.testng.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for <code>{@link ImageReader}</code>.
 *
 * @author Yvonne Wang
 */
public class ImageReaderTest {

  private ImageReader imageReader;

  @BeforeClass public void setUp() {
    imageReader = new ImageReader();
  }

  @Test public void shouldReadImageFile() throws IOException {
    final File imageFile = file("red.png");
    BufferedImage image = imageReader.read(imageFile);
    assertNotNull(image);
    int w = image.getWidth();
    assertEquals(w, 6);
    int h = image.getHeight();
    assertEquals(h, 6);
    for (int x = 0; x < w; x++)
      for (int y = 0; y < h; y++)
        assertEquals(image.getRGB(x, y), RED.getRGB());
  }

}
