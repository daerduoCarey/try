:set nu
:set autoindent
:syntax on
:set bg=dark

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

map <F9> :call RunCode()<CR>
imap <F9> :call RunCode()<CR>
vmap <F9> :call RunCode()<CR>

"filetype plugin on
"set grepprg=grep\ -nH\ $*
"let g:tex_flavor='latex'
"let g:Imap_UsePlaceHolders=0
"let g:Tex_DefaultTargetFormat = 'pdf'
"set iskeyword+=:
"autocmd BufEnter *.tex set sw=2
