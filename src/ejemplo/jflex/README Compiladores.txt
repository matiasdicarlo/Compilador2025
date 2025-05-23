📘 Trabajo Compilador - Grupo 1 - UNNOBA 2025


Este proyecto es una implementación de un analizador léxico y sintáctico para un lenguaje de programación simple, desarrollado con JFlex y CUP. Incluye una interfaz gráfica (AnalizadorGUI.java) que permite cargar código de prueba, analizarlo y visualizar los resultados directamente desde la aplicación. El objetivo es identificar y validar estructuras léxicas y sintácticas del lenguaje definido.


Características
* Análisis léxico: reconoce tokens como palabras clave, identificadores, operadores, literales, etc.
* Análisis sintáctico: valida que las secuencias de tokens formen sentencias válidas según una gramática definida.
* Implementado en Java
* Interfaz gráfica: permite al usuario cargar texto y ver el resultado del análisis en pantalla.
* Tabla de símbolos.

🛠 Estructura del Proyecto


TrabajoCompiladorGRUPO1/
src/ejemplo.jflex/
│
├── AnalizadorGUI.java                # Interfaz gráfica del analizador
├── AnalizadorLexicoJFlex.java        # Clase para ejecución del análisis léxico
├── AnalizadorSintacticoJavaCup.java  # Clase para ejecución del análisis 
|                                                                sintáctico
├── Generador.java                    # Clase que genera el lexer y parser a partir 
|                                                                de JFlex/JCUP
├── MiLexico.java                     # Lexer generado (o clase intermedia para él)
├── MiParser.java                     # Parser generado por JCUP
├── MiParserSym.java                  # Símbolos del parser
├── MiToken.java                      # Representación de los tokens
├── SymbolTable.java                  # Tabla de símbolos
├── TextAreaOutputStream.java         # Para redirigir salida al JTextArea
├── pruebas.txt                       # Archivo de prueba con código fuente de 3          
|                                                                programas
├── lexico.flex                       # Definición léxica (JFlex)
└── parser.cup                        # Gramática del lenguaje (JCUP)
└── README.txt                        # Este archivo




Cómo Ejecutar el Proyecto

Ejecuta AnalizadorGUI.java. Esto abrirá la ventana principal de la aplicación.(Puede que antes de ejecutar, se requiera realizar un 
"clean and build")
🖥️ Uso de la Interfaz
   * Botón "Análisis Léxico": Realiza el análisis léxico del código ingresado.

   * Botón "Análisis Sintáctico": Realiza el análisis sintáctico del código.

   * Área superior: Ingresar el código fuente a analizar.

   * Área inferior: Visualizar el resultado del análisis.


📂 Carpeta de Pruebas
Se incluye un archivos pruebas.txt que contiene ejemplos de código que se puede copiar y pegar directamente en el área de texto de la aplicación. Son útiles para probar distintos casos del analizador.

Autores
Trabajo realizado por el GRUPO 1 para la asignatura Compiladores — UNNOBA