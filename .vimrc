:set nu
:set autoindent
:syntax on
:set bg=dark
:set smarttab
:set ruler

filetype indent on
filetype plugin on

inoremap ( ()<Esc>i
inoremap { {<Space><Return>}<Esc>kli<CR>
inoremap [ []<Esc>i

function! RemoveNextDoubleChar(char)
	let l:line = getline(".")
	let l:next_char = l:line[col(".")]

	if a:char == l:next_char
		execute "normal! l"
	else
		execute "normal! a".a:char.""
	end
endfunction

inoremap ) <ESC>:call RemoveNextDoubleChar(')')<CR>a
inoremap ] <ESC>:call RemoveNextDoubleChar(']')<CR>a
inoremap } <ESC>:call RemoveNextDoubleChar('}')<CR>a

func! CompileGpp()
	exec "w"
	exec "!g++ % -o %<"
	exec "! ./%<"
endfunc

func! CompileGcc()
	exec "w"
	exec "!gcc % -o %<"
	exec "! ./%<"
endfunc

func! RunPython()
	exec "!python %"
endfunc

func! CompileJava()
	exec "!javac %"
	exec "!java %<"
endfunc

func! RunCode()
	exec "w"
	if &filetype == "cpp"
		exec "call CompileGpp()"
	elseif &filetype == "c"
		exec "call CompileGcc()"
	elseif &filetype == "python"
		exec "call RunPython()"
	elseif &filetype == "java"
		exec "call CompileJava()"
	elseif &filetype == 'tex'
		exec "!xelatex %"
		exec "!evince %<.pdf"
	endif
endfunc

func! RunPdflatex()
	exec "w"
	if &filetype == 'tex'
		exec "!pdflatex %"
		exec "!evince %<.pdf"
	endif
endfunc

map <F9> :call RunCode()<CR>
imap <F9> :call RunCode()<CR>
vmap <F9> :call RunCode()<CR>

map <F5> :call RunPdflatex()<CR>
imap <F5> :call RunPdflatex()<CR>
vmap <F5>:call RunPdflatex()<CR>

"filetype plugin on
"set grepprg=grep\ -nH\ $*
"let g:tex_flavor='latex'
"let g:Imap_UsePlaceHolders=0
"let g:Tex_DefaultTargetFormat = 'pdf'
"set iskeyword+=:
"autocmd BufEnter *.tex set sw=2

if has("autocmd")
	autocmd Filetype java setlocal omnifunc=javacomplete#Complete
endif

setlocal completefunc=javacomplete#CompleteParamsInfo
inoremap <buffer> <C-X><C-U> <C-X><C-U><C-P>
inoremap <buffer> <C-S-Space> <C-X><C-U><C-P>
autocmd FileType java inoremap <buffer> . .<C-X><C-O><C-P><Down>
