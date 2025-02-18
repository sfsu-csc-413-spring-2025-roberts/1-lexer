# Assignment 1 - Lexer - CSC 413 Spring 2025

Name: ALLAN TAYLOR APARICIO

// Video stoped: 18:41

## Notes

To run the compiler tools to regenerate lexer files:

```
javac -cp src:. -d out src/tools/CompilerTools.java
java -cp out tools.CompilerTools src/tools/definition/grammar.txt -1 --summary
```

Running the `CompilerTools` without any parameters will display a usage message, including the different options you can provide (like `-1` and `--summary`).
