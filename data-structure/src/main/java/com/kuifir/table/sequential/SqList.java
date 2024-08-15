package com.kuifir.table.sequential;

public class SqList<T> {
    private static int MAXSIZE = 10;
    private static Object[] DEFAULT_ELEMENTS = {};
    int length;
    private T[] elements;

    public SqList<T> initList(){
        SqList<T> sqList = new SqList<>();
        sqList.length = 0;
        elements = (T[]) new Object[MAXSIZE];
        return sqList;
    }


    // 时间复杂度O(1)
    public Boolean getElem(SqList<T> L, int i, T t){
        if(i<1 || i>length) {
            return Boolean.FALSE;
        }
        t = (T) elements[i-1];
        return Boolean.TRUE;
    }

    //时间复杂度O(n)
    public int LocateElem(SqList<T> sqList,T t){
        for (int i = 0; i < length; i++) {
            if(elements[i].equals(t)){
                return i+1;
            }
        }
        return 0;
    }

    // 时间复杂度 O(n)
    public Boolean listInsert(SqList<T> sqList, int i, T t){
        if(i < 1 || i>length){
            return Boolean.FALSE;
        }
        if(length == MAXSIZE){
            return Boolean.FALSE;
        }
        for (int j = length-1; j > i-1; j--) {
            elements[j+1] = elements[j];
        }
        elements[i-1] = t;
        ++ length;
        return Boolean.TRUE;
    }

    // 时间复杂度 O(n)
    public Boolean listDelete(SqList<T> sqList, int i) {
       if(i < 1 || i>length){
            return Boolean.FALSE;
        }
        for (int j = i; j < length-1; j++) {
            elements[j-1] = elements[j];
        }
        -- length;
        return Boolean.TRUE;
    }

}
