# PictureProgressBar
欢迎 Star or Fork！有什么意见和建议可以在Issue上提出。
## 简介
　　这是一个可在进度条的当前进度点设置图片的ProgressBar，并且可以设置几种动画模式，效果如图：
![](http://i.imgur.com/076zTuA.gif)

## 使用
### Gradle

```
    compile 'com.yanzhikaijky:PictureProgressbar:1.1.0'
```



### 属性

|**属性名称**|**意义**|**类型**|**默认值**|
|--|--|:--:|:--:|
|backGroundColor      | 进度条背景颜色     | color| #888888|
|barColor            |  进度条颜色        | color | #ff0000|
|drawable             | 进度条图片drawable| drawable| null|
|halfDrawableWidth    | drawable宽度     | dimension| 0 |
|halfDrawableHeight   | drawable高度     | dimension| 0 |
|drawableHeightOffset | drawable高度偏移量 | dimension | 0 |
|isRound              | 进度条是否为圆角   |boolean | true|
|roundX               |  进度条圆角X半径    | dimension| 20px|
|roundY               |  进度条圆角Y半径   | dimension| 20px|
|progress             |  初始进度   | int| 0 |
|max                  |  最大进度   | int| 0 |
|isSetBar            |   是否自定义进度条高度 |boolean |flase |
|progressHeight      |   进度条高度          | dimension| 100px |
|progressHeightOffset |  进度条高度偏移量     |  dimension| | 30px |
|refreshTime          |  刷新时间          | int | 100|
|animMode             |  动画模式         | eunm| ANIM_NULL|
|rotateRate           |  旋转角度/每次刷新         | int| 10 |
|rotateDegree         |  旋转初始角度      | int|  0 |
|scaleMax             |  缩放最大倍数      | float| 1.5f|
|scaleMin            |   缩放最小倍数      |  float| 0.5f|
|scaleRate            |  缩放倍数/每次刷新      | float | 0.1|
|gradientStartColor   |  进度条渐变色开始颜色 |color | #ff0000 |
|gradientEndColor     |  进度条渐变色结束颜色 |color | #ffff00|

> 注意：上面的drawable属性可以是图片，也可以是Shape（demo里面的正方形就是自动画的Shape），其他的类型应该不行了。

**对应的animMode模式有：**

|animMode模式|意义|使用|
|--|--|--
|ANIM_NULL|无动画模式|不需要设置Drawable|
|ANIM_ROTATE|旋转动画模式|需要设置Drawable，最好设置rotateRate、rotateDegree属性，使用时注意动画范围会不会超出View的实际范围，超出部分不会显示|
|ANIM_SCALE |缩放动画模式|需要设置Drawable，最好设置scaleMax、scaleMin，scaleRate属性，使用时注意动画范围会不会超出View的实际范围，超出部分不会显示|
|ANIM_ROTATE_SCALE|旋转加缩放动画模式|需要设置Drawable，上两个模式的属性，使用时注意动画范围会不会超出View的实际范围，超出部分不会显示|
|ANIM_FRAME|帧动画模式|不需要设置Drawable，需要设置drawableIds数组|


除了上面属性的set、get方法，还有：

```
    //设置动画开关
    public void setAnimRun(boolean isAnimRun)

    //设置帧动画时要传入的图片ID数组
    public void setDrawableIds(int[] drawableIds)

    //设置图片
    public void setPicture(int id)

    //设置颜色渐变器
    public void setLinearGradient(LinearGradient linearGradient)

    //设置进度监听器
    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener)

    //进度监听器
    public interface OnProgressChangeListener {
        //进度改变时的回调
        public void onOnProgressChange(int progress);
        //进度完成时的回答
        public void onOnProgressFinish();
    }
```
## 开源协议
　　PictureProgressBar遵循Apache 2.0开源协议。
## 后续
　　目前没有加入文字，主要是想不到在哪个位置加比较好。后续看看需求加入文字和一些其它的动画，目前在学习RxJava，看看用观察者模式来实现会不会更好，貌似Android源码的ProgressBar就是基于观察者模式来刷新进度条的，可以继承它来实现试试，不同于我这种定时刷新，减少了资源的消耗，但是这样的话动画的处理会是个难点。

## 关于作者
id：炎之铠
炎之铠的邮箱：yanzhikai_yjk@qq.com
CSDN：http://blog.csdn.net/totond
PictureProgressBar全解析blog：
http://blog.csdn.net/totond/article/details/72359888
http://www.jianshu.com/p/009cb305ec5f

