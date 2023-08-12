package com.dev.demoapp.database.prefs

import com.dev.demoapp.dev.utils.LanguageUtil
import com.dev.demoapp.model.Language
import com.orhanobut.hawk.Hawk
import com.dev.demoapp.model.UserLogin

object Preference {

    private const val userLogin = "USER_LOGIN"

    private const val isLogin = "IS_LOGIN"

    private const val isReload = "RELOAD_LANGUAGE"

    private const val language = "LANGUAGE"

    private const val token_login = "TOKEN_LOGIN"

    private const val userName = "USER_NAME"

    private const val passWord = "PASSWORD"

    /**
     * Save
     * **/
    fun saveToken(token: String?) {
        if (token == null) {
            clearToken()
            return
        } else {
            Hawk.put(token_login, token)
        }
    }

    fun saveUserName(token: String?) {
        if (token == null) {
            clearUserName()
            return
        } else {
            Hawk.put(userName, token)
        }
    }

    fun savePassWord(token: String?) {
        if (token == null) {
            clearPassword()
            return
        } else {
            Hawk.put(passWord, token)
        }
    }

    fun saveUser(user: UserLogin?) {
        if (user == null) {
            clearUser()
            return
        } else {
            Hawk.put(userLogin, user)
        }
    }

    fun saveLanguage(item: Language?) {
        if (item == null) {
            clearLanguage()
            return
        }
        Hawk.put(language, item)
    }

    fun isLogin(login: Boolean?) {
        if (login == null) {
            clearLogin()
            return
        }
        Hawk.put(isLogin, login)
    }

    fun saveReload(reload: Boolean?) {
        if (reload == null) {
            clearReload()
            return
        }
        Hawk.put(isReload, reload)
    }

    /**
     * Get
     * **/
    fun getUser(): UserLogin? {
        return Hawk.get(userLogin)
    }

    fun getLanguage(): Language {
        return Hawk.get(language) ?: LanguageUtil.getLanguageDefault()
    }

    fun getLogin(): Boolean {
        return Hawk.get(isLogin) ?: false
    }

    fun getToken(): String {
        return Hawk.get(token_login) ?: ""
    }

    fun getUserName(): String {
        return Hawk.get(userName) ?: ""
    }

    fun getPassword(): String {
        return Hawk.get(passWord) ?: ""
    }

    fun getReload(): Boolean {
        return Hawk.get(isReload) ?: false
    }

    /**
     * Clear
     * **/

    fun clearAll() {
        Hawk.deleteAll()
    }

    fun clearUser() {
        Hawk.delete(userLogin)
    }

    fun clearToken() {
        Hawk.delete(token_login)
    }

    fun clearLogin() {
        Hawk.delete(isLogin)
    }

    fun clearLanguage() {
        Hawk.delete(language)
    }

    fun clearReload() {
        Hawk.delete(isReload)
    }

    fun clearUserName() {
        Hawk.delete(userName)
    }

    fun clearPassword() {
        Hawk.delete(passWord)
    }


}