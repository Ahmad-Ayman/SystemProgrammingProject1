package tech.nebulae_solutions;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ConverterBinaryHexa {
    String outputHexa;
    String inputHexa;
    // write your code here
    private Map<Character, String> mapping = new HashMap<Character, String>();


    public ConverterBinaryHexa(String inputHexa) {
        this.inputHexa = inputHexa;
        mapping.put('0', "0000");
        mapping.put('1', "0001");
        mapping.put('2', "0010");
        mapping.put('3', "0011");
        mapping.put('4', "0100");
        mapping.put('5', "0101");
        mapping.put('6', "0110");
        mapping.put('7', "0111");
        mapping.put('8', "1000");
        mapping.put('9', "1001");
        mapping.put('a', "1010");
        mapping.put('b', "1011");
        mapping.put('c', "1100");
        mapping.put('d', "1101");
        mapping.put('e', "1110");
        mapping.put('f', "1111");
    }

    String returnedHexa() {
        char[] ch = new char[inputHexa.length()];

        // Copy character by character into array
        for (int i = 0; i < inputHexa.length(); i++) {
            ch[i] = inputHexa.charAt(i);
        }

        StringBuilder result = new StringBuilder();
        for (char c : ch) {
            // need to do some error checking here.
            result.append(mapping.get(Character.toLowerCase(c)));
        }

        int n = Integer.parseInt(result.toString(), 2);
        int n2 = Integer.parseInt("1000000000000000", 2);
        n += n2;
        String nout = Integer.toHexString(n);
        return nout;
    }


    public static String charToHex(String arg) {
        return String.format("%x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }
}
