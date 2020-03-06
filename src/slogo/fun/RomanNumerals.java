package slogo.fun;

import java.util.HashMap;

/**
 * adapted from http://java.candidjava.com/tutorial/Java-program-to-convert-integer-to-roman-letters.htm
 */


public class RomanNumerals {

  private static final int[] BASE_INT = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
  private static final String[] BASE_ROMAN = {"M", "CM", "D", "CD", "C", "SC", "L", "XL", "X", "IX", "V", "IV", "I"};

  private HashMap<Integer, String> intToRoman;

  public RomanNumerals(){
    intToRoman = new HashMap<>();
    setup();
  }

  private void setup() {
    //assumption: BASE_INT and BASE_ROMAN have the same length, and the count down from 1000 in the correct base units
    for(int k = 0; k<BASE_INT.length; k++){
      intToRoman.put(BASE_INT[k], BASE_ROMAN[k]);
    }
  }

  /**
   * return a String of the integer in roman numerals
   * @param num number to convert
   * @return roman numeral of num
   */
  public String intToNumeral(int num) {
    String result = "";
    for (int i : BASE_INT) {
      while (num >= i) {
        result += intToRoman.get(i);
        num -= i;
      }
    }
    return result;
  }
}
