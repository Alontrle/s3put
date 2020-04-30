package com.tobiassteely.s3put.api;

import java.awt.*;
import java.util.List;

public class Data {

    public String parseStringList(int start, List<String> stringArrayList) {
        StringBuilder parsedString = new StringBuilder();
        for(int i = start; i < stringArrayList.size(); i++) {
            if(i == stringArrayList.size() - 1)
                parsedString.append(stringArrayList.get(i));
            else
                parsedString.append(stringArrayList.get(i)).append(", ");
        }
        return parsedString.toString();
    }

    public String parseStringArray(int start, String[] stringArrayList) {
        StringBuilder parsedString = new StringBuilder();
        for(int i = start; i < stringArrayList.length; i++) {
            if(i == stringArrayList.length - 1)
                parsedString.append(stringArrayList[i]);
            else
                parsedString.append(stringArrayList[i]).append(", ");
        }
        return parsedString.toString();
    }

    public String parseStringListNoDelimiter(int start, List<String> stringArrayList) {
        StringBuilder parsedString = new StringBuilder();
        for(int i = start; i < stringArrayList.size(); i++) {
            if(i == stringArrayList.size() - 1)
                parsedString.append(stringArrayList.get(i));
            else
                parsedString.append(stringArrayList.get(i)).append(" ");
        }
        return parsedString.toString();
    }

    public String parseStringArrayNoDelimiter(int start, String[] stringArrayList) {
        StringBuilder parsedString = new StringBuilder();
        for(int i = start; i < stringArrayList.length; i++) {
            if(i == stringArrayList.length - 1)
                parsedString.append(stringArrayList[i]);
            else
                parsedString.append(stringArrayList[i]).append(" ");
        }
        return parsedString.toString();
    }

}
