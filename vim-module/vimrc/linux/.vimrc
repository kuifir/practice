set enc=utf-8
" 不与VI兼容
set nocompatible

" 不延迟加载菜单
let do_syntax_sel_menu=1
let do_no_lazyload_menus=1

source $VIMRUNTIME/vimrc_example.vim

" vim 文件解码顺序
set fileencodings=ucs-bom,utf-8,gb18030,latin1

" 配置保存策略
set nobackup
" fzf路徑添加到runtimepath
set rtp+=~/.fzf

if has('persistent_undo')
  set undofile
  set undodir=~/.vim/undodir
  if !isdirectory(&undodir)
    call mkdir(&undodir, 'p', 0700)
  endif
endif

" 配置鼠标
if has('mouse')
  if has('gui_running') || (&term =~ 'xterm' && !has('mac'))
    set mouse=a
  else
    set mouse=nvi
  endif
endif

" 配置字体 Linux 和 Windows 不同，不能用 '_' 取代空格
if has('gui_running')
  " 下面两行仅为占位使用；请填入你自己的字体
  set guifont=DejaVu\ Sans\ Mono\ 10
endif

" 配置minpac包管理器
if exists('*minpac#init')
  " Minpac is loaded.
  call minpac#init()
  call minpac#add('k-takata/minpac', {'type': 'opt'})

  " Other plugins
  call minpac#add('tpope/vim-eunuch')
  call minpac#add('yegappan/mru')
  call minpac#add('preservim/nerdtree')
  call minpac#add('majutsushi/tagbar')
  call minpac#add('skywind3000/asyncrun.vim')
  call minpac#add('tpope/vim-surround')
  call minpac#add('tpope/vim-repeat')
  call minpac#add('mbbill/undotree')
  call minpac#add('junegunn/fzf', {'do': {-> fzf#install()}})
  call minpac#add('junegunn/fzf.vim')
endif

if has('eval')
  " Minpac commands
  command! PackUpdate packadd minpac | source $MYVIMRC | call minpac#update('', {'do': 'call minpac#status()'})
  command! PackClean  packadd minpac | source $MYVIMRC | call minpac#clean()
  command! PackStatus packadd minpac | source $MYVIMRC | call minpac#status()
endif

" 配置无图形界面时，文本唤起菜单
if !has('gui_running')
  " 设置文本菜单
  if has('wildmenu')
    set wildmenu
    set cpoptions-=<
    set wildcharm=<C-Z>
    nnoremap <F10>      :emenu <C-Z>
    inoremap <F10> <C-O>:emenu <C-Z>
  endif
endif

"  支持在文件已经打开时自动切换到已经打开的 Vim 窗口上
if v:version >= 800
  packadd! editexisting
endif

" 切换窗口映射快捷键位
nnoremap <C-Tab>   <C-W>w
inoremap <C-Tab>   <C-O><C-W>w
nnoremap <C-S-Tab> <C-W>W
inoremap <C-S-Tab> <C-O><C-W>W

" 设置
set tags=./tags;,tags,/usr/local/etc/systags
" 开关 Tagbar插件的映射
nnoremap <F9>   :TagbarToggle<CR>
inoremap <F9>   <C-O>:TagbarToggle<CR>

" 启用man插件
source $VIMRUNTIME/ftplugin/man.vim

set keywordprg=:Man

