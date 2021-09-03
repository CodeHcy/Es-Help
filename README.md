###### Es-helper 
为了便捷连接操作elasticsearch,简单方便

步骤：
1.配置es的连接地址（前提：已经引入了es 7.x 相关的包）
·
spring:
    elasticsearch:
        rest:
        uris: 172.16.2.219:9200
·
2.注入EsDoor
·
    @Resource
     private EsDoor esDoor;
·
3.直接使用(只需要你定义好索引对应的实体类，就可以通过lambda的方式，避免写列名。)
·
 EsQueryWrapper<EsUserDTO> userEsQueryWrapper = new EsQueryWrapper<>();
         userEsQueryWrapper.query(EsUserDTO::getName,text);
         userEsQueryWrapper.highlight(EsUserDTO::getName);
         List<Map<String, Object>> query = esDoor.query("索引名", userEsQueryWrapper);
·
