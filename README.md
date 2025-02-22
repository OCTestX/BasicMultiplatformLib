# 通用的BasicLib，可以用在Desktop， Android， Server

# 技术栈

- 序列化Json，使用Ojson
- 日志使用io.klogging:klogging-jvm的noCoLogger<>()，需要初始化Ologger以提供输出到文件等服务,不过你只要初始化平台相关的Initialized,他会间接初始化CommonInitialized
- 保存键值对设置使用com.russhwolf:multiplatform-settings
- 保存可序列化结构使用io.github.xxfast:kstore
- 获取数据目录使用ca.gosyer:kotlin-multiplatform-appdirs
- 数据库使用app.cash.sqldelight,记得在IDEA中安装SQLDelight插件(已经附带平台差异驱动，如果你用的是sqlite的话,不需要你安装平台数据库驱动)
- 缓存数据使用io.github.reactivecircus.cache4k:cache4k
- 依赖注入使用io.insert-koin:koin-core
- 文件操作，字节流使用com.squareup.okio:okio

# 使用

## 初始化平台InitCenter
- AndroidInitCenter
- JVMInitCenter




