package com.konifar.materialcat.models.pojo

import android.util.Base64
import android.util.Log
import java.io.*

open class Model : Serializable, Cloneable {

    fun serializeToString(): String? {
        var encoded: String? = null
        try {
            val baos = ByteArrayOutputStream()
            val os = ObjectOutputStream(baos)
            os.writeObject(this)
            os.close()
            encoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
        } catch (e: IOException) {
            Log.e(TAG, e.message)
        }

        return encoded
    }

    companion object {

        private const val serialVersionUID = 1L
        private val TAG = Model::class.java.simpleName

        fun deSerializeFromString(string: String?): Model? {
            if (string == null) {
                return null
            }
            val bytes = Base64.decode(string.toByteArray(), Base64.DEFAULT)
            var data: Model? = null
            try {
                val `is` = ObjectInputStream(ByteArrayInputStream(bytes))
                data = `is`.readObject() as Model
            } catch (e: IOException) {
                Log.e(TAG, e.message)
            } catch (e: ClassNotFoundException) {
                Log.e(TAG, e.message)
            } catch (e: ClassCastException) {
                Log.e(TAG, e.message)
            }

            return data
        }
    }

}
