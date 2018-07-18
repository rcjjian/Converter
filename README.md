# DocumentToThumbnail  #

### 介绍 ###
>**DocumentToThumbnail** 是用于doc,docx,ppt,pptx格式文档导出png格式预览图的一个Java应用。作为资源服务器[Resource-Server](http://gitlab.dtedu.com/ruanchangjian/resource-server/tree/master "resource-server")项目的一个插件

### 版本迭代 ###
>v1.0版本文档导出预览图都必须借助外部服务应用（libreoffice,openoffice），那么就意味着多开一个进程。为提升效率降低cpu和内存消耗，最新版本已废弃以上方式，改为使用第三方java库。更加灵活，更轻便式。

### 版本说明 ###
|版本分支|office开源库|外部jar包|
|:----|:---|:---|
|master | x  | poi3.17,aspose-words-18.5,jpedal_lgpl |
|V1.0 |Libreoffice5.4.7  |JODConveter4.1|

### 技术关键词 ###
- **Java** jdk1.7及以上
- **Maven** 当前版本3.4，依赖库的安装工具

### 运行环境 ###
- **CentOS7** 
- **Java** jdk1.7及以上

### 依赖插件  ###
- **JDK** [下载地址](http://www.oracle.com/technetwork/java/javase/downloads/index.html "下载地址")

### 中文字体问题 ###
>转换文档过程，需要考虑中文字体的问题，那么就需要部署的环境增加中文常用字体库。详情[解决方法](https://blog.csdn.net/wlwlwlwl015/article/details/51482065 "解决方法")

### 命令行 ###
    java -jar DocumentConverter.jar {$document_path} {$thumb_dir_path}
    
### 参数 ###
|参数名|说明|
|:----|:---|
|document_path |文档路径  |
|thumb_dir_path |导出预览图路径  |