package tech.nebulae_solutions;

import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main(String[] args) {


        while (true) {
            System.out.print("Enter 1 - for SIC Machine, 2 - for SIC/XE Machine, 3 - for Linkage Loader, 4 - for Absolute Loader, 5 - for Linking Editor or 6 - to Exit: ");
            Scanner a = new Scanner(System.in);
            int mach = a.nextInt();
            if (mach == 1) {
                List<String> startAddress = new ArrayList<>();
                List<String> startAddressTit = new ArrayList<>();
                boolean checker = false;
                List<String> column1 = new ArrayList<>();
                List<String> column3 = new ArrayList<>();
                String programName;
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

                int addedvalue = 0;

                for (int k = 0; scanner.hasNextLine(); k++) {
                    checker = false;
                    String line = scanner.nextLine();
                    // process the line
                    String[] languages = line.split("\\s");
                    languages = line.trim().split("\\s+");
                    if (k == 1) {
                        if (languages.length > 2) {
                            startAddress.add(RPad(Integer.toHexString(startingHex), 4, '0'));
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
                            startAddress.add(RPad(Integer.toHexString(startingHex), 4, '0'));
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
                                    startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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
                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                        startAddressTit.add(languages[1]);
                                        column1.add(languages[0]);
                                        column3.add(languages[2]);
                                        startingHex = add;
                                        break;
                                    }
                                    case "END": {
                                        int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                        add += addedvalue;
                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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
                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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

                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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

                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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
                                            startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                            startAddressTit.add(languages[1]);
                                            column1.add(languages[0]);
                                            column3.add(languages[2]);
                                        } else {
                                            int counter = languages[2].length() - 3;
                                            int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                            // add += counter;
                                            add += addedvalue;
                                            addedvalue = counter;
                                            startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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
                                    startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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
                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                        startAddressTit.add(languages[0]);
                                        column1.add(null);
                                        column3.add(languages[1]);
                                        break;
                                    }
                                    case "END": {
                                        int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                        add += addedvalue;
                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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
                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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

                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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

                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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
                                            startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                            startAddressTit.add(languages[0]);
                                            column1.add(null);
                                            column3.add(languages[1]);
                                        } else {
                                            int counter = languages[1].length() - 3;
                                            int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                            // add += counter;
                                            add += addedvalue;
                                            addedvalue = counter;
                                            startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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
                        passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k).toUpperCase(), startAddressTit.get(k).toUpperCase(), column3.get(k), "------"};
                    } else {

                        if ((column1.get(k) == null || column1.get(k).isEmpty()) && (column3.get(k) == null || column3.get(k).isEmpty())) {
                            for (int l = 0; l < converter.OPTAB.length; l++) {
                                if (startAddressTit.get(k).toUpperCase().equals(converter.OPTAB[l][0])) {
                                    code = converter.OPTAB[l][2];
                                    break;
                                }
                            }
                            passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k), startAddressTit.get(k).toUpperCase(), column3.get(k), LPad(code, 6, '0')};

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
                            passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k), startAddressTit.get(k).toUpperCase(), column3.get(k).toUpperCase(), RPad(code, 6, '0')};

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

                System.out.println("Location" + "\t\t|\t\t\t\t\t\t\t\t\t" + "Source Statement" + "\t\t\t\t\t\t\t\t|\t\t  Object Code");
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
                System.out.println("--------------------------------------------------------HTE Record--------------------------------------------------------");
                StringBuilder hRecord = new StringBuilder();
                StringBuilder tRecord = new StringBuilder();
                StringBuilder eRecord = new StringBuilder();
                List<StringBuilder> t = new ArrayList<>();
                // ArrayList<String> ttRecord = new ArrayList<>();

                LinkedHashMap<String, String> ttRecord = new LinkedHashMap<>();
                hRecord.append("H˄");
                hRecord.append(LPad(readingInput.progName(), 6, 'x'));
                hRecord.append("˄");
                hRecord.append(RPad(startAddress.get(0), 6, '0'));
                hRecord.append("˄");
                int startAddressForH = Integer.parseInt(startAddress.get(0), 16);
                int endAddressForH = Integer.parseInt(startAddress.get(startAddress.size() - 1), 16);
                int lengthForH = endAddressForH - startAddressForH;
                String leng = Integer.toHexString(lengthForH);
                hRecord.append(RPad(leng, 6, '0'));
                hRecord.append("\n");
                System.out.print(hRecord.toString());
                tRecord.append("T˄");

                tRecord.append(RPad(passTwoTable[0][0], 6, '0'));
                tRecord.append("˄");
                ////////////////////// Cutted code here ////////////////////
                LinkedHashMap<String, String> linkedTRecord = new LinkedHashMap<>();
                List<String> keys = new ArrayList<>();
                List<String> values = new ArrayList<>();
                for (int i = 0; i < passTwoTable.length - 1; i++) {
                    linkedTRecord.put(passTwoTable[i][0], passTwoTable[i][4]);
                    keys.add(passTwoTable[i][0]);
                    values.add(passTwoTable[i][4]);
                }
                //   System.out.println(linkedTRecord.entrySet());

                Builder b = new Builder(values, keys);
                b.build();

                // System.out.println(Arrays.toString(tr.toArray()));


                eRecord.append("E˄");
                eRecord.append(RPad(startAddress.get(0), 6, '0'));
                System.out.print(eRecord.toString());
                System.out.print("\n\n");

                //////////////////// End of SIC Machine ////////////////////
            } else if (mach == 2) {
                //////////////////// Start of SIC/XE Machine ////////////////////
                List<String> startAddress = new ArrayList<>();
                List<String> startAddressTit = new ArrayList<>();
                boolean checker = false;
                List<String> column1 = new ArrayList<>();
                List<String> column3 = new ArrayList<>();
                String programName;
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
                int addedvalue = 0;
                ArrayList<Integer> formats = new ArrayList<>();
                for (int k = 0; scanner.hasNextLine(); k++) {
                    checker = false;
                    String line = scanner.nextLine();
                    // process the line
                    String[] languages = line.split("\\s");
                    languages = line.trim().split("\\s+");
                    System.out.println(Arrays.toString(languages));
                    if (k == 1) {
                        if (languages.length > 2) {
                            startAddress.add(RPad(Integer.toHexString(startingHex), 4, '0'));
                            startAddressTit.add(languages[1]);
                            column1.add(languages[0]);
                            column3.add(languages[2]);
                            for (int i = 0; i < converter.OPTAB.length; i++) {
                                if (languages[1].contains(converter.OPTAB[i][0])) {
                                    // Format 4
                                    if (languages[1].contains("+")) {
                                        addedvalue = 4;
                                        formats.add(addedvalue);
                                    }
                                    // Format 2
                                    else if (isFormatTwo(languages[2])) {
                                        addedvalue = 2;
                                        formats.add(addedvalue);
                                    }
                                    // Format 3
                                    else {
                                        addedvalue = 3;
                                        formats.add(addedvalue);
                                    }

                                    //  startAddress.add(Integer.toHexString(Integer.parseInt((startAddress.get(startAddress.size() - 1) + 3),16)));
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
                                    case "BYTE": {
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
                                    case "BASE": {
                                        addedvalue = 0;
                                    }
                                }
                            }

                        } else {
                            startAddress.add(RPad(Integer.toHexString(startingHex), 4, '0'));
                            startAddressTit.add(languages[0]);
                            if (languages.length == 2) {
                                column1.add(null);
                                column3.add(languages[1]);


                                // means 1 or 2 parts
                                for (int i = 0; i < converter.OPTAB.length; i++) {
                                    if (languages[0].contains(converter.OPTAB[i][0])) {
//                        startAddress.add(startAddress.get(startAddress.size() - 1) + 3);
                                        if (languages[0].contains("+")) {
                                            addedvalue = 4;
                                            formats.add(addedvalue);
                                        }
                                        // Format 2
                                        else if (isFormatTwo(languages[1])) {
                                            addedvalue = 2;
                                            formats.add(addedvalue);
                                        }
                                        // Format 3
                                        else {
                                            addedvalue = 3;
                                            formats.add(addedvalue);
                                        }
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
                                        case "BYTE": {
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
                                        case "BASE": {
                                            addedvalue = 0;
                                        }
                                    }

                                }
                            } else if (languages.length < 2) {
                                column1.add(null);
                                column3.add(null);
                                if (languages[0].equals("RSUB")) {
                                    // Format 3
                                    addedvalue = 3;
                                    formats.add(addedvalue);
                                } else {
                                    // Format 1
                                    addedvalue = 1;
                                    formats.add(addedvalue);
                                }
                            }

                        }
                    } else {

                        if (languages.length > 2) {
                            // means 3 parts
                            for (int i = 0; i < converter.OPTAB.length; i++) {
                                if (languages[1].contains(converter.OPTAB[i][0])) {
                                    //  startAddress.add(Integer.toHexString(Integer.parseInt((startAddress.get(startAddress.size() - 1) + 3),16)));
                                    int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                    //add += 3;
                                    add += addedvalue;
                                    if (languages[1].contains("+")) {
                                        addedvalue = 4;
                                        formats.add(addedvalue);
                                    }
                                    // Format 2
                                    else if (isFormatTwo(languages[2])) {
                                        addedvalue = 2;
                                        formats.add(addedvalue);
                                    }
                                    // Format 3
                                    else {
                                        addedvalue = 3;
                                        formats.add(addedvalue);
                                    }

                                    startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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
                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                        startAddressTit.add(languages[1]);
                                        column1.add(languages[0]);
                                        column3.add(languages[2]);
                                        startingHex = add;
                                        formats.add(0);

                                        break;
                                    }
                                    case "END": {
                                        int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                        add += addedvalue;
                                        formats.add(0);

                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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
                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                        startAddressTit.add(languages[1]);
                                        column1.add(languages[0]);
                                        column3.add(languages[2]);
                                        formats.add(0);

                                        break;
                                    }
                                    case "RESB": {
                                        int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                        int reswValue = Integer.parseInt(languages[2]);
                                        // add += Integer.parseInt(String.valueOf(reswValue));
                                        add += addedvalue;
                                        addedvalue = Integer.parseInt(String.valueOf(reswValue));
                                        formats.add(0);

                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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
                                        formats.add(0);

                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                        startAddressTit.add(languages[1]);
                                        column1.add(languages[0]);
                                        column3.add(languages[2]);
                                        break;
                                    }
                                    case "BYTE": {
                                        if (languages[2].startsWith("X")) {
                                            int counter = 0;
                                            int si = languages[2].length() - 3;
                                            counter = si / 2;
                                            int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                            //add += counter;
                                            add += addedvalue;
                                            addedvalue = counter;
                                            formats.add(0);

                                            startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                            startAddressTit.add(languages[1]);
                                            column1.add(languages[0]);
                                            column3.add(languages[2]);
                                        } else {
                                            int counter = languages[2].length() - 3;
                                            int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                            // add += counter;
                                            add += addedvalue;
                                            addedvalue = counter;
                                            formats.add(0);

                                            startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                            startAddressTit.add(languages[1]);
                                            column1.add(languages[0]);
                                            column3.add(languages[2]);
                                        }
                                        break;
                                    }
                                    case "BASE": {
                                        int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                        // add += 3;
                                        add += addedvalue;
                                        addedvalue = 0;
                                        formats.add(0);

                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                        startAddressTit.add(languages[1]);
                                        column1.add(languages[0]);
                                        column3.add(languages[2]);
                                        break;
                                    }
                                }
                            }

                        } else if (languages.length == 2) {
                            // means 1 or 2 parts
                            for (int i = 0; i < converter.OPTAB.length; i++) {
                                if (languages[0].contains(converter.OPTAB[i][0])) {
//                        startAddress.add(startAddress.get(startAddress.size() - 1) + 3);
                                    int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                    //add += 3;
                                    add += addedvalue;
                                    if (languages[0].contains("+")) {
                                        addedvalue = 4;
                                        formats.add(addedvalue);
                                    }
                                    // Format 2
                                    else if (isFormatTwo(languages[1])) {
                                        addedvalue = 2;
                                        formats.add(addedvalue);
                                    }
                                    // Format 3
                                    else {
                                        addedvalue = 3;
                                        formats.add(addedvalue);
                                    }

                                    startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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
                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                        startAddressTit.add(languages[0]);
                                        column1.add(null);
                                        column3.add(languages[1]);
                                        formats.add(0);

                                        break;
                                    }
                                    case "END": {
                                        int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                        add += addedvalue;
                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                        startAddressTit.add(languages[0]);
                                        column1.add(null);
                                        column3.add(languages[1]);
                                        formats.add(0);
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
                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                        startAddressTit.add(languages[0]);
                                        column1.add(null);
                                        column3.add(languages[1]);
                                        formats.add(0);
                                        break;
                                    }
                                    case "RESB": {
                                        int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                        int reswValue = Integer.parseInt(languages[1]);
                                        formats.add(0);
                                        //add += Integer.parseInt(String.valueOf(reswValue));
                                        add += addedvalue;
                                        addedvalue = Integer.parseInt(String.valueOf(reswValue));

                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
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
                                        formats.add(0);
                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                        startAddressTit.add(languages[0]);
                                        column1.add(null);
                                        column3.add(languages[1]);
                                        break;
                                    }
                                    case "BYTE": {
                                        if (languages[1].startsWith("X")) {
                                            int counter = 0;
                                            int si = languages[1].length() - 3;
                                            counter = si / 2;
                                            int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                            //  add += counter;
                                            formats.add(0);
                                            add += addedvalue;
                                            addedvalue = counter;
                                            startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                            startAddressTit.add(languages[0]);
                                            column1.add(null);
                                            column3.add(languages[1]);
                                        } else {
                                            int counter = languages[1].length() - 3;
                                            int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                            // add += counter;
                                            add += addedvalue;
                                            formats.add(0);
                                            addedvalue = counter;
                                            startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                            startAddressTit.add(languages[0]);
                                            column1.add(null);
                                            column3.add(languages[1]);
                                        }
                                        break;
                                    }
                                    case "BASE": {
                                        int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                                        //add += 3;
                                        add += addedvalue;
                                        addedvalue = 0;
                                        formats.add(0);
                                        startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                                        startAddressTit.add(languages[0]);
                                        column1.add(null);
                                        column3.add(languages[1]);
                                        break;
                                    }
                                }

                            }
                        } else if (languages.length < 2) {
                            int add = Integer.parseInt((startAddress.get(startAddress.size() - 1)), 16);
                            add += addedvalue;
                            column1.add(null);
                            column3.add(null);
                            startAddress.add(RPad(Integer.toHexString(add), 4, '0'));
                            startAddressTit.add(languages[0]);
                            if (languages[0].equals("RSUB")) {
                                // Format 3
                                addedvalue = 3;
                                formats.add(addedvalue);
                            } else {
                                // Format 1
                                addedvalue = 1;
                                formats.add(addedvalue);
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

                System.out.println(Arrays.toString(formats.toArray()));
                String[][] passTwoTable = new String[startAddress.size() - 1][5];
                for (int i = 0; i < startAddress.size() - 1; i++) {
                    String objectCode = "";
                    String n = "0", iii = "0", x = "0", b = "0", p = "0", e = "0";
                    int k = i + 1;
                    String code = "";
                    if (startAddressTit.get(k).toUpperCase().equals("RESW") || startAddressTit.get(k).toUpperCase().equals("RESB")) {
                        passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k).toUpperCase(), startAddressTit.get(k).toUpperCase(), column3.get(k), "------"};
                    } else {

                        if ((column1.get(k) == null || column1.get(k).isEmpty()) && (column3.get(k) == null || column3.get(k).isEmpty())) {
                            for (int l = 0; l < converter.OPTAB.length; l++) {
                                if (startAddressTit.get(k).toUpperCase().contains(converter.OPTAB[l][0])) {
                                    code = converter.OPTAB[l][2];
                                    break;
                                }
                            }
                            passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k), startAddressTit.get(k).toUpperCase(), column3.get(k), LPad(code, 6, '0')};

                        } else if ((column1.get(k) == null || column1.get(k).isEmpty()) && (column3.get(k) != null || !column3.get(k).isEmpty()) && startAddressTit.get(k).toUpperCase().equals("BYTE") && startAddressTit.get(k).toUpperCase().equals("WORD")) {
                            for (int l = 0; l < converter.OPTAB.length; l++) {
                                if (startAddressTit.get(k).toUpperCase().contains(converter.OPTAB[l][0])) {
                                    code = converter.OPTAB[l][2];
                                    break;
                                }
                            }

                            // if format 2
                            if (formats.get(k) == 2) {
                                if (column3.get(k).length() == 1) {
                                    code += regNumberReturn(column3.get(k));
                                    code += "0";
                                } else {
                                    code += regNumberReturn(column3.get(k).substring(0, 1));
                                    code += regNumberReturn(column3.get(k).substring(2));
                                }
                            } else if (formats.get(k) == 3 || formats.get(k) == 4) {
                                // E Flag
                                if (formats.get(k) == 4) {
                                    e = "1";

                                } else if (formats.get(k) == 3) {
                                    e = "0";
                                }
                                // Indexed Flag ( X )
                                if (column3.get(k).toUpperCase().endsWith(",X")) {
                                    x = "1";
//                                String valueofbeforeX = column3.get(k).substring(0, column3.get(k).length() - 2);
//                                ConverterBinaryHexa converterBinaryHexa = new ConverterBinaryHexa(map.get(valueofbeforeX.toUpperCase()));
//                                code += converterBinaryHexa.returnedHexa();
                                } else {
                                    x = "0";
                                    //   code += map.get(column3.get(k).toUpperCase());
                                }
                                // Immediate or Indirect ( I & N )
                                if (column3.get(k).toUpperCase().startsWith("#")) {
                                    iii = "1";
                                    n = "0";
                                    b = "0";
                                    p = "0";
                                } else if (column3.get(k).toUpperCase().startsWith("@") && formats.get(k) == 3) {
                                    iii = "0";
                                    n = "1";
                                    b = "0";
                                    p = "1";
                                } else if (!(column3.get(k).toUpperCase().startsWith("@")) && !(column3.get(k).toUpperCase().startsWith("#"))) {
                                    iii = "1";
                                    n = "1";
                                    b = "0";
                                    p = "1";
                                }
                                if (formats.get(k) == 4) {
                                    b = "0";
                                    p = "0";
                                }

                                if (p.equals("1")) {
                                    String dispHex;
                                    if (Integer.parseInt(startAddress.get(k), 16) < Integer.parseInt(startAddress.get(k + 1), 16)) {

                                        dispHex = Integer.toHexString((Integer.parseInt(printOneAndTwosComplement(startAddress.get(k)), 16) + Integer.parseInt(startAddress.get(k + 1), 16)));
                                    } else {
                                        dispHex = Integer.toHexString((Integer.parseInt(startAddress.get(k), 16) - Integer.parseInt(startAddress.get(k + 1), 16)));
                                    }

//                                    String dispHex;
//                                    if(formats.get(k)==3) {
//                                         dispHex = RPad(Integer.toHexString(Integer.parseInt(startAddress.get(k), 16) - Integer.parseInt(startAddress.get(k + 1), 16)),3,'0');
//                                    }else{
//                                        dispHex = RPad(Integer.toHexString(Integer.parseInt(startAddress.get(k), 16) - Integer.parseInt(startAddress.get(k + 1), 16)),5,'0');
//
//                                    }
                                    int dispDecimal = Integer.parseInt(dispHex, 16);
                                    if (dispDecimal > 2047) {
                                        p = "0";
                                        b = "1";

                                    }

                                }

                                String opCodeInBinary = opCodeToBinary(code);
                                opCodeInBinary = opCodeInBinary.concat(n).concat(iii).concat(x).concat(b).concat(p).concat(e);
                                System.out.println(" OP CODE = " + opCodeInBinary);

                                if (formats.get(k) == 3) {
                                    String dispHex;
                                    String TA;
                                    if (p.equals("1") && b.equals("0")) {

                                        // CASE OF INDEXED
                                        if (column3.get(k).endsWith(",X")) {
                                            TA = map.get(column3.get(k).substring(0, column3.get(k).length() - 2));
                                        }
                                        // CASE OF IMMEDIATE
//                                        else if (column3.get(k).startsWith("#")) {
//                                            if (map.containsKey(column3.get(k).substring(1))) {
//                                                TA = map.get(column3.get(k).substring(1));
//                                            } else {
//                                                TA = column3.get(k).substring(1);
//                                            }

                                        //    }
                                        // CASE OF INDIRECT
                                        else if (column3.get(k).startsWith("@")) {
                                            TA = map.get(column3.get(k).substring(1));
                                            // CASE OF SIMPLE
                                        } else {
                                            TA = map.get(column3.get(k));
                                        }
                                        // Displacement in CASE OF PC Relative
                                        if (Integer.parseInt(TA, 16) < Integer.parseInt(startAddress.get(k + 1), 16)) {

                                            dispHex = RPad(Integer.toHexString((Integer.parseInt(printOneAndTwosComplement(TA), 16) + Integer.parseInt(startAddress.get(k + 1), 16))), 3, '0');
                                        } else {
                                            dispHex = RPad(Integer.toHexString((Integer.parseInt(TA, 16) - Integer.parseInt(startAddress.get(k + 1), 16))), 3, '0');
                                        }

                                        //  dispHex = RPad((Integer.toHexString(Integer.parseInt(TA, 16) - Integer.parseInt(startAddress.get(k + 1), 16))), 3, '0');
                                    }
                                    // START OF CASE OF BASE RELATIVE
                                    else if (b.equals("1") && p.equals("0")) {
                                        int poss = 0;
                                        for (int pp = 0; pp < k; pp++) {
                                            if (startAddressTit.get(pp).equals("BASE")) {
                                                poss = pp;
                                                break;
                                            }
                                        }
                                        String baseValue;
                                        if (column3.get(poss).startsWith("#")) {
                                            baseValue = column3.get(poss).substring(1);
                                        } else {
                                            baseValue = map.get(column3.get(poss));
                                        }
                                        String TA2;
                                        // CASE OF INDEXED
                                        if (column3.get(k).endsWith(",X")) {
                                            TA2 = map.get(column3.get(k).substring(0, column3.get(k).length() - 2));
                                        }
                                        // CASE OF IMMEDIATE
//                                        else if (column3.get(k).startsWith("#")) {
//                                            if (map.containsKey(column3.get(k).substring(1))) {
//                                                TA2 = map.get(column3.get(k).substring(1));
//                                            } else {
//                                                TA2 = column3.get(k).substring(1);
//                                            }
                                        //    }
                                        // CASE OF INDIRECT
                                        else if (column3.get(k).startsWith("@")) {
                                            TA2 = map.get(column3.get(k).substring(1));
                                            // CASE OF SIMPLE
                                        } else {
                                            TA2 = map.get(column3.get(k));
                                        }
                                        // CASE OF BASE RELATIVE
                                        if (Integer.parseInt(TA2, 16) < Integer.parseInt(startAddress.get(k + 1), 16)) {

                                            dispHex = RPad(Integer.toHexString((Integer.parseInt(printOneAndTwosComplement(TA2), 16) + Integer.parseInt(startAddress.get(k + 1), 16))), 3, '0');
                                        } else {
                                            dispHex = RPad(Integer.toHexString((Integer.parseInt(TA2, 16) - Integer.parseInt(startAddress.get(k + 1), 16))), 3, '0');
                                        }
                                        //     dispHex = RPad((Integer.toHexString(Integer.parseInt(TA2, 16) - Integer.parseInt(baseValue, 16))), 3, '0');

//
//                                        if(formats.get(k)==3) {
//                                            dispHex = RPad(Integer.toHexString(Integer.parseInt(TA2, 16) - Integer.parseInt(baseValue, 16)),3,'0');
//                                        }else{
//                                            dispHex = RPad(Integer.toHexString(Integer.parseInt(TA2, 16) - Integer.parseInt(baseValue, 16)),5,'0');
//
//                                        }

                                    } else {

                                        String TA3 = "";

                                        //CASE OF IMMEDIATE
                                        if (column3.get(k).startsWith("#")) {
                                            if (map.containsKey(column3.get(k).substring(1))) {
                                                TA3 = map.get(column3.get(k).substring(1));
                                            } else {
                                                TA3 = column3.get(k).substring(1);
                                            }
                                        }

                                        if (Integer.parseInt(TA3, 16) < Integer.parseInt(startAddress.get(k + 1), 16)) {

                                            dispHex = RPad(Integer.toHexString((Integer.parseInt(printOneAndTwosComplement(TA3), 16) + Integer.parseInt(startAddress.get(k + 1), 16))), 3, '0');
                                        } else {
                                            dispHex = RPad(Integer.toHexString((Integer.parseInt(TA3, 16) - Integer.parseInt(startAddress.get(k + 1), 16))), 3, '0');
                                        }
                                        // dispHex = RPad(Integer.toHexString(Integer.parseInt(TA3, 16)), 3, '0');
//                                        if(formats.get(k)==3) {
//                                            dispHex = RPad(Integer.toHexString(Integer.parseInt(TA3, 16)),3,'0');
//                                        }else{
//                                            dispHex = RPad(Integer.toHexString(Integer.parseInt(TA3, 16)),5,'0');
//
//                                        }
                                    }
                                    objectCode = binaryToHex(opCodeInBinary);
                                    objectCode = objectCode.concat(dispHex);
                                } else if (formats.get(k) == 4) {
                                    String dispHex2;
                                    String TA4;
                                    if (column3.get(k).endsWith(",X")) {
                                        TA4 = map.get(column3.get(k).substring(0, column3.get(k).length() - 2));
                                    }
                                    //  CASE OF IMMEDIATE
                                    else if (column3.get(k).startsWith("#")) {
                                        if (map.containsKey(column3.get(k).substring(1))) {
                                            TA4 = map.get(column3.get(k).substring(1));
                                        } else {
                                            TA4 = column3.get(k).substring(1);
                                        }
                                    }
                                    // CASE OF INDIRECT
                                    else if (column3.get(k).startsWith("@")) {
                                        TA4 = map.get(column3.get(k).substring(1));
                                        // CASE OF SIMPLE
                                    } else {
                                        TA4 = map.get(column3.get(k));
                                    }

                                    dispHex2 = RPad(Integer.toHexString(Integer.parseInt(TA4, 16)), 5, '0');
                                    objectCode = binaryToHex(opCodeInBinary);
                                    objectCode = objectCode.concat(dispHex2);
                                }


                            }

                            passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k), startAddressTit.get(k).toUpperCase(), column3.get(k).toUpperCase(), objectCode};

                        } else if (startAddressTit.get(k).toUpperCase().equals("WORD")) {
                            int num = Integer.parseInt(column3.get(k));
                            code = Integer.toHexString(num);
                            passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k), startAddressTit.get(k).toUpperCase(), column3.get(k).toUpperCase(), RPad(code, 6, '0')};

                        } else if (startAddressTit.get(k).toUpperCase().equals("BYTE")) {
                            if (column3.get(k).startsWith("C")) {
                                String w = column3.get(k).substring(2, column3.get(k).length() - 1);
                                code = ConverterBinaryHexa.charToHex(w);
                            } else {
                                code = column3.get(k).substring(2, column3.get(k).length() - 1);
                            }
                            passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k), startAddressTit.get(k).toUpperCase(), column3.get(k), code};

                        } else {
//                            for (int l = 0; l < converter.OPTAB.length; l++) {
//                                if (startAddressTit.get(k).toUpperCase().equals(converter.OPTAB[l][0])) {
//                                    code = converter.OPTAB[l][2];
//                                    break;
//                                }
//                            }
//                            if (column3.get(k).toUpperCase().endsWith(",X")) {
//                                String valueofbeforeX = column3.get(k).substring(0, column3.get(k).length() - 2);
//                                ConverterBinaryHexa converterBinaryHexa = new ConverterBinaryHexa(map.get(valueofbeforeX.toUpperCase()));
//                                code += converterBinaryHexa.returnedHexa();
//                            } else {
//                                code += map.get(column3.get(k));
//                            }
//
//
//                            //   String u = (String) map.get(column3.get(k));
//                            passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k), startAddressTit.get(k).toUpperCase(), column3.get(k), code};

                            for (int l = 0; l < converter.OPTAB.length; l++) {
                                String checkkk = startAddressTit.get(k).toUpperCase();
                                if (startAddressTit.get(k).toUpperCase().contains("+")) {
                                    checkkk = checkkk.substring(1);
                                }
                                if (checkkk.equals(converter.OPTAB[l][0])) {
                                    code = converter.OPTAB[l][2];
                                    break;
                                }
                            }

                            // if format 2
                            if (formats.get(k) == 2) {
                                if (column3.get(k).length() == 1) {
                                    code += regNumberReturn(column3.get(k));
                                    code += "0";
                                } else {
                                    code += regNumberReturn(column3.get(k).substring(0, 1));
                                    code += regNumberReturn(column3.get(k).substring(2));
                                }
                            } else if (formats.get(k) == 3 || formats.get(k) == 4) {
                                // E Flag
                                if (formats.get(k) == 4) {
                                    e = "1";

                                } else if (formats.get(k) == 3) {
                                    e = "0";
                                }
                                // Indexed Flag ( X )
                                if (column3.get(k).toUpperCase().endsWith(",X")) {
                                    x = "1";
//                                String valueofbeforeX = column3.get(k).substring(0, column3.get(k).length() - 2);
//                                ConverterBinaryHexa converterBinaryHexa = new ConverterBinaryHexa(map.get(valueofbeforeX.toUpperCase()));
//                                code += converterBinaryHexa.returnedHexa();
                                } else {
                                    x = "0";
                                    //   code += map.get(column3.get(k).toUpperCase());
                                }
                                // Immediate or Indirect ( I & N )
                                if (column3.get(k).toUpperCase().startsWith("#")) {
                                    iii = "1";
                                    n = "0";
                                    b = "0";
                                    p = "0";
                                } else if (column3.get(k).toUpperCase().startsWith("@") && formats.get(k) == 3) {
                                    iii = "0";
                                    n = "1";
                                    b = "0";
                                    p = "1";
                                } else if (!(column3.get(k).toUpperCase().startsWith("@")) && !(column3.get(k).toUpperCase().startsWith("#"))) {
                                    iii = "1";
                                    n = "1";
                                    b = "0";
                                    p = "1";
                                }
                                if (formats.get(k) == 4) {
                                    b = "0";
                                    p = "0";
                                }


                                if (p.equals("1")) {
                                    String TA;
                                    // CASE OF INDEXED
                                    if (column3.get(k).endsWith(",X")) {
                                        TA = map.get(column3.get(k).substring(0, column3.get(k).length() - 2));
                                    }
                                    // CASE OF IMMEDIATE
                                    else if (column3.get(k).startsWith("#")) {
                                        if (map.containsKey(column3.get(k).substring(1))) {
                                            TA = map.get(column3.get(k).substring(1));
                                        } else {
                                            TA = column3.get(k).substring(1);
                                        }

                                    }
                                    // CASE OF INDIRECT
                                    else if (column3.get(k).startsWith("@")) {
                                        TA = map.get(column3.get(k).substring(1));
                                        // CASE OF SIMPLE
                                    } else {
                                        TA = map.get(column3.get(k));
                                    }

                                    String dispHex;
                                    if (Integer.parseInt(TA, 16) < Integer.parseInt(startAddress.get(k + 1), 16)) {

                                        dispHex = Integer.toHexString((Integer.parseInt(printOneAndTwosComplement(TA), 16) + Integer.parseInt(startAddress.get(k + 1), 16)));
                                    } else {
                                        dispHex = Integer.toHexString((Integer.parseInt(TA, 16) - Integer.parseInt(startAddress.get(k + 1), 16)));
                                    }
                                    //  dispHex = Integer.toHexString((Integer.parseInt(TA, 16) - Integer.parseInt(startAddress.get(k + 1), 16)));
//                                    if(formats.get(k)==3) {
//                                        dispHex = RPad(Integer.toHexString(Integer.parseInt(TA, 16) - Integer.parseInt(startAddress.get(k + 1), 16)),3,'0');
//                                    }else{
//                                        dispHex = RPad(Integer.toHexString(Integer.parseInt(TA, 16) - Integer.parseInt(startAddress.get(k + 1), 16)),5,'0');
//
//                                    }


                                    int dispDecimal;
                                    try {
                                        dispDecimal = Integer.parseInt(dispHex, 16);
                                    } catch (Exception eq) {
                                        System.out.println(eq.toString() + " \n \n loop number : " + k + "\n TA = " + TA + "\n dispHex=" + dispHex);
                                        return;
                                    }

                                    if (dispDecimal > 2047) {
                                        p = "0";
                                        b = "1";

                                    }

                                }
                                String opCodeInBinary = opCodeToBinary(code);

                                opCodeInBinary = opCodeInBinary.concat(n).concat(iii).concat(x).concat(b).concat(p).concat(e);

                                if (formats.get(k) == 3) {
                                    String dispHex;
                                    String TA;
                                    if (p.equals("1") && b.equals("0")) {

                                        // CASE OF INDEXED
                                        if (column3.get(k).endsWith(",X")) {
                                            TA = map.get(column3.get(k).substring(0, column3.get(k).length() - 2));
                                        }
                                        //  CASE OF IMMEDIATE
                                        else if (column3.get(k).startsWith("#")) {
                                            if (map.containsKey(column3.get(k).substring(1))) {
                                                TA = map.get(column3.get(k).substring(1));
                                            } else {
                                                TA = RPad(column3.get(k).substring(1), 3, '0');
                                            }

                                        }
                                        // CASE OF INDIRECT
                                        else if (column3.get(k).startsWith("@")) {
                                            TA = map.get(column3.get(k).substring(1));
                                            // CASE OF SIMPLE
                                        } else {
                                            TA = map.get(column3.get(k));
                                        }
                                        // Displacement in CASE OF PC Relative
                                        if (Integer.parseInt(TA, 16) < Integer.parseInt(startAddress.get(k + 1), 16)) {

                                            dispHex = RPad(Integer.toHexString((Integer.parseInt(printOneAndTwosComplement(TA), 16) + Integer.parseInt(startAddress.get(k + 1), 16))), 3, '0');
                                        } else {
                                            dispHex = RPad(Integer.toHexString((Integer.parseInt(TA, 16) - Integer.parseInt(startAddress.get(k + 1), 16))), 3, '0');
                                        }
                                        //dispHex = RPad((Integer.toHexString(Integer.parseInt(TA, 16) - Integer.parseInt(startAddress.get(k + 1), 16))), 3, '0');


//                                        if(formats.get(k)==3) {
//                                            dispHex = RPad(Integer.toHexString(Integer.parseInt(TA, 16) - Integer.parseInt(startAddress.get(k + 1), 16)),3,'0');
//                                        }else{
//                                            dispHex = RPad(Integer.toHexString(Integer.parseInt(TA, 16) - Integer.parseInt(startAddress.get(k + 1), 16)),5,'0');
//                                        }


                                    }
                                    // START OF CASE OF BASE RELATIVE
                                    else if (b.equals("1") && p.equals("0")) {
                                        int poss = 0;
                                        for (int pp = 0; pp < k; pp++) {
                                            if (startAddressTit.get(pp).equals("BASE")) {
                                                poss = pp;
                                                break;
                                            }
                                        }

                                        String baseValue;
                                        if (column3.get(poss).startsWith("#")) {
                                            baseValue = column3.get(poss).substring(1);
                                        } else {
                                            baseValue = map.get(column3.get(poss));
                                        }

                                        String TA2;
                                        // CASE OF INDEXED
                                        if (column3.get(k).endsWith(",X")) {
                                            TA2 = map.get(column3.get(k).substring(0, column3.get(k).length() - 2));
                                        }
                                        // CASE OF IMMEDIATE
                                        else if (column3.get(k).startsWith("#")) {
                                            if (map.containsKey(column3.get(k).substring(1))) {
                                                TA2 = map.get(column3.get(k).substring(1));
                                            } else {
                                                TA2 = RPad(column3.get(k).substring(1), 3, '0');

                                            }
                                        }
                                        // CASE OF INDIRECT
                                        else if (column3.get(k).startsWith("@")) {
                                            TA2 = map.get(column3.get(k).substring(1));
                                            // CASE OF SIMPLE
                                        } else {
                                            TA2 = map.get(column3.get(k));
                                        }
                                        // CASE OF BASE RELATIVE
                                        if (Integer.parseInt(TA2, 16) < Integer.parseInt(baseValue, 16)) {

                                            dispHex = RPad(Integer.toHexString((Integer.parseInt(printOneAndTwosComplement(TA2), 16) + Integer.parseInt(baseValue, 16))), 3, '0');
                                        } else {
                                            dispHex = RPad((Integer.toHexString((Integer.parseInt(TA2, 16) - Integer.parseInt(baseValue, 16)))), 3, '0');
                                        }

                                        // dispHex = RPad((Integer.toHexString(Integer.parseInt(TA2, 16) - Integer.parseInt(baseValue, 16))), 3, '0');
//                                        if(formats.get(k)==3) {
//                                            dispHex = RPad(Integer.toHexString(Integer.parseInt(TA2, 16) - Integer.parseInt(baseValue, 16)),3,'0');
//                                        }else{
//                                            dispHex = RPad(Integer.toHexString(Integer.parseInt(TA2, 16) - Integer.parseInt(baseValue, 16)),5,'0');
//
//                                        }

                                    } else {

                                        String TA3 = "";

                                        //CASE OF IMMEDIATE
                                        if (column3.get(k).startsWith("#")) {
                                            if (map.containsKey(column3.get(k).substring(1))) {
                                                TA3 = map.get(column3.get(k).substring(1));
                                            } else {
                                                TA3 = column3.get(k).substring(1);
                                            }
                                        }

                                        //  dispHex = RPad(Integer.toHexString(Integer.parseInt(TA3, 16)), 3, '0');
                                        if (formats.get(k) == 3) {
                                            dispHex = RPad(Integer.toHexString(Integer.parseInt(TA3, 16)), 3, '0');
                                        } else {
                                            dispHex = RPad(Integer.toHexString(Integer.parseInt(TA3, 16)), 5, '0');

                                        }
                                    }
                                    objectCode = binaryToHex(opCodeInBinary);

                                    objectCode = objectCode.concat(dispHex);

                                } else if (formats.get(k) == 4) {
                                    String dispHex2;
                                    String TA4;
                                    if (column3.get(k).endsWith(",X")) {
                                        TA4 = map.get(column3.get(k).substring(0, column3.get(k).length() - 2));
                                    }
                                    //  CASE OF IMMEDIATE
                                    else if (column3.get(k).startsWith("#")) {
                                        if (map.containsKey(column3.get(k).substring(1))) {
                                            TA4 = map.get(column3.get(k).substring(1));
                                        } else {
                                            TA4 = column3.get(k).substring(1);
                                        }
                                    }
                                    // CASE OF INDIRECT
                                    else if (column3.get(k).startsWith("@")) {
                                        TA4 = map.get(column3.get(k).substring(1));
                                        // CASE OF SIMPLE
                                    } else {
                                        TA4 = map.get(column3.get(k));
                                    }

                                    // dispHex2 = RPad(Integer.toHexString(Integer.parseInt(TA4, 16)), 5, '0');
                                    if (formats.get(k) == 3) {
                                        dispHex2 = RPad(Integer.toHexString(Integer.parseInt(TA4, 16)), 3, '0');
                                    } else {
                                        dispHex2 = RPad(Integer.toHexString(Integer.parseInt(TA4, 16)), 5, '0');

                                    }
                                    objectCode = binaryToHex(opCodeInBinary);
                                    objectCode = objectCode.concat(dispHex2);
                                }


                            }

                            passTwoTable[i] = new String[]{startAddress.get(k), column1.get(k), startAddressTit.get(k).toUpperCase(), column3.get(k).toUpperCase(), objectCode};

                        }
                    }
                    // passTwoTable[i] = new String[]{startAddress.get(i + 1), column1.get(i + 1), startAddressTit.get(i + 1), column3.get(i + 1),}
                    //   System.out.println("k= " + k + "\nn=" + n + "\niii=" + iii + "\nx=" + x + "\nb=" + b + "\np=" + p + "\ne=" + e + "\n");

                }
                System.out.println("----------------------------------------------------------Pass 2 Start-----------------------------------------------------------");
                System.out.println("--------------------------------------------------------Object Code Table--------------------------------------------------------");

                System.out.print("\n\n");
                System.out.println("---------------------------------------------------------------------------------------------------------------------------------");

                System.out.println("Location" + "\t\t|\t\t\t\t\t\t\t\t\t" + "Source Statement" + "\t\t\t\t\t\t\t\t|\t\t  Object Code");
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
                System.out.println("--------------------------------------------------------HTE Record--------------------------------------------------------");
                StringBuilder hRecord = new StringBuilder();
                StringBuilder tRecord = new StringBuilder();
                StringBuilder mRecord = new StringBuilder();
                StringBuilder eRecord = new StringBuilder();
                List<StringBuilder> t = new ArrayList<>();
                // ArrayList<String> ttRecord = new ArrayList<>();

                LinkedHashMap<String, String> ttRecord = new LinkedHashMap<>();
                hRecord.append("H˄");
                hRecord.append(LPad(readingInput.progName(), 6, 'x'));
                hRecord.append("˄");
                hRecord.append(RPad(startAddress.get(0), 6, '0'));
                hRecord.append("˄");
                int startAddressForH = Integer.parseInt(startAddress.get(0), 16);
                int endAddressForH = Integer.parseInt(startAddress.get(startAddress.size() - 1), 16);
                int lengthForH = endAddressForH - startAddressForH;
                String leng = Integer.toHexString(lengthForH);
                hRecord.append(RPad(leng, 6, '0'));
                hRecord.append("\n");
                System.out.print(hRecord.toString());
                tRecord.append("T˄");

                tRecord.append(RPad(passTwoTable[0][0], 6, '0'));
                tRecord.append("˄");
                ////////////////////// Cutted code here ////////////////////
                LinkedHashMap<String, String> linkedTRecord = new LinkedHashMap<>();
                List<String> keys = new ArrayList<>();
                List<String> values = new ArrayList<>();
                for (int i = 0; i < passTwoTable.length - 1; i++) {
                    linkedTRecord.put(passTwoTable[i][0], passTwoTable[i][4]);
                    keys.add(passTwoTable[i][0]);
                    values.add(passTwoTable[i][4]);
                }
                //   System.out.println(linkedTRecord.entrySet());

                Builder b = new Builder(values, keys);
                b.build();

                // System.out.println(Arrays.toString(tr.toArray()));

                // Modification Record
