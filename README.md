<h1 > ssmServer </h1>
<h1 > 一、项目宗旨： </h1>

- 该项目的整个开发过程会清晰地展示给读者，方式在“三、版本递进说明”会详细解释。
- 我们希望读者通过阅读这个项目源码，亲身感受到“ssm框架是怎么使用”以及“ssm框架的作用如何在项目中发挥”
- 总的来说，读者可以通过这个项目达到“结合实例学习ssm框架”的目的

<h1 > 二、项目特点：</h1>

1. 实现前后端分离
2. 依赖MQ中间件完成前后端交互
3. 代码注释很全面，认真看都能看懂

<h2 > 1.前端：VUE框架 </h2>

<h2 > 2.媒介：MQ </h2>

<h2 > 3.后端： </h2>

- <h3 > 1.SSM框架 </h3>

    - <h4 > IOC </h4>
    - <h4 > AOP </h4>
    - <h4 > log4j </h4>
    
- <h3 > 2.服务部署环境 </h3>

    - <h4 > Nginx（反向代理服务器，用于增加并发量） </h4>
    - <h4 > Tomcat（JavaWeb服务运行环境） </h4>
    - <h4 > JDK（链接JavaWe与MySQL） </h4>
    - <h4 > MySQL（数据持久化） </h4>
- <h3 > 3.服务框架 </h3>
![如果图片显示失败，请检查路径](src/main/webapp/images/ssmServer-1.png) 

<h1 > 三、版本递进说明 </h1>

<h2 > 1.项目分支结构大体如下： </h2>

- master
- feature_wy
- feature_lz
- version_2.0
- ... ...
- version_1.1
- version_1.0

<h2 > 2.说明： </h2>

> **master**：整个项目的总分支

> **feature_xx**：开发者在各自的feature分支上开发，读者不用看

> **version_x.y**：每当项目开发到一个阶段，就创建一个对应的版本分支，读者可以根据版本递进，一步一步学习ssm框架；比如version_1.0分支对应的阶段就是“ssm框架基本配置完成”；在每个版本分支的README.md中对该版本“做了什么事”都有详细的介绍

<h1 > 四、项目部署 </h1>

<h2 > 1.云服务器环境要求： </h2>

**1. tomcat（自行搜索安装教程）**

**2. mysql（自信搜索安装教程）**

**3. nodejs（vueServer项目的分支version_1.0中有介绍）**

<h2 > 2.按照下面步骤将该项目部署到云服务器上 </h2>

1. 在该项目根目录下使用maven指令将项目打war包

```bash
$ mvn clean package
```
2. 打包后会在项目根目录中生成一个target文件夹，将target文件夹中的war包上传到云服务器
```bash
#在target目录下输入下面指令
$ sftp root@云服务器ip
root@云服务器ip's password:
sftp> put ssmServer.war
```
3. 登录云服务器，将war包放入tomcat的webapps文件夹中

```bash
#先清空webapps文件夹
$ rm -r root/tomcat/webapps/*
#tomcat的路径要更改成自己云服务器上的路径，下面的路径只是示例
$ mv root/ssmServer.war root/tomcat/webapps
```
4. 更改tomcat的server.xml配置文件

```bash
$ vim /root/tomcat/conf/server.xml
# 在<Host name="localhost" appBase="webapps" unpackWARs="true" autoDeploy="true">下面添加这行代码
<Context path="" docBase="/root/tomcat/webapps/ssmServer" debug="0"  privileged="true" reloadable="true"/>
# context元素：每个Context元素代表了运行在虚拟主机上的单个Web应用
# docBase 属性：自己web应用的路径，可以是相对路径也可以是绝对路径，相对路径是相对于host的webapps，绝对路径如上例。
# path 属性：即要建立的虚拟目录，是tomcat访问这个应用的URL路径，如果为空则代表访问路径为web应用的根目录，访问路径：http://ip:port。如果非空则访问路径：http://ip:port/path。
# reloadable：如果这个属性设为true，Tomcat服务器在运行状态下会监视在WEB-INF/classes和Web-INF/lib目录CLASS文件的改变，如果监视到有class文件被更新，服务器自动重新加载Web应用，这样我们可以在不重启tomcat的情况下改变应用程序
```
5. 至此完成，运行tomcat

```bash
# 如果tomcat已经运行，先查一下其进程号
ps -ef | grep -v grep | grep tomcat
# 然后关闭进程
kill -9 [进程号]
#这里没使用tomcat的shutdown.sh关闭tomcat的原因是有时候会因为端口占用，shutdown.sh关不了tomcat，还不如kill来得直接方便

# 重新启动tomcat
sh /root/tomcat/bin/startup.sh
```
<h2 > 3.实用脚本 </h2>

> 提醒：脚本里的路径都要改成自己云服务器上的路径哦～

<h3 > 1.重启tomcat脚本 </h3>

> 每次重启tomcat都要重复执行好几个指令，费劲～
>
> 现在只用sh restartTomcat.sh，一键搞定！
>
> 建议将该脚本文件放在root目录下，因为这样不管在哪个文件夹下都可以 sh ~/restartTomcat.sh，快捷方便～

```bash
#! /bin/bash

#获取当前运行的tomcat的pid
tomcat_pid=$(ps -ef | grep -v grep | grep tomcat |awk '{ print $2}')

#判空
if [ $tomcat_pid ]; then
    kill -9 $tomcat_pid
    echo $tomcat_pid
    echo "关闭当前tomcat，重新启动"
fi

#启动tomcat
sh /usr/local/zstack/apache-tomcat/bin/startup.sh
echo 'tomcat启动成功'
tomcat_pid=$(ps -ef | grep -v grep | grep tomcat |awk '{ print $2}')

```
<h3 > 2.打印tomcat日志脚本 </h3>

> 项目出bug，经常去tomcat中查看日志排查问题
>
> 现在只用sh tail-tomcat-log.sh.sh，一键搞定！
>
> 建议将该脚本文件放在root目录下，因为这样不管在哪个文件夹下都可以 sh ~/tail-tomcat-log.sh.sh，快捷方便～

```bash
#! /bin/bash
tail -f /usr/local/zstack/apache-tomcat/logs/management-server.log
```


