package groovyx.guava

import com.google.common.base.Predicate
import com.google.common.collect.AbstractIterator
import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSortedSet
import spock.lang.Specification

class ImmutablesSpecification extends Specification {
    def "should create an immutable list"() {
        given: "any iterable object"
            def iterable = source
        when: "we call the asImmutableList extension method"
            def imm = iterable.asImmutableList()
        then: "an immutable list is created"
            imm instanceof ImmutableList
            imm.size() == source.size()
            imm.containsAll(source)
        and: "if we add an additional element to the source collection"
            source << 'extra'
        then: "the immutable version hasn't changed"
            imm.size() == source.size() -1
        where:
            source << [
                [],
                [1,2,3],
                ['a','b','c'],
                [1,'a','b',4],
                new HashSet(['foo','bar'])
                ]
    }

    def "should create an immutable sorted set"() {
        given: "any iterable object"
            def iterable = source
        when: "we call the asImmutableSortedSet extension method"
            def imm = iterable.asImmutableSortedSet(comparator)
        then: "an immutable sorted set is created"
            imm instanceof ImmutableSortedSet
            imm.size() == expectedSize
            imm.containsAll(source)
        when: "we compare it to a mutable set"
            def result = new TreeSet(comparator)
            result.addAll source
        then: "it is sorted appropriately"
            imm.collect() == result.collect()
        where:
            source                      | comparator              || expectedSize
            []                          | {}                      || 0
            [3, 1, 2]                   | { a, b -> a<=>b }       || 3
            ['c', 'a', 'b']             | { a, b -> a<=>b }       || 3
            [1, 'a', 'b', 4, 4, '1']    | { a, b -> "$a"<=>"$b" } || 4
            new HashSet(['foo', 'bar']) | { a, b -> a<=>b }       || 2
    }

    def "test concatenation using + operator"() {
        given: "an immutable list and a mutable list"
            def list = ['a','b','c'].asImmutableList()
            def more = [1,2,3]
        when: "we concatenate the immutable list with the mutable list"
            def result = list + more
        then: "result is an immutable list"
            result instanceof ImmutableList
        and: "it contains elements of both lists in order"
            result.containsAll(list)
            result.containsAll(more)
            result.collect() == [*list, *more]
    }
}
