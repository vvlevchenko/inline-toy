import kotlinx.cinterop.*
import source_info.*
import platform.posix.*
      
inline fun sum(a:Int, b:Int):Int {
  return a + b
}

/**
 * program.kexe`kfun:foo_runner()ValueType:
 *     0x100056780 <+0>:  pushq  %rbp
 *     0x100056781 <+1>:  movq   %rsp, %rbp
 *     0x100056784 <+4>:  xorl   %eax, %eax
 *     0x100056786 <+6>:  movl   %eax, %ecx
 *     0x100056788 <+8>:  movq   %rcx, -0x10(%rbp)
 * ->  0x10005678c <+12>: movq   -0x10(%rbp), %rax
 *     0x100056790 <+16>: movq   %rax, -0x18(%rbp)
 *     0x100056794 <+20>: movl   $0x1, -0x4(%rbp)
 *     0x10005679b <+27>: movl   $0xffffffff, -0x8(%rbp)   ; imm = 0xFFFFFFFF
 *     0x1000567a2 <+34>: movl   -0x4(%rbp), %eax
 *     0x1000567a5 <+37>: addl   -0x8(%rbp), %eax
 *     0x1000567a8 <+40>: movl   %eax, -0x1c(%rbp)
 *     0x1000567ab <+43>: movl   -0x1c(%rbp), %eax
 *     0x1000567ae <+46>: movl   %eax, -0x20(%rbp)
 *     0x1000567b1 <+49>: movl   -0x20(%rbp), %eax
 *     0x1000567b4 <+52>: popq   %rbp
 *     0x1000567b5 <+53>: retq
 *     0x1000567b6 <+54>: nop
 *     0x1000567b7 <+55>: nop
 *     0x1000567b8 <+56>: nop
 *     0x1000567b9 <+57>: nop
 *     0x1000567ba <+58>: nop
 *     0x1000567bb <+59>: nop
 *     0x1000567bc <+60>: nop
 *     0x1000567bd <+61>: nop
 *     0x1000567be <+62>: nop
 *     0x1000567bf <+63>: nop
*/

fun foo_runner():Int {
  return sum(1, -1)
}

fun main() {
  val foo_runner_ref = ::foo_runner
  println("foo_runner_addr: $foo_runner_addr")
  listOf<Long>(0, 1, 4, 6, 8, 12,16, 20, 27, 34, 37, 40, 43,46,49,52,53,54).forEach {
    Kotlin_getSourceInfo0(foo_runner_addr.toLong() + it).useContents {
      println("$it: ${fileName?.toKString()}:$lineNumber")
    }
  }
}