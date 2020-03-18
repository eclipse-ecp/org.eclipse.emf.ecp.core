#!python
import os
import sys

SOURCE_POSTFIX = '.source'

def get_missing_source_bundles(root_dir = '.', exceptions = []):
  missing_source = []
  source_jars = []
  jars = []
  for root, dirs, files in os.walk(root_dir):
    for file in files:
      # get rid of the version identifier and file extension. only the bundle name is relevant
      stripped_name = file.split('_')[0]
      if stripped_name.endswith(SOURCE_POSTFIX):
        source_jars.append(stripped_name[:len(stripped_name) - len(SOURCE_POSTFIX)])
      else:
        jars.append(stripped_name)

  for jar in jars:
    if (jar not in source_jars) and (jar not in exceptions):
      missing_source.append(jar)

  return missing_source

# Assumes that the scripts workding dir is the repository bundle's project base dir.
# arguments
# argv[0] - path to exceptions
def main(argv):
  print("Verify source bundles have been exported...")
  with open(argv[0], mode="rt") as exceptions_file:
    exceptions = [ex.strip() for ex in exceptions_file if ex.strip() and not ex.strip().startswith('#')]

  plugins_missing_src = get_missing_source_bundles('./target/repository/plugins', exceptions)
  features_missing_src = get_missing_source_bundles('./target/repository/features', exceptions)

  if plugins_missing_src:
    print('----------------------------------')
    print('Plugins with missing source jars:')
    print('----------------------------------')
    plugins_missing_src.sort()
    for b in plugins_missing_src:
      print(b)
    print('----------------------------------')

  if features_missing_src:
    print('----------------------------------')
    print('Features with missing source jars:')
    print('----------------------------------')
    features_missing_src.sort()
    for b in features_missing_src:
      print(b)
    print('----------------------------------')

  if plugins_missing_src or features_missing_src:
    print("There are missing source bundles! See output above.")
    sys.exit(2)
  print("...verification successful.")

if __name__ == "__main__":
  main(sys.argv[1:])