package com.juanalmeyda.featureflags.repository

import com.juanalmeyda.infra.WithDatabase
import org.junit.jupiter.api.BeforeEach
import redis.clients.jedis.JedisPool

@WithDatabase
class RedisFeatureFlagRepositoryTest : FeatureFlagRepositoryContractTest {
    val jedisPool = JedisPool("localhost", 6379)
    override val featureFlagRepository = RedisFeatureFlagRepository(jedisPool)

    @BeforeEach
    fun cleanup() {
        jedisPool.resource.use { it.flushDB() }
    }
}
