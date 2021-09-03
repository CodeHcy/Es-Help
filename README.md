# Es-helper 

为了便捷连接操作elasticsearch,简单方便

# 适用场景
适用于 Es 7.x 的多条件查询，节约代码，快速开发。
详情见 pom.xml

###步骤：
####1.配置es的连接地址
```
spring:
    elasticsearch:
        rest:
        uris: 172.16.2.219:9200
```
####2.注入EsDoor
```
    @Resource
     private EsDoor esDoor;
```
####3.直接使用(只需要你定义好索引对应的实体类，就可以通过lambda的方式，避免写列名。)
```
 EsQueryWrapper<EsUserDTO> userEsQueryWrapper = new EsQueryWrapper<>();
         userEsQueryWrapper.query(EsUserDTO::getName,text);
         userEsQueryWrapper.highlight(EsUserDTO::getName);
         List<Map<String, Object>> query = esDoor.query("索引名", userEsQueryWrapper);
```
