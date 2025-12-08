package org.greenrobot.greendao.internal;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

/* loaded from: classes.dex */
public final class FastCursor implements Cursor {
    public final CursorWindow a;
    public int b;
    public final int c;

    public FastCursor(CursorWindow cursorWindow) {
        this.a = cursorWindow;
        this.c = cursorWindow.getNumRows();
    }

    @Override // android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public void deactivate() {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public byte[] getBlob(int i) {
        return this.a.getBlob(this.b, i);
    }

    @Override // android.database.Cursor
    public int getColumnCount() {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public int getColumnIndex(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public int getColumnIndexOrThrow(String str) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public String getColumnName(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public String[] getColumnNames() {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public int getCount() {
        return this.a.getNumRows();
    }

    @Override // android.database.Cursor
    public double getDouble(int i) {
        return this.a.getDouble(this.b, i);
    }

    @Override // android.database.Cursor
    public Bundle getExtras() {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public float getFloat(int i) {
        return this.a.getFloat(this.b, i);
    }

    @Override // android.database.Cursor
    public int getInt(int i) {
        return this.a.getInt(this.b, i);
    }

    @Override // android.database.Cursor
    public long getLong(int i) {
        return this.a.getLong(this.b, i);
    }

    @Override // android.database.Cursor
    public Uri getNotificationUri() {
        return null;
    }

    @Override // android.database.Cursor
    public int getPosition() {
        return this.b;
    }

    @Override // android.database.Cursor
    public short getShort(int i) {
        return this.a.getShort(this.b, i);
    }

    @Override // android.database.Cursor
    public String getString(int i) {
        return this.a.getString(this.b, i);
    }

    @Override // android.database.Cursor
    public int getType(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public boolean getWantsAllOnMoveCalls() {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public boolean isAfterLast() {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public boolean isBeforeFirst() {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public boolean isClosed() {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public boolean isFirst() {
        return this.b == 0;
    }

    @Override // android.database.Cursor
    public boolean isLast() {
        return this.b == this.c - 1;
    }

    @Override // android.database.Cursor
    public boolean isNull(int i) {
        return this.a.isNull(this.b, i);
    }

    @Override // android.database.Cursor
    public boolean move(int i) {
        return moveToPosition(this.b + i);
    }

    @Override // android.database.Cursor
    public boolean moveToFirst() {
        this.b = 0;
        return this.c > 0;
    }

    @Override // android.database.Cursor
    public boolean moveToLast() {
        int i = this.c;
        if (i <= 0) {
            return false;
        }
        this.b = i - 1;
        return true;
    }

    @Override // android.database.Cursor
    public boolean moveToNext() {
        int i = this.b;
        if (i >= this.c - 1) {
            return false;
        }
        this.b = i + 1;
        return true;
    }

    @Override // android.database.Cursor
    public boolean moveToPosition(int i) {
        if (i < 0 || i >= this.c) {
            return false;
        }
        this.b = i;
        return true;
    }

    @Override // android.database.Cursor
    public boolean moveToPrevious() {
        int i = this.b;
        if (i <= 0) {
            return false;
        }
        this.b = i - 1;
        return true;
    }

    @Override // android.database.Cursor
    public void registerContentObserver(ContentObserver contentObserver) {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public boolean requery() {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public Bundle respond(Bundle bundle) {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public void setNotificationUri(ContentResolver contentResolver, Uri uri) {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public void unregisterContentObserver(ContentObserver contentObserver) {
        throw new UnsupportedOperationException();
    }

    @Override // android.database.Cursor
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        throw new UnsupportedOperationException();
    }
}
