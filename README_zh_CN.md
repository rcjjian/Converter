# DocumentToThumbnail  #

## 介绍 ##
DocumentToThumbnail 是基于 openoffice 第三方服务，使用java语言写的一个可将 doc,docx,ppt,pptx格式导出缩略图png的应用，支持windows,linux，跨平台性很好。</br>
以下安装步骤，以CentOS7作为例子

## 运行环境 ##
1. Jdk1.8

## 安装和运行 ##

1. 下载 openoffice </br>
[https://jaist.dl.sourceforge.net/project/openofficeorg.mirror/4.1.5/binaries/zh-CN/Apache_OpenOffice_4.1.5_Linux_x86-64_install-rpm_zh-CN.tar.gz](https://jaist.dl.sourceforge.net/project/openofficeorg.mirror/4.1.5/binaries/zh-CN/Apache_OpenOffice_4.1.5_Linux_x86-64_install-rpm_zh-CN.tar.gz)

1. 安装 openoffice</br>
tar -zxvf Apache_OpenOffice_4.1.5_Linux_x86-64_install-rpm_zh-CN.tar.gz </br>
cd RPMS </br>
yum localinstall *.rpm </br>
rpm -ivh　openoffice4.1.5-redhat-menus-4.1.5-9775.noarch.rpm </br>

1. 运行 openoffice</br>
cd /opts/openoffice4 </br>
soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard

1. 运行 DocumentToThumbnail.jar

cd ~/DocumentToThumb 当前目录的位置  </br>
java -jar DocumentToThumbnail.jar /data/test/test.pptx /data/test 
 
运行成功后 /data/test/thumb 可看到缩略图

## 备注 ##
在安装的过程里面，出现了问题可以在此留言，baidu google
