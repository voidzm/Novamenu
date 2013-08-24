@echo off
echo ---------------- Building ----------------
echo ------------ Novamenu 1.1.2 --------------
xcopy ..\..\forge\mcp\src ..\..\forge\mcp\src-backup /E /I /Q
echo Finished backing up forge sources.
xcopy source ..\..\forge\mcp\src\minecraft /E /Q
echo Finished copying Novamenu sources to build path.
cd ..\..\forge\mcp
call recompile.bat
call reobfuscate_srg.bat
echo Source compiled and obfuscated.
cd ..\..\projects\novamenu
xcopy resources ..\..\forge\mcp\reobf\minecraft /E /Q
echo Added resources and assets to artifact.
cd ..\..\forge\mcp
rmdir /S /Q src
xcopy src-backup src /E /I /Q
rmdir /S /Q src-backup
cd reobf\minecraft
jar cmf META-INF\MANIFEST.MF Novamenu1.1.2.jar com assets mcmod.info remap.csv
echo ----- Build complete! Artifact is located at forge\mcp\reobf\minecraft\Novamenu1.1.2.jar -----
pause