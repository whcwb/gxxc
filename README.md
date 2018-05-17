# ldz
#安装oracle驱动至本地maven仓库
mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.5.0 -Dpackaging=jar -Dfile=/develop/workspace/ldz/ojdbc7.jar

创建时间，修改时间使用`DateUtils.getNowTime()`方式获取