package com.stanfy.stats;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import android.app.Activity;

/**
 * Tests for error reports messages.
 * @author Roman Mazur (Stanfy - http://www.stanfy.com)
 */
public class ErrorReportTest {

  /** Example stack trace. */
  private static final String EXAMPLE_ST =
        "android.database.sqlite.SQLiteConstraintException: error code 19: constraint failed\n"
        + "at android.database.sqlite.SQLiteStatement.native_execute(Native Method)\n"
        + "at android.database.sqlite.SQLiteStatement.execute(SQLiteStatement.java:61)\n"
        + "at android.database.sqlite.SQLiteDatabase.insertWithOnConflict(SQLiteDatabase.java:1582)\n"
        + "at android.database.sqlite.SQLiteDatabase.insert(SQLiteDatabase.java:1426)\n"
        + "at com.stanfy.content.AppContentProvider.insert(AppContentProvider.java:109)\n"
        + "at android.content.ContentProvider$Transport.insert(ContentProvider.java:198)\n"
        + "at android.content.ContentResolver.insert(ContentResolver.java:604)\n"
        + "at com.stanfy.images.DefaultImagesDAO.createCachedImage(DefaultImagesDAO.java:77)\n"
        + "at com.stanfy.images.ImagesManager$ImageLoader.call(ImagesManager.java:736)\n"
        + "at com.stanfy.images.ImagesManager$ImageLoader.call(ImagesManager.java:1)\n"
        + "at java.util.concurrent.FutureTask$Sync.innerRun(FutureTask.java:306)\n"
        + "at java.util.concurrent.FutureTask.run(FutureTask.java:138)\n"
        + "at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1088)\n"
        + "at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:581)\n"
        + "at java.lang.Thread.run(Thread.java:1019)";


  @Test
  public void testStackTraceTrim() {
    final String trimmed = StatsManagerAdapter.trimStackTrace(EXAMPLE_ST);
    assertTrue("Stack trace is not trimmed", trimmed.length() < EXAMPLE_ST.length());
    assertEquals("Stack trace has \\n", -1, trimmed.indexOf('\n'));
    assertEquals("Stack trace has \\r", -1, trimmed.indexOf('\r'));
    assertEquals("Stack trace has \\t", -1, trimmed.indexOf('\t'));
    assertEquals("Exception class has not been cut", 0, trimmed.indexOf("SQLiteConstraintException"));
  }

  @Test
  public void testReadError() {
    final int maxLen = 255;
    final String msg = new StatsManagerAdapter() {
      @Override
      public void onStartScreen(final Activity activity) {
      }
      @Override
      public void onLeaveScreen(final Activity activity) {
      }
      @Override
      public void onComeToScreen(final Activity activity) {
      }
      @Override
      public void event(final String tag, final Map<String, String> params) {
      }
      @Override
      public void error(final String tag, final Throwable e) {
      }
    }
    .readException(new Throwable(), maxLen);
    assertTrue(msg.length() <= maxLen);
    assertTrue(msg.startsWith("Throwable"));
  }

}
