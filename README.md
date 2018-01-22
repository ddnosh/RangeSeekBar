# RangeSeekBar
A seekbar for customized range  
一个可以自定义滑动起点、滑动范围以及滑块提示语的滑动条

example1 | example 2
:----: | :----:
![screenshot1](https://github.com/ddnosh/RangeSeekBar/blob/master/art/screenshot1.png) | ![screenshot2](https://github.com/ddnosh/RangeSeekBar/blob/master/art/screenshot2.png)

# Usage
* Include RangeSeekBar in a layout xml file:
```
<la.xiong.seekbar.RangeSeekBar
        android:id="@+id/seekBar"
        android:layout_width="368dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:layout_gravity="center"
        android:max="16"
        android:maxHeight="3dp"
        android:progress="0"
        android:indeterminateOnly="false"
        android:progressDrawable="@drawable/rangeseekbar"
        android:splitTrack="false"
        app:blockTextColor="#fff"
        app:blockTextSize="16sp"
        app:blockColor="#ff0000"
        app:start="10"
        app:unit="°"/>
```
# Attributes
RangeSeekBar will offer several attributes for deeper setting, the following table will show all the options and their default values:

Name | Decription | Values | Default
:----: | ---- | :----: | :----:
blockTextColor | seekbar block text color | color | #FFFFFF
blockTextSize | seekbar block text size | dimension | 16sp
blockColor | seekbar block color | color | #FF0000
start | seekbar start value | integer | 10
unit | seekbar block text value | string | 