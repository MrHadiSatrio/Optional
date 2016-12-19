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

import com.hadisatrio.optional.function.Consumer;
import com.hadisatrio.optional.function.Function;
import com.hadisatrio.optional.function.Supplier;

import java.io.Serializable;

public final class Optional<T> implements Serializable {

    private static final Optional<?> ABSENT = new Optional<>();

    private final T value;

    private Optional() {
        this.value = null;
    }

    private Optional(T value) {
        if (value == null) {
            throw new IllegalArgumentException();
        } else {
            this.value = value;
        }
    }

    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    public static <T> Optional<T> ofNullable(T value) {
        return value == null ? (Optional<T>) absent() : of(value);
    }

    public static <T> Optional<T> absent() {
        return (Optional<T>) ABSENT;
    }

    public boolean isPresent() {
        return value != null;
    }

    public T get() {
        if (isPresent()) {
            return value;
        }
        throw new IllegalStateException("Value is absent.");
    }

    public T or(T other) {
        if (other == null) {
            throw new IllegalArgumentException("null may not be passed as an argument; use orNull() instead.");
        }

        return isPresent() ? value : other;
    }

    public T or(Supplier<T> otherSupplier) {
        if (otherSupplier == null) {
            throw new IllegalArgumentException("null may not be passed as an argument; use orNull() instead.");
        }

        return isPresent() ? value : otherSupplier.get();
    }

    public <X extends Throwable> T orThrow(X throwable) throws X {
        if (throwable == null) {
            throw new IllegalArgumentException("null may not be passed as an argument; use orNull() instead.");
        }

        if (isPresent()) {
            return value;
        }
        throw throwable;
    }

    public <X extends Throwable> T orThrow(Supplier<? extends X> throwableSupplier) throws X {
        if (throwableSupplier == null) {
            throw new IllegalArgumentException("null may not be passed as an argument; use orNull() instead.");
        }

        if (isPresent()) {
            return value;
        }
        throw throwableSupplier.get();
    }

    public T orNull() {
        return isPresent() ? value : null;
    }

    public void ifPresent(Consumer<T> consumer) {
        if (isPresent()) {
            consumer.consume(value);
        }
    }

    public void ifPresentOrElse(Consumer<T> consumer, Function function) {
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

        if (!(object instanceof Optional)) {
            return false;
        }

        final Optional<?> other = (Optional<?>) object;
        return (value == null) ? (other.value == null) : value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return isPresent() ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return isPresent() ? String.format("Optional[%s]", value) : "Optional.ABSENT";
    }
}
