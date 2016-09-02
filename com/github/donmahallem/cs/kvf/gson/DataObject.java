package com.github.donmahallem.cs.kvf.gson;

import java.util.HashMap;

/**
 * Created by windo on 22.08.2016.
 */
public class DataObject {

    HashMap<String,Object> mData=new HashMap<>();

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("DataObject{");
        boolean first=true;
        for(String key:this.mData.keySet()){
            if(!first){
                stringBuilder.append(',');
            }else
                first=false;
            stringBuilder.append(key);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public void put(String name, Object value){
        if(name==null)
            throw new NullPointerException("name must not be null");
        if(value==null)
            this.mData.remove(name);
        this.mData.put(name,value);
    }

    public Object get(String name){
        if(name==null)
            throw new NullPointerException("name must not be null");
        return this.mData.get(name);
    }

    public float getAsFloat(String name){
        final Object obj=this.get(name);
        if(obj instanceof Float)
            return (float)obj;
        else if(obj instanceof String) {
            final float val = Float.parseFloat((String) obj);
            this.mData.put(name,val);
            return val;
        }else
            throw new ClassCastException(name+" is of type: "+obj.getClass().getName());
    }

    public String getAsString(String name){
        return (String)this.get(name);
    }
}
