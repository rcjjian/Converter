# DocumentToThumbnail  #

### 介绍 ###
>**DocumentToThumbnail** 是用于doc,docx,ppt,pptx格式文档导出png格式预览图的一个Java ServerSocket应用。

### 版本迭代 ###
>在对上的两个大版本，文档导出预览图都必须借助外部服务应用（libreoffice,openoffice），那么就意味着多开一个进程。为提升效率降低cpu和内存消耗，最新版本已废弃以上方式，改为使用第三方java库。更加灵活，更轻便式。在当前版本 **（master分支）** 加入socket和多线程后，大大提高并发效率，改掉上一版本一次转换打开一个进程的低效局面

### 版本说明 ###
|版本分支|office开源库|外部jar包|多线程|
|:----|:---|:---|:---|
|master | x  | poi3.17,aspose-words-18.5,jpedal_lgpl |支持|
|v2.0 | x  | poi3.17,aspose-words-18.5,jpedal_lgpl |x|
|V1.0 |Libreoffice5.4.7  |JODConveter4.1|x|

### 更新记录 ###
- 2018.07.27 增加了可中断执行转换的操作
- 2018.07.20 加入socket和多线程提高并发
- 2018.07.18 使用poi和apose-word作为转换文档解决方案
- 2018.07.13 使用libreoffice和JODConverter作为转换文档解决方案

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

### 运行命令行 ###
    java -jar DocumentConverter.jar {$port}
    
### 参数 ###
|参数名|说明|
|:----|:---|
|port |server socket端口 |

### 使用接口说明 ###
DocumentConverter为一个ServerSocket，默认端口为8100，当然在启动时指定其他端口也可以。使用其提供转换功能，请使用Socket链接，并且发送json格式字符串，末尾必须带上'\n'作为结束符。

### 请求参数 ###
|参数名|是否必须|说明|
|:----|:---|:---|
|taskId | 否|任务id(当action为stop，该参数为必须参数)|
|inputPath |否| 输入路径(当action为play，该参数为必须参数) |
|outputPath |否| 输出路径(当action为play，该参数为必须参数)|
|action |是| 执行动作（play为转换，stop为停止转换或删除转换） |
|externalId |否| 外部引入id，作为回调使用 |

### 返回参数 ###
|参数名|说明|
|:----|:---|
|code | 返回编码|
|taskId | 分配的任务id |
|message | 提示信息 |
|externalId | 外部引入id |

### 返回编码 ###
|编码|说明|
|:----|:---|
|200 | 服务端成功接收命令|
|201 | 执行命令完成 |
|202 | 暂停命令 |
|400 | 请求参数有误 |
|500 | 服务端异常 |

### node.js socket 对接 java serversocket例子 ###
[https://github.com/rcjjian/document_to_thumbnail/tree/master/nodejs_connect](https://github.com/rcjjian/document_to_thumbnail/tree/master/nodejs_connect "进入查看")
