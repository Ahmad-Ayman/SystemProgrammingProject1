package tech.nebulae_solutions;

import java.util.*;

public class Main {


    public static void main(String[] args) {
        List<String> startAddress = new ArrayList<>();
        List<String> startAddressTit = new ArrayList<>();
        boolean checker = false;
        List<String> column1 = new ArrayList<>();
        List<String> column3 = new ArrayList<>();
        Scanner opop = new Scanner(System.in);
        System.out.print("Enter the path of the problem text : ");
        String ll = opop.nextLine();
        // write your code here
        ReadingInput readingInput = new ReadingInput(ll);
        Converter converter = new Converter();

        //System.out.print(readingInput.input);
        String inp = readingInput.input;
        Scanner scanner = new Scanner(inp);
        int startingHex = 0;
//        if (scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//            String[] languages = line.split("\\s");
//            languages = line.trim().split("\\s+");
//
//            //startAddress.add(Integer.toHexString(Integer.parseInt(languages[(languages.length)-1],16)));
//            startingHex = Integer.parseInt(languages[(languages.length) - 1], 16);
//
//        }
        int addedvalue = 0;
        for (int k = 0; scanner.hasNextLine(); k++) {
            checker = false;
            String line = scanner.nextLine();
            // process the line
            String[] languages = line.split("\\s");
            languages = line.trim().split("\\s+");
            if (k == 1) {
                if (languages.length > 2) {
                    startAddress.add(Integer.toHexString(startingHex));
                    startAddressTit.add(languages[1]);
                    column1.add(languages[0]);
                    column3.add(languages[2]);
                    for (int i = 0; i < converter.OPTAB.length; i++) {
                        if (languages[1].equals(converter.OPTAB[i][0])) {
                            //  startAddress.add(Integer.toHexString(Integer.parseInt((startAddress.get(startAddress.size() - 1) + 3),16)));
                            addedvalue = 3;
                            checker = true;
                            break;
                        }


                    }
                    if (!checker) {
                        switch (languages[1].toUpperCase()) {
                            case "RESW": {
                                int reswValue = Integer.parseInt(languages[2]);
                                int reswDec = reswValue * 3;
                                int numInHex = Integer.parseInt(String.valueOf(reswDec));
                                addedvalue = numInHex;

                                break;
                            }
                            case "RESB": {
                                int reswValue = Integer.parseInt(languages[2]);
                                addedvalue = Integer.parseInt(String.valueOf(reswValue));

                                break;
                            }
                            case "WORD": {
                                addedvalue = 3;

                                break;
                            }
                            case "BYTE":
                                if (languages[2].startsWith("X")) {
                                    int counter = 0;
                                    int si = languages[2].length() - 3;
                                    counter = si / 2;
                                    addedvalue = counter;

                                } else {
                                    int counter = languages[2].length() - 3;
                                    addedvalue = counter;

                                }
                                break;
                        }
                    }

                } else {
                    startAddress.add(Integer.toHexString(startingHex));
                    startAddressTit.add(languages[0]);
                    if (languages.length == 2) {
                        column1.add(null);
                        column3.add(languages[1]);
                    } else if (languages.length == 1) {
                        column1.add(null);
                        column3.add(null);
                    }

                    // means 1 or 2 parts
                    for (int i = 0; i < converter.OPTAB.length; i++) {
                        if (languages[0].equals(converter.OPTAB[i][0])) {
//                        startAddress.add(startAddress.get(startAddress.size() - 1) + 3);
                            addedvalue = 3;
                            checker = true;
                            break;
                        }
                    }
                    if (!checker) {
                        switch (languages[0].toUpperCase()) {

                            case "RESW": {
                                int reswValue = Integer.parseInt(languages[1]);
                                int reswDec = reswValue * 3;
                                int numInHex = Integer.parseInt(String.valueOf(reswDec));
                                addedvalue = numInHex;

                                break;
                            }
                            case "RESB": {
                                int reswValue = Integer.parseInt(languages[1]);

                                addedvalue = Integer.parseInt(String.valueOf(reswValue));

                                break;
                            }
                            case "WORD": {
                                addedvalue = 3;

                                break;
                            }
                            case "BYTE":
                                if (languages[1].startsWith("X")) {
                                    int counter = 0;
                                    int si = languages[1].length() - 3;
                                    counter = si / 2;
                                    addedvalue = counter;

                                } else {
                                    int counter = languages[1].length() - 3;
                                    addedvalue = counter;
                                }
                                break;
                        }

                    }


                }
            } else {

                if (languages.length > 2) {
                    // means 3 parts
                    for (int i = 0; i < converter.OPTAB.length; i++) {
                        if (languages[1].equals(converter.OPTAB[i][0])) {
                            //  startAddress.add(Integer.toHexString(Integer.parseInt((startAddress.get(startAddress.size() - 1) + 3),16)));
                            int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                            //add += 3;
                            add += addedvalue;
                            addedvalue = 3;
                            startAddress.add(Integer.toHexString(add));
                            startAddressTit.add(languages[1]);
                            column1.add(languages[0]);
                            column3.add(languages[2]);
                            checker = true;
                            break;
                        }


                    }
                    if (!checker) {
                        switch (languages[1].toUpperCase()) {
                            case "START": {
                                int add = Integer.parseInt(languages[2], 16);
                                startAddress.add(Integer.toHexString(add));
                                startAddressTit.add(languages[1]);
                                column1.add(languages[0]);
                                column3.add(languages[2]);
                                startingHex = add;
                                break;
                            }
                            case "END": {
                                int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                add += addedvalue;
                                startAddress.add(Integer.toHexString(add));
                                startAddressTit.add(languages[1]);
                                column1.add(languages[0]);
                                column3.add(languages[2]);
                                break;
                            }

                            case "RESW": {
                                int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                int reswValue = Integer.parseInt(languages[2]);
                                int reswDec = reswValue * 3;
                                int numInHex = Integer.parseInt(String.valueOf(reswDec));
                                // add += numInHex;

                                add += addedvalue;
                                addedvalue = numInHex;
                                startAddress.add(Integer.toHexString(add));
                                startAddressTit.add(languages[1]);
                                column1.add(languages[0]);
                                column3.add(languages[2]);
                                break;
                            }
                            case "RESB": {
                                int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                int reswValue = Integer.parseInt(languages[2]);
                                // add += Integer.parseInt(String.valueOf(reswValue));
                                add += addedvalue;
                                addedvalue = Integer.parseInt(String.valueOf(reswValue));

                                startAddress.add(Integer.toHexString(add));
                                startAddressTit.add(languages[1]);
                                column1.add(languages[0]);
                                column3.add(languages[2]);
                                break;
                            }
                            case "WORD": {
                                int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                // add += 3;
                                add += addedvalue;
                                addedvalue = 3;

                                startAddress.add(Integer.toHexString(add));
                                startAddressTit.add(languages[1]);
                                column1.add(languages[0]);
                                column3.add(languages[2]);
                                break;
                            }
                            case "BYTE":
                                if (languages[2].startsWith("X")) {
                                    int counter = 0;
                                    int si = languages[2].length() - 3;
                                    counter = si / 2;
                                    int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                    //add += counter;
                                    add += addedvalue;
                                    addedvalue = counter;
                                    startAddress.add(Integer.toHexString(add));
                                    startAddressTit.add(languages[1]);
                                    column1.add(languages[0]);
                                    column3.add(languages[2]);
                                } else {
                                    int counter = languages[2].length() - 3;
                                    int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                    // add += counter;
                                    add += addedvalue;
                                    addedvalue = counter;
                                    startAddress.add(Integer.toHexString(add));
                                    startAddressTit.add(languages[1]);
                                    column1.add(languages[0]);
                                    column3.add(languages[2]);
                                }
                                break;
                        }
                    }

                } else {
                    // means 1 or 2 parts
                    for (int i = 0; i < converter.OPTAB.length; i++) {
                        if (languages[0].equals(converter.OPTAB[i][0])) {
//                        startAddress.add(startAddress.get(startAddress.size() - 1) + 3);
                            int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                            //add += 3;
                            add += addedvalue;
                            addedvalue = 3;
                            startAddress.add(Integer.toHexString(add));
                            startAddressTit.add(languages[0]);
                            if (languages.length == 2) {
                                column1.add(null);
                                column3.add(languages[1]);
                            } else if (languages.length == 1) {
                                column1.add(null);
                                column3.add(null);
                            }
                            checker = true;
                            break;
                        }
                    }
                    if (!checker) {
                        switch (languages[0].toUpperCase()) {
                            case "START": {
                                int add = Integer.parseInt(languages[1]);
                                startAddress.add(Integer.toHexString(add));
                                startAddressTit.add(languages[0]);
                                column1.add(null);
                                column3.add(languages[1]);
                                break;
                            }
                            case "END": {
                                int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                add += addedvalue;
                                startAddress.add(Integer.toHexString(add));
                                startAddressTit.add(languages[0]);
                                column1.add(null);
                                column3.add(languages[1]);
                                break;
                            }
                            case "RESW": {
                                int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                int reswValue = Integer.parseInt(languages[1]);
                                int reswDec = reswValue * 3;
                                int numInHex = Integer.parseInt(String.valueOf(reswDec));
                                // add += numInHex;
                                add += addedvalue;
                                addedvalue = numInHex;
                                startAddress.add(Integer.toHexString(add));
                                startAddressTit.add(languages[0]);
                                column1.add(null);
                                column3.add(languages[1]);
                                break;
                            }
                            case "RESB": {
                                int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                int reswValue = Integer.parseInt(languages[1]);

                                //add += Integer.parseInt(String.valueOf(reswValue));
                                add += addedvalue;
                                addedvalue = Integer.parseInt(String.valueOf(reswValue));

                                startAddress.add(Integer.toHexString(add));
                                startAddressTit.add(languages[0]);
                                column1.add(null);
                                column3.add(languages[1]);
                                break;
                            }
                            case "WORD": {
                                int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                //add += 3;
                                add += addedvalue;
                                addedvalue = 3;

                                startAddress.add(Integer.toHexString(add));
                                startAddressTit.add(languages[0]);
                                column1.add(null);
                                column3.add(languages[1]);
                                break;
                            }
                            case "BYTE":
                                if (languages[1].startsWith("X")) {
                                    int counter = 0;
                                    int si = languages[1].length() - 3;
                                    counter = si / 2;
                                    int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                    //  add += counter;
                                    add += addedvalue;
                                    addedvalue = counter;
                                    startAddress.add(Integer.toHexString(add));
                                    startAddressTit.add(languages[0]);
                                    column1.add(null);
                                    column3.add(languages[1]);
                                } else {
                                    int counter = languages[1].length() - 3;
                                    int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                    // add += counter;
                                    add += addedvalue;
                                    addedvalue = counter;
                                    startAddress.add(Integer.toHexString(add));
                                    startAddressTit.add(languages[0]);
                                    column1.add(null);
                                    column3.add(languages[1]);
                                }
                                break;
                        }

                    }
                }
            }
        }

        scanner.close();
//        System.out.println(startAddress.size());
//        System.out.println(startAddressTit.size());
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------Pass 1 Start-----------------------------------------------------------\n");

        for (int i = 0; i < startAddress.size(); i++) {
            System.out.println(startAddress.get(i) + "  " + column1.get(i) + "  " + startAddressTit.get(i) + "  " + column3.get(i));
        }
//        System.out.println(Arrays.toString(startAddress.toArray()));
//        System.out.println(Arrays.toString(startAddressTit.toArray()));
        System.out.println("\n\n");
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 1; i < startAddress.size(); i++) {
            if (column1.get(i) != null) {
                map.put(column1.get(i), startAddress.get(i));
            }
        }
        System.out.println("Symbol Table");
        System.out.println("-------------------------------------------");
        for (Map.Entry m : map.entrySet()) {
            if (m.getKey().toString().length() <= 3) {
                System.out.println(m.getKey() + "\t\t\t\t|\t\t\t" + m.getValue());

            } else {
                System.out.println(m.getKey() + "\t\t\t|\t\t\t" + m.getValue());
            }
            System.out.println("-------------------------------------------");
        }

