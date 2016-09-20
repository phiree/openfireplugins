xcopy  src ..\openfire\src\plugins /e /y

cd ..\openfire\build
call ant clean
call ant plugins

copy ..\target\openfire\plugins\chatArchive.jar D:\"Program Files (x86)\Openfire\plugins\"
copy ..\target\openfire\plugins\getuserstate.jar D:\"Program Files (x86)\Openfire\plugins\"

cd ..\..\openfireplugins


