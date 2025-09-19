cmake_minimum_required(VERSION 3.16)
project(demo C)
set(CMAKE_C_STANDARD 11)

# apps/ 下每个 .c/.C -> 独立可执行
aux_source_directory("${CMAKE_SOURCE_DIR}/apps" APP_SRCS)
foreach(src IN LISTS APP_SRCS)
  get_filename_component(name "${src}" NAME_WE)
  add_executable(${name} "${src}")
endforeach()

# programs/ 下每个子目录 -> 1 个可执行（该目录所有 .c/.C）
file(GLOB CHILDREN RELATIVE "${CMAKE_SOURCE_DIR}/programs" "${CMAKE_SOURCE_DIR}/programs/*")
foreach(child IN LISTS CHILDREN)
  if(IS_DIRECTORY "${CMAKE_SOURCE_DIR}/programs/${child}")
    aux_source_directory("${CMAKE_SOURCE_DIR}/programs/${child}" SUB_SRCS)
    if(SUB_SRCS)
      string(REPLACE " " "_" tgt "${child}")
      add_executable(${tgt} ${SUB_SRCS})
    endif()
  endif()
endforeach()
