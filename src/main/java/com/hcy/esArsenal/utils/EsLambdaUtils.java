package com.hcy.esArsenal.utils;


import java.io.*;
import java.util.Locale;

/**
 * @Author huchenying
 * @Description 用于lambda接参的工具类 copy mybatis-plus
 * @Date 2021/8/17
 **/
public class EsLambdaUtils {
    public static String getColumn(SerializedLambda lambda, boolean onlyColumn) {
        if (lambda == null){
            return null;
        }
        return methodToProperty(lambda.getImplMethodName());
    }

    public static String methodToProperty(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else if (name.startsWith("get") || name.startsWith("set")) {
            name = name.substring(3);
        } else {
//            throw new ReflectionException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
        }
        if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
            name = name.substring(0, 1).toLowerCase(new Locale("en")) + name.substring(1);
        }
        return name;
    }

    public static  <T> SerializedLambda resolve(Func<T, ?> func){
        Class<?> clazz = func.getClass();
        String name = clazz.getName();
        SerializedLambda lambda = null;
        try {
            lambda = resolveLambda(func);
            return lambda;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static SerializedLambda resolveLambda(Func<?, ?> lambda) throws Exception {
        if (!lambda.getClass().isSynthetic()) {
            throw new Exception("该方法仅能传入 lambda 表达式产生的合成类");
        }
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(serialize(lambda))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz;
                try {
                    clazz = toClassConfident(objectStreamClass.getName());
                } catch (Exception ex) {
                    clazz = super.resolveClass(objectStreamClass);
                }
                                                                            //这边class类型要改成自己的
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        }) {
            return (SerializedLambda) objIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new Exception("This is impossible to happen", e);
        }
    }

    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            oos.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
        }
        return baos.toByteArray();
    }

    public  static Class<?> toClassConfident(String name) throws Exception {
        try {
            return Class.forName(name, false, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException ex) {
                throw new Exception("找不到指定的class！请仅在明确确定会有 class 的时候，调用该方法", e);
            }
        }
    }
}
