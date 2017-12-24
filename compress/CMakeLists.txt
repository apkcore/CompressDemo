cmake_minimum_required(VERSION 3.4.1)

set(distribution_DIR ../../../../libs)
set(SOURCE_FILES src/main/cpp/compress.cpp)
set(INC_DIR src/main/cpp/include)

include_directories(${INC_DIR})

find_library(   log-lib
                log)
find_library(graphics jnigraphics)

add_library(    libjpeg
                SHARED
                IMPORTED)

set_target_properties(  libjpeg
                        PROPERTIES IMPORTED_LOCATION
                        ${distribution_DIR}/${ANDROID_ABI}/libjpeg.so)

add_library( compress
             SHARED
             ${SOURCE_FILES} )

target_link_libraries( # Specifies the target library.
                       compress
                       libjpeg
                       ${graphics}
                       ${log-lib} )