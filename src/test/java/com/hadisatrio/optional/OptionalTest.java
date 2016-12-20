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
import org.junit.Assert;
import org.junit.Test;

public class OptionalTest {

    @Test
    public void of() throws Exception {
        Assert.assertTrue(Optional.of("42").isPresent());

        try {
            Optional.of(null);

            Assert.fail("Invoking of() with null argument should raise an exception.");
        } catch (IllegalArgumentException expected) {
            // No-op. This is the expected behaviour.
        }
    }

    @Test
    public void ofNullable() throws Exception {
        Assert.assertTrue(Optional.ofNullable("42").isPresent());

        Assert.assertFalse(Optional.ofNullable(null).isPresent());
    }

    @Test
    public void absent() throws Exception {
        Assert.assertFalse(Optional.absent().isPresent());
    }

    @Test
    public void isPresent() throws Exception {
        Assert.assertTrue(Optional.of("42").isPresent());

        Assert.assertTrue(Optional.ofNullable("42").isPresent());

        Assert.assertFalse(Optional.ofNullable(null).isPresent());

        Assert.assertFalse(Optional.absent().isPresent());
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals("42", Optional.of("42").get());

        Assert.assertEquals("42", Optional.ofNullable("42").get());

        try {
            Optional.ofNullable(null).get();

            Optional.absent().get();

            Assert.fail("Invoking get() on an absent optional should raise an exception.");
        } catch (IllegalStateException expected) {
            // No-op. This is the expected behaviour.
        }
    }

    @Test
    public void or() throws Exception {
        Assert.assertEquals("42", Optional.ofNullable(null).or("42"));

        Assert.assertEquals("42", Optional.absent().or("42"));
    }

    @Test
    public void orWithSupplier() throws Exception {
        final Supplier<String> aStringSupplier = new Supplier<String>() {
            @Override
            public String get() {
                return "42";
            }
        };

        Assert.assertEquals("42", Optional.<String>ofNullable(null).or(aStringSupplier));

        Assert.assertEquals("42", Optional.<String>absent().or(aStringSupplier));
    }

    @Test
    public void orThrow() throws Exception {
        try {
            Optional.ofNullable(null).orThrow(new Exception("An exception occurred."));

            Optional.absent().orThrow(new Exception("An exception occurred."));

            Assert.fail("Invoking orThrow() on an absent optional should throw an exception.");
        } catch (Exception anException) {
            // No-op. This is the expected behaviour.
        }
    }

    @Test
    public void orThrowWithSupplier() throws Exception {
        final Supplier<Exception> anExceptionSupplier = new Supplier<Exception>() {
            @Override
            public Exception get() {
                return new Exception("An exception occurred.");
            }
        };

        try {
            Optional.ofNullable(null).orThrow(anExceptionSupplier);

            Optional.absent().orThrow(anExceptionSupplier);

            Assert.fail("Invoking orThrow() on an absent optional should throw an exception.");
        } catch (Exception anException) {
            // No-op. This is the expected behaviour.
        }
    }

    @Test
    public void orNull() throws Exception {
        Assert.assertNull(Optional.ofNullable(null).orNull());

        Assert.assertNull(Optional.absent().orNull());
    }

    @Test
    public void ifPresent() throws Exception {
        Optional.ofNullable("42").ifPresent(new Consumer<String>() {
            @Override
            public void consume(String value) {
                Assert.assertEquals("42", value);
            }
        });

        Optional.ofNullable(null).ifPresent(new Consumer<Object>() {
            @Override
            public void consume(Object value) {
                Assert.fail("ifPresent() on an absent optional should never call its consumer");
            }
        });

        Optional.absent().ifPresent(new Consumer<Object>() {
            @Override
            public void consume(Object value) {
                Assert.fail("ifPresent() on an absent optional should never call its consumer");
            }
        });
    }

    @Test
    public void ifPresentOrElse() throws Exception {
        Optional.ofNullable("42").ifPresentOrElse(new Consumer<String>() {
            @Override
            public void consume(String value) {
                Assert.assertEquals("42", value);
            }
        }, new Function() {
            @Override
            public void call() {
                Assert.fail("ifPresent() on an non-absent optional should call its consumer");
            }
        });

        Optional.ofNullable(null).ifPresentOrElse(new Consumer<Object>() {
            @Override
            public void consume(Object value) {
                Assert.fail("ifPresent() on an absent optional should never call its consumer");
            }
        }, new Function() {
            @Override
            public void call() {
                Assert.assertTrue(true);
                // No-op. This is the expected behaviour.
            }
        });

        Optional.absent().ifPresentOrElse(new Consumer<Object>() {
            @Override
            public void consume(Object value) {
                Assert.fail("ifPresent() on an absent optional should never call its consumer");
            }
        }, new Function() {
            @Override
            public void call() {
                Assert.assertTrue(true);
                // No-op. This is the expected behaviour.
            }
        });
    }
}
