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

/**
 * A container object which may or may not contain a int value.
 * If a value is present, {@code isPresent()} will return {@code true} and
 * {@code get()} will return the value.
 * <p>
 * <p>Additional methods that depend on the presence or absence of a contained
 * value are provided, such as {@link #or(float) orElse()}
 * (return a default value if value not present),
 * {@link #ifPresent(FloatConsumer) ifPresent()} (execute a block of code
 * if the value is present), and
 * {@link #ifPresentOrElse(FloatConsumer, Function) ifPresentOrElse()} (execute
 * a block of code if the value is present, another block otherwise).
 *
 * @author Hadi Satrio
 */
public final class OptionalFloat implements Serializable {

    /**
     * Common instance for {@code OptionalFloat}s with no value present / {@link #absent()}.
     */
    private static final OptionalFloat ABSENT = new OptionalFloat();

    /**
     * Indicates whether or not the value is present.
     */
    private final boolean isPresent;

    /**
     * The value.
     */
    private final float value;

    /**
     * Constructs an absent instance.
     */
    private OptionalFloat() {
        this.isPresent = false;
        this.value = 0;
    }

    /**
     * Constructs an instance with the value present.
     *
     * @param value the value to be present
     */
    private OptionalFloat(float value) {
        this.isPresent = true;
        this.value = value;
    }

    /**
     * Returns an {@code OptionalFloat} with the specified present value.
     *
     * @param value the value to be present
     * @return an {@code OptionalFloat} with the value present
     */
    public static OptionalFloat of(float value) {
        return new OptionalFloat(value);
    }

    /**
     * Returns an absent {@code OptionalFloat} instance.  No value is present for this
     * Optional.
     *
     * @return an absent {@code OptionalFloat}
     */
    public static OptionalFloat absent() {
        return ABSENT;
    }

    /**
     * Return {@code true} if there is a value present, otherwise {@code false}.
     *
     * @return {@code true} if there is a value present, otherwise {@code false}
     */
    public boolean isPresent() {
        return isPresent;
    }

    /**
     * If a value is present in this {@code OptionalFloat}, returns the value,
     * otherwise throws {@code IllegalStateException}.
     *
     * @return the value held by this {@code OptionalFloat}
     * @throws IllegalStateException if there is no value present
     */
    public float get() {
        if (isPresent()) {
            return value;
        }
        throw new IllegalStateException("Value is absent.");
    }

    /**
     * Return the value if present, otherwise return {@code other}.
     *
     * @param other the value to be returned if there is no value present
     * @return the value, if present, otherwise {@code other}
     */
    public float or(float other) {
        return isPresent() ? value : other;
    }

    /**
     * Return the value if present, otherwise invoke {@code otherSupplier} and return
     * the result of that invocation.
     *
     * @param otherSupplier a {@code Supplier} whose result is returned if no value
     *                      is present
     * @return the value if present otherwise the result of {@code otherSupplier.get()}
     * @throws IllegalArgumentException if {@code otherSupplier} is null
     */
    public float or(FloatSupplier otherSupplier) {
        if (otherSupplier == null) {
            throw new IllegalArgumentException("null may not be passed as an argument.");
        }

        return isPresent() ? value : otherSupplier.get();
    }

    /**
     * Return the contained value, if present, otherwise throw an exception
     * instance provided as the parameter.
     *
     * @param <X>       Type of the exception to be thrown
     * @param throwable The throwable instance to be thrown
     * @return the present value
     * @throws X                        if there is no value present
     * @throws IllegalArgumentException if {@code throwable} is null
     */
    public <X extends Throwable> float orThrow(X throwable) throws X {
        if (throwable == null) {
            throw new IllegalArgumentException("null may not be passed as an argument.");
        }

        if (isPresent()) {
            return value;
        }
        throw throwable;
    }

    /**
     * Return the contained value, if present, otherwise throw an exception
     * to be created by the provided supplier.
     *
     * @param <X>               Type of the exception to be thrown
     * @param throwableSupplier a {@code Supplier} which will return the exception to
     *                          be thrown
     * @return the present value
     * @throws X                        if there is no value present
     * @throws IllegalArgumentException if {@code throwableSupplier} is null
     */
    public <X extends Throwable> float orThrow(Supplier<? extends X> throwableSupplier) throws X {
        if (throwableSupplier == null) {
            throw new IllegalArgumentException("null may not be passed as an argument.");
        }

        if (isPresent()) {
            return value;
        }
        throw throwableSupplier.get();
    }

    /**
     * If a value is present, invoke the specified consumer with the value,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a value is present
     */
    public void ifPresent(FloatConsumer consumer) {
        if (isPresent()) {
            consumer.consume(value);
        }
    }

    /**
     * If a value is present, invoke the specified consumer with the value,
     * otherwise invoke the function passed as the second parameter.
     *
     * @param consumer block to be executed if a value is present
     * @param function block to be executed if a value is absent
     */
    public void ifPresentOrElse(FloatConsumer consumer, Function function) {
        if (isPresent()) {
            consumer.consume(value);
        } else {
            function.call();
        }
    }

    /**
     * Indicates whether some other object is "equal to" this Optional. The
     * other object is considered equal if:
     * <ul>
     * <li>it is also an {@code OptionalFloat} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via the "{@code ==}"
     * operator.
     * </ul>
     *
     * @param object an object to be tested for equality
     * @return {code true} if the other object is "equal to" this object
     * otherwise {@code false}
     */
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

    /**
     * Returns the hash code value of the present value, if any, or 0 (zero) if
     * no value is present.
     *
     * @return hash code value of the present value or 0 if no value is present
     */
    @Override
    public int hashCode() {
        return isPresent() ? new Float(value).hashCode() : 0;
    }

    /**
     * Returns a non-empty string representation of this OptionalFloat suitable for
     * debugging. The exact presentation format is unspecified and may vary
     * between implementations and versions.
     *
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return isPresent() ? String.format("OptionalFloat[%s]", value) : "OptionalFloat.ABSENT";
    }
}
