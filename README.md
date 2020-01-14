# tony_book

## 目录结构
|- build  //自动生成  
|- files  //存放原始excel文件，需手动创建  
|- src  
|--- main  
|-----java  
|------- com.yudao.MainApplication  //web启动程序  
|------- com.yudao.index.IndexWriter  //生成索引文件  
|----- resources  
|------- index  //存放生成后的索引文件  
|----- webapp

## 部署方法
### 0、依赖环境
- jdk1.8
- intellij idea Community版即可

### 1、建索引
- 将需要生成索引的excel文件拷贝到 *files* 目录。
- 运行 *IndexWriter* 类的 *main* 方法，在intellij idea程序中即可。
- 观察 *resources/index* 文件夹下有索引二进制文件生成。

### 2、打包
- 在项目路径下使用 *gradle clean build* 命令。
- 观察到 *build* 文件夹被自动创建，并且 *build/libs/* 文件夹下有.war部署包生成。

### 3、启动服务器
- 在服务器安装tomcat8.0。
- 将 *build/libs/tony_book-1.0-SHAPSHOT.war* 拷贝到tomcat的webapps目录下，并启动。
- 访问 http://localhost:8080/index


