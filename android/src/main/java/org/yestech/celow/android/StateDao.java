package org.yestech.celow.android;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;
import android.util.Log;
import org.yestech.celow.core.GameResultEnum;
import org.yestech.celow.core.IState;
import org.yestech.celow.core.State;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.getExternalStorageState;
import static java.lang.Enum.valueOf;

/**
 * Always try to save to external storage.  then fall back to internal if not available...
 *
 */
public class StateDao {
    private final static String TAG = "StateDao";

    private static final String FILENAME_NAME = "gameState.db";

    private Context context;

    public StateDao(Context context) {
        this.context = context;
    }

    public void save(IState state) {

        ObjectOutputStream stream = null;
        try {
            FileOutputStream fileStream = null;
            if (Environment.MEDIA_MOUNTED.equals(getExternalStorageState())) {
                //save to external
                File externalFilesDir = context.getExternalFilesDir(null);
                fileStream = new FileOutputStream(new File(externalFilesDir, FILENAME_NAME));
            } else {
                //save internal
                fileStream = context.openFileOutput(FILENAME_NAME, Context.MODE_PRIVATE);
            }
            stream = new ObjectOutputStream(fileStream);
            stream.writeObject(state);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(stream);
        }
    }

    private void closeStream(ObjectOutputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }

    private void closeStream(ObjectInputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }

    public void delete() {
        FileOutputStream fileStream = null;
        if (Environment.MEDIA_MOUNTED.equals(getExternalStorageState())) {
            //save to external
            File externalFilesDir = context.getExternalFilesDir(null);
            File gameStateFile = new File(externalFilesDir, FILENAME_NAME);
            //delete external file
            gameStateFile.delete();
        }
        //delete internal
        context.deleteFile(FILENAME_NAME);
    }

    public IState load() {
        IState state = new State();
        ObjectInputStream stream = null;
        try {
            FileInputStream fileStream = null;
            if (Environment.MEDIA_MOUNTED.equals(getExternalStorageState())) {
                //save to external
                File externalFilesDir = context.getExternalFilesDir(null);
                fileStream = new FileInputStream(new File(externalFilesDir, FILENAME_NAME));
            } else {
                //save internal
                fileStream = context.openFileInput(FILENAME_NAME);
            }
            stream = new ObjectInputStream(fileStream);
            state = (IState) stream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeStream(stream);
        }
        return state;
    }

}
