package com.juanalmeyda.featureflags.repository

import com.juanalmeyda.featureflags.FeatureFlag
import redis.clients.jedis.JedisPool

class RedisFeatureFlagRepository(val jedisPool: JedisPool) : FeatureFlagRepository {

    override fun getFeatureFlag(id: String): FeatureFlag {
        val enabled = jedisPool.resource.use { it.get(id)?.toBoolean() == true }
        return FeatureFlag(id, enabled)
    }

    override fun updateFeatureFlag(id: String, enabled: Boolean) {
        jedisPool.resource.use { it.set(id, enabled.toString()) }
    }
}
