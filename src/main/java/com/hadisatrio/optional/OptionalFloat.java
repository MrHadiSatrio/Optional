/*
 *     Copyright (C) 2016 Hadi Satrio
 *
 *     Permission is hereby granted, free of charge, to any person obtaining a copy
 *     of this software and associated documentation files (the "Software"), to deal
 *     in the Software without restriction, including without limitation the rights
 *     to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *     copies of the Software, and to permit persons to whom the Software is
 *     furnished to do so, subject to the following conditions:
 *
 *     The above copyright notice and this permission notice shall be included in all
 *     copies or substantial portions of the Software.
 *
 *     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *     IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *     FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *     AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *     LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *     OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *     SOFTWARE.
 */

package com.hadisatrio.optional;

import com.hadisatrio.optional.function.FloatConsumer;
import com.hadisatrio.optional.function.FloatSupplier;
import com.hadisatrio.optional.function.Function;
import com.hadisatrio.optional.function.Supplier;

import java.io.Serializable;

public final class OptionalFloat implements Serializable {

    private static final OptionalFloat ABSENT = new OptionalFloat();

    private final boolean isPresent;
    private final float value;

    private OptionalFloat() {
        this.isPresent = false;
        this.value = 0;
    }

    private OptionalFloat(float value) {
        this.isPresent = true;
        this.value = value;
    }

    public static OptionalFloat of(float value) {
        return new OptionalFloat(value);
    }

    public static OptionalFloat absent() {
        return ABSENT;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public float get() {
        if (isPresent()) {
            return value;
        }
        throw new IllegalStateException("Value is absent.");
    }

    public float or(float other) {
        return isPresent() ? value : other;
    }

    public float or(FloatSupplier otherSupplier) {
        if (otherSupplier == null) {
            throw new IllegalArgumentException("null may not be passed as an argument.");
        }

        return isPresent() ? value : otherSupplier.get();
    }

    public <X extends Throwable> float orThrow(X throwable) throws X {
        if (throwable == null) {
            throw new IllegalArgumentException("null may not be passed as an argument.");
        }

        if (isPresent()) {
            return value;
        }
        throw throwable;
    }

    public <X extends Throwable> float orThrow(Supplier<? extends X> throwableSupplier) throws X {
        if (throwableSupplier == null) {
            throw new IllegalArgumentException("null may not be passed as an argument.");
        }

        if (isPresent()) {
            return value;
        }
        throw throwableSupplier.get();
    }

    public void ifPresent(FloatConsumer consumer) {
        if (isPresent()) {
            consumer.consume(value);
        }
    }

    public void ifPresentOrElse(FloatConsumer consumer, Function function) {
        if (isPresent()) {
            consumer.consume(value);
        } else {
            function.call();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof OptionalFloat)) {
            return false;
        }

        final OptionalFloat other = (OptionalFloat) object;
        return (isPresent() && other.isPresent())
                ? get() == other.get()
                : isPresent() == other.isPresent();
    }

    @Override
    public int hashCode() {
        return isPresent() ? new Float(value).hashCode() : 0;
    }

    @Override
    public String toString() {
        return isPresent() ? String.format("OptionalFloat[%s]", value) : "OptionalFloat.ABSENT";
    }
}
