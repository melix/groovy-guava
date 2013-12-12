package groovyx.guava

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import spock.lang.Specification

class CacheSpecification extends Specification {
    def "should create a cache loader"() {
        when: "we call the static from method on CacheLoader"
            def loader = CacheLoader.from { String str ->
                str.toUpperCase()
            }
        then: "a cache loader is created"
            loader instanceof CacheLoader
        when: "a cache is created"
            def cache = CacheBuilder.newBuilder().build(loader)
        then: "the cache is empty"
            cache.size() == 0
        when: "we request for a key"
            def value = cache.get('foo')
        then: "the uppercase version is returned"
            value == 'FOO'
    }
}
