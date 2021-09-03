package com.hcy.esArsenal.utils;

import java.io.Serializable;
import java.lang.invoke.MethodHandleInfo;
import java.util.Objects;

/**
 * @Author huchenying
 * @Description 这个类是从 {@link java.lang.invoke.SerializedLambda} 里面 copy 过来的，
 *  字段信息完全一样
 *  <p>负责将一个支持序列的 Function 序列化为 SerializedLambda</p>
 * @Date 2021/8/17
 **/
public class SerializedLambda implements Serializable {
    private static final long serialVersionUID = 8025925345765570181L;
    private final Class<?> capturingClass;
    private final String functionalInterfaceClass;
    private final String functionalInterfaceMethodName;
    private final String functionalInterfaceMethodSignature;
    private final String implClass;
    private final String implMethodName;
    private final String implMethodSignature;
    private final int implMethodKind;
    private final String instantiatedMethodType;
    private final Object[] capturedArgs;


    public SerializedLambda(Class<?> capturingClass,
                            String functionalInterfaceClass,
                            String functionalInterfaceMethodName,
                            String functionalInterfaceMethodSignature,
                            int implMethodKind,
                            String implClass,
                            String implMethodName,
                            String implMethodSignature,
                            String instantiatedMethodType,
                            Object[] capturedArgs) {
        this.capturingClass = capturingClass;
        this.functionalInterfaceClass = functionalInterfaceClass;
        this.functionalInterfaceMethodName = functionalInterfaceMethodName;
        this.functionalInterfaceMethodSignature = functionalInterfaceMethodSignature;
        this.implMethodKind = implMethodKind;
        this.implClass = implClass;
        this.implMethodName = implMethodName;
        this.implMethodSignature = implMethodSignature;
        this.instantiatedMethodType = instantiatedMethodType;
        this.capturedArgs = Objects.requireNonNull(capturedArgs).clone();
    }


    public String getCapturingClass() {
        return capturingClass.getName().replace('.', '/');
    }


    public String getFunctionalInterfaceClass() {
        return functionalInterfaceClass;
    }


    public String getFunctionalInterfaceMethodName() {
        return functionalInterfaceMethodName;
    }


    public String getFunctionalInterfaceMethodSignature() {
        return functionalInterfaceMethodSignature;
    }


    public String getImplClass() {
        return implClass;
    }


    public String getImplMethodName() {
        return implMethodName;
    }


    public String getImplMethodSignature() {
        return implMethodSignature;
    }


    public int getImplMethodKind() {
        return implMethodKind;
    }


    public final String getInstantiatedMethodType() {
        return instantiatedMethodType;
    }


//    public int getCapturedArgCount() {
//        return capturedArgs.length;
//    }
//
//
//    public Object getCapturedArg(int i) {
//        return capturedArgs[i];
//    }

//    private Object readResolve() throws ReflectiveOperationException {
//        try {
//            Method deserialize = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>() {
//                @Override
//                public Method run() throws Exception {
//                    Method m = capturingClass.getDeclaredMethod("$deserializeLambda$", java.lang.invoke.SerializedLambda.class);
//                    m.setAccessible(true);
//                    return m;
//                }
//            });
//
//            return deserialize.invoke(null, this);
//        }
//        catch (PrivilegedActionException e) {
//            Exception cause = e.getException();
//            if (cause instanceof ReflectiveOperationException)
//                throw (ReflectiveOperationException) cause;
//            else if (cause instanceof RuntimeException)
//                throw (RuntimeException) cause;
//            else
//                throw new RuntimeException("Exception in SerializedLambda.readResolve", e);
//        }
//    }

    @Override
    public String toString() {
        String implKind=MethodHandleInfo.referenceKindToString(implMethodKind);
        return String.format("SerializedLambda[%s=%s, %s=%s.%s:%s, " +
                        "%s=%s %s.%s:%s, %s=%s, %s=%d]",
                "capturingClass", capturingClass,
                "functionalInterfaceMethod", functionalInterfaceClass,
                functionalInterfaceMethodName,
                functionalInterfaceMethodSignature,
                "implementation",
                implKind,
                implClass, implMethodName, implMethodSignature,
                "instantiatedMethodType", instantiatedMethodType,
                "numCaptured", capturedArgs.length);
    }
}

