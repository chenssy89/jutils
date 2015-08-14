# jutils
在java编码过程中我们可能会遇到各种各样的util，jutils是LZ在编程过程中总结的工具类，包括：基础工具类（时间、正则表达式、字符串、随机数等等），excel解析生成、word解析生成、文件操作、图片操作、敏感字、加解密等等。

<h2>特性</h2>
<p>1、功能齐全，有你所想。</p>
<p>2、简单易用，无学习成本。</p>

<h2>主要功能</h2>
<h3>package:base--基本功能</h3>
<p>BigDecimalUtils：提供精确的加减乘除运算</p>
<p>DateUtils：时间处理工具类</p>
<p>MoneyUtils:金钱处理工具类</p>
<p>RandomUtils:随机数工具类</p>
<p>RegexUtils:正则表达式工具类，验证数据是否符合规范</p>
<p>StringUtils:字符串工具类，对字符串进行常规的处理</p>
<p>ValidateHelper: 判断对象、字符串、集合是否为空、不为空</p>

<h3></h3>
<h3>package:clone--克隆</h3>
<p>CloneUtils:克隆工具类，进行深克隆,包括对象、集合</p>

<h3></h3>
<h3>package:encrypt--加解密</h3>
<p>MD5Utils:md5加密处理工具类</p>

<h3></h3>
<h3>package:excel--excel</h3>
<p>ExcelExportHelper:Excel 生成通用类，为了兼容，所有 Excel 统一生成 Excel2003 即：xx.xls</p>
<p>ExcelReadHelper:解析Excel，支持2003、2007</p>

<h3>package:file--文件处理</h3>
<p>FileUtils:文件处理工具类</p>
<p>ZipUitls:ZIP工具类</p>

<h3>package:ImageUtil--图像处理</h3>
<p>ImageUtil:图像处理。对图片进行压缩、水印、伸缩变换、透明处理、格式转换操作</p>

<h3>package:ImageUtil--图像处理</h3>
<p>ImageUtil:图像处理。对图片进行压缩、水印、伸缩变换、透明处理、格式转换操作</p>

<h3>package:jsp--JSP</h3>
<p>JSPBeanUtils:在jsp页面中使用，JavaBean</p>

<h3>package:sensitiveword--J敏感词</h3>
<p>SensitivewordFilterUtil:铭感词过滤工具类</p>
<p>SensitiveWordInit:初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型</p>
