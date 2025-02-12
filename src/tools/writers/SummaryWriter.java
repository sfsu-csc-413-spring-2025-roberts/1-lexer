package tools.writers;

import java.util.List;

import tools.grammar.Grammar;

public class SummaryWriter extends GrammarWriter {

    public SummaryWriter(Grammar grammar) {
        super(grammar);
    }

    @Override
    public void write() throws Exception {
        System.out.println("=== Grammar Summary ===");
        System.out.printf("%d non terminals, %d AST nodes, %d terminals, %d productions%n",
                grammar.getNonTerminals().size(), grammar.getAstCount(),
                grammar.getTerminals().size(), grammar.getProductions().size());

        outputLexemes("Keywords", grammar.getKeywords(), 7);
        outputLexemes("Operators", grammar.getOperators(), 2);
        outputLexemes("Separators", grammar.getSeparators(), 2);

        System.out.println();
    }

    private void outputLexemes(String title, List<String> lexemes, int columnSize) {
        String lineFormat = "%-20s %s%n";
        String lexemeFormat = "%-" + columnSize + "s";

        String heading = String.format("%-11s (%2d):", title, lexemes.size());
        List<String> displayLexemes = lexemes.stream().map(lexeme -> String.format(lexemeFormat, lexeme)).toList();

        System.out.printf(lineFormat, heading, String.join(" ", displayLexemes));
    }
}
