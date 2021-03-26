# Hands-on Clikt

Demo repository for building a CLI with Kotlin, Clikt and GraalVM.

## GraalVM installation

I used Jabba as a JVM manager to install GraalVM on my machine. Once this is
done, make sure to install the `native-image` utility using the Graal updater.

```
# use Jabba to install GraalVM
$ jabba remote-ls
$ jabba install graalvm-ce-java8@20.3.0
$ jabba use graalvm-ce-java8@20.3.0

$ export GRAALVM_HOME=$JAVA_HOME

$ gu available
$ gu install native-image
```

## Building and using CLI

```
$ ./gradlew build
$ ./gradlew buildNativeImage

$ cd build/
$ ./hands-on-clikt
$ ./hands-on-clikt --name Leander --count 3
$ ./hands-on-clikt --help
```

## Maintainer

M.-Leander Reimer (@lreimer), <mario-leander.reimer@qaware.de>

## License

This software is provided under the MIT open source license, read the `LICENSE`
file for details.
