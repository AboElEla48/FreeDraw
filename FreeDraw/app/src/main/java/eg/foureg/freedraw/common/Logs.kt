package eg.foureg.freedraw.common

import android.util.Log

class Logs private constructor(){

    companion object {
        private const val MASTER_LOGGER = true

        fun debug(tag : String, msg : String) {
            if(MASTER_LOGGER) {
                Log.d(tag, msg)

            }
        }

        fun error(tag : String, msg : String) {
            if(MASTER_LOGGER) {
                Log.e(tag, msg)
            }
        }

        fun warning(tag : String, msg : String) {
            if(MASTER_LOGGER) {
                Log.w(tag, msg)
            }
        }
    }
}