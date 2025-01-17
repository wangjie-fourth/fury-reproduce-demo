# fury-reproduce-demo

复现Fury在兼容默认情况下，没有强制关闭元数据共享时，会出现同个类增加新字段后，无法反序列化。

具体请参考Main.java，可以运行查看报错：
```shell
mvn exec:java -Dexec.mainClass="org.github.fourth.fury.Main"
```

