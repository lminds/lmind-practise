@echo off

set BAT_HOME=%~dp0

call jjtree -OUTPUT_DIRECTORY:%BAT_HOME%\src\main\java\org\lmind\jel\core\ast -STATIC:false -NODE_PACKAGE:org.lmind.jel.core.ast ast.jjt
call javacc -OUTPUT_DIRECTORY:%BAT_HOME%\src\main\java\org\lmind\jel\core\ast -STATIC:false %BAT_HOME%\src\main\java\org\lmind\jel\core\ast\ast.jj