package com.example.oursharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Lớp helper để quản lý các thao tác với SharedPreferences
 */
class PreferenceHelper(context: Context) {

    // Constants
    companion object {
        private const val PREF_NAME = "AppPreferences"

        // Keys for storing data
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_LAST_LOGIN_TIME = "last_login_time"

        // Instance for Singleton pattern
        @Volatile
        private var INSTANCE: PreferenceHelper? = null

        /**
         * Lấy instance của PreferenceHelper (Singleton pattern)
         */
        fun getInstance(context: Context): PreferenceHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferenceHelper(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }

    // SharedPreferences instance
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    /**
     * Lưu thông tin đăng nhập
     */
    fun saveUserCredentials(username: String, password: String) {
        prefs.edit {
            putString(KEY_USERNAME, username)
            putString(KEY_PASSWORD, password)
            putBoolean(KEY_IS_LOGGED_IN, true)
            putLong(KEY_LAST_LOGIN_TIME, System.currentTimeMillis())
        }
    }

    /**
     * Lấy tên người dùng đã lưu
     */
    fun getUsername(): String {
        return prefs.getString(KEY_USERNAME, "") ?: ""
    }

    /**
     * Lấy mật khẩu đã lưu
     */
    fun getPassword(): String {
        return prefs.getString(KEY_PASSWORD, "") ?: ""
    }

    /**
     * Kiểm tra xem người dùng đã đăng nhập chưa
     */
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    /**
     * Lưu ID người dùng
     */
    fun saveUserId(userId: Int) {
        prefs.edit {
            putInt(KEY_USER_ID, userId)
        }
    }

    /**
     * Lấy ID người dùng
     */
    fun getUserId(): Int {
        return prefs.getInt(KEY_USER_ID, -1)
    }

    /**
     * Lấy thời gian đăng nhập cuối cùng
     */
    fun getLastLoginTime(): Long {
        return prefs.getLong(KEY_LAST_LOGIN_TIME, 0L)
    }

    /**
     * Xóa tất cả dữ liệu người dùng (đăng xuất)
     */
    fun clearUserData() {
        prefs.edit {
            remove(KEY_USERNAME)
            remove(KEY_PASSWORD)
            remove(KEY_IS_LOGGED_IN)
            remove(KEY_USER_ID)
            remove(KEY_LAST_LOGIN_TIME)
        }
    }

    /**
     * Lưu một giá trị chuỗi bất kỳ
     */
    fun saveString(key: String, value: String) {
        prefs.edit {
            putString(key, value)
        }
    }

    /**
     * Lấy một giá trị chuỗi bất kỳ
     */
    fun getString(key: String, defaultValue: String = ""): String {
        return prefs.getString(key, defaultValue) ?: defaultValue
    }

    /**
     * Lưu một giá trị boolean
     */
    fun saveBoolean(key: String, value: Boolean) {
        prefs.edit {
            putBoolean(key, value)
        }
    }

    /**
     * Lấy một giá trị boolean
     */
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    /**
     * Lưu một giá trị số nguyên
     */
    fun saveInt(key: String, value: Int) {
        prefs.edit {
            putInt(key, value)
        }
    }

    /**
     * Lấy một giá trị số nguyên
     */
    fun getInt(key: String, defaultValue: Int = 0): Int {
        return prefs.getInt(key, defaultValue)
    }

    /**
     * Lưu một giá trị số thực
     */
    fun saveFloat(key: String, value: Float) {
        prefs.edit {
            putFloat(key, value)
        }
    }

    /**
     * Lấy một giá trị số thực
     */
    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        return prefs.getFloat(key, defaultValue)
    }

    /**
     * Lưu một giá trị số nguyên dài
     */
    fun saveLong(key: String, value: Long) {
        prefs.edit {
            putLong(key, value)
        }
    }

    /**
     * Lấy một giá trị số nguyên dài
     */
    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return prefs.getLong(key, defaultValue)
    }

    /**
     * Kiểm tra xem một key có tồn tại trong SharedPreferences không
     */
    fun contains(key: String): Boolean {
        return prefs.contains(key)
    }

    /**
     * Xóa một giá trị cụ thể khỏi SharedPreferences
     */
    fun remove(key: String) {
        prefs.edit {
            remove(key)
        }
    }

    /**
     * Xóa tất cả dữ liệu trong SharedPreferences
     */
    fun clearAll() {
        prefs.edit {
            clear()
        }
    }
}
