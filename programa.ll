; Módulo generado por el compilador
declare void @printInt(i32)

declare i32 @puts(i8*)
declare i32 @printf(i8*, ...)
@.integer = private constant [4 x i8] c"%d\0A\00"
@.float = private constant [4 x i8] c"%f\0A\00"
@.true_str = private constant [5 x i8] c"true\00"
@.false_str = private constant [6 x i8] c"false\00"
declare i32 @scanf(i8*, ...)
@int_read_format = unnamed_addr constant [3 x i8] c"%d\00"
@double_read_format = unnamed_addr constant [4 x i8] c"%lf\00"
declare i32 @leerArray(double*, i32)@.str0 = private constant [6 x i8] c"false\00"
@.str1 = private constant [3 x i8] c"a:\00"
@.str2 = private constant [3 x i8] c"OR\00"
define i32 @main() {
  %a = alloca i32

  %t0 = add i32 0, 0
store i32 %t0, i32* %a
  br label %cond_label1
cond_label1:
%t1 = xor i1 0, 0
br i1 %t1, label %loop_label0, label %or_rhs_label3
or_rhs_label3:
%t2 = load i32, i32* %a
%t3 = add i32 0, 3
%t4 = icmp slt i32 %t2, %t3
br i1 %t4, label %loop_label0, label %fin_label2
loop_label0:
%t5 = getelementptr [3 x i8], [3 x i8]* @.str1, i32 0, i32 0
call i32 @puts(i8* %t5)
%t6 = load i32, i32* %a
%t7 = getelementptr [4 x i8], [4 x i8]* @.integer, i32 0, i32 0
call i32 (i8*, ...) @printf(i8* %t7, i32 %t6)
%t8 = load i32, i32* %a
%t9 = add i32 0, 1
%t10 = add i32 %t8, %t9
store i32 %t10, i32* %a
br label %cond_label1
fin_label2:

  %t11 = getelementptr [3 x i8], [3 x i8]* @.str2, i32 0, i32 0
call i32 @puts(i8* %t11)
  ret i32 0
}
