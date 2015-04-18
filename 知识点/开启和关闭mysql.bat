view plain
@echo off 
for /f "skip=3 tokens=4" %%i in ('sc query mysql') do set "zt=%%i" &goto :next 
 
:next 
if /i "%zt%"=="RUNNING" ( 
echo 已经发现该服务在运行，正在已经关闭服务 
net stop mysql 
) else ( 
echo 该服务现在处理停止状态，正在现在开启服务 
net start mysql 
) 
exit 
pause 