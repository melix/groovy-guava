package groovyx.guava

import com.google.common.base.Function
import com.google.common.cache.CacheLoader

class CacheStaticExtension {
    public static CacheLoader from(CacheLoader loader, Closure cl) {
        CacheLoader.from(cl as Function)
    }
}
