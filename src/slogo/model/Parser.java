package slogo.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Parser {
    // where to find resources specifically for this class
    private String resourcesPackage;
    // "types" and the regular expression patterns that recognize those types
    // note, it is a list because order matters (some patterns may be more generic)
    private List<Entry<String, Pattern>> mySymbols;

    /**
     * Create an empty parser
     */
    public Parser (String resourcesPackageName) {
      resourcesPackage = resourcesPackageName;
      mySymbols = new ArrayList<>();
    }

    /**
     * Adds the given resource file to this language's recognized types
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
     */
    public String getSymbol (String text) {
      final String ERROR = "NO MATCH";
      for (Entry<String, Pattern> e : mySymbols) {
        if (match(text, e.getValue())) {
          return e.getKey();
        }
      }
      // FIXME: perhaps throw an exception instead
      return ERROR;
    }

  /** Returns true if the given text matches the given regular expression pattern
   * @param text text we're searching for
   * @param regex list of choices to match to
   * @return if the text is in the file
   */
    private boolean match (String text, Pattern regex) {
      // THIS IS THE IMPORTANT LINE
      return regex.matcher(text).matches();
    }
  }
