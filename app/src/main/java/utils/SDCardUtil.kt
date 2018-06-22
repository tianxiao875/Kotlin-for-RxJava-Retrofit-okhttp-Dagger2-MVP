package utils

import android.content.Context
import android.os.Environment
import android.os.StatFs
import java.io.File

/**
 *描述:
 *------------------------------------------------------------------------
 * 工程:
 * #0000     tian xiao     创建日期: 2018-06-14 13:49
 *
 */
class SDCardUtil {

    companion object {


        private fun SDCardUtil() {
            /* cannot be instantiated */
            throw UnsupportedOperationException("cannot be instantiated")
        }

        /**
         * 判断SDCard是否可用
         *
         * @return
         */
        fun isSDCardEnable(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

        }

        /**
         * 获取SD卡路径
         *
         * @return
         */
        fun getSDCardPath(): String {
            return Environment.getExternalStorageDirectory().absolutePath + File.separator
        }

        /**
         * 获取APP在 sd路径
         *
         * @return
         */
        fun getAppSDCardPath(context: Context): String {
            return (Environment.getExternalStorageDirectory().absolutePath
                    + File.separator + context.packageName + File.separator)
        }

        /**
         * 获取SD卡的剩余容量 单位byte
         *
         * @return
         */
        fun getSDCardAllSize(): Long {
            if (isSDCardEnable()) {
                val stat = StatFs(getSDCardPath())
                // 获取空闲的数据块的数量
                val availableBlocks = stat.availableBlocks.toLong() - 4
                // 获取单个数据块的大小（byte）
                val freeBlocks = stat.availableBlocks.toLong()
                return freeBlocks * availableBlocks
            }
            return 0
        }

        /**
         * 获取指定路径所在空间的剩余可用容量字节数，单位byte
         *
         * @param filePath
         * @return 容量字节 SDCard可用空间，内部存储可用空间
         */
        fun getFreeBytes(filePath: String): Long {
            var filePath = filePath
            // 如果是sd卡的下的路径，则获取sd卡可用容量
            if (filePath.startsWith(getSDCardPath())) {
                filePath = getSDCardPath()
            } else {
                // 如果是内部存储的路径，则获取内存存储的可用容量
                filePath = Environment.getDataDirectory().absolutePath
            }
            val stat = StatFs(filePath)
            val availableBlocks = stat.availableBlocks.toLong() - 4
            return stat.blockSize * availableBlocks
        }

        /**
         * 获取系统存储路径
         *
         * @return
         */
        fun getRootDirectoryPath(): String {
            return Environment.getRootDirectory().absolutePath
        }

        fun initAppSDCardPath(context: Context) {
            val sdCardPath = getSDCardPath()
            val packageName = context.packageName
            val path = sdCardPath + packageName
            val file = File(path)
            if (isSDCardEnable()) {
                if (!file.exists() || !file.isDirectory) {
                    file.mkdirs()
                }
            }
        }

    }

}