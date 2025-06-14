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
declare i32 @leerArray(double*, i32)@.str0 = private constant [24 x i8] c"Valor de la variable a:\00"
@.str1 = private constant [24 x i8] c"Valor de la variable b:\00"
@.str2 = private constant [24 x i8] c"Valor de la variable c:\00"
@.str3 = private constant [24 x i8] c"Valor de la variable x:\00"
@.str4 = private constant [24 x i8] c"Valor de la variable y:\00"
@.str5 = private constant [32 x i8] c"Valor de la variable resultado:\00"
@.str6 = private constant [26 x i8] c"Valor de la variable a+1:\00"
@.str7 = private constant [36 x i8] c"Valor de la variable mi_arreglo[0]:\00"
@.str8 = private constant [5 x i8] c"true\00"
@.str9 = private constant [36 x i8] c"La variable resultado es igual a 10\00"
@.str10 = private constant [39 x i8] c"La variable resultado NO es igual a 10\00"
@.str11 = private constant [29 x i8] c"Display desde CONDITION, x>y\00"
@.str12 = private constant [22 x i8] c"ITERANDO EN LOOP WHEN\00"
@.str13 = private constant [17 x i8] c"[10.0, 9.2, 6.2]\00"
@.str14 = private constant [54 x i8] c"FUNCION ALL, todos los elementos son mayores que 5.8:\00"
@.str15 = private constant [19 x i8] c"[10.5, 20.4, 30.4]\00"
@.str16 = private constant [16 x i8] c"[0.2, 0.3, 0.5]\00"
@.str17 = private constant [51 x i8] c"ERROR PP: Las listas deben tener la misma longitud\00"
@.str18 = private constant [83 x i8] c"El resultado del Promedio ponderado entre [10.5, 20.4, 30.4], [0.2, 0.3, 0.5] es: \00"
define i32 @main() {
%c = alloca i32%b = alloca i32%a = alloca i32
%prom = alloca double%resultado = alloca double%y = alloca double%x = alloca double
%mi_arreglo = alloca [5 x double]
%resultado_all = alloca i1%mayor = alloca i1%es_igual = alloca i1
%_suma_valores_2 = alloca double%_resultado_2 = alloca double%_suma_pesos_2 = alloca double
%_i_2 = alloca i32
%_valores_2 = alloca [3 x double]
%_pesos_2 = alloca [3 x double]
%_arrayAll_2 = alloca [3 x double]
%_iAll_1 = alloca i32%_longitudAll_1 = alloca i32
%_condicionLocalAll_1 = alloca i1%_resultadoAll_1 = alloca i1
%t0 = add i32 0, 5
store i32 %t0, i32* %a
%t1 = add i32 0, 3
store i32 %t1, i32* %b
%t2 = add i32 0, 10
store i32 %t2, i32* %c
%t3 = fadd double 0.0, 20.5
store double %t3, double* %x
%t4 = fadd double 0.0, 3.2
%t5 = fsub double 0.0, %t4
store double %t5, double* %y
%t6 = add i32 0, 0
%t7 = load double, double* %x
%t8 = load double, double* %y
%t9 = fadd double %t7, %t8
%t10 = getelementptr inbounds [5 x double], [5x double]* %mi_arreglo, i32 0, i32 %t6
store double %t9, double* %t10
%t11 = getelementptr [24 x i8], [24 x i8]* @.str0, i32 0, i32 0
call i32 @puts(i8* %t11)
%t12 = load i32, i32* %a
%t13 = getelementptr [4 x i8], [4 x i8]* @.integer, i32 0, i32 0
call i32 (i8*, ...) @printf(i8* %t13, i32 %t12)
%t14 = getelementptr [24 x i8], [24 x i8]* @.str1, i32 0, i32 0
call i32 @puts(i8* %t14)
%t15 = load i32, i32* %b
%t16 = getelementptr [4 x i8], [4 x i8]* @.integer, i32 0, i32 0
call i32 (i8*, ...) @printf(i8* %t16, i32 %t15)
%t17 = getelementptr [24 x i8], [24 x i8]* @.str2, i32 0, i32 0
call i32 @puts(i8* %t17)
%t18 = load i32, i32* %c
%t19 = getelementptr [4 x i8], [4 x i8]* @.integer, i32 0, i32 0
call i32 (i8*, ...) @printf(i8* %t19, i32 %t18)
%t20 = getelementptr [24 x i8], [24 x i8]* @.str3, i32 0, i32 0
call i32 @puts(i8* %t20)
%t21 = load double, double* %x
%t22 = getelementptr [4 x i8], [4 x i8]* @.float, i32 0, i32 0
call i32 (i8*, ...) @printf(i8* %t22, double %t21)
%t23 = getelementptr [24 x i8], [24 x i8]* @.str4, i32 0, i32 0
call i32 @puts(i8* %t23)
%t24 = load double, double* %y
%t25 = getelementptr [4 x i8], [4 x i8]* @.float, i32 0, i32 0
call i32 (i8*, ...) @printf(i8* %t25, double %t24)
%t26 = load i32, i32* %a
%t27 = load i32, i32* %b
%t28 = load i32, i32* %c
%t29 = mul i32 %t27, %t28
%t30 = add i32 %t26, %t29
%t31 = sitofp i32 %t30 to double
%t32 = load double, double* %x
%t33 = fdiv double %t31, %t32
store double %t33, double* %resultado
%t34 = getelementptr [32 x i8], [32 x i8]* @.str5, i32 0, i32 0
call i32 @puts(i8* %t34)
%t35 = load double, double* %resultado
%t36 = getelementptr [4 x i8], [4 x i8]* @.float, i32 0, i32 0
call i32 (i8*, ...) @printf(i8* %t36, double %t35)
%t37 = load i32, i32* %a
%t38 = add i32 0, 1
%t39 = add i32 %t37, %t38
store i32 %t39, i32* %a
%t40 = getelementptr [26 x i8], [26 x i8]* @.str6, i32 0, i32 0
call i32 @puts(i8* %t40)
%t41 = load i32, i32* %a
%t42 = getelementptr [4 x i8], [4 x i8]* @.integer, i32 0, i32 0
call i32 (i8*, ...) @printf(i8* %t42, i32 %t41)
%t43 = getelementptr [36 x i8], [36 x i8]* @.str7, i32 0, i32 0
call i32 @puts(i8* %t43)
%t44 = add i32 0, 0
%t45 = getelementptr inbounds [5 x double], [5x double]* %mi_arreglo, i32 0, i32 %t44
%t46 = load double, double* %t45
%t47 = getelementptr [4 x i8], [4 x i8]* @.float, i32 0, i32 0
call i32 (i8*, ...) @printf(i8* %t47, double %t46)
%t48 = load double, double* %resultado
%t49 = add i32 0, 10
%t50 = sitofp i32 %t49 to double
%t51 = fcmp oeq double %t48, %t50
store i1 %t51, i1* %es_igual
%t52 = load i1, i1* %es_igual
%t53 = xor i1 0, 1
%t54 = icmp eq i1 %t52, %t53
br i1 %t54, label %then_label0, label %else_label1
then_label0:
%t55 = getelementptr [36 x i8], [36 x i8]* @.str9, i32 0, i32 0
call i32 @puts(i8* %t55)
br label %ifend_label2
else_label1:
%t56 = getelementptr [39 x i8], [39 x i8]* @.str10, i32 0, i32 0
call i32 @puts(i8* %t56)
br label %ifend_label2
ifend_label2:

%t57 = load double, double* %x
%t58 = load double, double* %y
%t59 = fcmp ogt double %t57, %t58
%t60 = load i1, i1* %es_igual
%t61 = xor i1 %t60, true
%t62 = and i1 %t59, %t61
store i1 %t62, i1* %mayor
%t63 = load i1, i1* %mayor
%t64 = xor i1 0, 1
%t65 = icmp eq i1 %t63, %t64
br i1 %t65, label %then_label3, label %else_label4
then_label3:
%t66 = getelementptr [29 x i8], [29 x i8]* @.str11, i32 0, i32 0
call i32 @puts(i8* %t66)
br label %ifend_label5
else_label4:
br label %ifend_label5
ifend_label5:

br label %cond_label7
cond_label7:
%t67 = load i32, i32* %a
%t68 = add i32 0, 8
%t69 = icmp slt i32 %t67, %t68
br i1 %t69, label %loop_label6, label %fin_label8
loop_label6:
%t70 = getelementptr [22 x i8], [22 x i8]* @.str12, i32 0, i32 0
call i32 @puts(i8* %t70)
%t71 = load i32, i32* %a
%t72 = add i32 0, 1
%t73 = add i32 %t71, %t72
store i32 %t73, i32* %a
br label %cond_label7
fin_label8:

%t74 = alloca [3 x double]
%t75 = getelementptr inbounds [3 x double], [3 x double]* %t74, i32 0, i32 0
%t76 = fadd double 0.0, 10.0
store double %t76, double* %t75
%t77 = getelementptr inbounds [3 x double], [3 x double]* %t74, i32 0, i32 1
%t78 = fadd double 0.0, 9.2
store double %t78, double* %t77
%t79 = getelementptr inbounds [3 x double], [3 x double]* %t74, i32 0, i32 2
%t80 = fadd double 0.0, 6.2
store double %t80, double* %t79

%t81 = getelementptr inbounds [3 x double], [3 x double]* %t74, i32 0, i32 0
%t82 = load double, double* %t81
%t83 = getelementptr inbounds [3 x double], [3 x double]* %_arrayAll_2, i32 0, i32 0
store double %t82, double* %t83
%t84 = getelementptr inbounds [3 x double], [3 x double]* %t74, i32 0, i32 1
%t85 = load double, double* %t84
%t86 = getelementptr inbounds [3 x double], [3 x double]* %_arrayAll_2, i32 0, i32 1
store double %t85, double* %t86
%t87 = getelementptr inbounds [3 x double], [3 x double]* %t74, i32 0, i32 2
%t88 = load double, double* %t87
%t89 = getelementptr inbounds [3 x double], [3 x double]* %_arrayAll_2, i32 0, i32 2
store double %t88, double* %t89

%t90 = add i32 0, 0
store i32 %t90, i32* %_iAll_1
%t91 = add i32 0, 3
store i32 %t91, i32* %_longitudAll_1
%t92 = xor i1 0, 1
store i1 %t92, i1* %_resultadoAll_1
br label %cond_label10
cond_label10:
%t93 = load i32, i32* %_iAll_1
%t94 = load i32, i32* %_longitudAll_1
%t95 = icmp slt i32 %t93, %t94
br i1 %t95, label %loop_label9, label %fin_label11
loop_label9:
%t96 = load i32, i32* %_iAll_1
%t97 = getelementptr inbounds [3 x double], [3x double]* %_arrayAll_2, i32 0, i32 %t96
%t98 = load double, double* %t97
%t99 = fadd double 0.0, 5.8
%t100 = fcmp ogt double %t98, %t99
store i1 %t100, i1* %_condicionLocalAll_1
%t101 = load i1, i1* %_condicionLocalAll_1
%t102 = xor i1 0, 0
%t103 = icmp eq i1 %t101, %t102
br i1 %t103, label %then_label12, label %else_label13
then_label12:
%t104 = xor i1 0, 0
store i1 %t104, i1* %_resultadoAll_1
br label %fin_label11
br label %ifend_label14
else_label13:
br label %ifend_label14
ifend_label14:

%t105 = load i32, i32* %_iAll_1
%t106 = add i32 0, 1
%t107 = add i32 %t105, %t106
store i32 %t107, i32* %_iAll_1
br label %cond_label10
fin_label11:

%t108 = load i1, i1* %_resultadoAll_1
store i1 %t108, i1* %resultado_all
%t109 = load i1, i1* %resultado_all
%t110 = xor i1 0, 1
%t111 = icmp eq i1 %t109, %t110
br i1 %t111, label %then_label15, label %else_label16
then_label15:
%t112 = getelementptr [54 x i8], [54 x i8]* @.str14, i32 0, i32 0
call i32 @puts(i8* %t112)
br label %ifend_label17
else_label16:
br label %ifend_label17
ifend_label17:

%t113 = add i32 0, 0
store i32 %t113, i32* %_i_2
%t114 = fadd double 0.0, 0.0
store double %t114, double* %_suma_valores_2
%t115 = fadd double 0.0, 0.0
store double %t115, double* %_suma_pesos_2
%t116 = alloca [3 x double]
%t117 = getelementptr inbounds [3 x double], [3 x double]* %t116, i32 0, i32 0
%t118 = fadd double 0.0, 10.5
store double %t118, double* %t117
%t119 = getelementptr inbounds [3 x double], [3 x double]* %t116, i32 0, i32 1
%t120 = fadd double 0.0, 20.4
store double %t120, double* %t119
%t121 = getelementptr inbounds [3 x double], [3 x double]* %t116, i32 0, i32 2
%t122 = fadd double 0.0, 30.4
store double %t122, double* %t121

%t123 = getelementptr inbounds [3 x double], [3 x double]* %t116, i32 0, i32 0
%t124 = load double, double* %t123
%t125 = getelementptr inbounds [3 x double], [3 x double]* %_valores_2, i32 0, i32 0
store double %t124, double* %t125
%t126 = getelementptr inbounds [3 x double], [3 x double]* %t116, i32 0, i32 1
%t127 = load double, double* %t126
%t128 = getelementptr inbounds [3 x double], [3 x double]* %_valores_2, i32 0, i32 1
store double %t127, double* %t128
%t129 = getelementptr inbounds [3 x double], [3 x double]* %t116, i32 0, i32 2
%t130 = load double, double* %t129
%t131 = getelementptr inbounds [3 x double], [3 x double]* %_valores_2, i32 0, i32 2
store double %t130, double* %t131

%t132 = alloca [3 x double]
%t133 = getelementptr inbounds [3 x double], [3 x double]* %t132, i32 0, i32 0
%t134 = fadd double 0.0, 0.2
store double %t134, double* %t133
%t135 = getelementptr inbounds [3 x double], [3 x double]* %t132, i32 0, i32 1
%t136 = fadd double 0.0, 0.3
store double %t136, double* %t135
%t137 = getelementptr inbounds [3 x double], [3 x double]* %t132, i32 0, i32 2
%t138 = fadd double 0.0, 0.5
store double %t138, double* %t137

%t139 = getelementptr inbounds [3 x double], [3 x double]* %t132, i32 0, i32 0
%t140 = load double, double* %t139
%t141 = getelementptr inbounds [3 x double], [3 x double]* %_pesos_2, i32 0, i32 0
store double %t140, double* %t141
%t142 = getelementptr inbounds [3 x double], [3 x double]* %t132, i32 0, i32 1
%t143 = load double, double* %t142
%t144 = getelementptr inbounds [3 x double], [3 x double]* %_pesos_2, i32 0, i32 1
store double %t143, double* %t144
%t145 = getelementptr inbounds [3 x double], [3 x double]* %t132, i32 0, i32 2
%t146 = load double, double* %t145
%t147 = getelementptr inbounds [3 x double], [3 x double]* %_pesos_2, i32 0, i32 2
store double %t146, double* %t147

%t148 = add i32 0, 3
%t149 = add i32 0, 3
%t150 = icmp ne i32 %t148, %t149
br i1 %t150, label %then_label18, label %else_label19
then_label18:
%t151 = getelementptr [51 x i8], [51 x i8]* @.str17, i32 0, i32 0
call i32 @puts(i8* %t151)
%t152 = fadd double 0.0, 0.0
store double %t152, double* %_resultado_2
br label %ifend_label20
else_label19:
br label %cond_label22
cond_label22:
%t153 = load i32, i32* %_i_2
%t154 = add i32 0, 3
%t155 = icmp slt i32 %t153, %t154
br i1 %t155, label %loop_label21, label %fin_label23
loop_label21:
%t156 = load double, double* %_suma_valores_2
%t157 = load i32, i32* %_i_2
%t158 = getelementptr inbounds [3 x double], [3x double]* %_valores_2, i32 0, i32 %t157
%t159 = load double, double* %t158
%t160 = load i32, i32* %_i_2
%t161 = getelementptr inbounds [3 x double], [3x double]* %_pesos_2, i32 0, i32 %t160
%t162 = load double, double* %t161
%t163 = fmul double %t159, %t162
%t164 = fadd double %t156, %t163
store double %t164, double* %_suma_valores_2
%t165 = load double, double* %_suma_pesos_2
%t166 = load i32, i32* %_i_2
%t167 = getelementptr inbounds [3 x double], [3x double]* %_pesos_2, i32 0, i32 %t166
%t168 = load double, double* %t167
%t169 = fadd double %t165, %t168
store double %t169, double* %_suma_pesos_2
%t170 = load i32, i32* %_i_2
%t171 = add i32 0, 1
%t172 = add i32 %t170, %t171
store i32 %t172, i32* %_i_2
br label %cond_label22
fin_label23:

br label %ifend_label20
ifend_label20:

%t173 = load double, double* %_suma_pesos_2
%t174 = fadd double 0.0, 1.0
%t175 = fcmp oeq double %t173, %t174
br i1 %t175, label %then_label24, label %else_label25
then_label24:
%t176 = load double, double* %_suma_valores_2
%t177 = load double, double* %_suma_pesos_2
%t178 = fdiv double %t176, %t177
store double %t178, double* %_resultado_2
br label %ifend_label26
else_label25:
%t179 = fadd double 0.0, 0.0
store double %t179, double* %_resultado_2
br label %ifend_label26
ifend_label26:

%t180 = load double, double* %_resultado_2
store double %t180, double* %prom
%t181 = getelementptr [83 x i8], [83 x i8]* @.str18, i32 0, i32 0
call i32 @puts(i8* %t181)
%t182 = load double, double* %prom
%t183 = getelementptr [4 x i8], [4 x i8]* @.float, i32 0, i32 0
call i32 (i8*, ...) @printf(i8* %t183, double %t182)
  ret i32 0
}
