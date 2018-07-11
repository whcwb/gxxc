# 共享学车
#安装oracle驱动至本地maven仓库
mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.5.0 -Dpackaging=jar -Dfile=/develop/workspace/ldz/ojdbc7.jar

# ldz
创建时间，修改时间使用`DateUtils.getNowTime()`方式获取

**_更新数据库禁止用：_**
`Mapper.updateByPrimaryKey`根据主键更新属性为null的值。意思就是说，如果对应中有null值，就将null值写到数据库中，所以这个方法使用一定要注意！！！ 


本地调试，端口必须是9086
请求地址是：http://47.98.39.45:9087/#/login


系统本分学员、会员 学员分佣会员不会。、。