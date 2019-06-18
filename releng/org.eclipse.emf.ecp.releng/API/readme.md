# Setup Ant

- Add `apitooling-ant.jar` to classpath
- Add `org.eclipse.core.runtime_3.10.0.v20140318-2214.jar` to classpath

Maybe it is possible to use the jar from the eclipse runtime. Maybe convert this project to a plugin project?

# Generate API/SPI Changes page

1. Download current official release
2. Download latest build (usually RC1)
3. Set path of `baseline` to the path of the release (step 1), the path must point to an eclipse update site structure
4. Set path of `profile` to the path of the latest build (step 2), the path must point to an eclipse update site structure
5. Execute `generateXml` of the ant script
6. Apply regex expression from the `regex.txt` to the xml
7. Execute `convertToHTML` of the ant script
8. Fix title it the HTML to `Changes Since ECP <latestRelease>`
9. Add to website and rename to fit the naming scheme
