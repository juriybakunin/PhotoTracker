package test.example.phototracker;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;

import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.util.regex.Pattern;

import tenet.lib.base.MyLog;
import test.example.phototracker.common.GraphicOverlay;
import test.example.phototracker.textrecognition.TextGraphic;

public class RectGraphic extends GraphicOverlay.Graphic {
    private static final Pattern patternId = Pattern.compile("\\d\\d\\d\\d");
    private static final String textPrefix = "www.";
    RectF mRect;
    final Paint mPaint;
    public RectGraphic(GraphicOverlay overlay, RectF bounds) {
        super(overlay);
        mRect = bounds;
        mPaint = new Paint();
        mPaint.setColor(0x660000ff);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public static GraphicOverlay.Graphic createBlock(GraphicOverlay graphicOverlay, TextGraphic idBlock, TextGraphic textBlock) {

        MyLog.log("Blocks: "+idBlock.getText()+"; "+textBlock.getText());
        RectF r = new RectF(
                textBlock.getRect().left,
                idBlock.getRect().top,
                idBlock.getRect().right,
                textBlock.getRect().bottom
        );
        return new RectGraphic(graphicOverlay, r);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(mRect, mPaint);
    }

    public static boolean isIdBlock(FirebaseVisionText.Element block){
        String text = block.getText();
        return !TextUtils.isEmpty(text) && patternId.matcher(text).matches();
    }

    public static boolean isTextBlock(FirebaseVisionText.Element block){
        String text = block.getText();
        return !TextUtils.isEmpty(text) && text.toLowerCase().startsWith(textPrefix);
    }
}
