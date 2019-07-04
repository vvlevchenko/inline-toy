KONAN_HEADER_INT=-I${KONAN_HOME}/runtime/src/main/cpp
CFLAGS=-g ${KONAN_HEADER_INT}
CXXFLAGS=${CFLAGS} -DKONAN_CORE_SYMBOLICATION -std=c++11
CINTEROP=${KONAN_HOME}/dist/bin/cinterop
KONANC=${KONAN_HOME}/dist/bin/konanc
RMFLAGS=-rf

SourceInfo.o:${KONAN_HOME}/runtime/src/debug/cpp/SourceInfo.cpp
	${CXX} ${CLAGS} ${CXXFLAGS} -c -o $@ $<

inline.o: inline.c
	${CC} ${CFLAGS} -c -o $@ $<

inline.bc: inline.c
	${CC} ${CFLAGS} -emit-llvm -c -o $@ $<

inline:SourceInfo.o inline.o
	${CXX} -o $@ $^

source_info.klib:SourceInfo.def
	$(CINTEROP) -def $< -pkg source_info  -compiler-options "${KONAN_HEADER_INT}" -o $(notdir $(basename $@))

inline.kexe:inline-0.kt source_info.klib
	$(KONANC) -g $< -l source_info -Xtemporary-files-dir=.tmp_inline-0 -o $(notdir $(basename $@))

all:inline inline.kexe

.PHONY: clean
clean:
	${RM} ${RMFLAGS} inline SourceInfo.o inline.o inline.bc source_info.klib inline.kexe inline.kexe.dSYM
