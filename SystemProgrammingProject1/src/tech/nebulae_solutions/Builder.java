package tech.nebulae_solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Builder {

    List<String> list1;
    List<String> list2;

    public Builder(List<String> list1, List<String> list2) {
        this.list1 = list1;
        this.list2 = list2;
    }

    ArrayList<ArrayList<MyObject>> tempMatrix = new ArrayList<ArrayList<MyObject>>();
    ArrayList<String> lineOutput = new ArrayList<String>();
    ArrayList<String> calculatedOutput = new ArrayList<String>();
    int row = 0;

    ArrayList<String> allT = new ArrayList<>();

    void build() {
        tempMatrix.add(new ArrayList<MyObject>());
        for (int i = 0; i < list1.size(); i++) {
            String item = list1.get(i);
            if (!item.equals(new String("------"))) {
                tempMatrix.get(row).add(new MyObject(item, i));
            } else {
                if (tempMatrix.get(row).size() > 0) {
                    concatRow();
                    incrementRow();
                }
            }

            if (tempMatrix.get(row).size() == 11) {
                concatRow();
                incrementRow();
            }
        }
        concatRow();

        // System.out.println(Arrays.toString(tempMatrix.toArray()));
        // System.out.println(lineOutput);
        allT = lineOutput;
        for (int i = 0; i < lineOutput.size(); i++) {
            System.out.println(lineOutput.get(i));
        }
        //System.out.println(calculatedOutput);
    }

    ArrayList<String> allTRecords() {
        return allT;
    }

    void concatRow() {
        if (tempMatrix.get(row).size() > 0) {
            MyObject firstItemObject = tempMatrix.get(row).get(0);
            MyObject lastItemObject = tempMatrix.get(row).get(tempMatrix.get(row).size() - 1);
            int firstItemNumber = Integer.parseInt(list2.get(firstItemObject.index), 16);
            int lastItemNumber = Integer.parseInt(list2.get(lastItemObject.index), 16);
            int recordedDifference = lastItemNumber - firstItemNumber;
            calculatedOutput.add(Integer.toHexString(recordedDifference));
            StringBuilder s = new StringBuilder();
            s.append("T^");
            s.append(RPad(Integer.toHexString(firstItemNumber),6,'0'));
            s.append("^");
            s.append(Integer.toHexString(recordedDifference));
            s.append("^");
            s.append(join(map(tempMatrix.get(row))));
            s.deleteCharAt(s.length() - 1);
            lineOutput.add(s.toString());
            //  lineOutput.add("T^"+RPad(Integer.toHexString(firstItemNumber),6,'0')+ "^"+RPad(Integer.toHexString(recordedDifference),2,'0')+"^"+ join(map(tempMatrix.get(row))));
        }
    }

    public static String RPad(String str, Integer length, char car) {
        return (String.format("%" + length + "s", "").replace(" ", String.valueOf(car)) + str).substring(str.length(), length + str.length());
    }
    void incrementRow() {
        row++;
        tempMatrix.add(new ArrayList<MyObject>());
    }

    ArrayList<String> map(ArrayList<MyObject> l) {
        ArrayList<String> x = new ArrayList<String>();

        l.forEach((m) -> x.add(m.value));
        return x;
    }

    String join(ArrayList<String> l) {
        String s = "";
        for (String li : l) {
            s = s.concat(li);
            s = s.concat("^");
        }
        return s;
    }

}

class MyObject {
    String value;
    int index;

    MyObject(String v, int i) {
        value = v;
        index = i;
    }
}


