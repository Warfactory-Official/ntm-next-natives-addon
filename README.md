# ntm-next-natives-addon

NTM `hbm_natives` addon for Minecraft 26.2: native kernels and their Fabric and NeoForge mods. All Rights Reserved
(see `LICENSE`).

Libraries: CMake 3.25+, Ninja and a C++26 compiler (GCC 16.2).

    cmake -S . -B build -G Ninja -DCMAKE_BUILD_TYPE=Release
    cmake --build build

Mods: JDK 25 and the four libraries in `natives/`, named as the workflow names them.

    ./gradlew build

Each jar compiles against `com.hbm:ntm-next-<loader>:<ntm_version>` (`gradle.properties`) and requires that NTM
build. A `v*` tag publishes both jars as release assets.
