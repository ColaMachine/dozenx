@Echo Off
:start
sleep_s 10000
copy 1.m3u8 index.m3u8 /y
sleep_s 10000
copy 2.m3u8 index.m3u8 /y
sleep_s 10000
copy 3.m3u8 index.m3u8 /y
sleep_s 10000
copy 4.m3u8 index.m3u8 /y
sleep_s 10000
copy 5.m3u8 index.m3u8 /y
sleep_s 10000
copy 6.m3u8 index.m3u8 /y
echo 'hello'
goto start