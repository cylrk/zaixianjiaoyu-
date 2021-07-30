# zaixianjiaoyu-
2020-10 ~ 2020-12	在线教育（分布式）	全栈开发
在线教育系统，分为前台网站系统和后台运营平台，B2C模式。
前台用户系统包括课程、讲师、问答、文章几大大部分，使用了微服务技术架构，前后端分离开发。
项目技术
后端技术架构是：SpringBoot + SpringCloud + MyBatis-Plus + HttpClient + MySQL + Maven+EasyExcel+ nginx+Swagger

前端技术架构是：Node.js + Vue.js +element-ui+NUXT+ECharts

其他涉及到的中间件包括Redis、阿里云OSS、阿里云视频点播 业务中使用了使用EasyExcel完成分类批量添加、注册

l 分布式单点登录使用了JWT使用JWT生成token字符串l 使用了redis作为中间缓存，启用了redis的哨兵机制
l 使用网关解决了跨域问题，避免了@CrossOrigin l 成功部署在了linux系统上面
l 使用redis做了分布式锁，并且采用了布隆过滤器，以及后台逻辑，避免了大量无用的IO打到数据库
