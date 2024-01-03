package com.vardemin.zero.db.vault

import com.vardemin.zero.db.config.ZeroCborFileDbConfig
import com.vardemin.zero.db.config.ZeroDbConfig

object ZeroVaultFactory {
    operator fun get(key: String, config: ZeroDbConfig): ZeroVault {
        return when(config) {
            is ZeroCborFileDbConfig -> CborVault(key, config)
        }
    }
}