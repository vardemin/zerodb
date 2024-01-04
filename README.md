# Zero DB
Simple Kotlin Multiplatform file based key-value database where key is the name of file and the value is serialized into the contents of the file.

## Disclaimer
Currently under development and not recommended for production purposes.

# Usage
## 1. Include library
### 1.2 From GitHub repository
```groovy
allprojects {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/vardemin/zerodb")
        }
    }
}
```
```groovy
dependencies {
    implementation("com.vardemin.zero.db:shared:$zeroDbVersion")
}
```

## 2. Initialize db instance
```kotlin
val zeroDb = ZeroDb(
    ZeroCborFileDbConfig(
        directory = Path(javaFileDir.path)
    )
)
```
Currently implemented only cbor file based storage which known from ZeroCborFileDbConfig parameter. Key files created in provided directory.

## 3. Call instance methods
```kotlin
zeroDb.set("key", entity) // Write value
val actual: ExampleEntity = zeroDb.get<ExampleEntity>("key") // read or throw exception
val actualNullable: ExampleEntity? = zeroDb.getOrNull<ExampleEntity>("key") // read nullable
val actualOrDefault: ExampleEntity = zeroDb.getOrDefault<ExampleEntity>("key", ExampleEntity()) // read or return default
zeroDb.remove("key") // Remove key (key file content)
```

## Multiplatform IO
Based on [kotlinx-io](https://github.com/Kotlin/kotlinx-io) implementation. (Distributed along with sources)

## Serialization
ZeroDB uses [CBOR](https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/formats.md#byte-arrays-and-cbor-data-types) format from [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization).

## Concurrency
ZeroDB uses [Stately's](https://github.com/touchlab/Stately) synchronization implementation on IO operations and ConcurrentMutableMap for storing ZeroVault instances.

## License
ZeroDB is distributed under the MIT license. [See LICENSE](https://github.com/vardemin/zerodb/blob/master/LICENSE.md) for details.