        System.out.println("----------------------------------------------------------Pass 1 END-------------------------------------------------------------\n");

        String[][] passTwoTable = new String[startAddress.size() - 1][5];
        for (int i = 0; i < startAddress.size() - 1; i++) {
            int k = i + 1;
            String code = "";
            if (startAddressTit.get(k).toUpperCase().equals("RESW") || startAddressTit.get(k).toUpperCase().equals("RESB")) {
                passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k).toUpperCase(), startAddressTit.get(k).toUpperCase(), column3.get(k), "No Obj Code"};
            } else {

                if ((column1.get(k) == null || column1.get(k).isEmpty()) && (column3.get(k) == null || column3.get(k).isEmpty())) {
                    for (int l = 0; l < converter.OPTAB.length; l++) {
                        if (startAddressTit.get(k).toUpperCase().equals(converter.OPTAB[l][0])) {
                            code = converter.OPTAB[l][2];
                            break;
                        }
                    }
                    passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k), startAddressTit.get(k).toUpperCase(), column3.get(k), code};

                } else if ((column1.get(k) == null || column1.get(k).isEmpty()) && (column3.get(k) != null || !column3.get(k).isEmpty()) && startAddressTit.get(k).toUpperCase().equals("BYTE") && startAddressTit.get(k).toUpperCase().equals("WORD")) {
                    for (int l = 0; l < converter.OPTAB.length; l++) {
                        if (startAddressTit.get(k).toUpperCase().equals(converter.OPTAB[l][0])) {
                            code = converter.OPTAB[l][2];
                            break;
                        }
                    }
                    if (column3.get(k).toUpperCase().endsWith(",X")) {
                        String valueofbeforeX = column3.get(k).substring(0, column3.get(k).length() - 2);
                        ConverterBinaryHexa converterBinaryHexa = new ConverterBinaryHexa(map.get(valueofbeforeX.toUpperCase()));
                        code += converterBinaryHexa.returnedHexa();
                    } else {
                        code += map.get(column3.get(k).toUpperCase());
                    }
                    passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k), startAddressTit.get(k).toUpperCase(), column3.get(k).toUpperCase(), code};

                } else if (startAddressTit.get(k).toUpperCase().equals("WORD")) {
                    int num = Integer.parseInt(column3.get(k));
                    code = Integer.toHexString(num);
                    passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k), startAddressTit.get(k).toUpperCase(), column3.get(k).toUpperCase(), code};

                } else if (startAddressTit.get(k).toUpperCase().equals("BYTE")) {
                    if (column3.get(k).startsWith("C")) {
                        String w = column3.get(k).substring(2, column3.get(k).length() - 1);
                        code = ConverterBinaryHexa.charToHex(w);
                    } else {
                        code = column3.get(k).substring(2, column3.get(k).length() - 1);
                    }
                    passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k), startAddressTit.get(k).toUpperCase(), column3.get(k), code};

                } else {
                    for (int l = 0; l < converter.OPTAB.length; l++) {
                        if (startAddressTit.get(k).toUpperCase().equals(converter.OPTAB[l][0])) {
                            code = converter.OPTAB[l][2];
                            break;
                        }
                    }
                    if (column3.get(k).toUpperCase().endsWith(",X")) {
                        String valueofbeforeX = column3.get(k).substring(0, column3.get(k).length() - 2);
                        ConverterBinaryHexa converterBinaryHexa = new ConverterBinaryHexa(map.get(valueofbeforeX.toUpperCase()));
                        code += converterBinaryHexa.returnedHexa();
                    } else {
                        code += map.get(column3.get(k));
                    }


                    //   String u = (String) map.get(column3.get(k));
                    passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k), startAddressTit.get(k).toUpperCase(), column3.get(k), code};

                }
            }
            // passTwoTable[i] = new String[]{startAddress.get(i + 1), column1.get(i + 1), startAddressTit.get(i + 1), column3.get(i + 1),}
        }
        System.out.println("----------------------------------------------------------Pass 2 Start-----------------------------------------------------------");
        System.out.println("--------------------------------------------------------Object Code Table--------------------------------------------------------");

        System.out.print("\n\n");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        System.out.println("Location" + "\t\t|\t\t\t\t\t\t\t\t\t"+"Source Statement"+"\t\t\t\t\t\t\t\t|\t\t  Object Code");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");


        for (int i = 0; i < passTwoTable.length; i++) {
            for (int j = 0; j < passTwoTable[i].length; j++) {
                if (passTwoTable[i][j] != null) {
                    if (passTwoTable[i][j].length() <= 3) {
                        System.out.print(passTwoTable[i][j] + "\t\t\t\t|\t\t\t");

                    } else if (passTwoTable[i][j].length() > 6) {
                        System.out.print(passTwoTable[i][j] + "\t\t|\t\t\t");

                    } else {
                        System.out.print(passTwoTable[i][j] + "\t\t\t|\t\t\t");
                    }

                } else {
                    System.out.print(passTwoTable[i][j] + "\t\t\t|\t\t\t");

                }

            }
            System.out.print("\n");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

        }


    }
}
