# DocumentToThumbnail  #

### 介绍 ###
>**DocumentToThumbnail** 是用于doc,docx,ppt,pptx格式文档导出png格式预览图的一个Java应用。可作为与office相关项目的一个插件

### 版本说明 ###
|版本分支|office开源库|JODConveter版本|
|:----|:---|:---|
|master |Libreoffice5.4.7  |JODConveter4.1|
|V1.0 |Openoffice4.7  |JODConveter2.2|

### 技术关键词 ###
- **Java** jdk1.7及以上
- **Maven** 当前版本3.4，依赖库的安装工具
- **JODConvter** 当前版本为4.1，[参考文档](https://github.com/sbraconnier/jodconverter "参考文档")
- **Libreoffce** 当前版本为5.4.7

### 运行环境 ###
- **CentOS7** 
- **Java** jdk1.7及以上
- **Libreoffce** 当前版本为5.4.7

### 依赖插件  ###
- **JDK** [下载地址](http://www.oracle.com/technetwork/java/javase/downloads/index.html "下载地址")
- **Libreoffce** [下载地址](https://www.libreoffice.org/donate/dl/rpm-x86_64/5.4.7/zh-CN/LibreOffice_5.4.7_Linux_x86-64_rpm.tar.gz "下载地址")

### 中文字体问题 ###
>转换文档过程，需要考虑中文字体的问题，那么就需要部署的环境增加中文常用字体库。详情[解决方法](https://blog.csdn.net/wlwlwlwl015/article/details/51482065 "解决方法")

### 命令行 ###
    java -jar DocumentConverter.jar {$document_path} {$thumb_dir_path} {$port} {libreoffce_home}
    
### 参数 ###
|参数名|说明|
|:----|:---|
|document_path |文档路径  |
|thumb_dir_path |导出预览图路径  |
|port |libreoffice启动端口|
|libreoffce_home|libreoffice的目录路径|