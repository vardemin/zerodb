package com.vardemin.zero.db

import co.touchlab.stately.collections.ConcurrentMutableMap
import com.vardemin.zero.db.config.ZeroDbConfig
import com.vardemin.zero.db.vault.ZeroVault
import com.vardemin.zero.db.vault.ZeroVaultAdapter
import com.vardemin.zero.db.vault.ZeroVaultFactory

class ZeroDb(
    private val config: ZeroDbConfig
) {
    private val vaultMap by lazy {
        ConcurrentMutableMap<String, ZeroVault>()
    }

    fun getVault(key: String): ZeroVault {
        return vaultMap.getOrPut(key) { ZeroVaultFactory[key, config] }
    }

    inline fun <reified T : Any> get(key: String): T = ZeroVaultAdapter.read(getVault(key))
    inline fun <reified T : Any> getOrNull(key: String): T? = ZeroVaultAdapter.readOrNull(getVault(key))
    inline fun <reified T : Any> getOrDefault(key: String, default: T): T =
        ZeroVaultAdapter.readOrDefault(getVault(key), default)

    inline fun <reified T : Any> set(key: String, value: T) {
        ZeroVaultAdapter.write(getVault(key), value)
    }

    fun remove(key: String) {
        getVault(key).clear()
    }
}