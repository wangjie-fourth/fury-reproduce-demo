package org.github.fourth.fury;

import org.apache.fury.Fury;
import org.apache.fury.ThreadSafeFury;
import org.apache.fury.config.CompatibleMode;
import org.apache.fury.config.Language;

import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String[] args) {
        ThreadSafeFury threadSafeFury = Fury.builder().withLanguage(Language.JAVA).withRefTracking(false)
                .requireClassRegistration(false).withCompatibleMode(CompatibleMode.COMPATIBLE)
                .withDeserializeNonexistentClass(true)
                .buildThreadSafeFuryPool(2, 2 * 2, 30L, TimeUnit.MINUTES);
        // 客户端使用老版本类
        Entity entity = new Entity();
        entity.setId(false);
        // 客户端开始序列化
        byte[] bytes = threadSafeFury.serializeJavaObject(entity);
        // 服务端使用新版本类反序列化
        try {
            EntityV2 entityV2 = threadSafeFury.deserializeJavaObject(bytes, EntityV2.class);
        } catch (Exception e) {
            System.out.println("无法序列化");
        }


        // 如果强制设置成meta不共享
        threadSafeFury = Fury.builder().withLanguage(Language.JAVA).withRefTracking(false)
                .requireClassRegistration(false)
                .withMetaShare(false)
                .withCompatibleMode(CompatibleMode.COMPATIBLE)
                .withDeserializeNonexistentClass(true)
                .buildThreadSafeFuryPool(2, 2 * 2, 30L, TimeUnit.MINUTES);

        // 服务端使用新版本类反序列化
        EntityV2 entityV2 = threadSafeFury.deserializeJavaObject(threadSafeFury.serializeJavaObject(entity), EntityV2.class);
        System.out.println("entityV2 = " + entityV2);
        System.out.println("成功反序列化");

    }

}
