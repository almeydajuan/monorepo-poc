package com.juanalmeyda.featureflags.repository

import com.juanalmeyda.infra.Composed
import org.junit.jupiter.api.BeforeEach
import redis.clients.jedis.JedisPool

@Composed
class RedisFeatureFlagRepositoryTest : FeatureFlagRepositoryContractTest {
    val jedisPool = JedisPool("localhost", 6379)
    override val featureFlagRepository = RedisFeatureFlagRepository(jedisPool)

    @BeforeEach
    fun cleanup() {
        jedisPool.resource.use { it.flushDB() }
    }
}
