@echo off
echo ----- Novamenu 1.1.3 -----
echo ----- Build started  -----
xcopy ..\..\forge\mcp\src ..\..\forge\mcp\src-backup /E /I /Q
echo Minecraft source backed up.
xcopy source ..\..\forge\mcp\src\minecraft /E /Q
echo Copied Novamenu source.
cd ..\..\forge\mcp
call recompile.bat < nul
call reobfuscate_srg.bat < nul
echo Compile complete.
cd ..\..\projects\novamenu
xcopy resources ..\..\forge\mcp\reobf\minecraft /E /Q
echo Copied Novamenu resources.
cd ..\..\forge\mcp
rmdir /S /Q src
xcopy src-backup src /E /I /Q
rmdir /S /Q src-backup
cd reobf\minecraft
jar cmf META-INF\MANIFEST.MF Novamenu1.1.3.jar com assets mcmod.info remap.csv
echo -------- Build complete. Outputted to mcp\reobf\minecraft\Novamenu1.1.3.jar --------
pause