//                String allTT = String.join("", b.allTRecords());
//                allTT = allTT.replaceAll("[˄^]", "");
//                System.out.println(allTT);
//                allTT= allTT.substring(4);
//                System.out.println(allTT);
//                StringBuilder mRecord = new StringBuilder();
//                for(int q = 0 ; q < allTT.length(); q++){
//
//                }
//
//                for (int q = 0; q < formats.size(); q++) {
//                    if (formats.get(q) == 4) {
//                        String
//                        String sa = allTT.substring(0, allTT.indexOf(passTwoTable[q][4]));
//                        int len = sa.length() / 2;
//                        String lenHex = Integer.toHexString(len);
//                        mRecord.append("M˄");
//                        mRecord.append(RPad(lenHex, 6, '0'));
//                        mRecord.append("˄05");
//                        mRecord.append("\n");
//                    }
//                }

                //      System.out.print(mRecord.toString());


                for (int i = 0; i < passTwoTable.length; i++) {
                    if (passTwoTable[i][2].contains("+")) {
                        int de = Integer.parseInt(passTwoTable[i][0], 16);
                        de += 1;
                        String fin = Integer.toHexString(de);
                        mRecord.append("M˄");
                        mRecord.append(RPad(fin, 6, '0'));
                        mRecord.append("˄05\n");
                    }
                }

                System.out.print(mRecord.toString());

                eRecord.append("E˄");
                eRecord.append(RPad(startAddress.get(0), 6, '0'));
                System.out.print(eRecord.toString());
                System.out.print("\n\n");


                //////////////////// End of SIC/XE Machine ////////////////////
            } else if (mach == 3) {
                // Linkage Loader
                Scanner opop = new Scanner(System.in);
                System.out.print("Enter the number of the Programs : ");
                int numOfPrograms = opop.nextInt();


                //  String ll2[] = new String[numOfPrograms];
                List<String> ll = new ArrayList<>();
                for (int i = 0; i < numOfPrograms; i++) {
                    int h = i + 1;
                    System.out.print("Enter the path of the program " + h + " text : ");
                    String x = opop.next();
                    ll.add(x);
                }
                System.out.print("Enter the Starting Address of the Load : ");
                String startAddressForLoad = opop.next();
                String startAddressAgain = startAddressForLoad;

                LinkedHashMap<String, LinkedHashMap<String, String>> tRecordinMemory = new LinkedHashMap<>();
                int co = 0;
                for (int i = 0; i < 20; i++) {
                    LinkedHashMap<String, String> columns = new LinkedHashMap<>();
                    for (int j = 0; j < 16; j++) {
                        columns.put(Integer.toHexString(j), "");
                    }
                    int pp = Integer.parseInt(startAddressAgain, 16);
                    int pp2 = Integer.parseInt(String.valueOf(co), 16);
                    int pp3 = pp + pp2;
                    String addressToLoad = Integer.toHexString(pp3);
                    co += 10;
                    tRecordinMemory.put(addressToLoad, columns);
                }
                //  String [][] tRecordss = new String[1000000][15];
                List<String> allTRecordss = new ArrayList<>();
                LoadingTable table = new LoadingTable();
                table.setShowVerticalLines(true);
                //     table.setHeaders("0", "1", "2","3","4","5","6","7","8","9","A","B","C","D","E","F");
                table.setHeaders("Control Section", "Symbol name", "Address", "Length");
                HashMap<String, String> modificationHashMap = new HashMap<>();

                for (int i = 0; i < ll.size(); i++) {
                    HashMap<String, String> dRecordItems = new HashMap<>();
                    HashMap<Integer, List<String>> tRecoredItems = new HashMap<>();
                    HashMap<Integer, String> mRecoredItems = new HashMap<>();
                    HashMap<Integer, String> tRecordLengths = new HashMap<>();
                    HashMap<Integer, String> mRecordLengths = new HashMap<>();
                    HashMap<Integer, String> tRecordStartingAddresses = new HashMap<>();
                    HashMap<Integer, String> mRecordFromStarts = new HashMap<>();
                    String proName = null, startingAddForProg = null, ProgLength = null;
                    List<String> listToAdd = new ArrayList<>();
                    int count = 1, count2 = 1;
                    List<String> rRecordItems = new ArrayList<>();
                    ReadingInput readingInput = new ReadingInput(ll.get(i));
                    Converter converter = new Converter();
                    String inp = readingInput.input;
                    Scanner scanner = new Scanner(inp);
                    for (int k = 0; scanner.hasNextLine(); k++) {
                        String line = scanner.nextLine();

                        if (line.charAt(0) == 'H') {
                            proName = line.substring(2, 8);
                            proName = proName.replace("X", "");
                            startingAddForProg = line.substring(9, 15);
                            ProgLength = line.substring(16);
                        }
                        if (line.charAt(0) == 'D') {
                            String dRecord = line.substring(2);
                            String[] splittedDRecord = dRecord.split("\\.");

                            for (int y = 0; y < splittedDRecord.length - 1; y++) {
                                if (y % 2 == 0)
                                    dRecordItems.put(splittedDRecord[y], splittedDRecord[y + 1]);

                            }
                        }
                        if (line.charAt(0) == 'R') {
                            String rRecord = line.substring(2);
                            String[] splittedRRecord = rRecord.split("\\.");

                            rRecordItems = Arrays.asList(splittedRRecord);
                        }

                        if (line.charAt(0) == 'T') {
                            String tRecordFromHTE = line.substring(2);
                            String tRecordFromHTEStartAddress = tRecordFromHTE.substring(0, 6);
                            String tRecordFromtHTELength = tRecordFromHTE.substring(7, 9);
                            String tRecordFromHTEAfterFilter = tRecordFromHTE.substring(10);
                            String[] tRecordToPut = splitToNChar(tRecordFromHTEAfterFilter, 2);
                            tRecordLengths.put(count, tRecordFromtHTELength);
                            tRecordStartingAddresses.put(count, tRecordFromHTEStartAddress);
                            tRecoredItems.put(count++, Arrays.asList(tRecordToPut));

                        }
                        if (line.charAt(0) == 'M') {
                            String mRecordFromHTE = line.substring(2);
                            String mRecordFromHTEFarFromStart = mRecordFromHTE.substring(0, 6);
                            String mRecrodFromtHTELength = mRecordFromHTE.substring(7, 9);
                            String mRecordFromHTELengthRemaining = mRecordFromHTE.substring(9);
                            mRecordLengths.put(count2, mRecrodFromtHTELength);
                            mRecordFromStarts.put(count2, mRecordFromHTEFarFromStart);
                            mRecoredItems.put(count2++, mRecordFromHTELengthRemaining);
                        }
                    }

                    System.out.println("Program Name For 1st Program : " + proName + " and Starting address is : " + startingAddForProg + "  and the prog length is : " + ProgLength);
                    System.out.println("################ d Record Items #############");
                    dRecordItems.entrySet()
                            .forEach(System.out::println);
                    System.out.println("################ r Record Items #############");
                    rRecordItems.forEach(System.out::println);
                    System.out.println("################ T Record  #############");
                    System.out.println("################ T Records Lengths #############");
                    tRecordLengths.entrySet()
                            .forEach(System.out::println);
                    System.out.println("################ T Records Starting Address #############");
                    tRecordStartingAddresses.entrySet()
                            .forEach(System.out::println);
                    System.out.println("################ T Record Items #############");
                    tRecoredItems.entrySet()
                            .forEach(System.out::println);
                    System.out.println("################ M Record  #############");
                    System.out.println("################ M Records Lengths #############");
                    mRecordLengths.entrySet()
                            .forEach(System.out::println);
                    System.out.println("################ M Records Far From Start #############");
                    mRecordFromStarts.entrySet()
                            .forEach(System.out::println);
                    System.out.println("################ M Record Items #############");
                    mRecoredItems.entrySet()
                            .forEach(System.out::println);

                    table.addRow(proName, "", startAddressForLoad, ProgLength);
                    for (Map.Entry<String, String> entry : dRecordItems.entrySet()) {
                        System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
                        int ad = Integer.parseInt(entry.getValue(), 16);
                        int ad2 = Integer.parseInt(startAddressForLoad, 16);
                        int sum = ad + ad2;
                        String addd = Integer.toHexString(sum);
                        table.addRow("", entry.getKey(), startAddressForLoad + " + " + entry.getValue() + " = " + addd, "");
                        modificationHashMap.put(entry.getKey(), addd);
                    }
                    for (Map.Entry<Integer, List<String>> entry : tRecoredItems.entrySet()) {
                        listToAdd.addAll(entry.getValue());
                    }
                    allTRecordss.addAll(listToAdd);
                    System.out.println("######## Modification of " + proName);
                    LoadingTable table1 = new LoadingTable();
                    table1.setShowVerticalLines(true);
                    table1.setHeaders("Address", "Operation", "Value");
                    for (Map.Entry<Integer, String> entry : mRecoredItems.entrySet()) {
//                        System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
                        int mFromStart = Integer.parseInt(mRecordFromStarts.get(entry.getKey()), 16);
                        int ad2 = Integer.parseInt(startAddressForLoad, 16);
                        int sum = mFromStart + ad2;
                        String addd = Integer.toHexString(sum);
                        table1.addRow(startAddressForLoad + " + " + mRecordFromStarts.get(entry.getKey()) + " = " + addd, "", "");
                    }
                    table1.print();
                    startAddressForLoad = Integer.toHexString((Integer.parseInt(startAddressForLoad, 16)) + (Integer.parseInt(ProgLength, 16)));


                }
                table.print();
                System.out.println(Arrays.toString(allTRecordss.toArray()));
//                System.out.println(allTRecordss.size());
                int m = 0;
                for (Map.Entry<String, LinkedHashMap<String, String>> entry : tRecordinMemory.entrySet()) {
                    for (Map.Entry<String, String> entry2 : entry.getValue().entrySet()) {
                        if (m < allTRecordss.size()) {
                            entry2.setValue(allTRecordss.get(m++));
                        } else {
                            break;
                        }
                    }
                    entry.setValue(entry.getValue());

                }
                tRecordinMemory.entrySet()
                        .forEach(System.out::println);
                //System.out.print(readingInput.input);


                int startingHex = 0;
                int addedvalue = 0;
                ArrayList<Integer> formats = new ArrayList<>();

            } else if (mach == 4) {
                // Linkage Loader
                Scanner opop = new Scanner(System.in);
                System.out.print("Enter the number of the Programs : ");
                int numOfPrograms = opop.nextInt();


                //  String ll2[] = new String[numOfPrograms];
                List<String> ll = new ArrayList<>();
                for (int i = 0; i < numOfPrograms; i++) {
                    int h = i + 1;
                    System.out.print("Enter the path of the program " + h + " text : ");
                    String x = opop.next();
                    ll.add(x);
                }
                LinkedHashMap<String, LinkedHashMap<String, String>> tRecordinMemory = new LinkedHashMap<>();
                int co = 0;
                for (int i = 0; i < 20; i++) {
                    LinkedHashMap<String, String> columns = new LinkedHashMap<>();
                    for (int j = 0; j < 16; j++) {
                        columns.put(Integer.toHexString(j), "");
                    }
                    int pp = Integer.parseInt("000000", 16);
                    int pp2 = Integer.parseInt(String.valueOf(co), 16);
                    int pp3 = pp + pp2;
                    String addressToLoad = Integer.toHexString(pp3);
                    co += 10;
                    tRecordinMemory.put(addressToLoad, columns);
                }
                //  String [][] tRecordss = new String[1000000][15];
                List<String> allTRecordss = new ArrayList<>();
                List<Integer> tryAbsoluteSize = new ArrayList<>();

                for (int i = 0; i < ll.size(); i++) {
                    HashMap<String, String> dRecordItems = new HashMap<>();
                    HashMap<Integer, List<String>> tRecoredItems = new HashMap<>();
                    List<String> tryAbsolute = new ArrayList<>();
                    HashMap<Integer, String> mRecoredItems = new HashMap<>();
                    HashMap<Integer, String> tRecordLengths = new HashMap<>();
                    HashMap<Integer, String> mRecordLengths = new HashMap<>();
                    HashMap<Integer, String> tRecordStartingAddresses = new HashMap<>();
                    HashMap<Integer, String> mRecordFromStarts = new HashMap<>();
                    String proName = null, startingAddForProg = null, ProgLength = null;
                    List<String> listToAdd = new ArrayList<>();
                    int count = 1, count2 = 1;
                    List<String> rRecordItems = new ArrayList<>();
                    ReadingInput readingInput = new ReadingInput(ll.get(i));
                    Converter converter = new Converter();
                    String inp = readingInput.input;
                    Scanner scanner = new Scanner(inp);
                    for (int k = 0; scanner.hasNextLine(); k++) {
                        String line = scanner.nextLine();

                        if (line.charAt(0) == 'H') {
                            proName = line.substring(2, 8);
                            proName = proName.replace("X", "");
                            startingAddForProg = line.substring(9, 15);
                            ProgLength = line.substring(16);

                        }


                        if (line.charAt(0) == 'T') {
                            String tRecordFromHTE = line.substring(2);
                            String tRecordFromHTEStartAddress = tRecordFromHTE.substring(0, 6);
                            String tRecordFromtHTELength = tRecordFromHTE.substring(7, 9);
                            String tRecordFromHTEAfterFilter = tRecordFromHTE.substring(10);
                            String[] tRecordToPut = splitToNChar(tRecordFromHTEAfterFilter, 2);
                            tRecordLengths.put(count, tRecordFromtHTELength);
                            tRecordStartingAddresses.put(count, tRecordFromHTEStartAddress);
                            tryAbsolute.addAll(Arrays.asList(tRecordToPut));

                            //  tRecoredItems.put(count++, Arrays.asList(tRecordToPut));

                        }

                    }
                    tryAbsoluteSize.add(tryAbsolute.size());


                    System.out.println("Program Name For 1st Program : " + proName + " and Starting address is : " + startingAddForProg + "  and the prog length is : " + ProgLength);

                    System.out.println("################ T Record  #############");
                    System.out.println("################ T Records Lengths #############");
                    tRecordLengths.entrySet()
                            .forEach(System.out::println);
                    System.out.println("################ T Records Starting Address #############");
                    tRecordStartingAddresses.entrySet()
                            .forEach(System.out::println);
                    System.out.println("################ T Record Items #############");
                    tRecoredItems.entrySet()
                            .forEach(System.out::println);


                    allTRecordss.addAll(tryAbsolute);


                }
                ArrayList<String> nList = new ArrayList<>();
                int indexOfAllRecords=0;
                for (int mq = 0; mq < tryAbsoluteSize.size(); mq++) {
             //       System.out.print(" - "+mq+" - ");

                    for (int kk = 0; kk < tryAbsoluteSize.get(mq); kk++) {
                   //     System.out.print(kk+" ");
                        if (indexExists(nList, kk)) {
                            nList.set(kk, allTRecordss.get(indexOfAllRecords++));

                        } else {
                            nList.add(allTRecordss.get(indexOfAllRecords++));
                        }
                    }

                }

//                System.out.println(indexOfAllRecords);
//                System.out.println(Arrays.toString(allTRecordss.toArray()));
//                System.out.println(allTRecordss.size());
//                System.out.println(Arrays.toString(tryAbsoluteSize.toArray()));
//                System.out.println();
                System.out.println();
//                System.out.println(Arrays.toString(nList.toArray()));

//                System.out.println(allTRecordss.size());
                int m = 0;
                for (Map.Entry<String, LinkedHashMap<String, String>> entry : tRecordinMemory.entrySet()) {
                    for (Map.Entry<String, String> entry2 : entry.getValue().entrySet()) {
                        if (m < nList.size()) {
                            entry2.setValue(nList.get(m++));
                        } else {
                            break;
                        }
                    }
                    entry.setValue(entry.getValue());

                }
                tRecordinMemory.entrySet()
                        .forEach(System.out::println);
                //System.out.print(readingInput.input);


            } else if (mach == 5) {
                // Linking Editor
            } else if (mach == 6) {
                break;
            }
        }

    }

    /**
     * Split text into n number of characters.
     *
     * @param text the text to be split.
     * @param size the split size.
     * @return an array of the split text.
     */
    private static String[] splitToNChar(String text, int size) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts.toArray(new String[0]);
    }

    public static String RPad(String str, Integer length, char car) {
        return (String.format("%" + length + "s", "").replace(" ", String.valueOf(car)) + str).substring(str.length(), length + str.length());
    }

    public static String LPad(String str, Integer length, char car) {
        return (str + String.format("%" + length + "s", "").replace(" ", String.valueOf(car))).substring(0, length);
    }

    public static boolean isFormatTwo(String inputStr) {
        ArrayList<String> items = Converter.regname;
        boolean check = false;
        for (String item : items) {
            check = (item.equals(inputStr) && inputStr.length() == 1) || (item.contains(inputStr) && inputStr.contains(",") && inputStr.length() <= 3);
        }
        return check;
    }

    public static String regNumberReturn(String regName) {
        int pos = 0;
        for (int i = 0; i < Converter.regname.size(); i++) {
            if (Converter.regname.get(i).equals(regName)) {
                pos = i;
            }
        }
        return Converter.regvalue.get(pos);
    }

    public static String opCodeToBinary(String hex) {
        String bi = "";
        char[] ch = new char[2];

        ch[0] = hex.charAt(0);
        ch[1] = hex.charAt(1);

        int k = Integer.parseInt(String.valueOf(ch[0]), 16);
        int k2 = Integer.parseInt(String.valueOf(ch[1]), 16);
        bi += RPad(Integer.toBinaryString(k), 4, '0');


        String q = RPad(Integer.toBinaryString(k2), 4, '0');
        bi += q.substring(0, q.length() - 2);
        return bi;

    }

    public static String fromHexToBinary(String hex) {
        int i = Integer.parseInt(hex, 16);
        String bin = Integer.toBinaryString(i);
        return bin;
    }

    public static String binaryAfterTwosComplement(String hex) {
        String binary = fromHexToBinary(hex);
        char[] b = new char[binary.length()];
        for (int i = 0; i < binary.length(); i++) {
            b[i] = binary.charAt(i);
        }
        for (int i = 0; i < b.length; i++) {
            switch (b[i]) {
                case '1':
                    b[i] = '0';
                    break;
                case '0':
                    b[i] = '1';
            }
        }
        String binAfterFirstComplement = new String(b);
        int b1 = Integer.parseInt(binAfterFirstComplement, 2);
        int b2 = Integer.parseInt("1", 2);
        int sum = b1 + b2;
        return Integer.toBinaryString(sum);
    }

    public static String binaryToHex(String bin) {
        int decimal = Integer.parseInt(bin, 2);
        String hexStr = Integer.toString(decimal, 16);
        return hexStr.toUpperCase();
    }


    // Returns '0' for '1' and '1' for '0'
    static char flip(char c) {
        return (c == '0') ? '1' : '0';
    }

    // Print 1's and 2's complement of binary number
    // represented by "bin"
    static String printOneAndTwosComplement(String bin) {
        int n = bin.length();
        int i;

        String ones = "", twos = "";
        ones = twos = "";

        // for ones complement flip every bit
        for (i = 0; i < n; i++) {
            ones += flip(bin.charAt(i));
        }

        // for two's complement go from right to left in
        // ones complement and if we get 1 make, we make
        // them 0 and keep going left when we get first
        // 0, make that 1 and go out of loop
        twos = ones;
        for (i = n - 1; i >= 0; i--) {
            if (ones.charAt(i) == '1') {
                twos = twos.substring(0, i) + '0' + twos.substring(i + 1);
            } else {
                twos = twos.substring(0, i) + '1' + twos.substring(i + 1);
                break;
            }
        }

        // If No break : all are 1 as in 111 or 11111;
        // in such case, add extra 1 at beginning
        if (i == -1) {
            twos = '1' + twos;
        }

        return twos;
    }


    public static boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }
}