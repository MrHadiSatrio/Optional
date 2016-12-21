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

import com.hadisatrio.optional.function.ShortConsumer;
import com.hadisatrio.optional.function.ShortSupplier;
import com.hadisatrio.optional.function.Function;
import com.hadisatrio.optional.function.Supplier;
import org.junit.Assert;
import org.junit.Test;

public class OptionalShortTest {

    @Test
    public void of() throws Exception {
        Assert.assertTrue(OptionalShort.of(((short) 42)).isPresent());
    }

    @Test
    public void absent() throws Exception {
        Assert.assertFalse(OptionalShort.absent().isPresent());
    }

    @Test
    public void isPresent() throws Exception {
        Assert.assertTrue(OptionalShort.of(((short) 42)).isPresent());

        Assert.assertFalse(OptionalShort.absent().isPresent());
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(((short) 42), OptionalShort.of(((short) 42)).get());

        try {
            OptionalShort.absent().get();

            Assert.fail("Invoking get() on an absent optional should raise an exception.");
        } catch (IllegalStateException expected) {
            // No-op. This is the expected behaviour.
        }
    }

    @Test
    public void or() throws Exception {
        Assert.assertEquals(((short) 42), OptionalShort.absent().or(((short) 42)));
    }

    @Test
    public void orWithSupplier() throws Exception {
        Assert.assertEquals((short) 42, OptionalShort.absent().or(new ShortSupplier() {
            @Override
            public short get() {
                return 42;
            }
        }));
    }

    @Test
    public void orThrow() throws Exception {
        try {
            OptionalShort.absent().orThrow(new Exception("An exception occurred."));

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
            OptionalShort.absent().orThrow(anExceptionSupplier);

            Assert.fail("Invoking orThrow() on an absent optional should throw an exception.");
        } catch (Exception anException) {
            // No-op. This is the expected behaviour.
        }
    }

    @Test
    public void ifPresent() throws Exception {
        OptionalShort.of(((short) 42)).ifPresent(new ShortConsumer() {
            @Override
            public void consume(short value) {
                Assert.assertEquals(((short) 42), value);
            }
        });

        OptionalShort.absent().ifPresent(new ShortConsumer() {
            @Override
            public void consume(short value) {
                Assert.fail("ifPresent() on an absent optional should never call its consumer");
            }
        });
    }

    @Test
    public void ifPresentOrElse() throws Exception {
        OptionalShort.of(((short) 42)).ifPresentOrElse(new ShortConsumer() {
            @Override
            public void consume(short value) {
                Assert.assertEquals(((short) 42), value);
            }
        }, new Function() {
            @Override
            public void call() {
                Assert.fail("ifPresent() on an non-absent optional should call its consumer");
            }
        });

        OptionalShort.absent().ifPresentOrElse(new ShortConsumer() {
            @Override
            public void consume(short value) {
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
