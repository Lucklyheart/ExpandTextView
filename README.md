### ExpandTextView
一款可展开和收缩的TextView、展开末尾可添加图片
### 效果展示 
- 常规样式 

	![Alt](https://img-blog.csdnimg.cn/20191219145117483.gif)
- 非常规样式 

	![Alt](https://img-blog.csdnimg.cn/20191219145704788.gif)
### 方法调用
- 常规版样式 
		
		
		textView.initWidth(widthPixels);
        textView.setMaxLines(3);
        textView.setAppendText(true);
        textView.setCloseText(text);
	
- 非常规样式 
		
		
		textView.initWidth(widthPixels);
        textView.setMaxLines(Integer.MAX_VALUE);
        textView.setAppendText(false);
        textView.setRes(R.mipmap.ic_launcher);
        textView.setExpandText(text);

### 集成步骤
#### Step 1. Add the JitPack repository to your build file 
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	} 
#### Step 2. Add the dependency 
	dependencies {
	      implementation 'com.github.Lucklyheart:ExpandTextView:v1.0.1'
	} 
### Final
	欢迎指导和建议！！！
