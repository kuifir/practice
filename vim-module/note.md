

### Vi四种模式

- 正常模式（Normal-mode）： 该模式下所有输入的按键都是对Vim下的命令。（复制，粘贴）
- 插入模式（Insert-mode）: 进行文本内容的输入
- 命令模式（Command-mode）: 也叫末行模式，进行文本保存退出。
- 可视模式（Visual-mode）:高级编辑模式对一块文件进行插入操作

#### 正常模式

- i 进入插入模式, 在光标当前位置进入
- I 进入插入模式，光标为所在行的开头
- a 进入插入模式,光标位置后面一位
- A 进入插入模式,光标为所在行的结尾
- o 进入插入模式，在当前所在行下一行插入一个空行
- O 进入插入模式，在当前所在行上一行插入一个空行
- v 进入可视模式
- ：进入命令模式
- h 向左移动
- j 向下移动
- k 向上移动
- l 向右移动
- yy 复制一整行
- [n]yy 复制n行
- y$ 从光标位置复制到行尾
- p 粘贴
- dd 剪切一行
- d$ 剪切光标位置到行尾
- u 撤销操作
- ctrl + r 撤销撤销操作
- x 删除单个字符
- r 替换字符
- 11 + G 光标移动到指定行
- g 光标移动到第一行
- G 光标移动到最后一行
- ^ 光标移动到行首
- $ 光标移动到行尾

#### 插入模式



#### 命令模式

- 正常模式`:`进入命令模式

- 命令行进行set命令只单次生效，若要每次启动都生效，需要到/etc/vimrc 修改vim配置文件。

- : set nu 显示行数 
- : set nonu 取消显示行数
- :w + 路径 保存到路径下
- :w 保存文件
- :q 不保存退出
- :! 执行linux命令 例：编辑过程中要查看ip地址` :! ifconfig`,回车后可返回编辑。

- /  例：找字符`/x`，如果要查找多个，按n查找下一个，shift +n 查找上一个
- :set nohlsearch 取消查找的高亮显示
- :s/old/new 查找替换单个字符,默认当前行替换。例：`:s/x/X`
- :%s/old/new 全局查找替换字符，替换的是每行的第一个匹配字符。例：`:%s/x/X`
- :%s/old/new/g 全局查找替换字符。例：`:%s/x/X/g`
- :%s/old/new/g 全局查找替换字符。例：`:%s/x/X/g`
- 3,5 s/old/new/ 在第三到第五行之间进行替换
- 

#### 可视模式

- 正常模式`v`进入字符可视模式

- 正常模式`shift +　v`进入字符可视行模式

- 正常模式`ctrl +　v`进入可视块模式。 例：`ctrl +　v`进入可视块模式，选中块，

  插入字符，`I`，添加字符，连按两下`esc`,则选中块每一行后悔插入相同内容。

### 配置

#### 基本配置

```shell
set enc=utf-8
set nocompatible
source $VIMRUNTIME/vimrc_example.vim
```

####  备份和撤销文件 

 通常做法是，不产生备份文件，但保留跨会话撤销编辑的能力 

- Linux/macOS 

  ```shell
  set nobackup
  set undodir=~/.vim/undodir
  
  if !isdirectory(&undodir)
    call mkdir(&undodir, 'p', 0700)
  endif
  ```

- Windows

  ```shell
  set nobackup
  set undodir=~\vimfiles\undodir

  if !isdirectory(&undodir)
    call mkdir(&undodir, 'p', 0700)
  endif
  ```
  

####  鼠标支持 

```shell
if has('mouse')
  if has('gui_running') || (&term =~ 'xterm' && !has('mac'))
    set mouse=a
  else
    set mouse=nvi
  endif
endif
```

####  中文支持 

如果你明确需要处理中文，那在配置文件里最好明确写下下面的选项设定： 

```shell
set fileencodings=ucs-bom,utf-8,gb18030,latin1
```

####  图形界面的字体配置 

- Linux

  ```shell
  " Linux 和 Windows 不同，不能用 '_' 取代空格
  set guifont=DejaVu\ Sans\ Mono\ 10
  ```

- Windows

  ```shell
  if has('gui_running')
    set guifont=Courier_New:h10
  endif
  ```

  

####  减少对屏幕滚动和H、L命令设置的干扰 

 vimrc_example 有一个设定 ， 会设 set scrolloff=5，导致只要屏幕能滚动，光标就移不到最上面的 4 行和最下面的 4 行里，因为一移进去屏幕就会自动滚动。这同样也会导致 H 和 L 的功能发生变化：本来是移动光标到屏幕的最上面和最下面，现在则变成了移动到上数第 6 行和下数第 6 行，和没有这个设定时的 6H 与 6L 一样了。  一般会在 Vim 配置文件里设置 set scrolloff=1（你也可以考虑设成 0），减少这个设置的干扰。 

```shell
set scrolloff=1
```

