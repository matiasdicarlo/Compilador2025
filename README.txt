Analizador Léxico, Sintáctico y Generador de Código Intermedio en Java
Trabajo Compilador - Grupo 1 - UNNOBA 2025


Este proyecto implementa un analizador léxico ,sintáctico y un generador de código intermedio para un lenguaje de programación simple utilizando Java, JFlex y JCUP. El objetivo es identificar y validar estructuras léxicas y sintácticas del lenguaje definido. Además, incluye una interfaz gráfica en Java para facilitar el análisis de código fuente ingresado. 
Características
* Análisis léxico: reconoce tokens como palabras clave, identificadores, operadores, literales, etc.
* Análisis sintáctico: valida que las secuencias de tokens formen sentencias válidas según una gramática definida.
* Implementado en Java
* Interfaz gráfica: permite al usuario cargar texto y ver el resultado del análisis en pantalla.
* Tabla de símbolos.
* Generación de AST
* Generacion de codigo LLVM

Estructura del Proyecto


│TrabajoCompiladorGrupo1.jar          # Archivo .jar deberá ejecutar este archivo 
│                                     para poder acceder a la Gui del compilador 
│README.txt
│pruebasParaLLVM.txt                   # Archivo con un conjunto de pruebas para 
│                                                        realizarle al compilador, abarca todos las
│                                      funcionalidades del mismo, e indican 
│                                      resultados esperados
│arbol.dot 
│arbol.png                             #Archivo donde se aparecerá el AST         
│                                      generado
│programa.ll                           #Archivo donde se cargara el código LLVM        
│                                      generado
│scanf.ll
│leer_array.ll                        #Archivo necesario para el funcionamiento del
│                                     InputArray(n)
│programa.exe                                     #Programa ejecutable generado                                                
src/compilador.ast/
│
├── AccesoArray.java               
├── All.java
├── Any.java
├── Asignacion.java
├── AsignacionArray.java
________________
├── Break.java
├── Bucle.java
├── ConditionThenElse.java
├── Costante.java
├── ContextoLLVM.java
├── Continue.java
├── DeclaracionArray.java
├── DeclaracionMultiple.java
├── DeclaracionVariable.java
├── Display.java
├── Distinto.java
├── Expresion.java
├── GestorStringsLLVM.java
├── Identificador.java
├── Igual.java
├── InputArray.java
├── InputBool.java
├── InputFloat.java
├── InputInt.java
├── Longitud.java
├── Mayor.java
├── MayorIgual.java
├── Menor.java
├── MenorIgual.java
├── Negativo.java
├── Nodo.java
├── OperacionAnd.java
├── OperacionBinaria.java
├── OperacionDivision.java
├── OperacionMultiplicacion.java
├── OperacionNot.java
├── OperacionResata.java
├── OperacionSuma.java
├── ParDeArray.java
├── Programa.java
├── PromedioPonderado.java
├── ValidarTipos.java
src/ejemplo.jflex/
│
├── AnalizadorGUI.java                 # Interfaz gráfica del analizador
├── AnalizadorLexicoJFlex.java        # Clase para ejecución del análisis léxico
├── AnalizadorSintacticoJavaCup.java  # Clase para ejecución del análisis 
│                                                        sintáctico
├── Generador.java                    # Clase que genera el lexer y parser a partir 
│                                                        de JFlex/JCUP
├── MiLexico.java                     # Lexer generado (o clase intermedia para él)
├── MiParser.java                     # Parser generado por JCUP
├── MiParserSym.java                  # Símbolos del parser
├── MiToken.java                      # Representación de los tokens
├── SymbolTable.java                  # Tabla de símbolos
├── TextAreaOutputStream.java         # Para redirigir salida al JTextArea                     
├── lexico.flex                       # Definición léxica (JFlex)
└── parser.cup                        # Gramática del lenguaje (JCUP)


 
Requisitos
   * Java JDK 8 o superior

   * JFlex y JCUP instalados
 
   * LLVM
   
   * En Windows se va a tener que instalar las Build Tools para Visual Studio

 
Ejecución del proyecto


Se deberá ejecutar el archivo .jar ubicado en la raíz del proyecto entregado
 
Uso de la Interfaz
   * Botón "Análisis Léxico": Realiza el análisis léxico del código ingresado.


   * Botón "Análisis Sintáctico": Realiza el análisis sintáctico del código.


   * Botón "Generar AST- LLVM": Realiza el AST y genera el codigo LLVM, ademas de      ejecutar el comando clang


   * Área superior: Ingresar el código fuente a analizar.


   * Área inferior: Visualizar el resultado del análisis.




Observación: en el caso de utilizar el botón “Generar AST- LLVM”, en la salida del analizador apareceran todos los archivos generados, incluyendo el arbol.dot, arbol.png, programa.ll, y programa.exe. Para ejecutar el .exe, se debera abrir el CMD y posicionarse en la carpeta donde se encuentra el mismo, 
(“cd  C:\Users\Usuario\OneDrive\Documentos\NetBeansProjects\TrabajoCompiladorGrupo1 - Con AST y PP\”),  y ejecutar : “.\programa.exe”


Autores
Trabajo realizado por el GRUPO 1 para la asignatura Compiladores — UNNOBA