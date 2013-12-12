package groovyx.guava

import com.google.common.base.Predicate
import com.google.common.collect.AbstractIterator
import groovy.transform.CompileStatic
import spock.lang.Specification

class IteratorsSpecification extends Specification {
    void "should advance iterator"() {
        given:
            def it = (0..10).iterator()
        when:
            it.advance(num)
        then:
            it.next() == value
        where:
            num || value
            0   || 0
            1   || 1
            2   || 2
            3   || 3
            10  || 10
    }

    void "Iterators.all should be available on iterator using explicit predicate"() {
        given:
            def itr = ['Jochen','Paul','Guillaume','Cedric'].iterator()
        expect:
            itr.all ({ it.length() > 3 } as Predicate)
    }

    void "Iterators.all should be available on iterator using transparent closure coercion"() {
        given:
            def itr = ['Jochen','Paul','Guillaume','Cedric'].iterator()
        expect:
            itr.all { it.length() > 3 } // fails
    }

    void "Java way to create a lazy Fibonacci iterator"() {
        given:
            def fib = new AbstractIterator<Integer>() {
                int p0 = 1
                int p1 = 2

                @Override
                protected Integer computeNext() {
                    int p = p0
                    p0 = p1
                    p1 = p0+p
                    p
                }
            }
        expect:
            (0..5).collect { fib.next() } == [1,2,3,5,8,13]
    }

    void "Groovy way to create a lazy Fibonacci iterator"() {
        given:
            def p0 = 1
            def p1 = 2
            def fib = {
                def p
                (p, p0,p1) = [p0,p1,p0+p1]
                p
            } as AbstractIterator
        expect:
            (0..5).collect { fib.next() } == [1,2,3,5,8,13]
    }

    void "Alternate syntax to create a lazy Fibonacci iterator"() {
        given:
            def fib = {
                def p
                (p, p0,p1) = [p0,p1,p0+p1]
                p
            }.rehydrate([p0:1,p1:2], this, this) as AbstractIterator
        expect:
            (0..5).collect { fib.next() } == [1,2,3,5,8,13]
    }

    void "Nicer syntax to create a lazy Fibonacci iterator"() {
        given:
            def fib = build(p0:1, p1:2, p:0) {
                (p, p0,p1) = [p0,p1,p0+p1]
                p
            } as AbstractIterator
        expect:
            (0..5).collect { fib.next() } == [1,2,3,5,8,13]
    }

    void "A not so nice syntax to create a lazy Fibonacci iterator"() {
        given:
            def fib = build(p0:1, p1:2, p:0) {
                ((p, p0,p1) = [p0,p1,p0+p1]).getFirst()
            } as AbstractIterator
        expect:
            (0..5).collect { fib.next() } == [1,2,3,5,8,13]
    }

    private static Closure build(Map binding, Closure cl) {
        cl.rehydrate(binding, cl.owner, cl.thisObject)
    }

}
