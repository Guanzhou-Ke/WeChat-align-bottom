# WeChat-align-bottom
Android端的模仿微信朋友圈评论弹出输入框与listView底部对齐Demo

# 思路：
	通过ListView监听OnSizeChange的动作来改变各个Item与当前输入框的位置
	但是这种策略会导致最后一个item异常底部对齐，所以这里解决的策略是添加多一个
	空白的Item，Visible设置为InVisible，这样最后一个数据块就是倒数第二个，
	就可以正常的对齐。

