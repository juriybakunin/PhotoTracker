// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package test.example.phototracker.textrecognition;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.firebase.ml.vision.text.FirebaseVisionText;

import test.example.phototracker.common.GraphicOverlay;

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class TextGraphic extends GraphicOverlay.Graphic {

  private final FirebaseVisionText.Element text;
  private RectF mDrawRect;

  TextGraphic(GraphicOverlay overlay, FirebaseVisionText.Element text) {
    super(overlay);

    this.text = text;
    mDrawRect = getDrawRect(text.getBoundingBox());

  }

  /** Draws the text block annotations for position, size, and raw value on the supplied canvas. */
  @Override
  public void draw(Canvas canvas) {
    if (text == null) {
      throw new IllegalStateException("Attempting to draw a null text.");
    }

    // Draws the bounding box around the TextBlock.
//    RectF rect = new RectF(text.getBoundingBox());
//    rect.left = translateX(rect.left);
//    rect.top = translateY(rect.top);
//    rect.right = translateX(rect.right);
//    rect.bottom = translateY(rect.bottom);
    canvas.drawRect(mDrawRect, getOverlay().rectPaint);
    // Renders the text at the bottom of the box.
    canvas.drawText(text.getText(), mDrawRect.left+4, mDrawRect.bottom - 4, getOverlay().textPaint);
  }

  public String getText() {
    return text.getText();
  }

  public RectF getRect() {
    return mDrawRect;
  }
}
