package slogo.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Parser {

    private String resourcesPackage;
    private List<Entry<String, Pattern>> mySymbols;       // note, it is a list because order matters (some patterns may be more generic)

    /**
     * Create a parser that initializes the specific resource package and a new arrayList mySymbols
     * @param resourcesPackageName the specific resource package being parsed
     */
    public Parser (String resourcesPackageName) {
      resourcesPackage = resourcesPackageName;
      mySymbols = new ArrayList<>();
    }

    /**
     * Adds the given resource file to this language's recognized types
     * @param syntax the specific resource type e.g. language or syntax or parameters
     */
    public void addPatterns (String syntax) {
      ResourceBundle resources = ResourceBundle.getBundle(resourcesPackage + syntax);
      for (String key : Collections.list(resources.getKeys())) {
        String regex = resources.getString(key);
        mySymbols.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
      }
    }

    /**
     * Returns language's type associated with the given text if one exists
     * @param text each specific string to determine what type of syntax it is
     * @return the language type or no match
     */
    public String getSymbol (String text) {
      final String ERROR = "NO MATCH";
      for (Entry<String, Pattern> e : mySymbols) {
        if (match(text, e.getValue())) {
          return e.getKey();
        }
      }
      return ERROR;
    }

  /** Returns true if the given text matches the given regular expression pattern
   * @param text text we're searching for
   * @param regex list of choices to match to
   * @return if the text is in the file
   */
    private boolean match (String text, Pattern regex) {
        return regex.matcher(text).matches();
    }
  }
