分布式缓存
1. 基于其他各类场景，设计并在示例代码中实现简单demo：
- 实现分数排名或者排行榜；
  - 排名需求分析
    1. 创建排行榜 & 删除排行榜 ``` zadd ranking xxx userN```
    2. 记录用户分数 ```zadd ranking 89 user1```
    3. 对指定用户分数进行更新 ```zincrby ranking 10 user1```
    4. 查询指定用户的排名和分数 ```zrange ranking 0 -1 withscores```
    5. 按名次查询前 N 名用户信息 ```zrange ranking 0 N withscores```
    6. 查询指定用户后 M 名的用户

- 实现全局ID生成；
- 基于Bitmap实现id去重；
- 基于HLL实现点击量计数。
- 以redis作为数据库，模拟使用lua脚本实现前面课程的外汇交易事务。

2. 升级改造项目：
- 实现guava cache的spring cache适配；
- 替换jackson序列化为fastjson或者fst，kryo；
- 对项目进行分析和性能调优。

3. 以redis作为基础实现上个模块的自定义rpc的注册中心；
4. 练习redission的各种功能；
5. 练习hazelcast的各种功能；
6. 搭建hazelcast 3节点集群，写入100万数据到一个map，模拟和演示高可用，测试一下性能。