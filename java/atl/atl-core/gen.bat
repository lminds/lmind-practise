@echo off

set BAT_HOME=%~dp0

call jjtree -OUTPUT_DIRECTORY:%BAT_HOME%\src\main\java\org\lmind\atl\core\ast atl.jjt
call javacc -OUTPUT_DIRECTORY:%BAT_HOME%\src\main\java\org\lmind\atl\core\ast -STATIC:false %BAT_HOME%\src\main\java\org\lmind\atl\core\ast\atl.jj
del %BAT_HOME%\src\main\java\org\lmind\atl\core\ast\atl.jj