package com.konifar.materialcat.models.pojo;

import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Model implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    private static final String TAG = Model.class.getSimpleName();

    public static Model deSerializeFromString(String string) {
        if (string == null) {
            return null;
        }
        byte[] bytes = Base64.decode(string.getBytes(), Base64.DEFAULT);
        Model data = null;
        try {
            ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(bytes));
            data = (Model) is.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            Log.e(TAG, e.getMessage());
        }
        return data;
    }

    public String serializeToString() {
        String encoded = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(baos);
            os.writeObject(this);
            os.close();
            encoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return encoded;
    }

}
