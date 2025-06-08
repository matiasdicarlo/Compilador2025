@.str = private unnamed_addr constant [4 x i8] c"%lf\00"
declare i32 @scanf(i8*, ...)

define i32 @leerArray(double* %ptr, i32 %size) {
entry:
  %i = alloca i32
  store i32 0, i32* %i

  br label %loop

loop:
  %idx = load i32, i32* %i
  %cond = icmp slt i32 %idx, %size
  br i1 %cond, label %body, label %end

body:
  %elem_ptr = getelementptr inbounds double, double* %ptr, i32 %idx
  %res = call i32 (i8*, ...) @scanf(i8* getelementptr([4 x i8], [4 x i8]* @.str, i32 0, i32 0), double* %elem_ptr)
  %next = add i32 %idx, 1
  store i32 %next, i32* %i
  br label %loop

end:
  %final = load i32, i32* %i
  ret i32 %final
}