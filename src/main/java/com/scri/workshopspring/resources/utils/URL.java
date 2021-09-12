package com.scri.workshopspring.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    public static List<Integer> decodeIntList(String s){

        return Arrays.stream(s.split(",")).map(x -> Integer.parseInt(x)).collect(Collectors.toList());

        //for(int i = 0; i < vet.length; i++){e
        //            list.add(Integer.parseInt(vet[i]));
        //        }
        //        return list;
    }

    public static String decodeParam(String s){
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }
}
