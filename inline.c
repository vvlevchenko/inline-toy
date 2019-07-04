#include "SourceInfo.h"
#include <stdio.h>
// --------8<--------
static int __attribute__((always_inline)) sum(int a, int b) {
  return a + b;
}

/**
 * (lldb) disassemble
 * inline`foo_runner:
 *     0x100000f70 <+0>:  pushq  %rbp
 *     0x100000f71 <+1>:  movq   %rsp, %rbp
 *     0x100000f74 <+4>:  movl   $0x1, -0x4(%rbp)
 *     0x100000f7b <+11>: movl   $0xffffffff, -0x8(%rbp)   ; imm = 0xFFFFFFFF
 * ->  0x100000f82 <+18>: movl   -0x4(%rbp), %eax
 *     0x100000f85 <+21>: addl   -0x8(%rbp), %eax
 *     0x100000f88 <+24>: popq   %rbp
 *     0x100000f89 <+25>: retq
 *     0x100000f8a <+26>: nopw   (%rax,%rax)
 * 
 */

int foo_runner() {
  return sum(1, -1);
}
// --------8<--------

int main() {
  void *ptr = (void *)foo_runner;
  int offsets[] = {0, 1, 4, 11, 18, 21, 24, 25, 26};
  for (int i = 0, n = sizeof(offsets)/sizeof(int); i != n; ++i) {
    struct SourceInfo result = Kotlin_getSourceInfo(ptr + offsets[i]);
    printf("%s: %d\n", result.fileName, result.lineNumber);
  }
}
