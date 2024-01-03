package com.vardemin.zero.db.vault

import com.vardemin.zero.db.exception.ZeroDbException

object ZeroVaultAdapter {
    inline fun <reified T: Any> read(vault: ZeroVault): T {
        return when(vault) {
            is CborVault -> vault.read<T>()
        }
    }

    inline fun <reified T: Any> readOrNull(vault: ZeroVault): T? {
        return when(vault) {
            is CborVault -> try {
                vault.read<T>()
            } catch (_: ZeroDbException) {
                null
            }
        }
    }

    inline fun <reified T: Any> readOrDefault(vault: ZeroVault, default: T): T {
        return readOrNull(vault) ?: default
    }

    inline fun <reified T: Any> write(vault: ZeroVault, value: T) {
        when(vault) {
            is CborVault -> vault.write(value)
        }
    }

    fun clear(vault: ZeroVault) {
        vault.clear()
    }
}