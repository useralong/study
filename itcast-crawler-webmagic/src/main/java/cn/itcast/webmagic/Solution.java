package cn.itcast.webmagic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 面试题
 */
public class Solution {
    public static int minNumberInRotateArray(int [] array){
        //int [] array = new int[]{3,4,5,1,2};
        //int [] array = {3,4,5,1,2};
    /*    int minNum;
        if(array.length == 0){
            return 0;
        }
        for(int i = 0 ; i < array.length ; i++ ){
            for(int j = array.length-1 ; j > i ; j--){
                if(array[j] < array[j - 1]){
                    minNum = array[j];
                    System.out.println(minNum);
                    return minNum;
                }
            }
        }*/
        int minNum;
        for(int i = 0 ; i < array.length ; i++ ){
            for(int j = array.length-1 ; j > i ; j--){
                if(array[j] < array[j - 1]){
                    minNum = array[j];
                    System.out.println(minNum);
                    return minNum;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int [] array = {3,4,5,1,2};
        minNumberInRotateArray(array);
    }
}
