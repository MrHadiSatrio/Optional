`Optional` – Java 8 Optional-like APIs for everyone
---

![You get to use Optional! And you! And you there!](https://i.imgflip.com/1g8eyz.jpg)


### Differences compared to other Optional-like APIs

|                                  |             `Optional`            |               Java 8's Optional              |  Guava's Optional  | Optio |
|----------------------------------|:---------------------------------:|:--------------------------------------------:|:------------------:|:-----:|
| Required JDK version             |                1.7                |                      1.8                     |         1.6        |  1.7  |
| Android-compatible               |                Yes                |        Only for API 24 (Nougat) and up       |         Yes        |  Yes  |
| Serializable                     |                Yes                |                       No                     |         Yes        |   No  |
| Support for primitive types      | Yes, all of them except `boolean` |     Only for `int`, `double`, and `long`     |        None        |  None |
| Support for functional use-cases |             Partially             | Yes, including `filter()`, `flatMap()`, etc. |        None        |  None |


### Unique to `Optional`

- `absent()` instead of `empty()`. [Why?](http://englishthesaurus.net/antonym/present)
- `or()` instead of `orElse()`.
- `orNull()` instead of `orElse(null)`.
- `ifPresentOrElse(Consumer, Function)`.
- `OptionalByte`, `OptionalShort`, `OptionalFloat`, and `OptionalChar`.



### Including `Optional` to your project

Include `Optional` to your Gradle project by adding it as a dependency in your `build.gradle` like so:

```
   repositories {
       maven { url "https://jitpack.io" }
   }
   
   dependencies {
       compile 'com.hadisatrio:Optional:1.0.0'
   }
```


### Contributions 

Any kind of contributions will be appreciated. PR away!


### License

`Optional` is published under the [MIT license](https://opensource.org/licenses/MIT).

---

_**p.s.**, Please let me know if you're using `Optional` in your projects. Drop an email at 
hi[you-know-what-to-put-here]hadisatrio.com._ ;)