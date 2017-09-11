# PictureProgressBar

## Description
PictureProgressBar allows you to set picture and animation in the position of current progress.


### Demo
![](http://i.imgur.com/076zTuA.gif)

## Usage

### Gradle
```
    compile 'com.yanzhikaijky:PictureProgressbar:1.2.0'
```

### Attributes

|**Attribute Name**|**Description**|**Type**|**Default**|
|--|--|:--:|:--:|
|backGroundColor      | The background color of bar     | color| #888888|
|barColor            |  The color of bar        | color | #ff0000|
|drawable             | The drawable of picture| drawable| null|
|halfDrawableWidth    | The width of drawable     | dimension| 0 |
|halfDrawableHeight   | The height of drawable      | dimension| 0 |
|drawableHeightOffset | The height offset of drawable | dimension | 0 |
|isRound              | Is the angles of bar round   |boolean | true|
|roundX               | The X direction radius of round angles | dimension| 20px|
|roundY               | The X direction radius of round angles  | dimension| 20px|
|progress             |  Initial progress   | int| 0 |
|max                  |  Max progress   | int| 0 |
|isSetBar            |   Is the height of bar user-defined |boolean |flase |
|progressHeight      |   The height of progress bar   | dimension| 100px |
|progressHeightOffset |  The height offset of progress bar     |  dimension| | 30px |
|refreshTime          |  The refresh interval   | int | 100|
|animMode             |  Animation Mode         | eunm| ANIM_NULL|
|rotateRate           |  The rotation Angle of each interval time         | int| 10 |
|rotateDegree         |  The initial rotation Angle      | int|  0 |
|scaleMax             |  The max scale of ScaleAnimation      | float| 1.5f|
|scaleMin            |   The min scale of ScaleAnimation   |  float| 0.5f|
|scaleRate            |  The scale of each interval time  | float | 0.1|
|gradientStartColor   |  The gradient start color  |color | #ff0000 |
|gradientEndColor     |  The gradient end color   |color | #ffff00|
|**backgroundDrawable**   |  The background drawable  |reference |  |
|**barDrawable**     |  The bar drawable  |reference | |

 > PS: To set `backgroundDrawable` and `barDrawable` can provide a drawable background like Google's Progress:
 > ![](https://i.imgur.com/laHcRXm.png)
 > ![](https://i.imgur.com/GFgRZCz.gif)


**Introduction of animMode：**

|animMode|Description|Usage|
|--|--|--
|ANIM_NULL|Without animation|No need for Drawable|
|ANIM_ROTATE|Rotation animation|Need for Drawable. It is best to set `rotateRate` and `rotateDegree`. The part of Rotation Drawable，which is out of view's scope，will be gone.|
|ANIM_SCALE |Scale animation|Need for Drawable. It is best to set `scaleMax`、`scaleMin` and `scaleRate`. The part of Scale Drawable，which is out of view's scope，will be gone.| 
|ANIM_ROTATE_SCALE|Rotation and scale animation|Need for Drawable. The part of Scale Drawable，which is out of view's scope，will be gone.|
|ANIM_FRAME|Frame animation|Need for Drawable，but need for drawableIds array|


Except the setter and getter of above-mentioned attributes, there are some methods: 

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

//added by v1.2.0：

    //设置进度条背景图片
    public void setBarDrawableId(int id) 

    //设置进度条图片
    public void setBarBackgroundDrawableId(int id) 
```
## LICENSE

```
Copyright 2017 Yanzhikai

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Update
 - **version 1.1.1** : 2017/07/07 Fixed a bug：
Added Attribute `progressPercentage` to show the percentage of progress.
Changed the `setProgress()` to prevent the int type overflow:

- **version 1.2.0 **:2017/09/11：
 - Added Attribute, `backgroundDrawable` and `barDrawable`.The new effect is similar to a drawable background in Google's Progress.
## About
 > id：Yanzhikai

 > Email：yanzhikai_yjk@qq.com

 > CSDN：http://blog.csdn.net/totond



